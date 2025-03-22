CREATE TABLE users(
   id INT AUTO_INCREMENT,
   email VARCHAR(100)  NOT NULL,
   password VARCHAR(255) ,
   hire_date DATETIME,
   created_at DATETIME,
   updated_at DATETIME,
   username VARCHAR(50)  NOT NULL,
   status VARCHAR(100) ,
   token VARCHAR(500) ,
   is_password_set TINYINT(1),
   PRIMARY KEY(id),
   UNIQUE(email),
   UNIQUE(username)
);

CREATE TABLE oauth_users(
   id INT AUTO_INCREMENT,
   access_token VARCHAR(255)  NOT NULL,
   access_token_issued_at DATETIME NOT NULL,
   access_token_expiration DATETIME NOT NULL,
   refresh_token VARCHAR(255)  NOT NULL,
   refresh_token_issued_at DATETIME NOT NULL,
   refresh_token_expiration DATETIME,
   granted_scopes VARCHAR(255) ,
   email VARCHAR(255) ,
   user_id INT,
   PRIMARY KEY(id),
   UNIQUE(email),
   FOREIGN KEY(user_id) REFERENCES users(id)
);

CREATE TABLE user_profile(
   id INT AUTO_INCREMENT,
   first_name VARCHAR(50) ,
   last_name VARCHAR(50) ,
   phone VARCHAR(50) ,
   department VARCHAR(255) ,
   salary DECIMAL(10,2)  ,
   status VARCHAR(50) ,
   oauth_user_image_link VARCHAR(255) ,
   user_image BLOB,
   bio TEXT,
   youtube VARCHAR(255) ,
   twitter VARCHAR(255) ,
   facebook VARCHAR(255) ,
   country VARCHAR(100) ,
   _position_ VARCHAR(100) ,
   address VARCHAR(255) ,
   user_id INT,
   PRIMARY KEY(id),
   FOREIGN KEY(user_id) REFERENCES users(id)
);

CREATE TABLE roles(
   id INT AUTO_INCREMENT,
   name VARCHAR(255) ,
   PRIMARY KEY(id)
);

CREATE TABLE employee(
   id INT AUTO_INCREMENT,
   username VARCHAR(45)  NOT NULL,
   first_name VARCHAR(45)  NOT NULL,
   last_name VARCHAR(45)  NOT NULL,
   email VARCHAR(45)  NOT NULL,
   password VARCHAR(80)  NOT NULL,
   provider VARCHAR(45) ,
   PRIMARY KEY(id)
);

CREATE TABLE email_template(
   template_id INT AUTO_INCREMENT,
   name VARCHAR(255) ,
   content TEXT,
   json_design TEXT,
   created_at DATETIME,
   user_id INT,
   PRIMARY KEY(template_id),
   UNIQUE(name),
   FOREIGN KEY(user_id) REFERENCES users(id)
);

CREATE TABLE customer_login_info(
   id INT AUTO_INCREMENT,
   password VARCHAR(255) ,
   username VARCHAR(255) ,
   token VARCHAR(500) ,
   password_set TINYINT(1),
   PRIMARY KEY(id),
   UNIQUE(token)
);

CREATE TABLE customer(
   customer_id INT AUTO_INCREMENT,
   name VARCHAR(255) ,
   address VARCHAR(255) ,
   city VARCHAR(255) ,
   state VARCHAR(255) ,
   phone VARCHAR(20) ,
   country VARCHAR(255) ,
   description TEXT,
   _position_ VARCHAR(255) ,
   twitter VARCHAR(255) ,
   facebook VARCHAR(255) ,
   youtube VARCHAR(255) ,
   created_at DATETIME,
   email VARCHAR(255) ,
   profile_id INT,
   user_id INT,
   PRIMARY KEY(customer_id),
   FOREIGN KEY(profile_id) REFERENCES customer_login_info(id),
   FOREIGN KEY(user_id) REFERENCES users(id)
);

