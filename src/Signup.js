// Signup.js
import React, { useState } from 'react';
import './Signup.css';  // Optional: Add styles for signup form

const Signup = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSignupSubmit = (e) => {
    e.preventDefault();

    if (!email || !password || !confirmPassword) {
      setError('Please fill out all fields');
      return;
    }

    if (password !== confirmPassword) {
      setError('Passwords do not match');
      return;
    }

    setLoading(true);
    setError('');

    // Simulate an API call for signup (replace with your actual API call)
    setTimeout(() => {
      // Simulate successful signup
      alert('Signup successful');
      setLoading(false);
    }, 2000);  // Simulated delay
  };

  return (
    <div className="signup-container">
      <h2>Signup</h2> {/* Title */}
      <form onSubmit={handleSignupSubmit}>
        <div className="form-group">
          <label htmlFor="email">Email:</label>
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
          <label htmlFor="password">Password:</label>
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
          <label htmlFor="confirmPassword">Confirm Password:</label>
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
  );
};

export default Signup;
