# 🛒 E-commerce Application (Java)

This is a Java-based **E-commerce application** that I developed to demonstrate backend development, database integration, and secure user authentication. The project simulates the core features of an online shopping system, such as **user registration & login, product management, shopping cart, and order handling**.  

It is built as a **console-driven application** using **Java + MySQL**, structured with **MVC principles** for clean separation of concerns.  

---

## 🚀 Features

### 👤 User Management
- Register new users with role assignment (Customer / Employee / Admin)  
- Secure login with password hashing (**BCrypt**)  

### 📦 Product Management
- Add, update, delete, and view products  
- Store product details in a persistent database  

### 🛍 Shopping Flow
- Browse product catalog  
- Add products to cart  
- Place and manage orders  

### 💾 Data Persistence
- MySQL database to store users, products, and orders  
- JDBC for database connectivity  

### 🏗 Architecture
- **Controller Layer** → Business logic (Auth, Products, Cart, etc.)  
- **Model Layer** → Entities like `User`, `Role`, `Product`, `Order`  
- **Database Layer** → Connection handling (`databaseconnection.java`)  

---

## 🛠 Tech Stack

- **Language:** Java (JDK 17+)  
- **Database:** MySQL  
- **Libraries:**  
  - JDBC → Database connectivity  
  - BCrypt → Password encryption  
- **Tools:** IntelliJ IDEA / Eclipse / VS Code  
- **Build Tool:** (Manual `javac/java` currently, Maven/Gradle planned)  

---

## Project Structure
E-commerce-java/
│
├── src/
│ └── code/
│ ├── controller/ # Services: AuthService, ProductService, CartServices
│ ├── model/ # Data models: User, Role, Product, Order
│ ├── Database/ # Database connection setup
│ └── main/ # Entry point (MainApp.java)
│
├── lib/ # External libraries (e.g., MySQL connector, BCrypt)
├── README.md # Project documentation
└── .gitignore

---

---

## ⚙️ Getting Started

### ✅ Prerequisites
- JDK 17 or higher  
- MySQL installed locally  
- IDE (IntelliJ / Eclipse / VS Code)  

### 🔧 Setup

1. Clone the repository:  
   ```bash
   git clone https://github.com/Bhavya-mahyavanshi/E-commerce-java.git
   cd E-commerce-java

2. Set up the database:
   ## Create a database in MySQL:

   CREATE DATABASE ecommerce;

   Update DB credentials inside databaseconnection.java (username, password, URL).

3. Compile the code:

   javac -cp ".:lib/*" src/code/**/*.java


4. Run the application:

   java -cp ".:lib/*" src.code.main.MainApp


---

✅ This version is **ready for recruiters**:  
- Explains the project clearly  
- Shows your **skills and stack**  
- Lists **future updates** (shows ambition)  
- Explains **why you built it** (learning outcomes)  

Do you want me to also **add sample SQL schema & queries** (like `users`, `products`, `orders` tables) inside the README so recruiters see your DB design too?


## 📂 Project Structure

