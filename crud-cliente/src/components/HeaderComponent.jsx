import React, { Component } from 'react'
import { withRouter } from 'react-router'
import * as auth from '../utils/Auth'
class HeaderComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
                 
        }
    this.clickLogoutHandler = this.clickLogoutHandler.bind(this)
    }
clickLogoutHandler () {
    auth.clearSessao();
    this.props.history.push("/")

}
    render() {
        return (
            <div>
                <header>
                    <nav className="navbar navbar-expand-md navbar-dark bg-dark">
                        <div><a href="javascript: void(0)" className="navbar-brand">Gerenciamento de Clientes</a></div>
                        <a href="javsacript: void(0)" className="ml-auto" onClick={this.clickLogoutHandler} title="Log out"><i class="fa fa-sign-out text-white" aria-hidden="true" style={{fontSize: 1.4+"rem"}}></i></a>
                    </nav>
                </header>
            </div>
        )
    }
}

export default withRouter(HeaderComponent)