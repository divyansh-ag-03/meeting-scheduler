import React, { useState, useEffect } from 'react';
import { useParams, useLocation } from 'react-router-dom';
import api, { setBasicAuthHeader } from '../api/api';
import '../styles/UpdateBooking.css';

const UpdateBooking = () => {
    const { bookingID } = useParams();
    const location = useLocation();
    const email = location.state?.email || '';

    const [booking, setBooking] = useState(null);
    const [rooms, setRooms] = useState([]);
    const [selectedRoom, setSelectedRoom] = useState('');
    const [date, setDate] = useState('');
    const [startTime, setStartTime] = useState('');
    const [endTime, setEndTime] = useState('');
    const [message, setMessage] = useState('');
    const [error, setError] = useState('');

    useEffect(() => {
        const fetchBookingDetails = async () => {
            setBasicAuthHeader();
            try {
                const response = await api.get(`/bookings/${bookingID}`);
                setBooking(response.data);
                const bookingDate = new Date(response.data.startTime).toISOString().split('T')[0];
                setSelectedRoom(response.data.roomName);
                setDate(bookingDate);
                setStartTime(formatTime(response.data.startTime));
                setEndTime(formatTime(response.data.endTime));
            } catch (error) {
                console.error('Error fetching booking details:', error);
            }
        };

        const fetchRooms = async () => {
            setBasicAuthHeader();
            try {
                const response = await api.get('/rooms');
                setRooms(response.data);
            } catch (error) {
                console.error('Error fetching rooms:', error);
            }
        };

        fetchBookingDetails();
        fetchRooms();
    }, [bookingID]);

    const formatTime = (time) => {
        const date = new Date(time);
        const hours = date.getHours().toString().padStart(2, '0');
        const minutes = date.getMinutes().toString().padStart(2, '0');
        return `${hours}:${minutes}`;
    };

    const handleUpdateBooking = async (event) => {
        event.preventDefault();
        setBasicAuthHeader();

        const startDateTime = `${date}T${startTime}`;
        const endDateTime = `${date}T${endTime}`;

        const selectedDateTime = new Date(startDateTime);
        const selectedEndDateTime = new Date(endDateTime);

        if (selectedEndDateTime <= selectedDateTime) {
            setMessage('End time must be after start time! Please check AM/PM.');
            return;
        }

        try {
            const payload = {
                roomName: selectedRoom,
                startTime: startDateTime,
                endTime: endDateTime,
                userEmail: email,
            };

            const response = await api.patch(`/bookings/update/${bookingID}`, payload);
            console.log('Update response:', response.data);
            setMessage('Booking updated successfully!');
        } catch (error) {
            console.error('Error updating booking:', error);
            if (error.response && error.response.status === 500) {
                setMessage(error.response.data.message);
            } else {
                setMessage('Failed to update booking! Please try again.')
            }
        }
    };

    return (
        <div className="container update-booking-container">
            <h2>Update Booking</h2>
            <form onSubmit={handleUpdateBooking} className="update-booking-form">
                <label>
                    Room Name:
                    <select
                        value={selectedRoom}
                        onChange={(e) => setSelectedRoom(e.target.value)}
                        className="form-select"
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
                        className="form-input"
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
                        className="form-input"
                        required
                    />
                </label>

                <label>
                    End Time:
                    <input
                        type="time"
                        value={endTime}
                        onChange={(e) => setEndTime(e.target.value)}
                        className="form-input"
                        required
                    />
                </label>

                <button type="submit" className="update-button">Update Booking</button>
            </form>
            {message && <p className="message">{message}</p>}
            {error && <p className="error-message">{error}</p>}
        </div>
    );
};

export default UpdateBooking;






