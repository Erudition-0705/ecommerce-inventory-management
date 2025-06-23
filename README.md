# ecommerce-inventory-management

# E-commerce Inventory Management System (Spring Boot + Redis + MySQL)

This is a backend service for managing e-commerce inventory. It provides RESTful APIs to handle item supply, reservations, cancellations, and availability queries with Redis caching support to improve performance.

---

## Features

- Add or supply stock for a product (item)
- Reserve item quantity 
- Cancel an existing reservation
- Check available quantity for a specific item
- Built-in Redis caching for fast access to availability data
- Clean layered architecture (Controller, Service, Repository)
- Exception handling and input validations
- API documentation 

---

## Tech Stack

- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA**
- **MySQL 8**
- **Redis** 
- **Lombok**
- **Maven**
- **JUnit 5 + Mockito**

---

## Getting Started

### Prerequisites

Make sure you have the following installed:

- Java 17+
- Maven
- MySQL Workbench 8+
- Redis
- Postman (for API testing)

---

## Database Setup (MySQL)

Open MySQL Workbench and create a new database:
   ```sql
   CREATE DATABASE ecommerce_inventory;
