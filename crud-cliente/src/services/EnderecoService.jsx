import axios from 'axios'


const VIACEP_API_BASE_URL = "https://viacep.com.br/ws/"

class EnderecoService {

    getEnderecoByCep(cep){
       return axios.get(`${VIACEP_API_BASE_URL}/${cep}/json/`);
    }
}

export default new EnderecoService();