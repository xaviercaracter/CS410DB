-- Create a class
INSERT INTO Class (Course_Number, Term, Section_Number, Description)
VALUES ('CS410', 'Sp24', 1, 'Introduction to Database Systems');

INSERT INTO Class (Course_Number, Term, Section_Number, Description)
VALUES ('Math201', 'Sp24', 1, 'Calculus I');

INSERT INTO Class (Course_Number, Term, Section_Number, Description)
VALUES ('Eng101', 'Sp24', 1, 'Introduction to Composition');

-- add a category
INSERT INTO Category (Category_Name, Weight, Course_Number)
VALUES ('Homework', 0.40, 'CS410');

INSERT INTO Category (Category_Name, Weight, Course_Number)
VALUES ('Exam', 0.40, 'CS410');

INSERT INTO Category (Category_Name, Weight, Course_Number)
VALUES ('Project', 0.20, 'CS410');

INSERT INTO Category (Category_Name, Weight, Course_Number)
VALUES ('Quiz', 0.20, 'Math201');

INSERT INTO Category (Category_Name, Weight, Course_Number)
VALUES ('Essay', 0.50, 'Eng101');

INSERT INTO Category (Category_Name, Weight, Course_Number)
VALUES ('Participation', 0.30, 'Eng101');

-- Add students
INSERT INTO Student (Username, Name)
VALUES ('jsmith123', 'John Smith');

INSERT INTO Student (Username, Name)
VALUES ('bmiller09', 'Jane Miller');

INSERT INTO Student (Username, Name)
VALUES ('alireza', 'Ali Reza');

-- Add enrollment
INSERT INTO Enrollment (Student_ID, Course_Number)
VALUES (1, 'CS410');

INSERT INTO Enrollment (Student_ID, Course_Number)
VALUES (2, 'CS410');

INSERT INTO Enrollment (Student_ID, Course_Number)
VALUES (1, 'Math201');

INSERT INTO Enrollment (Student_ID, Course_Number)
VALUES (3, 'Eng101');

Show tables;
Select * from Class;
Select * from Category;
Select * from Student;
Select * from Enrollment;


-- List Classes, witht the number of students in Each
SELECT c.Course_Number, c.Term, c.Section_Number, c.Description, COUNT(e.Student_ID) AS Enrolled_Students
FROM Class c
LEFT JOIN Enrollment e ON c.Course_Number = e.Course_Number
GROUP BY c.Course_Number, c.Term, c.Section_Number, c.Description
ORDER BY c.Course_Number, c.Term, c.Section_Number;



