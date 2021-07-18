INSERT INTO role (name, init_date) VALUES ("ROLE_ADMIN", CURRENT_TIMESTAMP), ("ROLE_USER", CURRENT_TIMESTAMP);

INSERT INTO user (username, password, init_date) VALUES ("admin", "$2a$10$trPx7QBNwH2TtfMdbwKVd.PDpBKD6HLridd5ZQ66gKqW0qoRhiWSu", CURRENT_TIMESTAMP);

INSERT INTO user_role (user_id, role_id) VALUES ((SELECT id from user where username = "admin"), (SELECT id from role where name = "ROLE_USER"));

INSERT INTO subscription (color, name, user_id, init_date) VALUES 
("red", "Netflix", (SELECT id from user where username = "admin"), CURRENT_TIMESTAMP),
("blue", "Amazon Prime", (SELECT id from user where username = "admin"), CURRENT_TIMESTAMP);