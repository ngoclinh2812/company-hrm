INSERT INTO department (department_name)
VALUES ('Engineering'),
       ('Sales'),
       ('Human Resources'),
       ('Marketing'),
       ('Finance'),
       ('Customer Support'),
       ('IT'),
       ('Product Management'),
       ('Operations'),
       ('Design');

--password is password

INSERT INTO employee (email,
                      password,
                      date_of_birth,
                      phone,
                      first_name,
                      last_name,
                      salary,
                      department_id)
VALUES ('john.doe@example.com',
        '$2a$16$boP7lIniqAf4utjXUJPX4OvSoR.q..SxoiDNFndpKR9zxSX5PFKtG',
        '1980-01-01',
        '123-456-7890',
        'John',
        'Doe',
        80000,
        1);

INSERT INTO employee (email,
                      password,
                      date_of_birth,
                      phone,
                      first_name,
                      last_name,
                      salary,
                      department_id)
VALUES ('jane.smith@example.com',
        '$2a$16$boP7lIniqAf4utjXUJPX4OvSoR.q..SxoiDNFndpKR9zxSX5PFKtG',
        '1990-07-15',
        '987-654-3210',
        'Jane',
        'Smith',
        100000,
        2);

INSERT INTO employee (date_of_birth, email, phone, first_name, gender, last_name, middle_name, salary, department_id,
                      password, status, role)
VALUES ('1990-05-15', 'employee1@example.com', '123456789', 'John', 'MALE', 'Doe', NULL, 50000, 1,
        '$2a$16$boP7lIniqAf4utjXUJPX4OvSoR.q..SxoiDNFndpKR9zxSX5PFKtG', 'ACTIVE', 'ROLE_USER');
INSERT INTO employee (date_of_birth, email, phone, first_name, gender, last_name, middle_name, salary, department_id,
                      password, status, role)
VALUES ('1985-08-20', 'employee2@example.com', '987654321', 'Jane', 'FEMALE', 'Smith', NULL, 60000, 2, '$2a$16$boP7lIniqAf4utjXUJPX4OvSoR.q..SxoiDNFndpKR9zxSX5PFKtG
', 'ACTIVE', 'ROLE_USER');
INSERT INTO employee (date_of_birth, email, phone, first_name, gender, last_name, middle_name, salary, department_id,
                      password, status, role)
VALUES ('1978-03-10', 'employee3@example.com', '456789123', 'Michael', 'MALE', 'Johnson', 'Anthony', 70000, 1, '$2a$16$boP7lIniqAf4utjXUJPX4OvSoR.q..SxoiDNFndpKR9zxSX5PFKtG
', 'INACTIVE', 'ROLE_USER');
INSERT INTO employee (date_of_birth, email, phone, first_name, gender, last_name, middle_name, salary, department_id,
                      password, status, role)
VALUES ('1995-11-25', 'employee4@example.com', '654123789', 'Emily', 'FEMALE', 'Brown', NULL, 55000, 3, '$2a$16$boP7lIniqAf4utjXUJPX4OvSoR.q..SxoiDNFndpKR9zxSX5PFKtG
', 'ACTIVE', 'ROLE_ADMIN');
INSERT INTO employee (date_of_birth, email, phone, first_name, gender, last_name, middle_name, salary, department_id,
                      password, status, role)
VALUES ('1982-07-02', 'employee5@example.com', '789321654', 'David', 'MALE', 'Williams', 'Robert', 65000, 2, '$2a$16$boP7lIniqAf4utjXUJPX4OvSoR.q..SxoiDNFndpKR9zxSX5PFKtG
', 'ACTIVE', 'ROLE_USER');


INSERT INTO protein (protein_type, is_vegan)
VALUES ('Chicken', FALSE),
       ('Beef', FALSE),
       ('Pork', FALSE),
       ('Fish', FALSE),
       ('Shrimp', FALSE),
       ('Crab', FALSE),
       ('Eggs', FALSE),
       ('Tofu', TRUE),
       ('Tempeh', TRUE),
       ('Lentils', TRUE),
       ('Beans', TRUE),
       ('Nuts', TRUE),
       ('Seeds', TRUE),
       ('Various types of meat', FALSE)
;


INSERT INTO dish (dish_name, is_vegan, calories, protein_id)
VALUES ('Bánh Mì (Grilled Pork)', FALSE, 450, 3),
       ('Bún Bò Huế (Spicy Beef Noodle Soup)', FALSE, 600, 2),
       ('Phở (Beef Noodle Soup)', FALSE, 550, 2),
       ('Gỏi Cuốn (Spring Rolls)', TRUE, 300, 13),
       ('Bún Cha (Grilled Pork with Noodles)', FALSE, 500, 3),
       ('Banh Xeo (Crispy Savory Pancake)', TRUE, 400, 13),
       ('Mì Quảng (Turmeric Noodles)', FALSE, 500, 13),
       ('Canh Chua Cá (Sour Fish Soup)', FALSE, 450, 4),
       ('Bún Riêu Cua (Crab Noodle Soup)', FALSE, 500, 6),
       ('Gỏi Cuốn Tôm (Shrimp Spring Rolls)', FALSE, 350, 5);