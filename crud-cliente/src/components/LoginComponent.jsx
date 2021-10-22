import React, { Component } from 'react';
import LoginService from '../services/LoginService';
class LoginComponent extends Component {

    constructor(props) {
        super(props)

        this.state ={

        }

        this.submitHandler = this.submitHandler.bind(this)
        this.changeInputHandler = this.changeInputHandler.bind(this)
    }

    submitHandler () {
        const login = this.state;
        const carregando = document.getElementById("carregando")
        carregando.classList.remove("d-none")
        LoginService.autentica(JSON.stringify(login)).then(resp => {
            this.props.history.push({
                pathname: '/cliente',
                state: { usuario: resp.data.dados }
              })
            localStorage.setItem('token',resp.data.dados.token)
            localStorage.setItem('roles',JSON.stringify(resp.data.dados.usuario.authorities))
            this.props.history.push(`/cliente`)
        }).catch((resp) =>{
            alert("Credenciais invalidas")
            carregando.classList.add("d-none")
        })
    }

    changeInputHandler (event) {
            this.setState({[event.target.name] : event.target.value})
    }
    render() {
        return (
            <div class="container">
            <div className="row">
                <div className="col-md-4 offset-md-4 offset-md-4">
                    <div class="login-form border rounded p-4 mt-5">
                        <form>
                            <h2 class="text-center">Login</h2>
                            <div class="form-group">
                                <input type="text" class="form-control" placeholder="Usuario" required="required" name="login" onChange={this.changeInputHandler}/>
                            </div>
                            <div class="form-group">
                                <input type="password" class="form-control" placeholder="Senha" required="required" name="senha" onChange={this.changeInputHandler}/>
                            </div>
                            <div class="form-group">
                                <button type="button" class="btn btn-primary btn-block" onClick={this.submitHandler}>Entrar</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <div className="row mt-2">
                <div className="col-md-2 offset-md-5 offset-md-5 ">            
                <div class="spinner-border text-primary d-none" role="status" id="carregando">
                    <span class="sr-only">Carregando...</span>
                    </div>
                </div>
            </div>
        </div>
        );
    }
}

export default LoginComponent;