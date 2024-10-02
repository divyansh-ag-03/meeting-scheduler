// src/components/Login.jsx
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api, { setBasicAuthHeader } from '../api/api';
import '../styles/Login.css';

const Login = () => {
  const [email, setEmail] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleVerification = async () => {
    try {
      setBasicAuthHeader();
      const response = await api.get(`/users/verify?email=${email}`);
      navigate('/choose-option', { state: { email } });
    } catch (error) {
      setError('Error verifying user. Please try again.');
    }
  };

  const handleKeyPress = (event) => {
    if (event.key === 'Enter') {
      handleVerification();
    }
  };

  return (
      <div className="container login-container">
        <h1>DTDL Meeting Scheduler</h1>
        <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            onKeyDown={handleKeyPress}
            placeholder="Enter your email"
            className="login-input"
        />
        <button onClick={handleVerification} className="login-button">Verify Email</button>
        {error && <p className="error-message">{error}</p>}
      </div>
  );
};

export default Login;
