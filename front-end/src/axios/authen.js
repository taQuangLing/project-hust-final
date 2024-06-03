import axios from 'axios';

const API_URL = 'http://localhost:8080/api/auth/v1/';

class AuthService {
  login(user) {
    return axios
      .post(API_URL + 'login', {
        username: user.email,
        password: user.password
      })
      .then(response => {
        if (response.data.data) {
          localStorage.setItem('user', JSON.stringify(response.data.data.jwt).replace(/\"/g, ""));
        }
        return response.data;
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
