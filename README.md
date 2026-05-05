# PatientSphere: Event-Driven Healthcare Microservices Ecosystem

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![gRPC](https://img.shields.io/badge/gRPC-4285F4?style=for-the-badge&logo=google&logoColor=white)
![Apache Kafka](https://img.shields.io/badge/Apache_Kafka-231F20?style=for-the-badge&logo=apache-kafka&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=json-web-tokens&logoColor=white)

---

**PatientSphere** is a high-performance, distributed healthcare management platform built using **Java 21** and **Spring Boot 3.x**. The system is architected to handle complex medical workflows by leveraging modern communication protocols, asynchronous event processing, and Clean Architecture principles.

## 🏗 System Architecture & Communication

The ecosystem consists of specialized services designed for scalability and high availability:

*   **API Gateway**: The centralized entry point that handles request routing, rate limiting, and security enforcement.
*   **Auth Service**: A dedicated identity provider managing user registration, login, and **JWT (JSON Web Token)** issuance for secure access control.
*   **Patient Service**: The core domain service managing patient profiles and medical records.
*   **Billing Service**: Handles financial calculations and invoicing, interacting with the Patient Service via **gRPC**.
*   **Analytic Service**: Processes healthcare data trends in real-time by consuming events from **Apache Kafka**.

### Inter-Service Communication Patterns:
1.  **Synchronous (gRPC)**: Low-latency, contract-based communication between **Patient Service** and **Billing Service** using Protocol Buffers.
2.  **Asynchronous (Apache Kafka)**: Event-driven streaming where the **Patient Service** (Producer) sends data updates to the **Analytic Service** (Consumer).

## 🛠 Technology Stack

*   **Backend Framework**: Spring Boot 3.x (Java 21)
*   **Messaging & RPC**: Apache Kafka, gRPC
*   **Security**: Spring Security & JWT
*   **Database**: PostgreSQL (Per-service database pattern)
*   **DevOps**: Docker & Docker Compose
*   **Testing**: JUnit 5, Mockito, and Integration Tests (as seen in `/integration-test`)

## 📂 Project Structure

As shown in the project explorer (**image_f21d46.png**), the repository is organized as follows:

- `api-gateway/`: Spring Cloud Gateway implementation.
- `auth-service/`: Identity management and JWT provider.
- `patient-service/`: Core business logic for patient management.
- `billing-service/`: gRPC server for financial operations.
- `analytic-service/`: Kafka consumer for data insights.
- `grpc-requests/`: Collection of `.proto` files and gRPC test scripts.
- `integration-test/`: End-to-end testing suite for service orchestration.

## 🐳 Docker Deployment

The entire system is containerized for seamless environment parity. Optimized **Dockerfiles** are provided for each service.

### Quick Start
To launch the entire infrastructure (including PostgreSQL instances and Kafka brokers):

```bash
docker-compose up --build
```
## Default Port Mappings:
| Service | Port |
| :--- | :--- |
| **API Gateway** | 4004 |
| **Auth Service** |4005 |
| **Patient Service** |4000  |
| **Billing Service** | 4001 (HTTP) / 9001 (gRPC)) |
| **Analytic Service** | 4002 |

---
## 🚀 Key Features
- Contract-First Development: Strict API definitions using gRPC .proto files.

- Real-time Analytics: Decoupled, event-driven data processing.

- Centralized Security: JWT validation at the Gateway level to protect downstream services.

- Database Isolation: Each microservice maintains its own schema to ensure data independence.
