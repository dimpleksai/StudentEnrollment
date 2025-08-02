// Login.js
import React, { useState } from 'react';
import './Login.css';

const Login = ({ closeModal }) => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const handleLoginSubmit = (e) => {
    e.preventDefault();

    if (!email || !password) {
      setError('Please fill out both fields');
      return;
    }

    setLoading(true);
    setError('');

    setTimeout(() => {
      if (email === 'admin@example.com' && password === 'password') {
        alert('Login successful');
        closeModal(); // Close popup
      } else {
        setError('Invalid email or password');
      }
      setLoading(false);
    }, 2000);
  };

  return (
    <div className="login-modal">
      <div className="modal-content">
        <span className="close-btn" onClick={closeModal}>&times;</span>
        <h2>Login</h2>
        <form onSubmit={handleLoginSubmit}>
          <div className="form-group">
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
              placeholder="Enter your email"
            />
          </div>
          <div className="form-group">
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
              placeholder="Enter your password"
            />
          </div>
          <button type="submit" disabled={loading}>
            {loading ? 'Logging in...' : 'Login'}
          </button>
          {error && <p className="error">{error}</p>}
        </form>
      </div>
    </div>
  );
};

export default Login;
