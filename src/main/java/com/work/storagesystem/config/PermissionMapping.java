package com.work.storagesystem.config;

import java.util.HashMap;
import java.util.Map;

/**
 * 管理權限與 API 的對應關係
 */
public class PermissionMapping {

	private static final Map<String, String> PERMISSION_MAPPING = new HashMap<>();
	
    static {
        // 產品相關權限
        PERMISSION_MAPPING.put("GET:/api/products", "QUERY_PRODUCTS");
        PERMISSION_MAPPING.put("POST:/api/products", "CREATE_PRODUCT");
        PERMISSION_MAPPING.put("PUT:/api/products/{id}/admin", "EDIT_PRODUCTS");
        PERMISSION_MAPPING.put("PUT:/api/products/{id}/quantity", "UPDATE_PRODUCT_QUANTITY");
        PERMISSION_MAPPING.put("DELETE:/api/products/{id}", "DELETE_PRODUCTS");

        // 紀錄相關權限
        PERMISSION_MAPPING.put("GET:/api/records", "VIEW_RECORDS");
    }

    // 權限映射查詢
    public static String getPermission(String method, String uri) {
        // 替換路徑參數
        if (uri.matches("^/api/products/\\d+$")) {
            uri = "/api/products/{id}";
        } else if (uri.matches("^/api/products/\\d+/quantity$")) {
            uri = "/api/products/{id}/quantity";
        } else if (uri.matches("^/api/products/\\d+/admin$")) {
            uri = "/api/products/{id}/admin";
        }

        String key = method + ":" + uri;
        return PERMISSION_MAPPING.getOrDefault(key, null);
    }
}
