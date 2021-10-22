package com.br.italo.usuarioService.aop;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

public ResourceNotFoundException(String ids) {
	super(String.format("Registro ref.: {%s}, n\u00E3o encontrado", ids));
}
}
