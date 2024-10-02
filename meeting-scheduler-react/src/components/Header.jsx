// Header.jsx
import React from 'react';
import { useLocation } from 'react-router-dom';
import '../styles/Header.css'

const Header = () => {
    const location = useLocation();
    const userEmail = location.state?.email || '';
    return (
        <div className="header-container">
            {userEmail && <p className="user-email">{userEmail}</p>}
        </div>
    );
};

export default Header;
