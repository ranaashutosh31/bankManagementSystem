<h1>Banking System Console Application</h1>

<p>This project is a simple <b>menu-based console application</b> that simulates a banking system using <b>Core Java</b>, <b>MySQL</b>, and <b>JDBC</b>. It allows users to manage accounts, perform transactions, and handle customer data.</p>

<h2>Table of Contents</h2>
<ul>
  <li><a href="#features">Features</a></li>
  <li><a href="#technologies-used">Technologies Used</a></li>
  <li><a href="#database-schema">Database Schema</a></li>
  <li><a href="#project-structure">Project Structure</a></li>
  <li><a href="#installation">Installation</a></li>
  <li><a href="#usage">Usage</a></li>
  <li><a href="#contributing">Contributing</a></li>
  <li><a href="#license">License</a></li>
</ul>

<h2 id="features">Features</h2>

<h3>1. Account Management</h3>
<ul>
  <li>Create a new account</li>
  <li>View account details</li>
  <li>Update account information</li>
  <li>Close an account</li>
</ul>

<h3>2. Transaction Management</h3>
<ul>
  <li>Deposit funds into an account</li>
  <li>Withdraw funds from an account</li>
  <li>Transfer funds between accounts</li>
  <li>View transaction history</li>
</ul>

<h3>3. Customer Management</h3>
<ul>
  <li>Register a new customer</li>
  <li>View customer details</li>
  <li>Update customer information</li>
  <li>Delete a customer</li>
</ul>

<h2 id="technologies-used">Technologies Used</h2>
<ul>
  <li><b>Java</b> (Core Java for the application logic)</li>
  <li><b>JDBC</b> (Java Database Connectivity to communicate with the database)</li>
  <li><b>MySQL</b> (Database management system for storing customer, account, and transaction data)</li>
  <li><b>SQL</b> (Structured Query Language for database operations)</li>
</ul>

<h2 id="database-schema">Database Schema</h2>

<h3>1. Customer Table</h3>
<table>
  <thead>
    <tr>
      <th>Column</th>
      <th>Type</th>
      <th>Description</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>customer_id</td>
      <td>INT (PK)</td>
      <td>Unique customer identifier</td>
    </tr>
    <tr>
      <td>name</td>
      <td>VARCHAR(100)</td>
      <td>Customer name</td>
    </tr>
    <tr>
      <td>email</td>
      <td>VARCHAR(100)</td>
      <td>Customer email address</td>
    </tr>
    <tr>
      <td>phone_number</td>
      <td>VARCHAR(15)</td>
      <td>Customer phone number</td>
    </tr>
    <tr>
      <td>address</td>
      <td>VARCHAR(255)</td>
      <td>Customer address</td>
    </tr>
  </tbody>
</table>

<h3>2. Account Table</h3>
<table>
  <thead>
    <tr>
      <th>Column</th>
      <th>Type</th>
      <th>Description</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>account_number</td>
      <td>INT (PK)</td>
      <td>Unique account identifier</td>
    </tr>
    <tr>
      <td>customer_id</td>
      <td>INT (FK)</td>
      <td>Foreign key referencing Customer table</td>
    </tr>
    <tr>
      <td>balance</td>
      <td>DECIMAL(10,2)</td>
      <td>Current account balance</td>
    </tr>
    <tr>
      <td>type</td>
      <td>ENUM</td>
      <td>Account type (savings/checking)</td>
    </tr>
  </tbody>
</table>

<h3>3. Transaction Table</h3>
<table>
  <thead>
    <tr>
      <th>Column</th>
      <th>Type</th>
      <th>Description</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>transaction_id</td>
      <td>INT (PK)</td>
      <td>Unique transaction identifier</td>
    </tr>
    <tr>
      <td>account_number</td>
      <td>INT (FK)</td>
      <td>Foreign key referencing Account table</td>
    </tr>
    <tr>
      <td>transaction_type</td>
      <td>ENUM</td>
      <td>Type of transaction (deposit/withdrawal/transfer deposit/transfer withdrawal)</td>
    </tr>
    <tr>
      <td>amount</td>
      <td>DECIMAL(10,2)</td>
      <td>Transaction amount</td>
    </tr>
    <tr>
      <td>transaction_date</td>
      <td>TIMESTAMP</td>
      <td>Date and time of the transaction</td>
    </tr>
  </tbody>
</table>

<h2 id="project-structure">Project Structure</h2>
<pre>
src/
├── dao/
│   ├── AccountDAO.java         # Data Access Object for account operations
│   ├── CustomerDAO.java        # Data Access Object for customer operations
│   ├── TransactionDAO.java     # Data Access Object for transaction operations
├── model/
│   ├── Account.java            # Account entity model
│   ├── Customer.java           # Customer entity model
│   ├── Transaction.java        # Transaction entity model
├── service/
│   ├── BankingService.java     # Business logic layer
├── util/
│   ├── DBConnection.java       # Database connection utility class
└── BankingApp.java             # Main menu-driven application
</pre>

<h2 id="installation">Installation</h2>

<h3>Prerequisites</h3>
<ul>
  <li><b>Java Development Kit (JDK)</b> 8 or above</li>
  <li><b>MySQL</b> (or any other compatible SQL database)</li>
  <li><b>MySQL JDBC Connector</b> (for Java to connect with MySQL)</li>
  <li><b>IDE</b> (like IntelliJ IDEA, Eclipse, etc.)</li>
</ul>

<h3>Steps</h3>
<ol>
  <li><b>Clone the repository</b>:
    <pre>git clone https://github.com/your-repository/banking-system-console-app.git</pre>
  </li>
  
  <li><b>Set up the database</b>:
    <pre>
CREATE DATABASE banking_system;

CREATE TABLE Customer (
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    email VARCHAR(100),
    phone_number VARCHAR(15),
    address VARCHAR(255)
);

CREATE TABLE Account (
    account_number INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    balance DECIMAL(10,2),
    type ENUM('savings', 'checking'),
    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id)
);

CREATE TABLE Transaction (
    transaction_id INT PRIMARY KEY AUTO_INCREMENT,
    account_number INT,
    transaction_type ENUM('deposit', 'withdrawal', 'transfer deposit', 'transfer withdrawal'),
    amount DECIMAL(10,2),
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_number) REFERENCES Account(account_number)
);
    </pre>
  </li>
  
  <li><b>Configure the database connection</b>:
    <p>Open the <code>DBConnection.java</code> file inside the <code>util/</code> directory and update the MySQL connection URL, username, and password:</p>
    <pre>
public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/banking_system";
    private static final String USER = "your_mysql_username";
    private static final String PASSWORD = "your_mysql_password";
}
    </pre>
  </li>
  
  <li><b>Compile and run</b>:
    <pre>
javac -d bin src/*.java
java -cp .:mysql-connector-java-8.0.23.jar:bin BankingApp
    </pre>
  </li>
</ol>

<h2 id="usage">Usage</h2>
<p>After running the application, the main menu will provide options to manage accounts, perform transactions, and view transaction history.</p>

<h2 id="contributing">Contributing</h2>
<p>If you'd like to contribute, please follow these steps:</p>
<ol>
  <li>Fork the repository.</li>
  <li>Create a new branch: <code>git checkout -b feature/your-feature-name</code>.</li>
  <li>Make your changes.</li>
  <li>Commit your changes: <code>git commit -m 'Add some feature'</code>.</li>
  <li>Push to the branch: <code>git push origin feature/your-feature-name</code>.</li>
  <li>Open a pull request.</li>
</ol>

<h2 id="license">License</h2>
<p>This project is open source and available under the <a href="LICENSE">MIT License</a>.</p>
