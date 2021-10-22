import logo from './logo.svg';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'font-awesome/css/font-awesome.min.css';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import { isLoggedIn } from './utils/Auth';

import FooterComponent from './components/FooterComponent';
import HeaderComponent from './components/HeaderComponent';
import ListaClienteComponent from './components/ListaClienteComponent';
import AddClienteComponent from './components/AddClienteComponent';
import LoginComponent from './components/LoginComponent';
import { Redirect } from 'react-router';
function App() {
  return (
    <div>
        <Router>
           <Switch> 
                <Route path = "/" exact component = {LoginComponent}></Route>
                <div>
                  <HeaderComponent />
                    <div className="container">
                    <Switch> 
                      <Route path = "/cliente/"  exact render={() =>(
                          isLoggedIn() ? ( <Route  component={ListaClienteComponent} />)
                          : (<Redirect to="/" />)
                        )} />
                        <Route path = "/cliente/add/:id"  render={() =>(
                          isLoggedIn() ? ( <Route  component={AddClienteComponent} />)
                          : (<Redirect to="/" />)
                        )} />
                      </Switch>
                    </div>
                  <FooterComponent />
                </div>
              </Switch>
        </Router>
    </div>
  );
}

export default App;
