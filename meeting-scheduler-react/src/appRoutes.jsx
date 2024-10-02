// src/AppRoutes.jsx
import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import Login from './components/Login';
import ChooseOption from './components/ChooseOption';
import BookRoom from './components/BookRoom';
import UpdateBooking from './components/UpdateBooking';
import ShowBookings from './components/ShowBookings';

const AppRoutes = () => {
    return (
        <Routes>
            <Route path="/login" element={<Login />} />
            <Route path="/choose-option" element={<ChooseOption />} />
            <Route path="/book-room" element={<BookRoom />} />
            <Route path="/update-booking/:bookingID" element={<UpdateBooking />} />
            <Route path="/show-bookings" element={<ShowBookings />} />
            <Route path="*" element={<Navigate to="/login" />} />
        </Routes>
    );
};

export default AppRoutes;
