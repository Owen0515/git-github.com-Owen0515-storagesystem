package com.work.storagesystem.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.work.storagesystem.dto.auth.UserRequestDTO;
import com.work.storagesystem.exception.BadRequestException;
import com.work.storagesystem.exception.UnauthorizedException;
import com.work.storagesystem.model.User;
import com.work.storagesystem.service.AuthService;
import com.work.storagesystem.util.JWTUtil;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private AuthService authService;
	@Autowired
	private JWTUtil jwtUtil;
	
	/**
	 * 新增帳號
	 */
	@PostMapping("/register")
	public ResponseEntity<String> register(@Valid @RequestBody UserRequestDTO dto){
		String username = dto.getUsername();
		String password = dto.getPassword();
		authService.register(username,password);
		return ResponseEntity.ok("註冊成功 !");
	}
	
	/**
	 * 登入
	 * @param loginRequest
	 * @return
	 */
	@PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody UserRequestDTO dto) {
		String username = dto.getUsername();
	    String password = dto.getPassword();
        User user = authService.validateUser(username, password);
        if (user == null) {
            throw new BadRequestException("用戶名或密碼錯誤");
        }
        // 查詢角色
        List<String> roles = authService.getUserRoles(user.getUserId());
        // 生成 Token
        String token = jwtUtil.createToken(new JSONObject()
                .put("userId", user.getUserId())
                .toString());
        
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("username", username);
        response.put("roles", roles);

        return ResponseEntity.ok(response);
    }
	
	
}
