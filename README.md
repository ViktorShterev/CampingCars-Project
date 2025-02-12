Camping Cars Marketplace
============================

Welcome to the **Camping Cars Marketplace** platform! This project is designed to create a seamless experience for users to **offer, buy, or explore campers and caravans**. Whether you're a seller looking for a buyer or a traveler searching for the perfect vehicle, this platform has got you covered.

🚀 Features
-----------

*   **🛒 Marketplace Functionality**: Users can list, search, and buy campers and caravans.
    
*   **🌍 Internationalization**: Supports two languages for global reach.
    
*   **💱 Dynamic Exchange Rates**: Automatically refreshed daily for two currencies via a scheduled REST job. Ensures up-to-date pricing for international users.
    
*   **📊 Search Analytics**: Tracks the number of offer searches performed, providing valuable analytics.
    
*   **🔗 Microservices Architecture**:
    
    *   Main project for core functionalities.
        
    *   [**CampingCars-Brand-Rest-Microservice**](https://github.com/ViktorShterev/CampingCars-Brand-Rest-Microservice) handles brand and model data separately, communication via secure REST APIs using JSON and JWT tokens.
        
*   **🔒 Secure Environment**: Uses Spring Security for robust user authentication and authorization.
  
    

🛠 Tech Stack
-------------

### Backend

*   **Java**: Built with Java using Maven for robust and scalable backend development.
    
*   **Spring Framework**:
    
    *   🌱 Spring Boot for rapid application development.
        
    *   📂 Spring Data for database operations.
        
    *   🔐 Spring Security for application security.
        

### Database

*   **MySQL**: A reliable relational database connected through Spring Data.
    

### Frontend

*   **Thymeleaf**: Server-side rendering for dynamic and SEO-friendly pages.
    

### Architectural Patterns

*   **MVC (Model-View-Controller)**: Ensures separation of concerns for maintainable and testable code.
    
*   **OOP and SOLID Principles**: Provides a modular, flexible, and scalable codebase.
    
### Testing ✅
    
*   **JUnit 5**: For unit testing.
        
*   **Mockito**: For integration testing.
