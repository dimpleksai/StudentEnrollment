import React from 'react';
import './Home.css';

const Home = () => {
  return (
    <div className="page-wrapper">
      {/* White top section with heading */}
      <div className="top-white-space">
        <h1 className="home-title">Welcome to Student Enrollment Portal</h1>
      </div>

      {/* Background image starts below */}
      <div className="home-container">
        <header className="home-header">
          <div className="button-group">
            <button className="nav-button">Login</button>
            <button className="nav-button">Signup</button>
          </div>
        </header>
      </div>
    </div>
  );
};

export default Home;
