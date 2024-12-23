-- 建立資料庫
CREATE DATABASE storagesystem;
GO

USE storagesystem;
GO

CREATE TABLE users (
    user_id INT IDENTITY(1,1) PRIMARY KEY,			-- 使用者ID
    username NVARCHAR(50) NOT NULL UNIQUE,		-- 使用者名稱
    password NVARCHAR(255) NOT NULL,					-- 密碼（加密儲存）
    created_at DATETIME DEFAULT GETDATE()			-- 建立時間
);

CREATE TABLE roles (
    role_id INT IDENTITY(1,1) PRIMARY KEY,				-- 角色ID
    role_name NVARCHAR(50) NOT NULL UNIQUE,		-- 角色名稱
    description NVARCHAR(255) NULL						-- 角色描述
);

CREATE TABLE permissions (
    permission_id INT IDENTITY(1,1) PRIMARY KEY,	 -- 權限ID
    permission_name NVARCHAR(100) NOT NULL UNIQUE, -- 權限名稱
    description NVARCHAR(255) NULL						-- 權限描述
);

CREATE TABLE user_roles (
    id INT IDENTITY(1,1) PRIMARY KEY,					-- 自增主鍵
    user_id INT NOT NULL,										-- 使用者ID
    role_id INT NOT NULL,										-- 角色ID
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES Roles(role_id) ON DELETE CASCADE
);

CREATE TABLE role_permissions (
    id INT IDENTITY(1,1) PRIMARY KEY,					-- 自增主鍵
    role_id INT NOT NULL,										-- 角色ID
    permission_id INT NOT NULL,								-- 權限ID
    FOREIGN KEY (role_id) REFERENCES Roles(role_id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES Permissions(permission_id) ON DELETE CASCADE
);

CREATE TABLE products (
    product_id INT IDENTITY(1,1) PRIMARY KEY,		-- 產品ID，自增主鍵
    product_name NVARCHAR(255) NOT NULL,			-- 產品名稱
    product_description NVARCHAR(500),					-- 產品描述
    price DECIMAL(10,2) NOT NULL,							-- 價格
    quantity INT NOT NULL,										-- 庫存數量
    created_at DATETIME DEFAULT GETDATE(),			-- 創建時間
);

CREATE TABLE records (
    record_id INT IDENTITY(1,1) PRIMARY KEY,			-- 紀錄ID，自增主鍵
    product_id INT NOT NULL,									-- 關聯的產品ID
    operation_type NVARCHAR(50) NOT NULL,			-- 操作類型（如 "入庫" 或 "出庫"）
    quantity INT NOT NULL,										-- 操作數量
    operation_time DATETIME DEFAULT GETDATE(),	-- 操作時間
    operator NVARCHAR(100) NOT NULL,					-- 操作者名稱
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE
);

