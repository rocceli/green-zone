-- Software GreenZone =================================================================================

INSERT INTO "_user"
(id, created_at, updated_at, active, avatar_identifier, disabled_reason, email, 
email_activation_code, enabled, first_name, forgotten_password_code, last_name, password_hash, 
phone, username )
VALUES(1, '2024-10-10 11:38:57.761', '2024-10-10 11:47:42.928', true, NULL, NULL, 
'en@gmail.com', NULL, true, 'Elijah', NULL, 'Ngugi', 
'$2a$10$wuj3JVPQVzxcvf8pr1lbrutmBHSp57es/dgoQLmBlQp.KOoy4X4my', 
NULL, 'elation2');

INSERT INTO user_role
(user_id, role_id)
VALUES(1, 1);

INSERT INTO "_user"
(id, created_at, updated_at, active, avatar_identifier, disabled_reason, email, 
email_activation_code, enabled, first_name, forgotten_password_code, last_name, password_hash, 
phone, username )
VALUES(2, '2024-10-10 11:38:57.761', '2024-10-10 11:47:42.928', true, NULL, NULL, 
'en.admin@gmail.com', NULL, true, 'Elijah', NULL, 'Ngugi', 
'$2a$10$wuj3JVPQVzxcvf8pr1lbrutmBHSp57es/dgoQLmBlQp.KOoy4X4my', 
NULL, 'elation2');

INSERT INTO user_role
(user_id, role_id)
VALUES(2, 3);



INSERT INTO "_address"
(id, created_at, updated_at, line_1, line_2, line_3, line_4, town_city, longitude, latitude, fk_country)
VALUES(1, '2024-10-10 11:38:57.761', '2024-10-10 11:47:42.928', NULL, NULL, NULL, NULL, 'Nairobi', '37', '15', 109);

INSERT INTO "_project"
(id, created_at, updated_at, stage, description, size_area, fk_address, fk_user )
VALUES(1, '2024-10-10 11:38:57.761', '2024-10-10 11:47:42.928','Planting', 'This is a food security community farming project for locals in Mukuru Kwa Njenga', '1000 acres', 1, 1 );