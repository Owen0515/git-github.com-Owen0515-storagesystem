USE storagesystem;
GO

INSERT INTO roles ( role_name,description) VALUES
( 'admin','�t�κ޲z���A�֦��Ҧ��v��'),
( 'warehouseManager', '���x�޲z���A�֦����x�ާ@�v��'),
( 'employees', '�@����u�A�u��d�߲��~');

INSERT INTO permissions ( permission_name,description) VALUES
('QUERY_PRODUCTS', '�d�߲��~'),
('CREATE_PRODUCT', '�s�W���~'),
('UPDATE_PRODUCT_QUANTITY','�X�w�B�J�w'),
('EDIT_PRODUCTS','�קﲣ�~'),
( 'DELETE_PRODUCTS', '�R�����~'),
( 'VIEW_RECORDS', '�d�߾ާ@����')

INSERT INTO users (username, password) VALUES 
('admin', '$2a$10$/bkUmwNrGRVJP0WWa.JeJeHnro9zEP7KKc.v4yU8v9SyaBNYO8qS.'), 
('manager', '$2a$10$xnmVYc8IzdpdUbmhbbinxulat61JxgyVGisPhHQhkZdRZ0Fn1E.e6'), 
('employee', '$2a$10$DCGp5bgtgzVOn/hyH8bA6eLK6Ok1gT27Wg0XC9UPwYuaP8tRTGyx2');

-- Admin �֦������v��
INSERT INTO role_permissions (role_id, permission_id) VALUES
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5),(1,6)

-- ���x�޲z���֦������v��
INSERT INTO role_permissions (role_id, permission_id) VALUES
(2, 1), (2, 3), (2, 6);

-- �@����u�Ⱦ֦��d�߲��~�v��
INSERT INTO role_permissions (role_id, permission_id) VALUES
(3, 1);

INSERT INTO user_roles (user_id, role_id) VALUES
(1, 1), -- admin
(2, 2), -- ���x�޲z��
(3, 3); -- �@����u

-- ���եβ��~
INSERT INTO products (product_name, product_description, price, quantity) VALUES
('���', '���ݴ��z�����', 20000.00, 50),
('���O���q��', '�������Ī����O���q��', 40000.00, 30),
('���O�q��', '�h�\�७�O�q��', 15000.00, 40),
('�Ť��վ�', '������L�u�վ�', 5000.00, 100),
('���z���', '�\���״I�����z���', 12000.00, 60),
('��W���q��', '���į��W���q��', 35000.00, 20),
('��L', '������L', 3000.00, 150),
('�ƹ�', '�L�u�ƹ�', 1500.00, 200),
('��ܾ�', '4K���M��ܾ�', 25000.00, 15),
('�w��', '1TB�~�����w��', 8000.00, 70);




