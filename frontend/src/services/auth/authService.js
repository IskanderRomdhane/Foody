// authService.js

import axios from 'axios';

const loginUser = async (email, password) => {
  try {
    const response = await axios.post('http://localhost:8088/api/v1/auth/authenticate', {
      email,
      password,
    });
    const data = response.data;
    localStorage.setItem('token', data.token);
    return data;
  } catch (error) {
    console.error('Error logging in:', error);
    throw error;
  }
}

export default loginUser;
