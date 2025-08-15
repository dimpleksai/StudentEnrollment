// Signup.js
import React, { useState } from 'react';
import './Signup.css'; // Keep using your own CSS
import { useNavigate } from 'react-router-dom';


const Signup = ({ closeModal }) => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();


  const handleSignupSubmit = (e) => {
    e.preventDefault();
  
    if (!email || !password || !confirmPassword) {
      setError('Please fill out all fields');
      return;
    }
  
    // Gmail-only validation
    const gmailRegex = /^[a-zA-Z0-9._%+-]+@(gmail\.com|yahoo\.com|outlook\.com)$/;
    if (!gmailRegex.test(email)) {
      setError('Gmail, Yahoo, or Outlook email addresses are allowed');
      return;
    }
  
    if (password !== confirmPassword) {
      setError('Passwords do not match');
      return;
    }
  
    if (password.length < 6) {
      setError('Password must be at least 6 characters long');
      return;
    }
  
    setLoading(true);
    setError('');
  
    setTimeout(() => {
      alert('Signup successful');
      setLoading(false);
      navigate('/student-details');  // 👈 Navigate to next page
      closeModal();                  // 👈 Close modal after navigating
    }, 2000);
  };

  return (
    <div className="signup-modal">
      <div className="modal-content">
        <span className="close-btn" onClick={closeModal}>×</span>
        <h2>Signup</h2>
        <form onSubmit={handleSignupSubmit}>
          <div className="form-group">
            <input
              type="email"
              id="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
              placeholder="Enter your email"
            />
          </div>
          <div className="form-group">
            <input
              type="password"
              id="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
              placeholder="Enter your password"
            />
          </div>
          <div className="form-group">
            <input
              type="password"
              id="confirmPassword"
              value={confirmPassword}
              onChange={(e) => setConfirmPassword(e.target.value)}
              required
              placeholder="Confirm your password"
            />
          </div>
          <button type="submit" disabled={loading}>
            {loading ? 'Signing up...' : 'Signup'}
          </button>
          {error && <p className="error">{error}</p>}
        </form>
      </div>
    </div>
  );
};

export default Signup;
