-- Create a class
INSERT INTO Class (Course_Number, Term, Section_Number, Description)
VALUES ('CS410', 'Sp24', 1, 'Introduction to Database Systems');
INSERT INTO Class (Course_Number, Term, Section_Number, Description)
VALUES ('MATH201', 'Sp24', 1, 'Calculus I');
INSERT INTO Class (Course_Number, Term, Section_Number, Description)
VALUES ('ENG101', 'Sp24', 1, 'Introduction to Composition');
INSERT INTO Class (Course_Number, Term, Section_Number, Description)
VALUES ('ENG101', 'Sp24', 2, 'Introduction to Composition');
INSERT INTO Class (Course_Number, Term, Section_Number, Description)
VALUES ('BIOL104', 'Sp24', 1, 'Introduction to Human Biology');



INSERT INTO Class (Course_Number, Term, Section_Number, Description)
VALUES ('CS410', 'Fa23', 1, 'Introduction to Database Systems');
INSERT INTO Class (Course_Number, Term, Section_Number, Description)
VALUES ('ENG101', 'Fa23', 1, 'Introduction to Composition');
INSERT INTO Class (Course_Number, Term, Section_Number, Description)
VALUES ('MATH301', 'Fa23', 1, 'Introduction to Linear Algebra');



-- add a category
INSERT INTO Category (Category_Name, Weight, Course_ID)
VALUES ('Homework', 0.40, 1); -- CS410 Sp24
INSERT INTO Category (Category_Name, Weight, Course_ID)
VALUES ('Exam', 0.40, 1);
INSERT INTO Category (Category_Name, Weight, Course_ID)
VALUES ('Project', 0.20, 1);

INSERT INTO Category (Category_Name, Weight, Course_ID)
VALUES ('Quiz', 0.20, 2); -- MATH201 Sp24
INSERT INTO Category (Category_Name, Weight, Course_ID)
VALUES ('Homework', 0.40, 2); 
INSERT INTO Category (Category_Name, Weight, Course_ID)
VALUES ('Exam', 0.40, 2); 

INSERT INTO Category (Category_Name, Weight, Course_ID)
VALUES ('Essay', 0.50, 3); -- ENG101 Sp24 Sec 1
INSERT INTO Category (Category_Name, Weight, Course_ID)
VALUES ('Classwork', 0.20, 3); 
INSERT INTO Category (Category_Name, Weight, Course_ID)
VALUES ('Participation', 0.30, 3); 

INSERT INTO Category (Category_Name, Weight, Course_ID)
VALUES ('Essay', 0.50, 4); -- ENG101 Sp24 Sec 2
INSERT INTO Category (Category_Name, Weight, Course_ID)
VALUES ('Classwork', 0.20, 4); 
INSERT INTO Category (Category_Name, Weight, Course_ID)
VALUES ('Participation', 0.30, 4); 

INSERT INTO Category (Category_Name, Weight, Course_ID)
VALUES ('Lab', 0.30, 5); -- BIOL104 Sp24
INSERT INTO Category (Category_Name, Weight, Course_ID)
VALUES ('Homework', 0.30, 5); 
INSERT INTO Category (Category_Name, Weight, Course_ID)
VALUES ('Exam', 0.35, 5); 
INSERT INTO Category (Category_Name, Weight, Course_ID)
VALUES ('Participation', 0.05, 5); 


-- Add students
INSERT INTO Student (Username, Name)
VALUES ('jsmith123', 'John Smith');
INSERT INTO Student (Username, Name)
VALUES ('bmiller09', 'Jane Miller');
INSERT INTO Student (Username, Name)
VALUES ('alireza', 'Ali Reza');
INSERT INTO Student (Username, Name)
VALUES ('sbarlow', 'Sam Barlow');
INSERT INTO Student (Username, Name)
VALUES ('eramsey', 'Elliot Ramsey');
INSERT INTO Student (Username, Name)
VALUES ('gnelson02', 'Gianna Nelson');
INSERT INTO Student (Username, Name)
VALUES ('ksieves', 'Kenna Sieves');
INSERT INTO Student (Username, Name)
VALUES ('nwelch313', 'Natalie Welch');
INSERT INTO Student (Username, Name)
VALUES ('tjanson', 'Thomas Janson');
INSERT INTO Student (Username, Name)
VALUES ('cbrooks', 'Charlie Brooks');


