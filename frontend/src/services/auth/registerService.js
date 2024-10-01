// authService.js
import axios from 'axios';

const registerService = async (formData) => {
  try {
      const response = await axios.post('http://localhost:8088/api/v1/auth/register', formData); // Ensure correct URL
      localStorage.setItem('token' ,response.data);
  } catch (error) {
    console.error('Error registering:', error);
    throw error;
  }
}

export default registerService;
