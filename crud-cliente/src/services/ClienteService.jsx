import axios from 'axios'
import * as auth from '../utils/Auth'

const CLIENTE_API_BASE_URL = "http://localhost:8080/api/v1/clientes"
const JSON_REQUEST = {
    headers: {
      'Content-Type': 'application/json'
    }
  }
const AUTH_HEADERS = {
    headers : {
        "Authorization": null
    }
}
class ClienteService {

    getClientes(){
        AUTH_HEADERS.headers.Authorization = auth.getTokenFromCache();
        return axios.get(CLIENTE_API_BASE_URL,AUTH_HEADERS);
    }

    addCliente(cliente) {
        AUTH_HEADERS.headers.Authorization = auth.getTokenFromCache();
        const headers = {...JSON_REQUEST.headers,...AUTH_HEADERS.headers}
        return axios.post(CLIENTE_API_BASE_URL,cliente,{headers})
    }

    delCliente(id){
        AUTH_HEADERS.headers.Authorization = auth.getTokenFromCache();
        return axios.delete(`${CLIENTE_API_BASE_URL}/${id}`,AUTH_HEADERS);
    }

    getClienteById(id){
        AUTH_HEADERS.headers.Authorization = auth.getTokenFromCache();
        return axios.get(`${CLIENTE_API_BASE_URL}/${id}`, AUTH_HEADERS);
    }
}

export default new ClienteService();