// StudentDashboard.js
import React, { useState } from 'react';
import './StudentDashboard.css';
import { useNavigate } from 'react-router-dom';


const StudentDashboard = () => {
  const student = {
    name: 'John Doe',
    email: 'john@example.com',
    enrolledCourses: [
      { id: 1, title: 'Intro to Computer Science', instructor: 'Dr. Smith' },
      { id: 2, title: 'Advanced Mathematics', instructor: 'Prof. Allen' },
    ],
    nextClass: {
      course: 'Intro to Computer Science',
      time: '10:00 AM, Aug 6, 2025',
    }
  };

  const [menuOpen, setMenuOpen] = useState(false);
  const navigate = useNavigate();

  const handleSignOut = () => {
    // Clear session/localStorage if needed
    window.location.href = '/'; // redirect to home
  };

  const handleProfileClick = () => {
    alert("Profile clicked (you can redirect to profile page)");
  };

  return (
    <div className="dashboard-wrapper">
      {/* 🟡 Menu Button at Top-Right */}
      <div className="menu-container">
        <button className="menu-button" onClick={() => setMenuOpen(!menuOpen)}>☰</button>
        {menuOpen && (
          <div className="dropdown-menu">
            <button onClick={handleProfileClick}>👤 Profile</button>
            <button onClick={handleSignOut}>🚪 SignOut</button>
          </div>
        )}
      </div>

      {/* 🟢 Main Dashboard Content */}
      <div className="dashboard-container">
        <header className="dashboard-header">
          <h1>Welcome back, {student.name} 👋</h1>
          <p>{student.email}</p>
        </header>

        <section className="dashboard-section">
          <h2>📚 Your Enrolled Courses</h2>
          <div className="courses-list">
            {student.enrolledCourses.map(course => (
              <div className="course-card" key={course.id}>
                <h3>{course.title}</h3>
                <p>Instructor: {course.instructor}</p>
              </div>
            ))}
          </div>
        </section>

        <section className="dashboard-section highlight-box">
          <h2>📆 Next Class</h2>
          <p><strong>{student.nextClass.course}</strong> at {student.nextClass.time}</p>
        </section>

        <section className="dashboard-section">
          <button
            className="explore-btn"
            onClick={() => navigate('/course-listing')}
              >
              ➕ Explore More Courses
          </button>
        </section>
      </div>
    </div>
  );
};

export default StudentDashboard;
