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
2. Host setup to the file due the host could be conflit the sessions.
   ```sh
   c:\Windows\System32\Drivers\etc\hosts 
   127.0.0.1 auth-server
3. Start services:
   - Authorization Server by default running on port 9000
   - Bidding Service running on port 2024
   - Client Server running on port 8080
4. Client Generate Token:
- Authorization Code: 
   ```sh 
   GET: http://auth-server:9000/oauth2/authorize?response_type=code&client_id=client-id&scope=openid&redirect_uri=https://test.salesforce.com/login/aouth2/code/reg-client&code_challenge=cV0uYorzum0Q0pp5haiK2DyuVo4b2m7p1WliPp0tnY8&code_challenge_method=S256

- Exchange Token from Code: 
  ```sh 
   POST: http://auth-server:9000/oauth2/token
   [form-data]
   grant_type:authorization_code
   code:[exact_code_from_response]
   redirect_uri:https://test.salesforce.com/login/aouth2/code/reg-client
   code_verifier:WFu3bWLPDI5Xlndz2g6aoDR50dI5suuuG5A-nQbxaZ4TR4ggha4gXOGNJU5HwkQgWRTACZptSkxONkldw68yZNNwZQS-OPiD8Hvc7sVXn1FeBz3Ymyh9C_X0wY74l6zE
   client_id:client-id

- Exchange Token from Code:
  ```sh 
   POST: http://auth-server:9000/oauth2/token
   [form-data]
   grant_type:refresh_token
   client_id:public-client
   redirect_uri:https://test.salesforce.com/login/aouth2/code/public-client
   refresh_token:[exact_refresh_token_from_response]
   client_id:client-id