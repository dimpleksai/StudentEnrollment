import React, { useState } from 'react';
import { useLocation } from 'react-router-dom';
import { useEffect } from 'react';
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

  const location = useLocation();

useEffect(() => {
  if (location.state?.openLogin) {
    setShowLoginModal(true);
    // Optional: clear the state so it doesn’t trigger again on refresh
    window.history.replaceState({}, document.title);
  }
}, [location.state]);


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
