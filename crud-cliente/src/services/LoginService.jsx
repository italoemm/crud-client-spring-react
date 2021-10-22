import axios from 'axios';

const LOGIN_API_REQUEST = "http://localhost:8080/api/v1/user"
const JSON_REQUEST = {
    headers: {
      'Content-Type': 'application/json'
    }
  }

class LoginService {
    
    autentica(login){
        return axios.post(`${LOGIN_API_REQUEST}/autentica`,login,JSON_REQUEST)
    }
}

export default new LoginService();