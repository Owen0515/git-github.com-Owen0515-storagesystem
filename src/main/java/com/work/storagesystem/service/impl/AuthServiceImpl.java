package com.work.storagesystem.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.work.storagesystem.exception.BadRequestException;
import com.work.storagesystem.exception.EntityNotFoundException;
import com.work.storagesystem.model.User;
import com.work.storagesystem.repository.RoleRepository;
import com.work.storagesystem.repository.UserRepository;
import com.work.storagesystem.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;


@Service
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private PasswordEncoder pwdEncoder;
	@Autowired
    private RoleRepository roleRepo;
	
	/**
	 * 獲得 User 角色
	 */
	@Override
    public List<String> getUserRoles(Long userId) {
        return roleRepo.findRolesByUserId(userId);
    }
	
	/**
	 * 獲得 User 權限
	 */
    @Override
    public List<String> getUserPermissions(Long userId) {
        return roleRepo.findPermissionsByUserId(userId);
    }
	
	/**
	 * 登入驗證
	 */
    @Override
    public User validateUser(String username, String password) {
        User user = userRepo.findByUsername(username); 
        if (user == null || !pwdEncoder.matches(password, user.getPassword())) { 
            return null;
        }
        return user;
    }
	
	/**
	 * 新增帳號
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void register(String username,String password) {
		if(userRepo.existsByUsername(username)) {
			throw new BadRequestException("該使用者名稱已存在");
		}
		User user = new User();
		user.setUsername(username);
		user.setPassword(pwdEncoder.encode(password));
		userRepo.save(user);
	}
	
	/**
	 * 取得 User 實體
	 */
	public User findUserByRequest(HttpServletRequest req) {
		Long userId = (Long)req.getAttribute("userId");
    	Optional<User> op = userRepo.findById(userId);
    	if(op.isEmpty()) {
    		throw new EntityNotFoundException("未知使用者");
    	}
    	return op.get();
	}
	
}
