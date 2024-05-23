<a name="readme-top"></a>

<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->

<h3 align="center">SCHOOLWORK : LE MONDE DU DEV</h3>

<p align="center">
School work : A basic social mvp app allowing individuals to share their thoughts on dev related topics.
</p>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#frontend-installation">Frontend Installation</a></li>
        <li><a href="#backend-installation">Backend Installation</a></li>
      </ul>
    </li>
    <li><a href="#usages">Usages</a></li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->

## About The Project

A basic social mvp app allowing individuals to share their thoughts on dev related topics.

### Built With

- Java 17
- Spring Boot 3.1.5
- Spring Security 6.1.5
- Lombok Annotations
- MySQL 8.0
- Spring JPA
- Jakarta Validation

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- GETTING STARTED -->

## Getting Started

To run the app, you will need to clone the current repository first :

```
git clone https://github.com/ask0ldd/P6-FullStack.git
```

### Prerequisites

Before all you need to install these softwares, packages and librairies :

- nodejs
  ```
  https://nodejs.org/en
  ```
- npm (after installing nodejs)
  ```
  npm install -g npm
  ```
- java development kit 17 (jdk17) and if needed, add a JAVA_HOME environment variable pointing at your java installation folder.
  ```
  https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html
  ```
- mysql & mysqlwork bench (full install)

  ```
  https://dev.mysql.com/downloads/windows/
  ```

- the angular cli (after installing nodejs)
  ```
  npm install -g @angular/cli
  ```

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### FrontEnd Installation

1. Clone the front end repo

   ```
   git clone https://github.com/OpenClassrooms-Student-Center/Developpez-le-back-end-en-utilisant-Java-et-Spring.git
   ```

2. Install the packages needed for the front end (node & npm should be installed first)
   ```
   npm install
   ```
3. Start the Front End of the App (npm & the angular cli should be installed first)
   ```
   npm run start
   ```

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Backend Installation

1. Clone the repo
   ```
   git clone https://github.com/ask0ldd/P3-SpringV2.git
   ```
2. Install MySQL & Workbench and define a root password.

3. Create an env.properties file into the ressources folder of the project and add the following lines, with your root password replacing 'yourownrootpassword' (don't do this on a production server, create a new user with all the needed authorisations instead) :
   ```
   spring.datasource.username=root
   spring.datasource.password=yourownrootpassword
   ```
4. Open MySQL Workbench
   ```
   The following connection should already be set up :
      Local Instance MySQL80 / user : root / url : localhost:3306.
   ```
5. Create an "immo" empty schema with Workbench. You don't need to do more since all the mandatory tables will be created by Spring JPA when executing the project.

6. Build the project.

   ```
   mvnw package
   ```

7. Run the project with Maven.
   ```
   mvnw spring-boot:run
   ```

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- USAGE EXAMPLES -->

## Usages

- Register a new user account.
- Log into your account.
- View the user informations.
- Post a new rental listing, including a picture of the property.
- Update an existing rental listing.
- Browse all posted rental listings.
- View all the details regarding a specific rental.
- Leave a message to a rental owner.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- SWAGGER -->

## Swagger

After launching the server, go to the following url :

    http://127.0.0.1:3001/swagger-ui/index.html

Don't forget to register first then use the returned JWT to authenticate yourself.

<p align="right">(<a href="#readme-top">back to top</a>)</p>
