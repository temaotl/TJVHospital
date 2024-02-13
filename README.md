# TJVHospital

## Overview
The Online Hospital Management System is designed to facilitate the management of hospital operations, focusing on patient care, procedures, and medical card management. This Java-based project, built using the Spring Framework, provides a robust backend that supports full CRUD operations (Create, Read, Update, Delete) for managing patients, their associated medical cards, and the medical procedures they undergo. It also features a RESTful API that handles various types of HTTP requests for seamless interaction with the system.

## Entities and Relationships
- **Patient**: Represents individuals receiving medical attention.
- **Card**: A unique identifier for patients, containing vital medical information. Each patient has one card (OneToOne relationship).
- **Procedure**: Medical interventions or treatments that patients may undergo. Patients can participate in many procedures, and each procedure can involve many patients (ManyToMany relationship).

## Features
- **CRUD Operations**: Manage patients, cards, and procedures through create, read, update, and delete functionalities.
- **RESTful API**: Interface with the system using all types of REST requests (GET, POST, PUT, DELETE) to perform operations on patients, cards, and procedures.
- **Data Relationships**: Efficient handling of OneToOne and ManyToMany relationships between entities.

## Technology Stack
- **Programming Language**: Java
- **Framework**: Spring 
- **Database**: PostgreSQL 
- **API Testing**: Postman or any REST client for testing API endpoints

## Getting Started
 Access the API through your local server at `http://localhost:8080`.

## GIT BRANCH
--In the main and developer branches, you can find the new version without the Front-end.
--In the old branch, you can find everything including the Front-end.

