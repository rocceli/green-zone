-- RoleGroup  ======================================================================================

INSERT INTO role_group (id, created_at, updated_at, description, "name")
VALUES(1, current_timestamp, current_timestamp, 'Roles the Grower has', 'GROWER');

INSERT INTO role_group (id, created_at, updated_at, description, "name")
VALUES(2, current_timestamp, current_timestamp, 'Roles the Admin has', 'ADMIN');

INSERT INTO role_group (id, created_at, updated_at, description, "name")
VALUES(3, current_timestamp, current_timestamp, 'Roles the Super Admin has', 'SUPER_ADMIN');

INSERT INTO role_group (id, created_at, updated_at, description, "name")
VALUES(4, current_timestamp, current_timestamp, 'Roles the Transporter has', 'TRANSPORTER');

INSERT INTO role_group (id, created_at, updated_at, description, "name")
VALUES(5, current_timestamp, current_timestamp, 'Roles the Buyer has', 'BUYER');

-- Role  ===========================================================================================

INSERT INTO "role" (id, created_at, updated_at, description, "name")
VALUES(1, current_timestamp, current_timestamp, 'Permission for Growers', 'GROWER');

INSERT INTO "role" (id, created_at, updated_at, description, "name")
VALUES(2, current_timestamp, current_timestamp, 'Permission for Admins', 'ADMIN');

INSERT INTO "role" (id, created_at, updated_at, description, "name")
VALUES(3, current_timestamp, current_timestamp, 'Permission for Super Admins', 'SUPER_ADMIN');

INSERT INTO "role" (id, created_at, updated_at, description, "name")
VALUES(4, current_timestamp, current_timestamp, 'Permission for Transporters', 'TRANSPORTER');

INSERT INTO "role" (id, created_at, updated_at, description, "name")
VALUES(5, current_timestamp, current_timestamp, 'Permission for Buyers', 'BUYER');

--  rolegroup_role =================================================================================

INSERT INTO rolegroup_role (rolegroup_id, role_id)
VALUES(1, 1);

INSERT INTO rolegroup_role (rolegroup_id, role_id)
VALUES(2, 2);

INSERT INTO rolegroup_role (rolegroup_id, role_id)
VALUES(3, 3);

INSERT INTO rolegroup_role (rolegroup_id, role_id)
VALUES(4, 4);

INSERT INTO rolegroup_role (rolegroup_id, role_id)
VALUES(5, 5);