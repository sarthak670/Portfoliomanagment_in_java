-- Create the database
CREATE DATABASE PortfolioDB;

-- Use the PortfolioDB database
USE PortfolioDB;

-- Create a table for users
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL
);
INSERT INTO users values
(2563,"sarthak","sarthakmate@gmail.com","5874"),
(2541,"abhish","abhishbondre@gmail.com","8569")
;
select * from users;
-- Create a table for blog posts
CREATE TABLE blog_posts (
    post_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    date_posted DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

select * from blog_posts;
drop database PortfolioDB;
