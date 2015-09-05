USE ci6206;

INSERT INTO t_users(c_username, c_firstname, c_lastname) VALUES
("tinnguyen", "Nguyen", "Tri Tin"),
("michael", "Tan", "Michael"),
("loke", "Mukherjee", "Lokenath"),
("guojun", "Qiao", "Guo Jun");

UPDATE t_users SET c_password='tinnguyen' WHERE c_username='tinnguyen';
UPDATE t_users SET c_password='michael' WHERE c_username='michael';
UPDATE t_users SET c_password='loke' WHERE c_username='loke';
UPDATE t_users SET c_password='guojun' WHERE c_username='guojun';

