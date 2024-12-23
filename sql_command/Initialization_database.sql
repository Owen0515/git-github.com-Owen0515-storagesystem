-- �إ߸�Ʈw
CREATE DATABASE storagesystem;
GO

USE storagesystem;
GO

CREATE TABLE users (
    user_id INT IDENTITY(1,1) PRIMARY KEY,			-- �ϥΪ�ID
    username NVARCHAR(50) NOT NULL UNIQUE,		-- �ϥΪ̦W��
    password NVARCHAR(255) NOT NULL,					-- �K�X�]�[�K�x�s�^
    created_at DATETIME DEFAULT GETDATE()			-- �إ߮ɶ�
);

CREATE TABLE roles (
    role_id INT IDENTITY(1,1) PRIMARY KEY,				-- ����ID
    role_name NVARCHAR(50) NOT NULL UNIQUE,		-- ����W��
    description NVARCHAR(255) NULL						-- ����y�z
);

CREATE TABLE permissions (
    permission_id INT IDENTITY(1,1) PRIMARY KEY,	 -- �v��ID
    permission_name NVARCHAR(100) NOT NULL UNIQUE, -- �v���W��
    description NVARCHAR(255) NULL						-- �v���y�z
);

CREATE TABLE user_roles (
    id INT IDENTITY(1,1) PRIMARY KEY,					-- �ۼW�D��
    user_id INT NOT NULL,										-- �ϥΪ�ID
    role_id INT NOT NULL,										-- ����ID
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES Roles(role_id) ON DELETE CASCADE
);

CREATE TABLE role_permissions (
    id INT IDENTITY(1,1) PRIMARY KEY,					-- �ۼW�D��
    role_id INT NOT NULL,										-- ����ID
    permission_id INT NOT NULL,								-- �v��ID
    FOREIGN KEY (role_id) REFERENCES Roles(role_id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES Permissions(permission_id) ON DELETE CASCADE
);

CREATE TABLE products (
    product_id INT IDENTITY(1,1) PRIMARY KEY,		-- ���~ID�A�ۼW�D��
    product_name NVARCHAR(255) NOT NULL,			-- ���~�W��
    product_description NVARCHAR(500),					-- ���~�y�z
    price DECIMAL(10,2) NOT NULL,							-- ����
    quantity INT NOT NULL,										-- �w�s�ƶq
    created_at DATETIME DEFAULT GETDATE(),			-- �Ыخɶ�
);

CREATE TABLE records (
    record_id INT IDENTITY(1,1) PRIMARY KEY,			-- ����ID�A�ۼW�D��
    product_id INT NOT NULL,									-- ���p�����~ID
    operation_type NVARCHAR(50) NOT NULL,			-- �ާ@�����]�p "�J�w" �� "�X�w"�^
    quantity INT NOT NULL,										-- �ާ@�ƶq
    operation_time DATETIME DEFAULT GETDATE(),	-- �ާ@�ɶ�
    operator NVARCHAR(100) NOT NULL,					-- �ާ@�̦W��
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE
);

