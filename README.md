# Project Overview

This repository contains three Spring Boot projects that serve different purposes within our application.

- [Authorization Server](#authorization-server)
- [Bidding Service](#bidding-service)
- [Client Server](#client-server)

---

## Authorization Server

This project serves as the authorization server for our application.

### How to Start

#### Prerequisites

- Java Development Kit (JDK) installed
- Maven installed

#### Steps to Start

1. Clone the repository:
   ```sh
   git clone https://github.com/vireakv3/bidding-system-oauth2.git
   cd bidding-system-oauth2
2. Host setup:
   ```sh
   c:\Windows\System32\Drivers\etc\hosts 
   127.0.0.1 auth-server
to the file due the host could be conflit the sessions.
3. Start services:
- Authorization Server by default running on port 9000
- Bidding Service running on port 2024
- Client Server running on port 8080