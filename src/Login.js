// src/Login.js
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Login.css';
import { api } from './api';

const allowedDomainsRegex = /^[a-zA-Z0-9._%+-]+@(university\.edu)$/;

export default function Login({ closeModal }) {
  const navigate = useNavigate();
  const [mode, setMode] = useState("login"); // "login" | "change"
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  // Change Password (UI kept but no backend yet)
  const [currentPwd, setCurrentPwd] = useState('');
  const [newPwd, setNewPwd] = useState('');
  const [confirmNewPwd, setConfirmNewPwd] = useState('');

  const [error, setError] = useState('');
  const [status, setStatus] = useState('');
  const [busy, setBusy] = useState(false);

  const validateEmail = (val) => allowedDomainsRegex.test(val);

  async function handleLoginSubmit(e) {
    e.preventDefault();
    setError(''); setStatus('');
    if (!email || !password) {
      setError('Please fill out both fields');
      return;
    }
    if (!allowedDomainsRegex.test(email)) {
      setError('university.edu addresses are allowed');
      return;
    }
    if (password.length < 6) {
      setError('Password must be at least 6 characters long');
      return;

    }
    setBusy(true);
    try {
      const res = await api.login({ email, password });
      setStatus(res.message || 'Login successful');
      closeModal?.();
      navigate('/student-dashboard');;
    } catch (err) {
      setError('Invalid email or password');
    } finally {
      setBusy(false);
    } 
  }

  function handleChangePasswordSubmit(e) {
    e.preventDefault();
    setError('Change password API not available yet.');

    
  }

  return (
    <div className="modal-overlay">
      <div className="modal">
        <button className="close-btn" onClick={() => closeModal?.()}>×</button>

        {mode === "login" ? (
          <>
            <h2>Log in</h2>
            <form onSubmit={handleLoginSubmit} className="form">
              <div className="field">
                <label>Email</label>
                <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} placeholder="you@example.com" required />
              </div>
              <div className="field">
                <label>Password</label>
                <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} placeholder="Your password" required />
              </div>

              <button type="submit" disabled={busy}>{busy ? 'Working...' : 'Log in'}</button>

              <div className="alt-actions">
                <button type="button" className="link" onClick={() => setMode("change")}>
                  Forgot / Change password
                </button>
              </div>

              {status && <p className="status">{status}</p>}
              {error && <p className="error">{error}</p>}
            </form>
          </>
        ) : (
          <>
            <h2>Change password</h2>
            <form onSubmit={handleChangePasswordSubmit} className="form">
              <div className="field">
                <label>Email</label>
                <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} required />
              </div>
              <div className="field">
                <label>Current password</label>
                <input type="password" value={currentPwd} onChange={(e) => setCurrentPwd(e.target.value)} required />
              </div>
              <div className="field">
                <label>New password</label>
                <input type="password" value={newPwd} onChange={(e) => setNewPwd(e.target.value)} required />
              </div>
              <div className="field">
                <label>Confirm new password</label>
                <input type="password" value={confirmNewPwd} onChange={(e) => setConfirmNewPwd(e.target.value)} required />
              </div>

              <button type="submit" disabled>Change password (coming soon)</button>

              <button type="button" className="link" onClick={() => setMode("login")}>
                ← Back to Login
              </button>

              {error && <p className="error">{error}</p>}
            </form>
          </>
        )}
      </div>
    </div>
  );
}
