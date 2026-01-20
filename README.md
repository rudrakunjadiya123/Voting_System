# 🗳️ Console-Based Voting System (Java + MySQL)

A **console-based Voting Management System** developed using **Java** and **MySQL**, designed to simulate a secure and structured election process.  
The system supports **Admin**, **User (Voter)**, and **Party (Candidate)** roles, enabling region-wise elections, percentage voting analysis, and controlled election flow.

---

## 📌 Project Overview

This project is built to demonstrate:
- Core Java concepts
- JDBC connectivity with MySQL
- Role-based access control
- Real-world election workflow simulation
- Database-driven console application

The system ensures **one vote per Aadhaar ID**, region-specific voting, and controlled election lifecycle management.
---
## 🎥 Project Demo

▶️ **Video Walkthrough:**  
https://youtu.be/HYs3jTMOx6U

---

## ⚙️ Technologies Used

- **Java (Core Java, OOP Concepts)**
- **MySQL Database**
- **JDBC (Java Database Connectivity)**
- **Console-based UI**
- **NetBeans**

---

## 👥 User Roles & Functionalities

### 🔐 1. Admin Panel

Admin has complete control over the election process.

**Features:**
1. View All Election Results  
2. View Percentage Voting Results  
3. View Region-wise Results  
4. Start Election  
5. End Election  
6. Add New Region  
7. Remove Existing Region  
8. Reset Election Data  
9. Back to Main Menu  

**Purpose:**
- Controls election lifecycle
- Manages regions
- Analyzes voting data

---

### 🧑‍💼 2. User Panel (Voter)

**Features:**
1. User Registration  
2. Login & Vote  
3. Back to Main Menu  

**Voting Flow:**
- User enters Aadhaar ID
- Available parties and candidates are displayed
- User selects a party
- Vote is recorded for the user’s region
- Duplicate voting is strictly prevented
---

### 🏛️ 3. Party Panel (Candidate Management)

**Features:**
1. Add Candidate  
2. Remove Candidate  
3. Logout  

**Candidate Registration Details:**
- Candidate Name
- Mobile Number
- Date of Birth
- Region
- Party Name
---
### Database Tables:
* MySQL database with following tables:
- region: Electoral region management
- party: Political party information and seat counts
- party_represent: Party representative credentials
- party_candidates: Candidate information and vote counts
- user: Voter information and voting status

## ▶️ How to Run the Project

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/your-username/Voting_System.git
```
### 2️⃣ Import Database

Create a MySQL database (e.g., voting_system)
Import the provided .sql file
Update database credentials in the Java source code

### 3️⃣ Configure JDBC
Ensure the MySQL JDBC Driver is added to your project:
com.mysql.cj.jdbc.Driver

### 4️⃣ Start MySQL server
Initialize database with provided schema
Set root password to hello@123

## Application Execution
### compilation
```bash
javac -cp ".;mysql-connector-j-9.4.0.jar" Vote_system.java
```

### execution
```bash
java -cp ".;mysql-connector-j-9.4.0.jar" Vote_system
```
