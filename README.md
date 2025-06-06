🧳 Travel Agency System
📌 Project Description
The Travel Agency System is a modular, backend-only application built with Spring Boot. It is designed to facilitate seamless hotel and event bookings through RESTful APIs. The system includes user management, hotel booking features, and a personalized dashboard, enabling users to manage their bookings and profiles efficiently.


![System Overview](assets/images/system_overview.png)

✨ Features
🔐 User Management
User registration, login, and password reset.

Profile updates and account management.

Personalized dashboard with an overview of user activity.

🏨 Hotel Booking
Search for available hotels using filters.

Book, cancel, or modify hotel reservations.

View detailed booking history.

📊 User Dashboard
Displays:

Profile information

Booking history with hotel names, stay duration, and room types.

🛠 Technologies Used
Java 17+ – Programming language

Spring Boot – Backend framework

JAX-RS – REST API development

PostgreSQL – Relational database

Maven – Build and dependency management

Postman – API testing tool

IntelliJ IDEA – Development environment

🚀 Getting Started
✅ Prerequisites
Ensure the following tools are installed:

Java 17 or later

Maven

PostgreSQL

Postman

📂 Installation Steps
Clone the repository

bash
Copy
Edit
git clone <repository_url>
Navigate to the project directory

bash
Copy
Edit
cd travel_agency_system
Configure the PostgreSQL connection
Update application.properties with your database credentials.

Build the project

bash
Copy
Edit
mvn clean install
Run the application

bash
Copy
Edit
mvn spring-boot:run
Access the API

arduino
Copy
Edit
http://localhost:8080
🧪 API Endpoints
🔐 User Management
POST /users/register – Register a new user

POST /users/login – Authenticate and log in

POST /users/reset-password – Reset user password

🏨 Hotel Booking
GET /hotels/search – Search for hotels

POST /hotels/book – Make a booking

DELETE /hotels/cancel/{bookingId} – Cancel a booking

📊 User Dashboard
GET /users/{userId}/dashboard – View user profile and bookings

🧪 Testing with Postman
Import the included Postman Collection (located in the repository).

Use the predefined API requests for each endpoint.

Modify request payloads as needed and verify the responses.

📁 Project Structure
bash
Copy
Edit
travel_agency_system/
├── src/
│   └── main/
│       ├── java/
│       │   ├── usermanagement/       # User-related logic
│       │   ├── hotelbooking/         # Hotel booking features
│       │   ├── eventbooking/         # Event booking features
│       │   └── notifications/        # Notification system
│       └── resources/
│           └── application.properties # Application configuration
├── pom.xml                            # Maven project file
└── README.md                          # Project documentation
📬 Contribution
Contributions, issues, and feature requests are welcome. Feel free to open an issue or submit a pull request.

