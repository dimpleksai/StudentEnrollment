import React, { useState } from 'react';
import './Home.css';
import Login from './Login';     // ✅ Correct merged Login component
import Signup from './Signup';   // ✅ Correct merged Signup component

const Home = () => {
  const [showLoginModal, setShowLoginModal] = useState(false);
  const [showSignupModal, setShowSignupModal] = useState(false);

  const handleLoginClick = () => setShowLoginModal(true);
  const handleSignupClick = () => setShowSignupModal(true);
  const closeLoginModal = () => setShowLoginModal(false);
  const closeSignupModal = () => setShowSignupModal(false);

  return (
    <div className="page-wrapper">
      <div className="top-white-space">
        <h1 className="home-title">Welcome to Student Enrollment Portal</h1>
      </div>

      <div className="home-container">
        <header className="home-header">
          <div className="button-group">
            <button className="nav-button" onClick={handleLoginClick}>Login</button>
            <button className="nav-button" onClick={handleSignupClick}>Signup</button>
          </div>
        </header>
      </div>

      {/* Updated usage */}
      {showLoginModal && <Login closeModal={closeLoginModal} />}
      {showSignupModal && <Signup closeModal={closeSignupModal} />}
    </div>
  );
};

export default Home;
