// Login.js
import { useNavigate } from 'react-router-dom';
import React, { useState } from 'react';
import './Login.css';

const allowedDomainsRegex = /^[a-zA-Z0-9._%+-]+@(gmail\.com|yahoo\.com|outlook\.com|example\.com)$/;

const Login = ({ closeModal }) => {
  const [mode, setMode] = useState("login"); // "login" | "change"
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  // Change Password fields
  const [currentPwd, setCurrentPwd] = useState('');
  const [newPwd, setNewPwd] = useState('');
  const [confirmNewPwd, setConfirmNewPwd] = useState('');

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  // --- LOGIN SUBMIT ---
  const handleLoginSubmit = (e) => {
    e.preventDefault();

    if (!email || !password) {
      setError('Please fill out both fields');
      return;
    }
    if (!allowedDomainsRegex.test(email)) {
      setError('Only Gmail, Yahoo, or Outlook email addresses are allowed');
      return;
    }
    if (password.length < 6) {
      setError('Password must be at least 6 characters long');
      return;
    }

    setLoading(true);
    setError('');

    setTimeout(() => {
      if (email === 'admin@example.com' && password === 'password') {
        alert('Login successful');
        closeModal();
        navigate('/student-dashboard');
      } else {
        setError('Invalid email or password');
      }
      setLoading(false);
    }, 1200);
  };

  // --- CHANGE PASSWORD SUBMIT ---
  const handleChangePasswordSubmit = (e) => {
    e.preventDefault();

    if (!email || !currentPwd || !newPwd || !confirmNewPwd) {
      setError('Please fill out all fields');
      return;
    }
    if (!allowedDomainsRegex.test(email)) {
      setError(' Gmail, Yahoo, or Outlook email addresses are allowed');
      return;
    }
    if (newPwd.length < 6) {
      setError('New password must be at least 6 characters long');
      return;
    }
    if (newPwd !== confirmNewPwd) {
      setError('New passwords do not match');
      return;
    }

    setLoading(true);
    setError('');

    // Simulate API call to change password
    setTimeout(() => {
      // pretend current password is always "password"
      if (currentPwd !== 'password') {
        setError('Current password is incorrect');
        setLoading(false);
        return;
      }
      alert('Password changed successfully. You can now log in.');
      // Reset change-password fields and go back to login view
      setCurrentPwd(''); setNewPwd(''); setConfirmNewPwd('');
      setMode('login');
      setLoading(false);
    }, 1200);
  };

  const switchToChange = () => { setError(''); setMode('change'); };
  const backToLogin = () => { setError(''); setMode('login'); };

  return (
    <div className="login-modal">
      <div className="modal-content">
        <span className="close-btn" onClick={closeModal}>&times;</span>

        {mode === 'login' ? (
          <>
            <h2 className="modal-title">Login</h2>
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
                {/* Forgot password link under the password field */}
                <p className="forgot-password-link" onClick={switchToChange}>
                  Change Password
                </p>
              </div>

              <button type="submit" disabled={loading}>
                {loading ? 'Logging in...' : 'Login'}
              </button>

              {error && <p className="error">{error}</p>}
            </form>
          </>
        ) : (
          <>
            <h2 className="modal-title">Change Password</h2>
            <form onSubmit={handleChangePasswordSubmit}>
            <div className="form-group">
                <input
                  type="email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  required
                  placeholder="Your account email" 
                />
              </div>
              <div className="form-group">
                <input
                  type="password"
                  value={currentPwd}
                  onChange={(e) => setCurrentPwd(e.target.value)}
                  required
                  placeholder="Current password"
                />
              </div>
              <div className="form-group">
                <input
                  type="password"
                  value={newPwd}
                  onChange={(e) => setNewPwd(e.target.value)}
                  required
                  placeholder="New password"
                />
              </div>
              <div className="form-group">
                <input
                  type="password"
                  value={confirmNewPwd}
                  onChange={(e) => setConfirmNewPwd(e.target.value)}
                  required
                  placeholder="Confirm new password"
                />
              </div>

              <button type="submit" disabled={loading}>
                {loading ? 'Updating…' : 'Update Password'}
              </button>

              <button
                type="button"
                className="link-btn"
                onClick={backToLogin}
              >
                ← Back to Login
              </button>

              {error && <p className="error">{error}</p>}
            </form>
          </>
        )}
      </div>
    </div>
  );
};

export default Login;
