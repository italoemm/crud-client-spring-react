package com.br.italo.usuarioService.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.websocket.ClientEndpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.italo.usuarioService.aop.ResourceNotFoundException;
import com.br.italo.usuarioService.domain.ResponseSuccessWrapper;
import com.br.italo.usuarioService.dto.ClienteDTO;
import com.br.italo.usuarioService.enums.TipoTelefone;
import com.br.italo.usuarioService.model.ClienteEntity;
import com.br.italo.usuarioService.model.EmailEntity;
import com.br.italo.usuarioService.model.EnderecoEntity;
import com.br.italo.usuarioService.model.TelefoneEntity;
import com.br.italo.usuarioService.repository.ClienteRepository;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class ClienteController {
	
	@Autowired
	private ClienteRepository clienteRepostiroy;
	
	@GetMapping("/clientes")
	@ResponseBody
	public ResponseEntity<?> clientes (       
			@RequestParam(required = false) String title,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size) {
	
		Page<ClienteEntity> pageCliente =  clienteRepostiroy.findAll(PageRequest.of(page, size));
		ResponseSuccessWrapper response =  new ResponseSuccessWrapper(HttpStatus.OK, pageCliente);
		
	return new ResponseEntity<>(response,HttpStatus.OK);
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/clientes")
	@ResponseBody
	public ResponseEntity<?> salvaClientes (@Valid @RequestBody ClienteDTO clienteDTO ) {
	
		Set<EmailEntity> emails = clienteDTO.getEmails().stream().map(e -> new EmailEntity(e.getId(), e.getEmail(), e.getCreated_datetime(), e.getUpdated_datetime())).collect(Collectors.toSet());
		Set<TelefoneEntity> telefones =  clienteDTO.getTelefones().stream().map(t -> new TelefoneEntity(t.getId(), TipoTelefone.valueOf(t.getTipoTelefone()), t.getNumero())).collect(Collectors.toSet());
		EnderecoEntity endereco =  new EnderecoEntity(clienteDTO.getCidade(), clienteDTO.getUf(),clienteDTO.getBairro(), clienteDTO.getLogradouro(), clienteDTO.getCep());
		ClienteEntity cliente =  new ClienteEntity(clienteDTO.getId(), clienteDTO.getNome(), emails, telefones, clienteDTO.getCreated_datetime(), null, endereco);
		
		ResponseSuccessWrapper response =  new ResponseSuccessWrapper(HttpStatus.CREATED, clienteRepostiroy.saveAndFlush(cliente));
		
	return new ResponseEntity<>(response,HttpStatus.CREATED);
	}

	@PutMapping("/clientes/{id}")
	@ResponseBody
	public ResponseEntity<?> updateClientes (@Valid @Min(value = 1, message = "Id N\u00E3o deve ser nulo") @PathVariable ("id") Long id
											 ,@RequestBody ClienteDTO clienteDTO ) {
	
		clienteRepostiroy.findById(id).orElseThrow(() -> new ResourceNotFoundException(id.toString()));
		
		Set<EmailEntity> emails = clienteDTO.getEmails().stream().map(e -> new EmailEntity(e.getId(), e.getEmail(), e.getCreated_datetime(), e.getUpdated_datetime())).collect(Collectors.toSet());
		Set<TelefoneEntity> telefones =  clienteDTO.getTelefones().stream().map(t -> new TelefoneEntity(t.getId(), TipoTelefone.valueOf(t.getTipoTelefone()), t.getNumero())).collect(Collectors.toSet());
		EnderecoEntity endereco =  new EnderecoEntity(clienteDTO.getCidade(), clienteDTO.getUf(),clienteDTO.getBairro(), clienteDTO.getLogradouro(), clienteDTO.getCep());
		ClienteEntity cliente =  new ClienteEntity(clienteDTO.getId(), clienteDTO.getNome(), emails, telefones, clienteDTO.getCreated_datetime(), null, endereco);
		
		ResponseSuccessWrapper response =  new ResponseSuccessWrapper(HttpStatus.OK, clienteRepostiroy.saveAndFlush(cliente));
		
	return new ResponseEntity<>(response,HttpStatus.OK);
	}
	@GetMapping("/clientes/{id}")
	public ResponseEntity<?> buscarPorId (@Valid @Min(value = 1, message = "Id N\u00E3o deve ser nulo")  @PathVariable("id") Long id ) {
		
		ResponseSuccessWrapper response =  new ResponseSuccessWrapper(HttpStatus.OK, clienteRepostiroy.
					findById(id)
					.orElseThrow(() -> new ResourceNotFoundException(id.toString())));
		
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/clientes/{id}")
	public ResponseEntity<?> deletePorId (@Valid 
			  @Min(value = 1, message = "Id N\u00E3o deve ser nulo") 
			  @PathVariable("id") Long id ) {

		ClienteEntity cliente =  clienteRepostiroy.findById(id)
						.orElseThrow(() -> new ResourceNotFoundException(id.toString()));
		
		clienteRepostiroy.deleteById(cliente.getId());
		return new ResponseEntity<>(HttpStatus.OK);
		}

	
}
