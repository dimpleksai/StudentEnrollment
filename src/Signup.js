// src/Signup.js
import React, { useState } from "react";
import { useNavigate } from 'react-router-dom';
import './Signup.css';


const Signup = ({ closeModal }) => {
const navigate = useNavigate();
const [error, setError] = useState("");

const allowedEmailRegex = /^[A-Za-z0-9._%+-]+@university\.edu$/;


  const [formData, setFormData] = useState({
    name: "",
    email: "",
    password: "",
    confirm: ""
  });

  const [status, setStatus] = useState("");
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

   const handleSubmit = async (e) => {
    e.preventDefault();

      if (!formData.name || !formData.email || !formData.password || !formData.confirm) {
       setError('Please fill out all fields');
       return;
     }
        if (!allowedEmailRegex.test(formData.email)) {
       setError('Only university.edu email addresses are allowed');
       return;
     }
      if (formData.password !== formData.confirm) {
       setError('Passwords do not match');
       return;
     }
      if (formData.password.length < 6) {
       setError('Password must be at least 6 characters');
       return;
     }

     setLoading(true);
     setStatus("");

    try {
      const res = await fetch("http://localhost:8081/api/auth/signup", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          name: formData.name,
          email: formData.email,
          password: formData.password
        })
      });

      if (res.ok) {
        setStatus("Signup successful!");
        closeModal?.();                    // close the overlay
+       navigate('/student-details');  
      } else {
        const data = await res.json();
        setStatus(data.message || "Signup failed.");
      }
    } catch (err) {
      console.error(err);
      setStatus("An error occurred during signup.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="modal-overlay">
      <div className="modal">
        <button className="close-btn" onClick={() => closeModal?.()}>×</button>
        <h2>Sign up</h2>
        <form onSubmit={handleSubmit} className="form">
          <div className="field">
            <label>Full name</label>
            <input
              name="name"
              value={formData.name}
              onChange={handleChange}
              placeholder="Jane Doe"
              required
            />
          </div>
          <div className="field">
            <label>Email</label>
            <input
              type="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              placeholder="you@example.com"
              required
            />
          </div>
          <div className="field">
            <label>Password</label>
            <input
              type="password"
              name="password"
              value={formData.password}
              onChange={handleChange}
              placeholder="Password"
              required
            />
          </div>
          <div className="field">
            <label>Confirm password</label>
            <input
              type="password"
              name="confirm"
              value={formData.confirm}
              onChange={handleChange}
              placeholder="Confirm password"
              required
            />
          </div>

          <button type="submit" disabled={loading}>
            {loading ? "Signing up..." : "Create account"}
          </button>

          {status && <p className="status">{status}</p>}
        </form>
      </div>
    </div>
  );
};

// ✅ default export so Home.js can import it
export default Signup;
