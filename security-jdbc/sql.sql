CREATE TABLE users (
  username VARCHAR(20) NOT NULL ,
  password VARCHAR(255) NOT NULL ,
  enabled TINYINT NOT NULL DEFAULT 1 ,
  PRIMARY KEY (username));

CREATE TABLE user_roles (
  user_role_id int(11) NOT NULL AUTO_INCREMENT,
  username varchar(20) NOT NULL,
  role varchar(20) NOT NULL,
  PRIMARY KEY (user_role_id),
  UNIQUE KEY uni_username_role (role,username),
  KEY fk_username_idx (username),
  CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (username));

-- password: password
-- $2a$10$AdFRp.9FdbI9WAPlMlFw0eKOSwcbekX3Ydq4kJVhr423HXmcEO2p.  ==  password
INSERT INTO users(username,password,enabled) VALUES ('user','$2a$10$AdFRp.9FdbI9WAPlMlFw0eKOSwcbekX3Ydq4kJVhr423HXmcEO2p.', true);
INSERT INTO testdb.users(username,password,enabled) VALUES ('admin','$2a$10$AdFRp.9FdbI9WAPlMlFw0eKOSwcbekX3Ydq4kJVhr423HXmcEO2p.', true);

INSERT INTO user_roles (username, role) VALUES ('user', 'ROLE_USER');
INSERT INTO user_roles (username, role) VALUES ('admin', 'ROLE_ADMIN');
INSERT INTO user_roles (username, role) VALUES ('admin', 'ROLE_USER');

