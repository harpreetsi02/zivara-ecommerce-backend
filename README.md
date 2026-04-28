# 🛒 E-Commerce Backend (Spring Boot)

A robust e-commerce backend built using Spring Boot, providing secure user authentication with Email OTP, product management with category and subcategory filtering, and order processing APIs.

---

## 🚀 Features

- 🔐 Email OTP Authentication (Gmail SMTP)
- 👤 User Registration & Login
- 📦 Product Management (CRUD)
- 🗂️ Category & Subcategory Filtering
- 🛍️ Order Placement System
- 💰 Price Drop & Latest Products APIs
- ⚡ RESTful API Design

---

## 🛠️ Tech Stack

- Java (Spring Boot)
- Spring Data JPA (Hibernate)
- MySQL Database
- Gmail SMTP (Email OTP)
- Maven

---

## 📡 API Highlights

- `POST /api/auth/send-otp`
- `POST /api/auth/verify-otp`
- `POST /api/auth/register`
- `GET /api/products`
- `GET /api/products/category/{slug}?subcategory=...`
- `POST /api/orders`

---

## ⚙️ Setup Instructions

1. Clone the repository:
```bash
git clone https://github.com/your-username/your-repo.git
