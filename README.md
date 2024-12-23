# **Storage System - 倉儲管理系統**

## **專案簡介**
此專案為一個倉儲管理系統，包含完整的前後端功能實現，具備使用者角色與權限管理、產品 CRUD 功能以及操作紀錄管理功能。

---

## **功能介紹**
1. **使用者管理**  
   - 使用者登入功能，並根據角色分配不同的操作權限。
   - **角色**：
     - `Admin`：擁有所有功能的權限(產品新增、修改、查詢、刪除、記錄查詢)。
     - `Warehouse Manager`：擁有倉儲操作權限（出庫、入庫、記錄查詢）。
     - `Employee`：僅可查詢產品資料(僅可查詢產品)。

2. **產品管理**  
   - 新增、刪除、修改產品。
   - 查詢產品支持條件篩選。

3. **操作紀錄管理**
   - 所有操作皆會產生對應紀錄
   - 查詢產品出入庫操作紀錄，支持根據操作時間範圍、操作類型等條件篩選。

5. **權限驗證**  
   - 使用 JWT 進行使用者認證。
   - 基於 RBAC 模型的角色權限驗證，在未授權情況下拒絕操作。

---

## **資料表結構**
SQL 文件放置於 `/sql_command/` 目錄下，包含以下兩部分：
- `Initialization_database.sql`：用於創建資料庫結構。
- `Initialization_data.sql`：用於初始化測試數據。

### 資料表關聯圖
![Pasted image 20241223184003](https://github.com/user-attachments/assets/b1256126-0b48-4c5f-8655-c6cf20aa1987)

#### **1.1 使用者表 (`users`)**

- **用途**: 儲存系統使用者資訊，包含登入名稱、密碼及建立時間。
- **欄位**:
    - `user_id` (主鍵): 使用者的唯一識別碼。
    - `username`: 使用者名稱，需唯一。
    - `password`: 使用者密碼 (加密存儲)。
    - `created_at`: 使用者建立時間。

#### **1.2 角色表 (`roles`)**

- **用途**: 定義系統中可分配的角色 : 管理員、倉儲管理員、一般員工。
- **欄位**:
    - `role_id` (主鍵): 角色的唯一識別碼。
    - `role_name`: 角色名稱 (`Admin`, `warehouseManager`)。
    - `description`: 角色說明。

#### **1.3 權限表 (`permissions`)**

- **用途**: 定義系統中的操作權限，例如新增產品、查詢紀錄。
- **欄位**:
    - `permission_id` (主鍵): 權限的唯一識別碼。
    - `permission_name`: 權限名稱 (如 `QUERY_PRODUCTS`)。
    - `description`: 權限說明。

#### **1.4 使用者角色表 (`user_roles`)**

- **用途**: 建立使用者與角色之間的關聯。
- **欄位**:
    - `id` (主鍵): 自增識別碼。
    - `user_id`: 關聯的使用者。
    - `role_id`: 關聯的角色。

#### **1.5 角色權限表 (`role_permissions`)**

- **用途**: 定義角色擁有哪些權限。
- **欄位**:
    - `id` (主鍵): 自增識別碼。
    - `role_id`: 關聯的角色。
    - `permission_id`: 關聯的權限。

#### **1.6 產品表 (`products`)**

- **用途**: 儲存倉儲中的產品資訊。
- **欄位**:
    - `product_id` (主鍵): 產品的唯一識別碼。
    - `product_name`: 產品名稱。
    - `product_description`: 產品描述。
    - `price`: 產品價格。
    - `quantity`: 產品庫存數量。
    - `created_at`: 產品建立時間。

#### **1.7 操作紀錄表 (`records`)**

- **用途**: 儲存使用者對產品的操作紀錄，例如新增、修改、刪除。
- **欄位**:
    - `record_id` (主鍵): 操作紀錄唯一識別碼。
    - `product_id`: 關聯的產品。
    - `operation_type`: 操作類型 (如 `新增`, `刪除`)。
    - `quantity`: 操作數量。
    - `operation_time`: 操作時間。
    - `operator`: 操作者名稱。

---

### 2. 外鍵關係

1. **使用者與角色**:
    - `users` 與 `roles` 為多對多關係，透過 `user_roles` 表關聯。
2. **角色與權限**:
    - `roles` 與 `permissions` 為多對多關係，透過 `role_permissions` 表關聯。
3. **產品與操作紀錄**:
    - `products` 與 `records` 為一對多關係，每個產品可以有多條操作紀錄。

---

## **環境設定**
### **開發環境**
- **後端**：Spring Boot 2.x
- **資料庫**：SQL Server
- **前端**：Vue 3.x

### **系統需求**
- **JDK**：17
- **Node.js**：14.x 或以上
- **SQL Server**：已安裝，且開啟對應的 TCP 連接

---

## **執行步驟**
### **1. 資料庫設置**
1. 確保資料庫已啟動。
2. 執行 `/sql_command/Initialization_database.sql` 創建資料庫結構。
3. 執行 `/sql_command/Initialization_data.sql` 初始化測試數據。

### **2. 後端啟動**
1. 將專案克隆至本地：
   ```bash
   git clone https://github.com/your-repository/storage-system.git
   cd storage-system
2. 編輯 /src/main/resources/application-template.properties，填寫以下內容：
   - jwt.secret
     資料庫連接設定：
   - spring.datasource.url
   - spring.datasource.username
   - spring.datasource.password

## **提供測試帳號**

1. **管理員帳號**  
   - 帳號: `admin`  
   - 密碼: `admin123`

2. **倉儲管理員帳號**  
   - 帳號: `manager`  
   - 密碼: `manager123`

3. **一般員工帳號**  
   - 帳號: `employee`  
   - 密碼: `employee123`




   


