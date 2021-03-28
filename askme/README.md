# Askme
Askme is an online test portal where e.g. teachers or lecturers can test their students.

<!-- PROJECT LOGO -->
<br />
<p align="center">
  <a href="https://github.com/ej-feras/askme">
    <img src="images/askme-logo-orange.png" alt="Logo" width="300" height="250">
  </a>
</p>

<!-- TABLE OF CONTENTS -->
<details open="open">
  <summary><h2 style="display: inline-block">Table of Contents</h2></summary>
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
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
      <li><a href="#diagram1">Use Cases / Use Case Diagram</a></li>
    <li><a href="#how-to-sign-up">How to Sign up ?</a></li>
    <li><a href="#demo">Demo</a></li>
  </ol>
</details>

## About the project
### Built With

* [Spring MVC](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/mvc.html)
* [Spring Security](https://docs.spring.io/spring-security/site/docs/4.1.3.RELEASE/reference/htmlsingle/)
* [Thymeleaf](https://www.thymeleaf.org/documentation.html)
* [Bootstrap](https://getbootstrap.com/docs/4.0/getting-started/download/)
* [Jquery](https://jquery.com/)

<!-- GETTING STARTED -->
## Getting Started

To get a local copy up and running follow these simple steps.

### Prerequisites

Before you continue, ensure you have met the following requirements:
* You have installed [JDK 8](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html)
* You have installed [Spring Tool Suite](https://spring.io/tools)
* You have [MySQL](https://dev.mysql.com/doc/mysql-installation-excerpt/5.7/en/windows-installation.html) on your machine.
* You must have a Gmail account to send confirmation emails from it.
* Enabling less secure apps to access Gmail [Go to the "Less secure apps"](https://www.google.com/settings/security/lesssecureapps)

### Installation
1. Clone the repo
   ```sh
   git clone https://github.com/ej-feras/askme.git
   ```
2. Open Spring Tool Suite and import the project.

<!-- USAGE EXAMPLES -->
## Usage
1.After importing the project, go to application.properties in Spring Tool Suite under src\main\resources\application.properties.<br/>
2. Replace the username and the password of the database with your credentials. <img src="images/Username_passsword for database.PNG" raw=true alt="database-credentials"/><br/>
3. To run the program right click on the project in STS, then on Run As Spring boot App <img src="images/Run program.png" raw=true alt="run" style="margin-right: 10px;"/><br/>
4. Open the browser and go to:    ```localhost:8083/login```


## Use Cases / Use Case Diagram <a name="diagram1"/>

<img src="images/Use case diagram (Askme).png" raw=true /><br/>

## How to Sign up ?   <a name="how-to-sign-up"/>
The website was designed in such a way that every normal user must have an administrator. If you want to register as a normal user, you have to enter the e-mail address of the desired administrator when registering. Then this administrator has to send you a confirmation email. When you open the confirmation link, your account must then be activated and you can log in with the access data you have registered with. If you want to register as administrator, you can do so without a confirmation email.

<img src="images/Signup_Login Activity Diagram (Askme).png" raw=true /><br/>

## Demo   <a name="demo"/>




