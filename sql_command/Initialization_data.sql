USE storagesystem;
GO

INSERT INTO roles ( role_name,description) VALUES
( 'admin','系統管理員，擁有所有權限'),
( 'warehouseManager', '倉儲管理員，擁有倉儲操作權限'),
( 'employees', '一般員工，只能查詢產品');

INSERT INTO permissions ( permission_name,description) VALUES
('QUERY_PRODUCTS', '查詢產品'),
('CREATE_PRODUCT', '新增產品'),
('UPDATE_PRODUCT_QUANTITY','出庫、入庫'),
('EDIT_PRODUCTS','修改產品'),
( 'DELETE_PRODUCTS', '刪除產品'),
( 'VIEW_RECORDS', '查詢操作紀錄')

INSERT INTO users (username, password) VALUES 
('admin', '$2a$10$/bkUmwNrGRVJP0WWa.JeJeHnro9zEP7KKc.v4yU8v9SyaBNYO8qS.'), 
('manager', '$2a$10$xnmVYc8IzdpdUbmhbbinxulat61JxgyVGisPhHQhkZdRZ0Fn1E.e6'), 
('employee', '$2a$10$DCGp5bgtgzVOn/hyH8bA6eLK6Ok1gT27Wg0XC9UPwYuaP8tRTGyx2');

-- Admin 擁有全部權限
INSERT INTO role_permissions (role_id, permission_id) VALUES
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5),(1,6)

-- 倉儲管理員擁有部分權限
INSERT INTO role_permissions (role_id, permission_id) VALUES
(2, 1), (2, 3), (2, 6);

-- 一般員工僅擁有查詢產品權限
INSERT INTO role_permissions (role_id, permission_id) VALUES
(3, 1);

INSERT INTO user_roles (user_id, role_id) VALUES
(1, 1), -- admin
(2, 2), -- 倉儲管理員
(3, 3); -- 一般員工

-- 測試用產品
INSERT INTO products (product_name, product_description, price, quantity) VALUES
('手機', '高端智慧型手機', 20000.00, 50),
('筆記型電腦', '輕薄高效的筆記型電腦', 40000.00, 30),
('平板電腦', '多功能平板電腦', 15000.00, 40),
('藍牙耳機', '高音質無線耳機', 5000.00, 100),
('智慧手錶', '功能豐富的智慧手錶', 12000.00, 60),
('桌上型電腦', '高效能桌上型電腦', 35000.00, 20),
('鍵盤', '機械式鍵盤', 3000.00, 150),
('滑鼠', '無線滑鼠', 1500.00, 200),
('顯示器', '4K高清顯示器', 25000.00, 15),
('硬碟', '1TB外接式硬碟', 8000.00, 70);




