// src/components/ShowBookings.jsx
import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import api, { setBasicAuthHeader } from '../api/api';
import '../styles/ShowBookings.css';

const ShowBookings = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const email = location.state.email;

    const [bookings, setBookings] = useState([]);

    useEffect(() => {
        const fetchBookings = async () => {
            setBasicAuthHeader();
            try {
                const response = await api.get(`/bookings/user/${email}`);
                setBookings(response.data);
            } catch (error) {
                console.error('Error fetching bookings:', error);
            }
        };

        fetchBookings();
    }, [email]);

    const handleUpdate = (bookingId) => {
        navigate(`/update-booking/${bookingId}`);
    };

    /*const handleDelete = async (bookingId) => {
        try {
            await api.delete(`/bookings/delete/${bookingId}`);
            setBookings((prevBookings) => prevBookings.filter((booking) => booking.id !== bookingId));
        } catch (error) {
            console.error(`Error deleting booking with id ${bookingId}:`, error);
        }
    };*/
    const handleDelete = async (bookingId) => {
        const confirmed = window.confirm("Are you sure you want to delete this booking?"); //Prompt for delete confirmation
        if (confirmed) {
            try {
                await api.delete(`/bookings/delete/${bookingId}`);
                setBookings((prevBookings) => prevBookings.filter((booking) => booking.id !== bookingId));
            } catch (error) {
                console.error(`Error deleting booking with id ${bookingId}:`, error);
            }
        }
    };

    return (
        <div className="container show-bookings-container">
            <h2>Existing Bookings</h2>
            {bookings.length === 0 ? (
                <p>No bookings found.</p>
            ) : (
                <div className="booking-list">
                    {bookings.map((booking) => (
                        <div key={booking.id} className="booking-item">
                            <p>Date: {new Date(booking.startTime).toLocaleDateString()}</p>
                            <p>Start Time: {new Date(booking.startTime).toLocaleTimeString()}</p>
                            <p>End Time: {new Date(booking.endTime).toLocaleTimeString()}</p>
                            <p>Room: {booking.roomName.name}</p>
                            <div className="action-buttons">
                                <button className="update-btn" onClick={() => handleUpdate(booking.id)}>Update</button>
                                <button className="delete-btn" onClick={() => handleDelete(booking.id)}>Delete</button>
                            </div>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
};

export default ShowBookings;
