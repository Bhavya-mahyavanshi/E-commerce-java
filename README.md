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

## 📂 Project Structure

```
E-commerce-java/
│
├── src/
│   └── code/
│       ├── controller/     # Services: AuthService, ProductService, CartServices
│       ├── model/          # Data models: User, Role, Product, Order
│       ├── Database/       # Database connection setup
│       └── main/           # Entry point (MainApp.java)
│
├── lib/                    # External libraries (e.g., MySQL connector, BCrypt)
├── README.md               # Project documentation
└── .gitignore
```

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
   ```

2. Set up the database:  
   - Create a database in MySQL:
     ```sql
     CREATE DATABASE ecommerce;
     ```
   - Update DB credentials inside `databaseconnection.java` (username, password, URL).  

3. Compile the code:  
   ```bash
   javac -cp ".:lib/*" src/code/**/*.java
   ```

4. Run the application:  
   ```bash
   java -cp ".:lib/*" src.code.main.MainApp
   ```

---

## 📖 Usage

When you run the program, a **menu-driven console interface** appears:

```
1) Login
2) Register
3) Exit
```

- **Register** → Create a new account (choose role: Customer / Employee)  
- **Login** → Access the system  
  - Customers can browse products, add to cart, and place orders  
  - Employees/Admins can manage products (add/edit/delete)  

---

## 🔮 Roadmap / Future Enhancements

To make this project closer to a real-world system, I plan to add:

- ✅ Role-based access control (Admin / Employee / Customer)  
- ✅ Persistent shopping cart (saved in DB)  
- ✅ Product search, filtering & sorting  
- ✅ Order tracking (Pending → Shipped → Delivered)  
- ✅ Product images & detailed descriptions  
- ✅ Payment gateway simulation (Stripe/PayPal integration)  
- ✅ Email notifications (order confirmations, password reset)  
- ✅ Pagination for product listings  
- ✅ Unit & integration tests (JUnit)  
- ✅ Dockerization + CI/CD pipeline  

---

## 📌 Why This Project?

I created this project to:

- Strengthen my **Java and OOP skills**  
- Learn **database integration with JDBC & MySQL**  
- Implement **secure authentication** using hashed passwords  
- Practice **modular project design (MVC pattern)**  
- Build a **portfolio-ready project** to showcase on my CV  

---

## 👤 Author

**Bhavya Mahyavanshi**  
📍 Computer Programming Student – Seneca Polytechnic  

🔗 [GitHub](https://github.com/Bhavya-mahyavanshi)  
🔗 [LinkedIn](https://www.linkedin.com/) _(add yours here)_  

---

## 📜 License

This project is open-source and available under the **MIT License**.  
© 2025 Bhavya Mahyavanshi  
