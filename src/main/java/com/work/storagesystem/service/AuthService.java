package com.work.storagesystem.service;

import java.util.List;

import com.work.storagesystem.model.User;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {
	
	/**
	 * 獲得 User 角色
	 */
	List<String> getUserRoles(Long userId);
	
	/**
	 * 得 User 權限
	 */
	List<String> getUserPermissions(Long userId);
	
	/**
	 * 登入驗證
	 */
	User validateUser(String username, String password);
	
	/**
	 * 新增帳號
	 */
	void register(String username,String password);

	/**
	 * 獲得 User 實體
	 */
	User findUserByRequest(HttpServletRequest req);
}
