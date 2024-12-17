-- Software GreenZone =================================================================================

INSERT INTO "_user"
(id, created_at, updated_at, active, avatar_identifier, disabled_reason, email, 
email_activation_code, enabled, first_name, forgotten_password_code, last_name, password_hash, 
phone, username )
VALUES(2, '2024-10-10 11:38:57.761', '2024-10-10 11:47:42.928', true, NULL, NULL, 
'en@gmail.com', NULL, true, 'Elijah', NULL, 'Ngugi', 
'$2a$10$wuj3JVPQVzxcvf8pr1lbrutmBHSp57es/dgoQLmBlQp.KOoy4X4my', 
NULL, 'elation2');

INSERT INTO user_role
(user_id, role_id)
VALUES(2, 1);
