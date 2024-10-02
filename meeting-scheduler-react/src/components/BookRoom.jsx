// src/components/BookRoom.jsx
import React, { useState, useEffect } from 'react';
import api, { setBasicAuthHeader } from '../api/api';
import { useLocation } from 'react-router-dom';
import '../styles/BookRoom.css';

const BookRoom = () => {
    const [rooms, setRooms] = useState([]);
    const [selectedRoom, setSelectedRoom] = useState('');
    const [date, setDate] = useState('');
    const [startTime, setStartTime] = useState('');
    const [endTime, setEndTime] = useState('');
    const [message, setMessage] = useState('');
    const location = useLocation();
    const email = location.state.email || '';

    useEffect(() => {
        const fetchRooms = async () => {
            setBasicAuthHeader();
            try {
                const response = await api.get('/rooms');
                setRooms(response.data);
            } catch (error) {
                console.error('Error fetching rooms:', error);
            }
        };

        fetchRooms();
    }, []);

    const handleBooking = async (event) => {
        event.preventDefault();

        setBasicAuthHeader();
        const startDateTime = `${date}T${startTime}`;
        const endDateTime = `${date}T${endTime}`;

        const selectedDateTime = new Date(`${date}T${startTime}`);
        const selectedEndDateTime = new Date(`${date}T${endTime}`);

        if (selectedEndDateTime <= selectedDateTime) {
            setMessage('End time must be after start time! Please check AM/PM.');
            return;
        }

        try {
            const response = await api.post('/bookings/book', {
                roomName: selectedRoom,
                startTime: startDateTime,
                endTime: endDateTime,
                userEmail: email,
            });
            console.log('Booking response:', response.data);
            setMessage('Room booked successfully!');
        } catch (error) {
            console.error('Error booking room:', error);
            if (error.response && error.response.status === 500) {
                setMessage(error.response.data.message);
            } else {
                setMessage('Failed to book room! Please try again.');
            }
        }
    };

    return (
        <div className="container book-room-container">
            <h2>Book a Room</h2>
            <form onSubmit={handleBooking} className="book-room-form">
                <label>
                    Room Name:
                    <select
                        value={selectedRoom}
                        onChange={(e) => setSelectedRoom(e.target.value)}
                        className="room-select"
                        required
                    >
                        <option value="">Select a room</option>
                        {rooms.map((room) => (
                            <option key={room.id} value={room.name}>{room.name}</option>
                        ))}
                    </select>
                </label>

                <label>
                    Date:
                    <input
                        type="date"
                        value={date}
                        onChange={(e) => setDate(e.target.value)}
                        className="date-input"
                        min={new Date().toISOString().split('T')[0]} // Restrict past dates
                        required
                    />
                </label>

                <label>
                    Start Time:
                    <input
                        type="time"
                        value={startTime}
                        onChange={(e) => setStartTime(e.target.value)}
                        className="time-input"
                        required
                    />
                </label>

                <label>
                    End Time:
                    <input
                        type="time"
                        value={endTime}
                        onChange={(e) => setEndTime(e.target.value)}
                        className="time-input"
                        required
                    />
                </label>

                <button type="submit" className="book-button">Book Room</button>
            </form>
            {message && <p className="message">{message}</p>}
        </div>
    );
};

export default BookRoom;

