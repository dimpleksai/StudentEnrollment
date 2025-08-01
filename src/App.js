import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from './Home';
import Login from './Login';  // Import Login page component
import Signup from './Signup';  // Import Signup page component
import './App.css';

const App = () => {
  return (
    <Router>  {/* Ensure everything is inside Router */}
      <Routes>
        <Route path="/" element={<Home />} /> {/* Home route */}
        <Route path="/login" element={<Login />} /> {/* Login route */}
        <Route path="/signup" element={<Signup />} /> {/* Signup route */}
      </Routes>
    </Router>
  );
};

export default App;