-- Add enrollment
INSERT INTO Enrollment (Student_ID, Course_ID)
VALUES (1, 1); -- CS410 Sp24
INSERT INTO Enrollment (Student_ID, Course_ID)
VALUES (2, 1);
INSERT INTO Enrollment (Student_ID, Course_ID)
VALUES (4, 1);
INSERT INTO Enrollment (Student_ID, Course_ID)
VALUES (6, 1);
INSERT INTO Enrollment (Student_ID, Course_ID)
VALUES (7, 1);


INSERT INTO Enrollment (Student_ID, Course_ID)
VALUES (1, 2); -- MATH201 Sp24
INSERT INTO Enrollment (Student_ID, Course_ID)
VALUES (4, 2); 
INSERT INTO Enrollment (Student_ID, Course_ID)
VALUES (6, 2); 
INSERT INTO Enrollment (Student_ID, Course_ID)
VALUES (10, 2); 


INSERT INTO Enrollment (Student_ID, Course_ID)
VALUES (3, 3); -- ENGL101 Sp24 Sec 1
INSERT INTO Enrollment (Student_ID, Course_ID)
VALUES (4, 3);
INSERT INTO Enrollment (Student_ID, Course_ID)
VALUES (5, 3);
INSERT INTO Enrollment (Student_ID, Course_ID)
VALUES (8, 3);
INSERT INTO Enrollment (Student_ID, Course_ID)
VALUES (10, 3);


INSERT INTO Enrollment (Student_ID, Course_ID)
VALUES (1, 4); -- ENGL101 Sp24 Sec 2
INSERT INTO Enrollment (Student_ID, Course_ID)
VALUES (2, 4);
INSERT INTO Enrollment (Student_ID, Course_ID)
VALUES (7, 4);
INSERT INTO Enrollment (Student_ID, Course_ID)
VALUES (9, 4);


INSERT INTO Enrollment (Student_ID, Course_ID)
VALUES (2, 5); -- BIOL104 Sp24
INSERT INTO Enrollment (Student_ID, Course_ID)
VALUES (3, 5);
INSERT INTO Enrollment (Student_ID, Course_ID)
VALUES (6, 5);
INSERT INTO Enrollment (Student_ID, Course_ID)
VALUES (8, 5);
INSERT INTO Enrollment (Student_ID, Course_ID)
VALUES (9, 5);
INSERT INTO Enrollment (Student_ID, Course_ID)
VALUES (10, 5);

--- Assignments
INSERT INTO Assignment (Name, Description, Points_Possible, Due_Date, Category_Name, Course_ID)
VALUES ('Homework 1', 'ER Models', 60, '2024-01-26', 'Homework', 1); -- CS410 Sp24
INSERT INTO Assignment (Name, Description, Points_Possible, Due_Date, Category_Name, Course_ID)
VALUES ('Homework 2', 'SQL Schemas and Queries', 80, '2024-02-16', 'Homework', 1);
INSERT INTO Assignment (Name, Description, Points_Possible, Due_Date, Category_Name, Course_ID)
VALUES ('Homework 3', 'Anomalies', 70, '2024-04-05', 'Homework', 1);
INSERT INTO Assignment (Name, Description, Points_Possible, Due_Date, Category_Name, Course_ID)
VALUES ('Midterm', 'Midterm', 90, '2024-03-06', 'Exam', 1); 
INSERT INTO Assignment (Name, Description, Points_Possible, Due_Date, Category_Name, Course_ID)
VALUES ('Final Project', 'DB Design', 100, '2024-04-30', 'Project', 1);
INSERT INTO Assignment (Name, Description, Points_Possible, Due_Date, Category_Name, Course_ID)
VALUES ('Final Exam', 'Final Exam', 120, '2024-05-01', 'Exam', 1);


