import axios from 'axios';
import { jwtDecode } from "jwt-decode";

const API_URL = 'http://localhost:8080/api/auth/v1/';

class AuthService {
  async login(user) {
    return await axios
      .post(API_URL + 'login', {
        username: user.email,
        password: user.password
      })
      .then(response => {
        if (response.data.data) {
          const jwt = JSON.stringify(response.data.data.jwt).replace(/\"/g, "");
          const id = jwtDecode(jwt).id;
          localStorage.setItem('user', jwt);
          localStorage.setItem('id', id);
        }
        return response.data;
      })
      .catch(error => {
        console.log(error);
      });
  }

  logout() {
    localStorage.removeItem('user');
  }
  register(user) {
    return axios.post(API_URL + 'register', {
      fullName: user.fullName,
      email: user.email,
      password: user.password
    })
  }

  verifyRegister(user) {
    return axios.post(API_URL + 'verify-register', {
      email: user.email,
      otp: user.otp,
    })
  }

}

export default new AuthService();

