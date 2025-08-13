-- Enable useful extensions
CREATE EXTENSION IF NOT EXISTS pgcrypto;
CREATE EXTENSION IF NOT EXISTS citext;

-- Drop in dependency order (idempotent)
DROP TABLE IF EXISTS rosters CASCADE;
DROP TABLE IF EXISTS sections CASCADE;
DROP TABLE IF EXISTS undergraduate_students CASCADE;
DROP TABLE IF EXISTS instructors CASCADE;
DROP TABLE IF EXISTS courses CASCADE;

-- Courses: string IDs like 'CS101'
CREATE TABLE courses (
    course_id VARCHAR(10) PRIMARY KEY,
    discipline VARCHAR(50) NOT NULL,
    course_number VARCHAR(10) NOT NULL,
    CONSTRAINT uq_courses_discipline_num UNIQUE (discipline, course_number)
);

-- Instructors: matches seed columns exactly
CREATE TABLE instructors (
    instructor_id VARCHAR(10) PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    department VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    degree_institute VARCHAR(100),
    degree_type VARCHAR(20)
);

-- Students: add fields used by seed + minimal auth
CREATE TABLE undergraduate_students (
    student_id SERIAL PRIMARY KEY,
    student_number VARCHAR(20) UNIQUE NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email CITEXT UNIQUE NOT NULL,
    classification VARCHAR(15) NOT NULL CHECK (classification IN ('Freshman','Sophomore','Junior','Senior')),
    major VARCHAR(100),
    gpa DECIMAL(3,2),
    enrollment_status VARCHAR(20) DEFAULT 'Active',
    admission_date DATE NOT NULL,
    password_hash TEXT NOT NULL,
    email_verified BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

-- updated_at trigger
CREATE OR REPLACE FUNCTION set_updated_at() RETURNS trigger AS $$
BEGIN
  NEW.updated_at = now();
  RETURN NEW;
END; $$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_undergrad_updated_at ON undergraduate_students;
CREATE TRIGGER trg_undergrad_updated_at
BEFORE UPDATE ON undergraduate_students
FOR EACH ROW EXECUTE FUNCTION set_updated_at();

-- Sections: string IDs and text FKs, matches seed columns
CREATE TABLE sections (
    section_id VARCHAR(20) PRIMARY KEY,
    course_id VARCHAR(10) NOT NULL REFERENCES courses(course_id),
    semester VARCHAR(20) NOT NULL,
    section_number VARCHAR(3) NOT NULL,
    instructor_number VARCHAR(10) NOT NULL REFERENCES instructors(instructor_id),
    CONSTRAINT uq_sections_course_sem_num UNIQUE (course_id, semester, section_number)
);

-- Rosters: includes enrollment_date, grade used by seed
CREATE TABLE rosters (
    roster_id SERIAL PRIMARY KEY,
    section_id VARCHAR(20) NOT NULL REFERENCES sections(section_id) ON DELETE CASCADE,
    student_number VARCHAR(20) NOT NULL REFERENCES undergraduate_students(student_number) ON DELETE CASCADE,
    enrollment_date DATE DEFAULT CURRENT_DATE,
    grade VARCHAR(2),
    CONSTRAINT uq_rosters_unique_enrollment UNIQUE (section_id, student_number)
);
