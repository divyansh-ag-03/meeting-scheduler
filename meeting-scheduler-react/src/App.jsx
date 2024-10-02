// src/App.jsx
import React from 'react';
import AppRoutes from './appRoutes.jsx';
import Header from "./components/Header.jsx";

const App = () => {
    return (
        <div>
            <Header/>
            <AppRoutes />
        </div>
    );
};

export default App;
