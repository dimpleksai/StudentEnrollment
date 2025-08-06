// CourseListing.js
import React, { useState } from 'react';
import './CourseListing.css';

const CourseListing = () => {
  const allCourses = [
    { id: 1, title: 'Intro to Computer Science', department: 'CS', instructor: 'Dr. Smith' },
    { id: 2, title: 'Linear Algebra', department: 'Math', instructor: 'Prof. Allen' },
    { id: 3, title: 'Operating Systems', department: 'CS', instructor: 'Dr. Brown' },
    { id: 4, title: 'Statistics 101', department: 'Math', instructor: 'Prof. Miller' },
    { id: 5, title: 'Modern Art History', department: 'Arts', instructor: 'Dr. Taylor' },
  ];

  const [departmentFilter, setDepartmentFilter] = useState('');
  const [instructorFilter, setInstructorFilter] = useState('');
  const [enrolled, setEnrolled] = useState([]);

  const filteredCourses = allCourses.filter(course => {
    return (
      (departmentFilter ? course.department === departmentFilter : true) &&
      (instructorFilter ? course.instructor === instructorFilter : true)
    );
  });

  const handleEnroll = (courseId) => {
    if (!enrolled.includes(courseId)) {
      setEnrolled([...enrolled, courseId]);
      alert('Enrolled successfully!');
    }
  };

  const uniqueDepartments = [...new Set(allCourses.map(c => c.department))];
  const uniqueInstructors = [...new Set(allCourses.map(c => c.instructor))];

  return (
    <div className="course-listing-container">
      <h1>📚 Available Courses</h1>

      <div className="filters">
        <select value={departmentFilter} onChange={e => setDepartmentFilter(e.target.value)}>
          <option value="">Filter by Department</option>
          {uniqueDepartments.map(dep => <option key={dep} value={dep}>{dep}</option>)}
        </select>

        <select value={instructorFilter} onChange={e => setInstructorFilter(e.target.value)}>
          <option value="">Filter by Instructor</option>
          {uniqueInstructors.map(ins => <option key={ins} value={ins}>{ins}</option>)}
        </select>
      </div>

      <div className="courses-grid">
        {filteredCourses.map(course => (
          <div className="course-card" key={course.id}>
            <h3>{course.title}</h3>
            <p><strong>Department:</strong> {course.department}</p>
            <p><strong>Instructor:</strong> {course.instructor}</p>
            <button
              onClick={() => handleEnroll(course.id)}
              disabled={enrolled.includes(course.id)}
            >
              {enrolled.includes(course.id) ? 'Enrolled' : 'Enroll'}
            </button>
          </div>
        ))}
      </div>
    </div>
  );
};

export default CourseListing;
