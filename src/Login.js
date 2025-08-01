// Login.js
import React, { useState } from 'react';
import './Login.css';  // Optionally add styles for login form

const Login = ({ onLoginSuccess }) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const handleLoginSubmit = (e) => {
    e.preventDefault();

    // Basic validation
    if (!username || !password) {
      setError('Please enter both username and password');
      return;
    }

    setLoading(true); // Set loading state
    setError('');

    // Simulate API call for login
    setTimeout(() => {
      // Replace this logic with your actual authentication API call
      if (username === 'admin' && password === 'password') {
        // Simulate successful login
        onLoginSuccess();
      } else {
        // Show error message on failed login
        setError('Invalid username or password');
      }
      setLoading(false); // Stop loading state
    }, 2000); // Simulated API delay
  };

  return (
    <div className="login-container">
      <h2>Login</h2>
      <form onSubmit={handleLoginSubmit}>
        <div className="form-group">
          <label htmlFor="username">Username:</label>
          <input
            type="text"
            id="username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
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
          />
        </div>
        <button type="submit" disabled={loading}>
          {loading ? 'Logging in...' : 'Login'}
        </button>
        {error && <p className="error">{error}</p>}
      </form>
    </div>
  );
};

export default Login;
