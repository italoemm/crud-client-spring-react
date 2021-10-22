import React, { Component } from 'react';
import ClienteService from '../services/ClienteService';
import EnderecoService from '../services/EnderecoService';
import * as auth from '../utils/Auth'
import IMask from 'imask';


class AddClienteComponent extends Component {
    constructor(props){
        super(props)
        this.state =  {
                id: parseInt(this.props.match.params.id) || 0,
                nome:"",
                emails:[],
                telefones:[],
                cidade:"",
                uf:"",
                bairro:"",
                logradouro:"",
                cep:"",
                complemento:""
    }
    this.popUp = this.popUp.bind(this)

    this.setMaskTelefone = this.setMaskTelefone.bind(this)
    this.setMaskCep = this.setMaskCep.bind(this)

    this.changeInputHandler = this.changeInputHandler.bind(this)
    this.changeTipoTelefoneHandler = this.changeTipoTelefoneHandler.bind(this)
    this.changeCepHandler = this.changeCepHandler.bind(this)

    this.clickConfirmEmailHander = this.clickConfirmEmailHander.bind(this)
    this.clickRemoveEmailHandler = this.clickRemoveEmailHandler.bind(this)

    this.clickRemoveTelefoneHandler = this.clickRemoveTelefoneHandler.bind(this)
    this.clickConfirmTelefoneHandler = this.clickConfirmTelefoneHandler.bind(this)
    this.submitHandler = this.submitHandler.bind(this)
    

}
componentDidMount(){
    IMask(document.getElementById("telefone"), this.getIMaskTelefoneConfiguration())
    IMask(document.getElementById("cep"), this.getIMaskCepConfiguration());

    if(this.state.id === 0){
        return
    }else{
        ClienteService.getClienteById(this.state.id).then( (res) =>{
            const {endereco, ...cliente} = res.data.dados;
            this.setState({...cliente,...endereco
            });
        });
    }       
}
/* GETTERS / SETTERS */
getIMaskCepConfiguration() {
    return ({mask: "00000-000"})
}

getIMaskTelefoneConfiguration(tipoTelefone){
    tipoTelefone = !tipoTelefone ? document.getElementById("tipoTelefone").value : tipoTelefone
    const telefone = document.getElementById("telefone")
    return ({
        "RESIDENCIAL": { mask: '(00) 0000-0000'},
        "CELULAR": { mask: '(00) 0.0000-0000'},
        "COMERCIAL": { mask: '(00) 0000-0000'}
      })[tipoTelefone]

}

setMaskTelefone(tel,tipoTelefone) {
    const masked = IMask.createMask(this.getIMaskTelefoneConfiguration(tipoTelefone));
    return masked.resolve(`${tel}`)
}

setMaskCep(tel) {
    const masked = IMask.createMask(this.getIMaskCepConfiguration());
    return masked.resolve(`${tel}`)
}

/* VALIDACOES */
validaTelefone(obj) {
    if(!obj.numero || ( (obj.numero.length != 10 && (obj.tipoTelefone === 'RESIDENCIAL' || obj.tipoTelefone === 'COMERCIAL'))
                             || (obj.numero.length  != 11  && obj.tipoTelefone === 'CELULAR')) )                 
        return 'Numero Invalido'
    if(this.state.telefones.find(t => t.numero === obj.numero))
        return 'Telefone já inserido'
  return null  
}

validaEmail (obj) {
    if(this.state.emails.find(e => e.email === obj.email))
        return 'E-mail já inserido'
    if(!obj.email || !obj.email.match(/^\w+([-+.']\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/))
        return 'E-mail Invalido'
return null
}
/* EVENTOS */
changeInputHandler (event) {
    this.setState({[event.target.name]: event.target.value});
}

changeTipoTelefoneHandler(event){
    const telefone = document.getElementById("telefone")    
    const nTelefone = telefone.cloneNode(true)
    nTelefone.value = ""

    telefone.parentNode.replaceChild(nTelefone, telefone);
    IMask(nTelefone,this.getIMaskTelefoneConfiguration())
   
}
changeCepHandler(event) {
    const cep = IMask(event.target,this.getIMaskCepConfiguration()).unmaskedValue
    this.setState({cep:event.target.value})
    if(cep.length < 8) return

    EnderecoService.getEnderecoByCep(cep).then((res) => {
        const {cep,logradouro,bairro,localidade,uf} = res.data
        this.setState( { uf, cidade: localidade,bairro, logradouro, cep })
    })
}
clickRemoveEmailHandler (email){
    this.setState({emails: this.state.emails.filter(e => e.email != email)})
}
clickConfirmEmailHander (event) {
    const inputEmail =  document.getElementById("email")
    const obj = {
        id: 0,
        email : inputEmail.value
    }
    const IsNotvalidaEmail = this.validaEmail(obj)
    if(IsNotvalidaEmail){
        alert(IsNotvalidaEmail)
        return
    }
    this.setState({emails: [...this.state.emails, obj]});
    inputEmail.value ="";
    alert("Inserido na Lista!")
}

clickConfirmTelefoneHandler (event) {
    const inputTelefone =  document.getElementById("telefone")
    const selectTipoTelefone =  document.getElementById("tipoTelefone")
    const obj = {
        id: 0,
        numero : IMask(inputTelefone, this.getIMaskTelefoneConfiguration()).unmaskedValue,
        tipoTelefone : selectTipoTelefone.value
    }
    const IsNotvalidaTelefone = this.validaTelefone(obj)
    if(IsNotvalidaTelefone){
        alert(IsNotvalidaTelefone)
        return
    }
    this.setState({telefones: [...this.state.telefones, obj]});
    inputTelefone.value =""
    alert("Inserido na Lista!")
}

clickRemoveTelefoneHandler (numero){
    this.setState({telefones: this.state.telefones.filter(t => t.numero != numero)})
}

submitHandler(event){
event.preventDefault();
const cliente = this.state
const masked  = IMask.createMask(this.getIMaskCepConfiguration())
masked.value = cliente.cep;
cliente.cep = masked.unmaskedValue
    
ClienteService.addCliente(JSON.stringify(cliente)).then(res =>{
        alert("Sucesso")
        this.props.history.push(`/cliente/`)
    })
    .catch(res => {
        if(res.response.status === 403){
            alert("Sessao Expirada")
            auth.clearSessao()
            this.props.history.push(`/`)
        return
        }
        alert(Object.values(res.response.data.error).map(e => JSON.stringify(e)).join("\n"))
    })
}
/** TOOLS */
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
            <br></br>
            <h2 className="text-center mt-3">{`${this.state.id > 0 ? 'Editar' : 'Adicionar'} Cliente`}</h2>
               <div className = "">
               <form>
                    <div className = "row">
                        <div className = "card w-100">
                            <div className = "card-body">
                            <h5 class="card-title">Informações Pessoais:</h5>
                                    <div className="form-row mt-4">
                                        <div className = "form-group col-12 col-sm-6 col-md-4">
                                            <label> Nome: </label>
                                            <input placeholder="Nome" name="nome" className="form-control" 
                                                value={this.state.nome} onChange={this.changeInputHandler}/>
                                        </div>
                                        <div className = "form-group col-12 col-sm-5 col-md-3">
                                            <div className="row align-items-end">
                                                <div className="col-10 p-0 pl-3">
                                                    <label> E-mail: </label>
                                                    <input placeholder="E-mail" name="email" className="form-control" id="email"/>
                                                </div>
                                                <div className="col-1 p-0 pl-2">
                                                    <div className="d-flex flex-column  align-items-end">
                                                        <a href="javascript: void(0)" ><i className="fa fa-check-square text-success" style={{fontSize: 1.2+'rem'}} onClick={this.clickConfirmEmailHander}></i></a>
                                                        <div className="position-relative bg-white" >
                                                        
                                                            <a href="javascript: void(0)" 
                                                            onClick={this.popUp}
                                                            ><i className="fa fa-pencil-square text-secondary" style={{fontSize: 1.2+'rem'}}></i></a>

                                                            <div class="card position-absolute d-none " style={{width: 20+'rem', left: -250+'px', zIndex: 1}}>
                                                                <div class="card-header">
                                                                    E-mails
                                                                </div>
                                                                <div class="card-body">
                                                                    { this.state.emails.map(e => 
                                                                            <div className="d-flex flex-row justify-content-between" key={e.id}>
                                                                                <div class="card-text">
                                                                                    {e.email}
                                                                                    </div>
                                                                                <div>
                                                                                 <a href="javascript: void(0)" onClick={() => this.clickRemoveEmailHandler(e.email)} > <i className="fa fa-minus-square text-danger" aria-hidden="true"></i></a>
                                                                                </div>
                                                                            </div>)
                                                                    }
                                                                </div>
                                                            </div>
                                                    </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div className = "form-group col-12 col-sm-4 col-md-2">
                                            <label> Tipo Telefone: </label>
                                            <select className = "form-control" id="tipoTelefone" name="tipoTelefone" onChange={this.changeTipoTelefoneHandler}>
                                                <option value="CELULAR">Celular</option>
                                                <option value="RESIDENCIAL">Residencial</option>
                                                <option value="COMERCIAL">Comercial</option>
                                            </select>
                                        </div>
                                        <div className = "form-group col-12 col-sm-5 col-md-3">
                                        <div className="row align-items-end">
                                                <div className="col-10 p-0 pl-3">
                                                    <label> Telefone: </label>
                                                    <input placeholder="Telefone" name="telefone" className="form-control" id="telefone"/>
                                                </div>
                                                <div className="col-1 p-0 pl-2">
                                                    <div className="d-flex flex-column  align-items-end">
                                                        <a href="javascript: void(0)" ><i className="fa fa-check-square text-success" style={{fontSize: 1.2+'rem'}} onClick={this.clickConfirmTelefoneHandler}></i></a>
                                                        <div className="position-relative bg-white" >
                                                        
                                                            <a href="javascript: void(0)" 
                                                            onClick={this.popUp}
                                                            ><i className="fa fa-pencil-square text-secondary" style={{fontSize: 1.2+'rem'}}></i></a>

                                                            <div class="card position-absolute d-none " style={{width: 20+'rem', left: -250+'px', zIndex: 1}}>
                                                                <div class="card-header">
                                                                    Telefones
                                                                </div>
                                                                <div class="card-body">
                                                                    { this.state.telefones.map(t => 
                                                                            <div className="d-flex flex-row justify-content-between" key={t.id}>
                                                                                <div class="card-text">
                                                                                    <span >{t.tipoTelefone}: </span>
                                                                                    {this.setMaskTelefone(t.numero, t.tipoTelefone)}
                                                                                    </div>
                                                                                <div>
                                                                                 <a href="javascript: void(0)" onClick={() => this.clickRemoveTelefoneHandler(t.numero)}> <i className="fa fa-minus-square text-danger" aria-hidden="true"></i></a>
                                                                                </div>
                                                                            </div>)
                                                                    }
                                                                </div>
                                                            </div>
                                                    </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    
                            </div>
                        </div>
                    </div>
                    <div className = "row mt-1">
                        <div className = "card w-100">
                            <div className = "card-body">
                            <h5 class="card-title">Endereço:</h5>
                                    <div className="form-row mt-4">
                                        <div className = "form-group col-12 col-sm-4 col-md-2">
                                            <label> Cep: </label>
                                            <input placeholder="Cep" name="cep" className="form-control" id="cep" value={this.state.cep} onChange={this.changeCepHandler} 
                                            />
                                        </div>
                                        
                                        <div className = "form-group col-12 col-sm-4 col-md-2">
                                            <label> Cidade: </label>
                                            <input placeholder="Cidade" name="cidade" className="form-control" 
                                                value={this.state.cidade} onChange={this.changeInputHandler}/>
                                        </div>
                                        <div className = "form-group col-12 col-sm-2 col-md-1">
                                            <label> UF: </label>
                                            <input placeholder="UF" name="uf" className="form-control" 
                                                value={this.state.uf} onChange={this.changeInputHandler}/>
                                        </div>
                                        <div className = "form-group col-12 col-sm-4 col-md-2">
                                            <label> Bairro: </label>
                                            <input placeholder="Bairro" name="bairro" className="form-control" 
                                                value={this.state.bairro} onChange={this.changeInputHandler}/>
                                        </div>
                                        <div className = "form-group col-12 col-sm-5 col-md-5">
                                            <label> Logradouro: </label>
                                            <input placeholder="Logradouro" name="logradouro" className="form-control" 
                                                value={this.state.logradouro} onChange={this.changeInputHandler}/>
                                        </div>
                                        <div className = "form-group col-12 col-sm-6 col-md-4">
                                            <label> Complemento: </label>
                                            <input placeholder="Complemento" name="complemento" className="form-control" 
                                                value={this.state.complemento} onChange={this.changeInputHandler}/>
                                        </div>
                                    </div>
                                    
                            </div>
                        </div>
                    </div>
                    <div className="d-flex flex-row justify-content-between align-items-center p-4 mt-4"> 
                        <button type="button" class="btn btn-secondary" onClick={() => this.props.history.push(`/cliente`)}>Voltar</button>
                        <button type="button" class="btn btn-success" onClick={this.submitHandler}>Salvar</button>
                    </div>
                 </form>

               </div>
        </div>
        );
    }


}
export default AddClienteComponent;