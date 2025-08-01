// LoginModal.js
import React, { useState } from 'react';
import './LoginModal.css';

const LoginModal = ({ closeModal }) => {
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

    // Simulate login (replace with actual authentication logic)
    setTimeout(() => {
      if (email === 'admin@example.com' && password === 'password') {
        alert('Login successful');
        closeModal();  // Close modal on success
      } else {
        setError('Invalid email or password');
      }
      setLoading(false);
    }, 2000);  // Simulated delay
  };

  return (
    <div className="login-modal">
      <div className="modal-content">
        <span className="close-btn" onClick={closeModal}>×</span>
        <h2>Login</h2>  {/* Title */}
        <form onSubmit={handleLoginSubmit}>
          <div className="form-group">
            <input
              type="email"
              id="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
              placeholder="Enter your email"  // Placeholder for email
            />
          </div>
          <div className="form-group">
            <input
              type="password"
              id="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
              placeholder="Enter your password"  // Placeholder for password
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

export default LoginModal;
