// StudentDetails.js
import React, { useState, useEffect } from 'react';
import './StudentDetails.css';
import { useNavigate, useLocation } from 'react-router-dom';


const StudentDetails = ({ email: propEmail }) => {
  const navigate = useNavigate();
  const location = useLocation();

  // Try getting email from props, router state, or sessionStorage fallback
  const signupEmail =
   propEmail ||
   location.state?.email ||
   sessionStorage.getItem('signupEmail') ||
  '';


  const [formData, setFormData] = useState({
    email: signupEmail,
    firstname: '',
    lastname: '',
    gpa: '',
    major: '',
    classification: ''
  });

  useEffect(() => {
  setFormData(prev => ({ ...prev, email: signupEmail }));
  }, [signupEmail]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: name === 'gpa' && value !== '' ? Number(value) : value
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log('Submitted student data:', formData);
    navigate('/', { state: { openLogin: true } });
  };

  return (
    <div className="student-details-wrapper">
      <div className="student-details-container">
        <h2>Complete Your Student Profile</h2>
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Email:</label>
            <input type="email" name="email" value={formData.email} readOnly />
          </div>

          <div className="form-group">
            <label>First name:</label>
            <input type="text" name="firstname" value={formData.firstname} onChange={handleChange} required />
          </div>

          <div className="form-group">
            <label>Last name:</label>
            <input type="text" name="lastname" value={formData.lastname} onChange={handleChange} required />
          </div>

          <div className="form-group">
            <label>GPA:</label>
            <input type="number" name="gpa" value={formData.gpa} onChange={handleChange}
                   step="0.01" min="0" max="4" required />
          </div>

          <div className="form-group">
            <label>Major:</label>
            <input type="text" name="major" value={formData.major} onChange={handleChange} required />
          </div>

          <div className="form-group">
            <label>Classification:</label>
            <select
              name="classification"
              value={formData.classification}
              onChange={handleChange}
              required
            >
              <option value="">-- Select Classification --</option>
              <option value="Freshman">Freshman</option>
              <option value="Sophomore">Sophomore</option>
              <option value="Junior">Junior</option>
              <option value="Senior">Senior</option>
              <option value="Graduate">Graduate</option>
            </select>
          </div>

          <button type="submit">Submit</button>
        </form>
      </div>
    </div>
  );
};

export default StudentDetails;
