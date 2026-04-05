# Finance Data Processing and Access Control Backend

## 📌 Project Objective
This project is a Spring Boot-based backend for a **Finance Dashboard System**. It manages financial records, handles user roles (Admin, Analyst, Viewer), and provides summary-level analytics for a dashboard.

---

## 🚀 Features
* **User & Role Management:** Three distinct roles (Admin, Analyst, Viewer) with specific permissions.
* **Financial Records CRUD:** Full support for creating, reading, updating, and deleting transactions.
* **Dashboard Summary API:** Aggregated data including Total Income, Total Expenses, Net Balance, and Category-wise totals.
* **Role-Based Access Control (RBAC):** Secured endpoints using Spring Security with `@PreAuthorize` annotations.
* **Data Persistence:** Integrated with MySQL database using Spring Data JPA.

---

## 🛠️ Tech Stack
* **Java:** 17
* **Framework:** Spring Boot 3.x
* **Security:** Spring Security (Basic Auth)
* **Database:** MySQL
* **Build Tool:** Maven

---

## 🔐 Access Control Matrix

| Role | Summary View | Record View | Create/Update/Delete |
| :--- | :--- | :--- | :--- |
| **Admin** | ✅ Yes | ✅ Yes | ✅ Yes |
| **Analyst** | ✅ Yes | ✅ Yes | ❌ No |
| **Viewer** | ✅ Yes | ❌ No | ❌ No |

**Credentials (In-Memory):**
* **Admin:** `admin` / `admin123`
* **Analyst:** `analyst` / `analyst123`
* **Viewer:** `viewer` / `viewer123`

---

## 🔌 API Endpoints

### 1. Finance Summary
* **URL:** `GET /api/finance/summary`
* **Access:** Admin, Analyst, Viewer
* **Description:** Returns total income, expenses, balance, and category breakdown.

### 2. Financial Records
* **URL:** `GET /api/finance/records`
* **Access:** Admin, Analyst
* **Description:** Returns a list of all transactions.

### 3. Admin Operations
* **URL:** `POST /api/finance/admin/records`
* **Access:** Admin Only
* **Body Example:**
```json
{
    "amount": 2500.0,
    "type": "INCOME",
    "category": "Salary",
    "date": "2026-04-05",
    "description": "April Salary"
}