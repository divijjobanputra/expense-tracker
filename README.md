# Expense Tracker (Spring Boot + MySQL + HTML/JS)

## Tech
Java, Spring Boot, Spring Data JPA, MySQL, HTML, JavaScript

## Features
- Add transaction (INCOME/EXPENSE)
- List transactions with optional filters (category, from/to dates)
- Delete transaction
- Monthly summary (income, expense, balance)

## Run Backend
1. Create DB:
    - expense_tracker_db
2. Update `application.properties` with SQL username/password
3. Run Spring Boot app

Backend runs on: http://localhost:8080

## API Endpoints
- POST `/api/transactions`
- GET `/api/transactions?category=&from=&to=`
- DELETE `/api/transactions/{id}`
- GET `/api/summary?month=YYYY-MM`

## Run Frontend
Open `frontend/index.html` in browser.
