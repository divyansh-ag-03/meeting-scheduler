// src/components/ChooseOption.jsx
import React from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import '../styles/ChooseOption.css';

const ChooseOption = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const email = location.state.email;

  const handleOptionClick = (option) => {
    switch (option) {
      case 'book':
        navigate('/book-room', { state: { email } });
        break;
      case 'showBookings':
        navigate('/show-bookings', { state: { email } });
        break;
      default:
        break;
    }
  };

  return (
      <div className="container choose-option-container">
        <h2>Choose an Option</h2>
        <button onClick={() => handleOptionClick('book')} className="option-button">Book New Room</button>
        <button onClick={() => handleOptionClick('showBookings')} className="option-button">Show Existing Bookings</button>
      </div>
  );
};

export default ChooseOption;
