import axios from 'axios';
import React, { useEffect } from 'react';

const Home = () => {
  useEffect(() => {
    const fetchData = async () => {
      const token = localStorage.getItem('token'); // Retrieve token from localStorage

      if (token) {
        try {
          console.log(token);
        } catch (error) {
          console.log(error);
        }
      } else {
        console.log('No token found, please log in.');
      }
    };

    fetchData();
  }, []);

  return <div>Home Page - Protected Content</div>;
};

export default Home;
