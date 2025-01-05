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
VALUES(1, '2024-10-10 11:38:57.761', '2024-10-10 11:47:42.928', NULL, NULL, '1139', 'Ngong', 'Nairobi', '37', '15', 109);

INSERT INTO "_address"
(id, created_at, updated_at, line_1, line_2, line_3, line_4, town_city, longitude, latitude, fk_country)
VALUES(2, '2024-10-11 09:15:30.123', '2024-10-11 09:20:45.456', '4567', NULL,  NULL, 'Kibera', 'Nairobi', '36.8219', '-1.2921', 109);

INSERT INTO "_address"
(id, created_at, updated_at, line_1, line_2, line_3, line_4, town_city, longitude, latitude, fk_country)
VALUES(3, '2024-10-12 10:00:00.000', '2024-10-12 10:05:00.000', '7890', NULL, NULL, 'Westlands', 'Nairobi', '36.7954', '-1.2632', 109);

INSERT INTO "_address"
(id, created_at, updated_at, line_1, line_2, line_3, line_4, town_city, longitude, latitude, fk_country)
VALUES(4, '2024-10-13 14:30:00.000', '2024-10-13 14:35:00.000', '1234', NULL, NULL, 'Karen', 'Nairobi', '36.6880', '-1.3210', 109);

INSERT INTO "_project"
(id, created_at, updated_at, name, stage, description, size_area, fk_address, fk_user)
VALUES(2, '2024-10-11 09:15:30.123', '2024-10-11 09:20:45.456', 'Kibera Green-Up', 'Harvesting', 'A community initiative aimed at cleaning and beautifying the Kibera area.', 'less_than_10', 2, 1);

INSERT INTO "_project"
(id, created_at, updated_at, name, stage, description, size_area, fk_address, fk_user )
VALUES(1, '2024-10-10 11:38:57.761', '2024-10-10 11:47:42.928','Njaa Revolution', 'Flowering', 'This is a food security community farming project for locals in Gishagi Ngong', '500_to_1000', 1, 1 );

INSERT INTO "_project"
(id, created_at, updated_at, name, stage, description, size_area, fk_address, fk_user)
VALUES(3, '2024-10-12 10:00:00.000', '2024-10-12 10:05:00.000', 'Westlands Revitalization', 'Seedling', 'A project focused on enhancing public spaces and parks in Westlands.', 'more_than_5000', 3, 1);

INSERT INTO "_project"
(id, created_at, updated_at, name, stage, description, size_area, fk_address, fk_user)
VALUES(4, '2024-10-13 14:30:00.000', '2024-10-13 14:35:00.000', 'Karen Community Garden', 'Irrigation', 'Establishing a community garden to promote local food production in Karen.', '50_to_100', 4, 1);
