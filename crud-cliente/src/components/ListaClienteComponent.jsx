import React, { Component } from 'react';
import IMask from 'imask';
import ClienteService from '../services/ClienteService';
import { Dropdown } from 'react-bootstrap';
import * as auth from '../utils/Auth'

class ListaClienteComponent extends Component {
    constructor(props) {
        super(props)
        this.state = {
                clientes : []
        }
        this.addCliente = this.addCliente.bind(this)
        this.editarCliente = this.editarCliente.bind(this)
        this.excluirCliente = this.excluirCliente.bind(this)

        this.setMaskTelefone = this.setMaskTelefone.bind(this)
        this.setMaskCep = this.setMaskCep.bind(this)
        this.popUp = this.popUp.bind(this)
    }

    componentDidMount(){
       //console.log(this.props.location.state.usuario)
        ClienteService.getClientes().then((res) => {
            this.setState({ clientes: res.data.dados});
        }).catch(res => {
            if(res.response.status === 403){
                alert("Sessao Expirada")
                auth.clearSessao()
                this.props.history.push(`/`)
            }
        });
    }
    excluirCliente(id){
       ClienteService.delCliente(id).then((res) => {
           alert("Sucesso")
           this.setState({clientes: this.state.clientes.filter(c=> c.id !== id)})
        }).catch(res => {
            if(res.response.status === 403){
                alert("Sessao Expirada")
                auth.clearSessao()
                this.props.history.push(`/`)
            }
            alert("Falha")
        })
    }
    addCliente(){
        this.props.history.push(`/cliente/add/0`)
    }

    editarCliente(id){
        this.props.history.push(`/cliente/add/${id}`)
    }

    setMaskTelefone(tel) {
        var masked = IMask.createMask({
            mask: '(00) 0.0000-0000',
          });
    return masked.resolve(`${tel}`)
    }

    setMaskCep(tel) {
        var masked = IMask.createMask({
            mask: '00000-000',
          });
    return masked.resolve(`${tel}`)
    }

    popUp(event) {
        const el = event.target
        const tooltip = el.closest("div").querySelector(".card")

        if(tooltip.classList.contains("show")) {
            tooltip.classList.add("d-none")
            tooltip.classList.remove("show")
        }else {
            tooltip.classList.add("show")
            tooltip.classList.remove("d-none")
        }
    }
    render() {
        return (
            <div>
                  <h2 className="text-center mt-3">Lista Clientes</h2>
                 <div className = "row">
                     {
                         auth.getRolesFromCache().find(r => r.nome === 'ROLE_ADMIN') ?
                         <button className="btn btn-primary" onClick={this.addCliente}> Add Cliente</button>
                         :''
                     }
                </div>
                 <br></br>
                 <div className = "row">
                            <table className = "table table-striped table-bordered">
                                <thead>
                                    <tr>
                                        <th> Nome </th>
                                        <th> Cidade </th>
                                        <th> Bairro </th>
                                        <th> Logradouro</th>
                                        <th> UF </th>
                                        <th> CEP </th>
                                        <th> Complemento </th>
                                        <th> Detalhes</th>
                                        {
                                            auth.getRolesFromCache().find(r => r.nome === 'ROLE_ADMIN') ?
                                            <th> Ações</th>:''
                                        }
                                    </tr>
                                </thead>
                                <tbody>
                                    {
                                        this.state.clientes.map(
                                            c =>
                                            <tr key = {c.id}>
                                                 <td> {c.nome} </td>
                                                 <td> {c.endereco.cidade}</td>
                                                 <td> {c.endereco.bairro}</td>
                                                 <td> {c.endereco.logradouro}</td>
                                                 <td> {c.endereco.uf}</td>
                                                 <td> {this.setMaskCep(c.endereco.cep)}</td>
                                                 <td> {c.endereco.complemento == null ? ' - ' : c.endereco.complemento.length}</td>
                                                 <td >
                                                    <div className="d-flex flex-row justify-content-around">
                                                        <div className="position-relative bg-white">
                            
                                                            <a href="javascript: void(0)" title="Telefones"
                                                            onMouseOver={this.popUp}
                                                            onMouseOut={this.popUp}
                                                            ><i class="fa fa-phone-square text-secondary" style={{fontSize: 1.2+'rem'}}></i></a>
                                                            <div class="card position-absolute d-none " style={{width: 20+'rem', left: -250+'px', zIndex: 999}}>
                                                                <div class="card-header">
                                                                    Telefones
                                                                </div>
                                                                <div class="card-body">
                                                                    { c.telefones.map(t =>
                                                                            <p class="card-text">
                                                                            <span >{t.tipoTelefone}: </span>
                                                                            {this.setMaskTelefone(t.numero)}
                                                                            </p>)
                                                                    }
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div className="position-relative bg-white">
                            
                                                            <a href="javascript: void(0)" title="E-mails"
                                                            onMouseOver={this.popUp}
                                                            onMouseOut={this.popUp}
                                                            ><i class="fa fa-envelope text-secondary" style={{fontSize: 1.2+'rem'}}></i></a>
                                                            <div class="card position-absolute d-none " style={{width: 20+'rem', left: -250+'px',zIndex: 1}}>
                                                                <div class="card-header">
                                                                    E-mails
                                                                </div>
                                                                <div class="card-body">
                                                                    { c.emails.map(e =>
                                                                            <p class="card-text">
                                                                            <span >E-mail: </span>
                                                                            {e.email}
                                                                            </p>)
                                                                    }
                                                                </div>
                                                            </div>
                                                        </div>
                                                     </div>
                                                     </td>
                                                     {
                                                        auth.getRolesFromCache().find(r => r.nome === 'ROLE_ADMIN') ?
                                                        <td>
                                                         <div className="d-flex flex-column justify-content-center align-items-center">
                                                            <Dropdown>
                                                            <Dropdown.Toggle variant="none" id="dropdown-basic" className="btn-sm p-1">
                                                                <i class="fa fa-bars text-secondary" style={{fontSize: 1.3+'rem'}}></i>
                                                            </Dropdown.Toggle>
                                                            <Dropdown.Menu>
                                                                <Dropdown.Item href="javascript:void(0)" onClick={() => this.editarCliente(c.id)}>Editar</Dropdown.Item>
                                                                <Dropdown.Item href="javascript:void(0)" onClick={() => this.excluirCliente(c.id)}>Excluir</Dropdown.Item>
                                                            </Dropdown.Menu>
                                                            </Dropdown>
                                                            </div>
                                                        </td>
                                                        :''
                                                    }
                                                     
                                            </tr>
                                        )
                                    }
                                </tbody>
                            </table>
                        </div>

                 </div>
                
        );
    }
}

export default ListaClienteComponent;