// StudentDetails.js
import React, { useState, useEffect } from 'react';
import './StudentDetails.css';

const StudentDetails = ({ email }) => {
  const [formData, setFormData] = useState({
    email: '',
    name: '',
    age: '',
    address: '',
    phone: '',
    course: ''
  });

  useEffect(() => {
    setFormData(prev => ({ ...prev, email }));  // Pre-fill email from props
  }, [email]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log('Submitted student data:', formData);
    alert('Student details submitted!');
    // Optionally send this data to backend API
  };

  return (
    <div className="student-details-container">
      <h2>Complete Your Student Profile</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label>Email:</label>
          <input type="email" name="email" value={formData.email} disabled />
        </div>
        <div className="form-group">
          <label>Name:</label>
          <input type="text" name="name" value={formData.name} onChange={handleChange} required />
        </div>
        <div className="form-group">
          <label>Age:</label>
          <input type="number" name="age" value={formData.age} onChange={handleChange} required />
        </div>
        <div className="form-group">
          <label>Address:</label>
          <input type="text" name="address" value={formData.address} onChange={handleChange} required />
        </div>
        <div className="form-group">
          <label>Phone:</label>
          <input type="tel" name="phone" value={formData.phone} onChange={handleChange} required />
        </div>
        <div className="form-group">
          <label>Course:</label>
          <input type="text" name="course" value={formData.course} onChange={handleChange} required />
        </div>
        <button type="submit">Submit</button>
      </form>
    </div>
  );
};

export default StudentDetails;
