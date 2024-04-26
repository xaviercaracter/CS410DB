CREATE DATABASE IF NOT EXISTS Gradebook;
USE Gradebook;


CREATE TABLE IF NOT EXISTS Class (
  Course_ID INT AUTO_INCREMENT PRIMARY KEY,  -- Unique identifier for course
  Course_Number VARCHAR(10) NOT NULL,  -- Course Title/Number
  Term VARCHAR(10) NOT NULL,                  -- Term class is offered
  Section_Number INT,                         -- Section number
  Description TEXT,                           -- Description of content for the class
  CONSTRAINT chk_section_number CHECK (Section_Number >= 0)  -- Enforce non-negative section numbers
);

CREATE TABLE IF NOT EXISTS Category (
  Category_ID INT AUTO_INCREMENT PRIMARY KEY, -- Unique identifier for category
  Category_Name VARCHAR(50) NOT NULL,        -- Name for the category
  Weight DECIMAL(5,2) NOT NULL,              -- Weight for the Category
  CONSTRAINT chk_weight CHECK (Weight BETWEEN 0 AND 100),  -- Enforce weight between 0 and 100
  Course_ID INT NOT NULL,                     -- References the Course table
  FOREIGN KEY (Course_ID) REFERENCES Class(Course_ID)
);

CREATE TABLE IF NOT EXISTS Assignment (
  Assignment_ID INT AUTO_INCREMENT PRIMARY KEY,  -- Auto-generated unique id
  Name VARCHAR(100) NOT NULL,                     -- Name of the assignment
  Description TEXT,                                -- Description of assignment
  Points_Possible DECIMAL NOT NULL,                -- Total num of points achievable
  Category_ID INT NOT NULL,                        -- References the Category ID
  Course_ID INT NOT NULL,                          -- References the Course ID
  FOREIGN KEY (Category_ID) REFERENCES Category(Category_ID),
  FOREIGN KEY (Course_ID) REFERENCES Class(Course_ID),
  Unique (Name, Course_ID)  -- Unique assignment names within a class
);

-- Student
CREATE TABLE IF NOT EXISTS Student (
  Student_ID INT AUTO_INCREMENT PRIMARY KEY,  -- ID for student
  Username VARCHAR(50) NOT NULL UNIQUE,       -- Username
  Name VARCHAR(255) NOT NULL                   -- Full name of the student
);

CREATE TABLE IF NOT EXISTS Enrollment (
  Enrollment_ID INT AUTO_INCREMENT PRIMARY KEY,  -- Unique enrollment ID
  Student_ID INT NOT NULL,
  Course_ID INT NOT NULL,
  FOREIGN KEY (Student_ID) REFERENCES Student(Student_ID),
  FOREIGN KEY (Course_ID) REFERENCES Class(Course_ID),
  UNIQUE (Student_ID, Course_ID)  -- Ensures a student can't enroll in the same course twice
);

CREATE TABLE IF NOT EXISTS Grade (
  Grade_ID INT AUTO_INCREMENT PRIMARY KEY,  -- Unique grade ID
  Student_ID INT NOT NULL,
  Assignment_ID INT NOT NULL,
  Earned_Points DECIMAL NOT NULL,
  FOREIGN KEY (Student_ID) REFERENCES Student(Student_ID),
  FOREIGN KEY (Assignment_ID) REFERENCES Assignment(Assignment_ID),
  UNIQUE (Student_ID, Assignment_ID)  -- Ensures a student has only one grade per assignment
);


-- Indexes
CREATE INDEX idx_class_term_section ON Class(Term, Section_Number);  -- Filtering by term and section index
CREATE INDEX idx_assignment_category_course ON Assignment(Category_ID, Course_ID);  -- Faster retrieval based on category and course index
CREATE INDEX idx_enrollment_student_id ON Enrollment(Student_ID);  -- Lookup by student index
CREATE INDEX idx_enrollment_course_number ON Enrollment(Course_ID);  -- Lookup by course index 
CREATE INDEX idx_grade_student_id ON Grade(Student_ID);  -- Retrieval of grades by student index
CREATE INDEX idx_grade_assignment_id ON Grade(Assignment_ID);  -- Retrieval of grades by assignment index
