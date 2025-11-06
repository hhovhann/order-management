# Order Management Application

A production-ready Order Management System built with **Hexagonal Architecture**, following **SOLID**, **KISS**, **DRY**, and **Enterprise Integration Patterns (EIP)**.

---

## 🏗️ Architecture

### Hexagonal Architecture (Ports and Adapters)

```
┌──────────────────────────────────────────────────────────────┐
│                  Adapters (Infrastructure)                   │
│  ┌──────────────┐             ┌──────────────┐               │
│  │ REST API     │             │ MyBatis      │               │
│  │ Controllers  │             │ Repositories │               │
│  └──────────────┘             └──────────────┘               │
│          │                          │                        │
└──────────┼──────────────────────────┼────────────────────────┘
           │                          │
┌──────────┼──────────────────────────┼────────────────────────┐
│          ▼                          ▼                        │
│  ┌──────────────┐           ┌──────────────┐                 │
│  │ Input Ports  │           │ Output Ports │                 │
│  │ (Services)   │           │ (Repositories)│                │
│  └──────────────┘           └──────────────┘                 │
│           │                          │                       │
│           └──────────┬───────────────┘                       │
│                      ▼                                       │
│            ┌───────────────────┐                             │
│            │ Domain Layer      │                             │
│            │ (Business Logic)  │                             │
│            └───────────────────┘                             │
│                   Application Core (Domain)                  │
└──────────────────────────────────────────────────────────────┘
```

### Package Structure

```
am.hhovhann.order_management/
├── adapter/
│   ├── in/web/                   # REST controllers + DTOs + handlers
│   ├── out/persistence/          # MyBatis repository adapters
│   │   └── mybatis/              # Mapper interfaces + XMLs
│   └── integration/camel/        # Apache Camel routes
├── application/
│   ├── port/in/                  # Input ports (use cases)
│   ├── port/out/                 # Output ports (repositories)
│   └── service/                  # Service implementations
├── domain/
│   ├── model/                    # Domain entities (records)
│   └── exception/                # Domain exceptions
└── configuration/                # Spring, OpenAPI & MyBatis configs
```

---

## 🚀 Technology Stack

| Component             | Version | Purpose             |
|-----------------------|---------|---------------------|
| **Spring Boot**       | 3.5.7   | Core framework      |
| **Java**              | 21      | Runtime             |
| **MyBatis**           | 3.0.5   | ORM / Mapper        |
| **Apache Camel**      | 4.15.0  | Event routing (EIP) |
| **OpenAPI**           | 3.0.3   | API documentation   |
| **Gradle**            | 8.11    | Build tool          |
| **Flyway**            | 10.x    | DB migrations       |
| **PostgreSQL**        | 17.x    | Demo database       |
| **JUnit 5 / Mockito** | Latest  | Testing             |

---

## ⚙️ Setup & Run

```bash
git clone https://github.com/<your-user>/order-management.git
cd order-management
./gradlew clean build
./gradlew bootRun
```

### 🐳 Docker / Compose

```bash
docker-compose up --build
# or
docker build -t order-management:latest .
docker run -p 8080:8080 order-management:latest
```

---

## 🗄️ Database Migrations

Flyway automatically executes:

- **V1__init.sql** – creates `customer` & `orders`
- **V2__indexes.sql** – adds indexes and optimizations

Configuration (in `application.yml`):

```yaml
spring:
  flyway:
    enabled: true
    baseline-on-migrate: true
    locations: classpath:db/migration
```

---

## 🔐 Security

In-memory users (for demo):

| Username | Password   | Role |
|-----------|------------|------|
| `admin` | `admin123` | ADMIN |
| `user` | `user123`  | USER |

All `/api/v1/**` endpoints require Basic Auth.

---

## 📡 REST API Summary

| Entity | Operations |
|--------|-------------|
| Customer | Create · Update · Delete · Find · List (paged search) |
| Order | Create · Update · Cancel · Find · List (paged) · Search |
| Statistics | Aggregate orders by customer (total > 700 and count > 2) |

---

## 📊 Statistics Endpoint

```http
GET /api/v1/statistics/orders
```

### SQL Executed internally
```sql
SELECT c.id AS customer_id,
       c.first_name || ' ' || c.last_name AS customer_name,
       COUNT(o.id) AS total_orders,
       SUM(o.total_price) AS order_total_amount
FROM customer c
JOIN orders o ON c.id = o.customer_id
GROUP BY c.id, c.first_name, c.last_name
HAVING SUM(o.total_price) > 700 AND COUNT(o.id) > 2;
```

Returns JSON:

```json
[
  {
    "customerId": "e1b2c3d4-1111-2222-3333-444455556666",
    "customerName": "Hayk Hovhannisyan",
    "totalOrders": 5,
    "orderTotalAmount": 1200.50
  }
]
```

---

## 🔔 Domain Events via Apache Camel

Every order/customer create/update/cancel triggers:
```
    producerTemplate.sendBody("direct:domain-events", eventPayload);
```

Camel route (`direct:domain-events`) simply logs event payloads:

```
INFO  Camel[domain-events] - {"event":"ORDER_CREATED","orderId":"..."}
```

---

## 🧪 Testing

```bash
./gradlew test
./gradlew test --tests "*ServiceTest"
```

All tests rely on H2 and Flyway migrations.

---

## 🧰 Postman Collection

Import `postman_collection.json` from the repo root:

- Covers all CRUD and statistics endpoints
- Uses Basic Auth preconfigured (user/admin)
- Contains sample data payloads

Run requests in this order:
1. Create Customer
2. Create Order
3. Update/Cancel Order
4. List Orders (paged)
5. GET `/api/v1/statistics/orders`

---

## 🩺 Monitoring & Docs

| Resource | URL |
|-----------|-----|
| Swagger UI | `http://localhost:8080/swagger-ui.html` |
| OpenAPI spec | `http://localhost:8080/api-docs` |
| Health check | `/actuator/health` |
| Metrics | `/actuator/metrics` |

---

## 🧩 Design & Patterns

**Domain-Driven Design (DDD)** – Entities (Customer, Order), Value Objects, Repositories  
**SOLID**, **GoF** (Strategy, Factory, Repository, Adapter)  
**EIP** via Apache Camel (Router, WireTap, Filter, Multicast)

---

## 🧠 Sample Data & Demo Flow

1. Create Customer (Hayk Hovhannisyan)
2. Create several Orders (total > 700)
3. Call `GET /api/v1/statistics/orders` → aggregated report
4. Watch Camel logs for domain events

---

## 🧭 Project Goals / Next Steps (TODO)

- [ ] Add integration tests for Camel routes
- [ ] Add Keycloak/OAuth2 auth (optional)
- [ ] Extend statistics to top customers per month
- [ ] Enable Grafana + Prometheus metrics

---

## 📄 License

Apache License 2.0

---

**Author:** Hayk Hovhannisyan – Order Management  
📧 haik.hovhanisyan@gmail.  
🌐 GitHub [@order-management](https://github.com/hhovhann/order-management)
