import React, { useState } from 'react';
import './SignupModal.css';

const SignupModal = ({ closeModal }) => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

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

    // Simulate signup (replace with actual signup logic)
    setTimeout(() => {
      alert('Signup successful');
      closeModal();  // Close modal on success
      setLoading(false);
    }, 2000);  // Simulated delay
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
              placeholder="Enter your email"  // Placeholder inside the input
            />
          </div>
          <div className="form-group">
            <input
              type="password"
              id="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
              placeholder="Enter your password"  // Placeholder inside the input
            />
          </div>
          <div className="form-group">
            <input
              type="password"
              id="confirmPassword"
              value={confirmPassword}
              onChange={(e) => setConfirmPassword(e.target.value)}
              required
              placeholder="Confirm your password"  // Placeholder inside the input
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

export default SignupModal;
