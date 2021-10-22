import React, { Component } from 'react'

class FooterComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
                 
        }
    }

    render() {
        return (
            <div>
                <footer className = "footer d-flex flex-row justify-content-center align-items-center">
                    <span className="text-white">Teste Front-END direitos reservados @italo 2021</span>
                </footer>
            </div>
        )
    }
}

export default FooterComponent