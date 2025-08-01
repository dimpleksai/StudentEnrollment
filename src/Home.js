import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';  // Import useNavigate hook for navigation (if needed)
import './Home.css';
import LoginModal from './LoginModal';  // Import LoginModal component
import SignupModal from './SignupModal';  // Import SignupModal component

const Home = () => {
  const [showLoginModal, setShowLoginModal] = useState(false);  // To control Login Modal visibility
  const [showSignupModal, setShowSignupModal] = useState(false);  // To control Signup Modal visibility

  const handleLoginClick = () => {
    setShowLoginModal(true);  // Show Login modal
  };

  const handleSignupClick = () => {
    setShowSignupModal(true);  // Show Signup modal
  };

  const closeLoginModal = () => {
    setShowLoginModal(false);  // Close Login modal
  };

  const closeSignupModal = () => {
    setShowSignupModal(false);  // Close Signup modal
  };

  return (
    <div className="page-wrapper">
      {/* White top section with heading */}
      <div className="top-white-space">
        <h1 className="home-title">Welcome to Student Enrollment Portal</h1>
      </div>

      {/* Background image section */}
      <div className="home-container">
        <header className="home-header">
          <div className="button-group">
            <button className="nav-button" onClick={handleLoginClick}>
              Login
            </button>
            <button className="nav-button" onClick={handleSignupClick}>
              Signup
            </button>
          </div>
        </header>
      </div>

      {/* Show Login Modal */}
      {showLoginModal && <LoginModal closeModal={closeLoginModal} />}
      {/* Show Signup Modal */}
      {showSignupModal && <SignupModal closeModal={closeSignupModal} />}
    </div>
  );
};

export default Home;