CREATE TABLE trigger_lead(
   lead_id INT AUTO_INCREMENT,
   name VARCHAR(255) ,
   phone VARCHAR(20) ,
   status VARCHAR(50) ,
   meeting_id VARCHAR(255) ,
   google_drive TINYINT(1),
   google_drive_folder_id VARCHAR(255) ,
   user_id INT,
   id_employee_id INT,
   customer_id INT,
   PRIMARY KEY(lead_id),
   UNIQUE(meeting_id),
   FOREIGN KEY(user_id) REFERENCES users(id),
   FOREIGN KEY(id_employee_id) REFERENCES users(id),
   FOREIGN KEY(customer_id) REFERENCES customer(customer_id)
);

CREATE TABLE trigger_ticket(
   ticket_id INT AUTO_INCREMENT,
   subject VARCHAR(255) ,
   description TEXT,
   status VARCHAR(50) ,
   priority VARCHAR(50) ,
   created_at DATETIME,
   employee_id INT,
   manager_id INT,
   customer_id INT,
   PRIMARY KEY(ticket_id),
   FOREIGN KEY(employee_id) REFERENCES users(id),
   FOREIGN KEY(manager_id) REFERENCES users(id),
   FOREIGN KEY(customer_id) REFERENCES customer(customer_id)
);

CREATE TABLE trigger_contract(
   contract_id INT AUTO_INCREMENT,
   subject VARCHAR(255) ,
   status VARCHAR(100) ,
   description TEXT,
   start_date DATE,
   end_date DATE,
   amount DECIMAL(10,2)  ,
   google_drive TINYINT(1),
   google_drive_folder_id VARCHAR(255) ,
   created_at DATETIME,
   customer_id INT,
   user_id INT,
   lead_id INT,
   PRIMARY KEY(contract_id),
   FOREIGN KEY(customer_id) REFERENCES customer(customer_id),
   FOREIGN KEY(user_id) REFERENCES users(id),
   FOREIGN KEY(lead_id) REFERENCES trigger_lead(lead_id)
);

CREATE TABLE contract_settings(
   id INT AUTO_INCREMENT,
   amount TINYINT(1),
   subject TINYINT(1),
   description TINYINT(1),
   end_date TINYINT(1),
   start_date TINYINT(1),
   status TINYINT(1),
   amount_email_template INT,
   description_email_template INT,
   subject_email_template INT,
   end_email_template INT,
   start_email_template INT,
   status_email_template INT,
   customer_id INT,
   user_id INT,
   PRIMARY KEY(id),
   FOREIGN KEY(amount_email_template) REFERENCES email_template(template_id),
   FOREIGN KEY(description_email_template) REFERENCES email_template(template_id),
   FOREIGN KEY(subject_email_template) REFERENCES email_template(template_id),
   FOREIGN KEY(end_email_template) REFERENCES email_template(template_id),
   FOREIGN KEY(start_email_template) REFERENCES email_template(template_id),
   FOREIGN KEY(status_email_template) REFERENCES email_template(template_id),
   FOREIGN KEY(customer_id) REFERENCES customer_login_info(id),
   FOREIGN KEY(user_id) REFERENCES users(id)
);

CREATE TABLE lead_action(
   id INT AUTO_INCREMENT,
   action VARCHAR(255) ,
   date_time DATETIME,
   lead_id INT,
   PRIMARY KEY(id),
   FOREIGN KEY(lead_id) REFERENCES trigger_lead(lead_id)
);

CREATE TABLE lead_settings(
   id INT AUTO_INCREMENT,
   status TINYINT(1),
   meeting TINYINT(1),
   phone TINYINT(1),
   name TINYINT(1),
   name_email_template INT,
   phone_email_template INT,
   meeting_email_template INT,
   status_email_template INT,
   customer_id INT,
   user_id INT,
   PRIMARY KEY(id),
   FOREIGN KEY(name_email_template) REFERENCES email_template(template_id),
   FOREIGN KEY(phone_email_template) REFERENCES email_template(template_id),
   FOREIGN KEY(meeting_email_template) REFERENCES email_template(template_id),
   FOREIGN KEY(status_email_template) REFERENCES email_template(template_id),
   FOREIGN KEY(customer_id) REFERENCES customer_login_info(id),
   FOREIGN KEY(user_id) REFERENCES users(id)
);

