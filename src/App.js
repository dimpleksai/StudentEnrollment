import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from './Home';
import StudentDetails from './StudentDetails';
import './App.css';
import StudentDashboard from './StudentDashboard'; 
import CourseListing from './Courselisting';



const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/student-details" element={<StudentDetails />} />
        <Route path="/student-dashboard" element={<StudentDashboard />} />
        <Route path="/course-listing" element={<CourseListing />} />
      </Routes>
    </Router>
  );
};

export default App;
