# ⚡ CyberBite Express - Food Delivery System

A high-tech full-stack food delivery web application built with a **Java Spring Boot** RESTful backend and a dynamic **Glassmorphism Dark UI** frontend. Features real-time price calculations, live wallet balance tracking, dynamic stock synchronization, transaction logging, and robust domain-specific exception handling.

---

## 🌟 Key Features

- **Java 17 & Spring Boot Architecture**: Structured MVC pattern with REST controllers, DTOs, service layer, and global error handlers.
- **10 Custom Domain Exceptions**: Business logic validation including quantity limits, minimum order totals, pincode serviceability, wallet checks, coupon validation, and restaurant operating hours.
- **Live State & Stock Synchronization**: Real-time stock degradation per menu item and dynamic wallet balance deduction.
- **Glassmorphism UI/UX**: Dark dashboard featuring smooth animations, interactive quantity selectors, and instant pricing recalculations.
- **Automated Transaction Receipts**: Local file-based audit logging (`order_receipts.txt`) generated upon each successful checkout.
- **CORS Enabled**: Seamless cross-origin communication between frontend dashboards and backend APIs.

---

## 🛠️ Tech Stack

### **Backend**
* **Language**: Java 17
* **Framework**: Spring Boot 3.2+ (Web Starter)
* **Build Tool**: Apache Maven (`pom.xml`)
* **Logging**: Java File I/O for transaction receipts

### **Frontend**
* **Markup/Styling**: HTML5, CSS3 (Glassmorphism design system)
* **Scripting**: Vanilla JavaScript (ES6+ Fetch API)
* **Fonts**: Google Inter Font Family

---

## 📂 Project Structure

```text
food-delivery-system/
├── backend/
│   ├── pom.xml
│   └── src/
│       └── main/
│           ├── java/com/fooddelivery/
│           │   ├── FoodDeliveryApplication.java
│           │   ├── controller/
│           │   │   └── OrderController.java
│           │   ├── dto/
│           │   │   ├── OrderRequest.java
│           │   │   └── SystemStateResponse.java
│           │   ├── exception/
│           │   │   ├── FoodDeliveryException.java
│           │   │   ├── GlobalExceptionHandler.java
│           │   │   ├── InsufficientWalletBalanceException.java
│           │   │   ├── InvalidAddressException.java
│           │   │   ├── InvalidCouponException.java
│           │   │   ├── InvalidOrderQuantityException.java
│           │   │   ├── MinimumOrderValueException.java
│           │   │   ├── OutOfDeliveryAreaException.java
│           │   │   ├── PaymentFailedException.java
│           │   │   └── RestaurantClosedException.java
│           │   └── service/
│           │       └── OrderService.java
│           └── resources/
│               └── application.properties
└── frontend/
    ├── index.html
    ├── style.css
    └── app.js
