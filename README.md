YapSpace



YapSpace is a Java-based digital community platform where users aged 13 and above can share their thoughts, feelings, and experiences through posts while connecting and interacting with others through reactions and comments.



Made for OOP 101 (Team 4, Ro Ro Row Your Code).



Project Background



People aged 13 and above experience different concerns involving school, family, friendships, relationships, personal experiences, and emotional well-being. YapSpace aims to provide a digital community space where users can express themselves and connect with others.



Project Objectives



General Objective



To develop a Java-based digital community application that provides users aged 13 and above with a space to express their thoughts, feelings, and experiences.



Specific Objectives



1\. Develop a registration and login system.

2\. Allow users to create, edit, and delete their own posts.

3\. Provide categories for organizing posts.

4\. Allow users to express their current feelings.

5\. Enable reactions and comments.

6\. Display posts through a community feed.

7\. Provide a My Posts section.

8\. Apply Java and Object-Oriented Programming concepts.



What It Does



\- Sign up and log in.

\- Community feed with posts, newest first.

\- Create posts with a category and feeling.

\- React to posts (Support, Relate, Encourage).

\- Add, edit, and delete comments.

\- My Posts: view, edit, and delete your own posts.



Available Categories



\- School

\- Family

\- Friendship

\- Relationship

\- Mental Health

\- Career

\- Hobbies

\- Personal

\- Random Thoughts



Available Feelings



\- Happy

\- Sad

\- Angry

\- Stressed

\- Excited

\- Tired

\- Confused

\- Neutral



Technologies Used



\- Java

\- Object-Oriented Programming (OOP)

\- MySQL

\- JDBC (MySQL Connector/J)

\- Git and GitHub



Database Setup



1\. Install and open XAMPP.

2\. Start Apache and MySQL.

3\. Open phpMyAdmin at "http://localhost/phpmyadmin".

4\. Create or import the "yapspace\_db" database using the project's SQL setup script.

5\. Make sure the database connection settings in "src/database/DatabaseConnection.java" match your MySQL configuration.



Default local configuration:



\- Database: "yapspace\_db"

\- Host: "localhost"

\- Port: "3306"

\- Username: "root"

\- Password: empty by default in XAMPP



Run It in VS Code



1\. Install Java 17 or newer.

2\. Install the Extension Pack for Java in VS Code.

3\. Open the YapSpace project folder.

4\. Make sure XAMPP MySQL is running.

5\. Compile and run the project using the commands below.



Windows (CMD)



Compile:



powershell -NoProfile -Command "$files = Get-ChildItem -Recurse src -Filter \*.java | ForEach-Object { $\_.FullName }; \& javac -cp 'lib\\mysql-connector-j-26.10.0.jar' -d out $files"



Run:



java -cp "out;lib\\mysql-connector-j-26.10.0.jar" yapspace.Main



6\. Open "http://localhost:8080" in your browser.



Press Ctrl+C to stop the server.



Project Structure



YapSpace/

├── lib/

│   └── mysql-connector-j-26.10.0.jar

├── src/

│   ├── database/

│   └── yapspace/

│       ├── model/

│       ├── exception/

│       ├── repository/

│       ├── service/

│       ├── storage/

│       └── web/

├── data/

├── README.md

└── other project files



Object-Oriented Programming Concepts



Concept| Where It Is Used

Classes and Objects| User, Post, Comment, Reaction, Category

Methods| register(), login(), createPost(), editPost(), deletePost()

Encapsulation| Private fields and getters/setters

Inheritance| RegularUser and AdminUser extend User

Abstraction| Abstract classes and PasswordHasher

Polymorphism| Different user behaviors through overridden methods

Exception Handling| Try-catch blocks and custom exceptions

Data Handling| ArrayLists and MySQL database repositories



Not Included



Search, reporting posts, image uploads, and private messaging are not included in the current version.

