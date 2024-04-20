create database testgradebook;
use testgradebook;
show tables;

-- Class
CREATE TABLE Class (
  Course_Number VARCHAR(10) PRIMARY KEY,  -- Unique identifier for class 
  Term VARCHAR(10) NOT NULL,              -- Term class is offered
  Section_Number INT,                     -- Section number
  Description TEXT,                        -- Description of content for the class
  CONSTRAINT chk_section_number CHECK (Section_Number >= 0)  -- Enforce non-negative section numbers
);

-- Category
CREATE TABLE Category (
  Category_Name VARCHAR(50) PRIMARY KEY,  -- Name for the category
  Weight DECIMAL(5,2) NOT NULL,             -- Weight for the Class
  CONSTRAINT chk_weight CHECK (Weight BETWEEN 0 AND 100), -- Enforce weight between 0 and 100
  Course_Number VARCHAR(10) NOT NULL,  -- References the Class table
  FOREIGN KEY (Course_Number) REFERENCES Class(Course_Number)
);

-- Assignment
CREATE TABLE Assignment (
  Assignment_ID INT AUTO_INCREMENT PRIMARY KEY,  -- Auto-generated unique id
  Name VARCHAR(100) NOT NULL,                     -- Name of the assignment
  Description TEXT,                                -- Description of assignment
  Points_Possible INT NOT NULL,                    -- Total num of points achievable
  Due_Date DATE,                                   -- Due date of the assignment
  Category_Name VARCHAR(50) NOT NULL,             -- Name of the category
  Course_Number VARCHAR(10) NOT NULL,              -- Course number 
  FOREIGN KEY (Category_Name) REFERENCES Category(Category_Name),
  FOREIGN KEY (Course_Number) REFERENCES Class(Course_Number),
  UNIQUE (Name, Course_Number)  -- Unique assignment names within a class
);

-- Student
CREATE TABLE Student (
  Student_ID INT AUTO_INCREMENT PRIMARY KEY,  -- ID for student
  Username VARCHAR(50) NOT NULL UNIQUE,       -- Username
  Name VARCHAR(255) NOT NULL                   -- Full name of the student
);

-- Enrollment
CREATE TABLE Enrollment (
  Student_ID INT NOT NULL,
  Course_Number VARCHAR(10) NOT NULL,
  FOREIGN KEY (Student_ID) REFERENCES Student(Student_ID),
  FOREIGN KEY (Course_Number) REFERENCES Class(Course_Number),
  PRIMARY KEY (Student_ID, Course_Number)  -- Composite primary key
);

-- Grade 
CREATE TABLE Grade (
  Student_ID INT NOT NULL,
  Assignment_ID INT NOT NULL,
  Earned_Points INT NOT NULL,
  FOREIGN KEY (Student_ID) REFERENCES Student(Student_ID),
  FOREIGN KEY (Assignment_ID) REFERENCES Assignment(Assignment_ID),
  PRIMARY KEY (Student_ID, Assignment_ID)  -- Composite primary key
);

-- Indexes
CREATE INDEX idx_class_term_section ON Class(Term, Section_Number);  -- Filtering by term and section index
CREATE INDEX idx_assignment_category_course ON Assignment(Category_Name, Course_Number);  -- Faster retrieval based on category and course index
CREATE INDEX idx_enrollment_student_id ON Enrollment(Student_ID);  -- Lookup by student index
CREATE INDEX idx_enrollment_course_number ON Enrollment(Course_Number);  -- Lookup by course index 
CREATE INDEX idx_grade_student_id ON Grade(Student_ID);  -- Retrieval of grades by student index
CREATE INDEX idx_grade_assignment_id ON Grade(Assignment_ID);  -- Retrieval of grades by assignment index