CREATE TABLE ticket_settings(
   id INT AUTO_INCREMENT,
   priority TINYINT(1),
   subject TINYINT(1),
   description TINYINT(1),
   status TINYINT(1),
   template_id INT,
   priority_email_template INT,
   status_email_template INT,
   subject_email_template INT,
   customer_id INT,
   user_id INT,
   PRIMARY KEY(id),
   FOREIGN KEY(template_id) REFERENCES email_template(template_id),
   FOREIGN KEY(priority_email_template) REFERENCES email_template(template_id),
   FOREIGN KEY(status_email_template) REFERENCES email_template(template_id),
   FOREIGN KEY(subject_email_template) REFERENCES email_template(template_id),
   FOREIGN KEY(customer_id) REFERENCES customer_login_info(id),
   FOREIGN KEY(user_id) REFERENCES users(id)
);

CREATE TABLE file(
   file_id INT AUTO_INCREMENT,
   file_name VARCHAR(100) ,
   file_data BLOB,
   file_type VARCHAR(255) ,
   contract_id INT,
   lead_id INT,
   PRIMARY KEY(file_id),
   FOREIGN KEY(contract_id) REFERENCES trigger_contract(contract_id),
   FOREIGN KEY(lead_id) REFERENCES trigger_lead(lead_id)
);

CREATE TABLE google_drive_file(
   id INT AUTO_INCREMENT,
   drive_file_id VARCHAR(255) ,
   drive_folder_id VARCHAR(255) ,
   contract_id INT,
   lead_id INT,
   PRIMARY KEY(id),
   FOREIGN KEY(contract_id) REFERENCES trigger_contract(contract_id),
   FOREIGN KEY(lead_id) REFERENCES trigger_lead(lead_id)
);

CREATE TABLE budget(
   id INT AUTO_INCREMENT,
   amount DECIMAL(18,2)   NOT NULL,
   created_date DATE NOT NULL,
   customer_id INT NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(customer_id) REFERENCES customer(customer_id)
);

CREATE TABLE trigger_ticket_histo(
   id INT,
   subject VARCHAR(255) ,
   description TEXT,
   status VARCHAR(50) ,
   priority VARCHAR(50) ,
   created_at DATETIME,
   delete_at DATE NOT NULL,
   PRIMARY KEY(id)
);

CREATE TABLE ticket_expense(
   id INT AUTO_INCREMENT,
   amount DECIMAL(18,2)   NOT NULL,
   created_at DATE NOT NULL,
   id_1 INT NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_1) REFERENCES trigger_ticket_histo(id)
);

CREATE TABLE trigger_lead_histo(
   lead_id INT,
   name VARCHAR(255) ,
   phone VARCHAR(20) ,
   status VARCHAR(50) ,
   meeting_id VARCHAR(255) ,
   google_drive TINYINT(1),
   google_drive_folder_id VARCHAR(255) ,
   delete_at DATE NOT NULL,
   PRIMARY KEY(lead_id),
   UNIQUE(meeting_id)
);

CREATE TABLE lead_expense(
   id INT AUTO_INCREMENT,
   amount DECIMAL(18,2)   NOT NULL,
   created_at DATE NOT NULL,
   lead_id INT NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(lead_id) REFERENCES trigger_lead_histo(lead_id)
);

CREATE TABLE rate_config(
   id VARCHAR(50) ,
   rate DECIMAL(15,2)   NOT NULL,
   PRIMARY KEY(id)
);

CREATE TABLE user_roles(
   user_id INT,
   role_id INT,
   PRIMARY KEY(user_id, role_id),
   FOREIGN KEY(user_id) REFERENCES users(id),
   FOREIGN KEY(role_id) REFERENCES roles(id)
);
