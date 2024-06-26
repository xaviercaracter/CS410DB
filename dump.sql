-- Create a class
INSERT INTO Class (Course_Number, Term, Section_Number, Description)
VALUES ('CS410', 'Sp24', 1, 'Introduction to Database Systems'),
       ('MATH201', 'Sp24', 1, 'Calculus I'),
       ('ENG101', 'Sp24', 1, 'Introduction to Composition'),
       ('ENG101', 'Sp24', 2, 'Introduction to Composition'),
       ('BIOL104', 'Sp24', 1, 'Introduction to Human Biology'),
       ('CS410', 'Fa23', 1, 'Introduction to Database Systems'),
       ('ENG101', 'Fa23', 1, 'Introduction to Composition'),
       ('MATH301', 'Fa23', 1, 'Introduction to Linear Algebra');

-- Add categories
INSERT INTO Category (Category_Name, Weight, Course_ID)
VALUES ('Homework', 0.40, 1),
       ('Exam', 0.40, 1),
       ('Project', 0.20, 1),
       ('Quiz', 0.20, 2),
       ('Homework', 0.40, 2),
       ('Exam', 0.40, 2),
       ('Essay', 0.50, 3),
       ('Classwork', 0.20, 3),
       ('Participation', 0.30, 3),
       ('Essay', 0.50, 4),
       ('Classwork', 0.20, 4),
       ('Participation', 0.30, 4),
       ('Lab', 0.30, 5),
       ('Homework', 0.30, 5),
       ('Exam', 0.35, 5),
       ('Participation', 0.05, 5);

-- Add students
INSERT INTO Student (Student_ID, Username, Firstname, Lastname)
VALUES (1, 'jsmith123', 'John', 'Smith'),
       (2, 'bmiller09', 'Jane', 'Miller'),
       (3, 'alireza', 'Ali', 'Reza'),
       (4, 'sbarlow', 'Sam', 'Barlow'),
       (5, 'eramsey', 'Elliot', 'Ramsey'),
       (6, 'gnelson02', 'Gianna', 'Nelson'),
       (7, 'ksieves', 'Kenna', 'Sieves'),
       (8, 'nwelch313', 'Natalie', 'Welch'),
       (9, 'tjanson', 'Thomas', 'Janson'),
       (10, 'cbrooks', 'Charlie', 'Brooks');

-- Add enrollment
INSERT INTO Enrollment (Student_ID, Course_ID)
VALUES (1, 1),
       (2, 1),
       (4, 1),
       (6, 1),
       (7, 1),
       (1, 2),
       (4, 2),
       (6, 2),
       (10, 2),
       (3, 3),
       (4, 3),
       (5, 3),
       (8, 3),
       (10, 3),
       (1, 4),
       (2, 4),
       (7, 4),
       (9, 4),
       (2, 5),
       (3, 5),
       (6, 5),
       (8, 5),
       (9, 5),
       (10, 5);

-- Add assignments
INSERT INTO Assignment (Name, Description, Points_Possible, Category_ID, Course_ID)
VALUES ('Homework 1', 'ER Models', 60, 1, 1),
       ('Homework 2', 'SQL Schemas and Queries', 80, 1, 1),
       ('Homework 3', 'Anomalies', 70, 1, 1),
       ('Midterm', 'Midterm', 90, 2, 1),
       ('Final Project', 'DB Design', 100, 3, 1),
       ('Final Exam', 'Final Exam', 120, 2, 1),
       ('Assignment 1', 'Derivatives as Limits', 20, 1, 2),
       ('Assignment 2', 'Basic Derivative Rules', 20, 1, 2),
       ('Assignment 3', 'Derivatives of Complex Functions', 20, 1, 2),
       ('Quiz 1', 'Derivatives, Limits, and Tangent Lines', 10, 2, 2),
       ('Introduce Yourself', 'Create a brief introduction video about yourself.', 5, 3, 3),
       ('Personal Essay: Introduce Yourself', 'Write a detailed personal essay introducing yourself.', 45, 4, 3),
       ('Introduce Yourself', 'Create a brief introduction video about yourself.', 5, 3, 4),
       ('Personal Essay: Introduce Yourself', 'Write a detailed personal essay introducing yourself.', 45, 4, 4),
       ('Lab 01', 'Human Anatomy', 50, 5, 5),
       ('Homework 01', 'Genes', 40, 1, 5),
       ('Homework 02', 'Study of the Cell Structure', 60, 1, 5),
       ('Midterm', 'BIOL104 Midterm', 100, 2, 5);


-- CS410 Sp24 Section 1 grades
INSERT INTO Grade (Student_ID, Assignment_ID, Earned_Points)
VALUES
-- John Smith
(1, 1, 55), -- Homework 1
(1, 2, 75), -- Homework 2
(1, 3, 65), -- Homework 3
(1, 4, 85), -- Midterm
(1, 5, 95), -- Final Project
(1, 6, 110), -- Final Exam

-- Jane Miller
(2, 1, 50), -- Homework 1
(2, 2, 70), -- Homework 2
(2, 3, 60), -- Homework 3
(2, 4, 80), -- Midterm
(2, 5, 90), -- Final Project
(2, 6, 105), -- Final Exam

-- Sam Barlow
(4, 1, 58), -- Homework 1
(4, 2, 78), -- Homework 2
(4, 3, 68), -- Homework 3
(4, 4, 88), -- Midterm
(4, 5, 98), -- Final Project
(4, 6, 113), -- Final Exam

-- Gianna Nelson
(6, 1, 53), -- Homework 1
(6, 2, 73), -- Homework 2
(6, 3, 63), -- Homework 3
(6, 4, 83), -- Midterm
(6, 5, 93), -- Final Project
(6, 6, 108), -- Final Exam

-- Kenna Sieves
(7, 1, 52), -- Homework 1
(7, 2, 72), -- Homework 2
(7, 3, 62), -- Homework 3
(7, 4, 82), -- Midterm
(7, 5, 92), -- Final Project
(7, 6, 107); -- Final Exam




