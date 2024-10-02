// src/api/api.js

import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

// Function to set basic authorization header
export const setBasicAuthHeader = () => {
  const basicAuthHeader = `Basic ${btoa(`admin:admin`)}`;
  api.defaults.headers.common['Authorization'] = basicAuthHeader;
};

export default api;
