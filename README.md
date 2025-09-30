# CryptoLik

## Project Description

CryptoLik is a Java-based cryptocurrency simulation application that implements blockchain-like functionality with a mempool service for transaction management. The application features a modular architecture with dependency injection, database integration, and a user-friendly menu-driven interface.

The project simulates cryptocurrency operations including transaction processing, mempool management, and user authentication, providing a platform for understanding blockchain concepts.

## Technologies Used

- **Java** - Core programming language
- **Database Integration** - Custom database layer for data persistence
- **Dependency Injection** - Custom DI container for modular architecture
- **Multi-threading** - Asynchronous mempool processing
- **Menu-driven UI** - Interactive console-based user interface

## Project Structure

```
CryptoLik/
├── src/                          # Source code directory
│   ├── Main.java                 # Application entry point
│   ├── config/                   # Configuration files and settings
│   ├── db/                       # Database layer and initialization
│   ├── di/                       # Dependency injection system
│   ├── dto/                      # Data Transfer Objects
│   ├── entity/                   # Data models and entity classes
│   ├── exceptions/               # Custom exception classes
│   ├── mapper/                   # Object mapping utilities
│   ├── repository/               # Data access layer
│   ├── service/                  # Business logic services
│   ├── ui/                       # User interface components
│   └── util/                     # Utility classes and helpers
└── README.md                     # Project documentation
```

## Prerequisites and Installation

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- IDE (IntelliJ IDEA, Eclipse, or VS Code recommended)
- Git (for version control)

### Installation

1. **Clone the repository:**

   ```bash
   git clone git@github.com:tahajaiti/CryptoLik.git
   cd CryptoLik
   ```

2. **Compile the project:**

   ```bash
   javac -cp "lib/*:." src/**/*.java -d out
   ```

3. **Run the application:**

   ```bash
   java -cp "lib/*:out" Main
   ```

   Or if using an IDE:

   - Import the project
   - Set the main class as `Main.java`
   - Run the project

## Usage Guide

### Starting the Application

1. Launch the application by running the `Main` class
2. The system will automatically:
   - Initialize the dependency injection container
   - Set up the database
   - Start the mempool service in a separate thread
   - Launch the authentication menu

### Main Features

#### Authentication System

- The application starts with an authentication menu
- Users can register new accounts or log into existing ones

#### Mempool Service

- Runs continuously in the background
- Processes pending transactions
- Manages transaction validation and queuing

#### Menu Navigation

- Navigate through different menus using the provided options
- Each menu provides specific cryptocurrency-related functionalities

### Basic Workflow

1. **Start Application** → Authentication required
2. **Login/Register** → Access main features
3. **Transaction Management** → Create and manage transactions
4. **Mempool Monitoring** → View pending transactions
5. **Account Management** → Manage user accounts and balances

## Screenshots

---