INSERT INTO Assignment (Name, Description, Points_Possible, Due_Date, Category_Name, Course_ID)
VALUES ('Assignment 1', 'Derivatives as Limits', 20, '2024-01-08', 'Homework', 2); -- MATH201 Sp24
INSERT INTO Assignment (Name, Description, Points_Possible, Due_Date, Category_Name, Course_ID)
VALUES ('Assignment 2', 'Basic Derivative Rules', 20, '2024-01-15', 'Homework', 2); 
INSERT INTO Assignment (Name, Description, Points_Possible, Due_Date, Category_Name, Course_ID)
VALUES ('Assignment 3', 'Derivatives of Complex Functions', 20, '2024-01-22', 'Homework', 2); 
INSERT INTO Assignment (Name, Description, Points_Possible, Due_Date, Category_Name, Course_ID)
VALUES ('Quiz 1', 'Derivatives, Limits, and Tangent Lines', 10, '2024-01-17', 'Quiz', 2); 


INSERT INTO Assignment (Name, Description, Points_Possible, Due_Date, Category_Name, Course_ID)
VALUES ('Introduce Yourself', 'Create a brief introduction video about yourself.', 5, '2024-01-08', 'Classwork', 3); -- ENGL101 Sp24 1
INSERT INTO Assignment (Name, Description, Points_Possible, Due_Date, Category_Name, Course_ID)
VALUES ('Personal Essay: Introduce Yourself', 'Write a detailed personal essay introducing yourself.', 45, '2024-01-16', 'Essay', 3);

INSERT INTO Assignment (Name, Description, Points_Possible, Due_Date, Category_Name, Course_ID)
VALUES ('Introduce Yourself', 'Create a brief introduction video about yourself.', 5, '2024-01-08', 'Classwork', 4); -- ENGL101 Sp24 2
INSERT INTO Assignment (Name, Description, Points_Possible, Due_Date, Category_Name, Course_ID)
VALUES ('Personal Essay: Introduce Yourself', 'Write a detailed personal essay introducing yourself.', 45, '2024-01-16', 'Essay', 4);

INSERT INTO Assignment (Name, Description, Points_Possible, Due_Date, Category_Name, Course_ID)
VALUES ('Lab 01', 'Human Anatomy', 50, '2024-01-15', 'Lab', 5); -- BIOL104 Sp24 1
INSERT INTO Assignment (Name, Description, Points_Possible, Due_Date, Category_Name, Course_ID)
VALUES ('Homework 01', 'Genes', 40, '2024-01-20', 'Homework', 5);
INSERT INTO Assignment (Name, Description, Points_Possible, Due_Date, Category_Name, Course_ID)
VALUES ('Homework 02', 'Study of the Cell Structure', 60, '2024-02-03', 'Homework', 5); 
INSERT INTO Assignment (Name, Description, Points_Possible, Due_Date, Category_Name, Course_ID)
VALUES ('Midterm', 'BIOL104 Midterm', 100, '2024-02-22', 'Exam', 5);


Show tables;
Select * from Class;
Select * from Category;
Select * from Student;
Select * from Enrollment;

-- List Classes, witht the number of students in Each
SELECT c.Course_ID, c.Term, c.Section_Number, c.Description, COUNT(e.Student_ID) AS Enrolled_Students
FROM Class c
LEFT JOIN Enrollment e ON c.Course_ID = e.Course_ID
GROUP BY c.Course_ID, c.Term, c.Section_Number, c.Description
ORDER BY c.Course_ID, c.Term, c.Section_Number;
