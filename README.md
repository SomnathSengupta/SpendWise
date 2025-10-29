# SpendWise 💰

**SpendWise** is a multi-user expense management system designed to help individuals track their finances effectively, ensuring they stay on budget and gain clear visibility into their spending habits. Built with a powerful **Spring Boot** backend and a responsive **HTML/CSS/JavaScript** frontend, SpendWise offers personalized financial tracking.

---

## 🌟 Features

SpendWise offers a complete set of tools for daily financial management:

* **Multi-User Authentication:** Secure sign-up and login mechanism, ensuring separation for every user.
* **Expense Tracking:** Easily add new expenses with purpose and amount. Transactions are timestamped by the system.
* **Real-Time Balance:** Instantly view the remaining "in-hand" balance after accounting for all recorded expenses.
* **Flexible Data Filtering:** View expense history filtered by specific **month and year** or view all recorded expenses.
* **Monthly Income Management:** Update the monthly income (or "in-hand" amount) to accurately track available funds.
* **Reporting:** Generate and download PDF reports summarizing spending and balances.

---

## 🛠️ Technology Stack

| Component | Technology | Description |
| :--- | :--- | :--- |
| **Backend** | **Java** (JDK 17+) & **Spring Boot** | High-performance REST API development. |
| **Database** | **MySQL** | Persistent and relational storage for user accounts and expense records. |
| **Frontend** | **HTML5, CSS3, JavaScript** (Vanilla JS) | Single-page application dashboard for user interaction. |
| **Build Tool** | **Maven** | Dependency management and project build automation. |

---

## 🚀 Getting Started

Follow these steps to set up the SpendWise project on your local machine.

### 1. Prerequisites

Before starting, ensure you have the following installed:

* **Java Development Kit (JDK) 17 or higher**
* **Maven**
* **MySQL Database** (version 8.0 recommended)
* **Git**

### 2. Database Setup

1.  Log into your MySQL client.
2.  Create a new database for the application:
    ```sql
    CREATE DATABASE spendwise_db;
    ```
3.  Update the database connection settings in the Spring Boot application properties (usually `src/main/resources/application.properties` or `application.yml`) to match your local credentials:

    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/spendwise_db
    spring.datasource.username=your_mysql_user
    spring.datasource.password=your_mysql_password
    spring.jpa.hibernate.ddl-auto=update
    ```

### 3. Running the Backend

1.  Navigate to the project's root directory in your terminal.
2.  Build the project using Maven:
    ```bash
    mvn clean install
    ```
3.  Run the Spring Boot application:
    ```bash
    mvn spring-boot:run
    # The API will start on: http://localhost:8084
    ```

### 4. Running the Frontend

The frontend is served using simple HTML/CSS/JS.

1.  Ensure the backend is running on `http://localhost:8084`.
2.  Open the `dashboard.html` file in your web browser. (You might need a simple local server extension like Live Server in VS Code for proper functionality, although direct opening often works for simple JS apps).
3.  The application uses `localStorage` to manage the `userId` after a successful simulated login/signup.


## 🤝 Contribution

Contributions are welcome! If you have suggestions or want to improve the codebase, please feel free to fork the repository and submit a pull request.
