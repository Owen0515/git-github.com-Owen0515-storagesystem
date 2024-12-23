package com.work.storagesystem.interceptor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.nimbusds.jwt.JWTClaimsSet;
import com.work.storagesystem.config.PermissionMapping;
import com.work.storagesystem.exception.UnauthorizedException;
import com.work.storagesystem.service.AuthService;
import com.work.storagesystem.util.JWTUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

	@Autowired
	private AuthService authService;
	
    private final JWTUtil jwtUtil;

    public AuthorizationInterceptor(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
			return true;
		}
        String authHeader = request.getHeader("authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("未提供授權令牌");
        }
        String token = authHeader.substring(7);
        
        try {
            // 驗證 JWT Token
            JWTClaimsSet claims = jwtUtil.validateAndParseToken(token);
            
            // 獲取用戶ID
            Long userId = jwtUtil.getUserIdFromToken(claims);
            request.setAttribute("userId", userId);
            
            // 根據用戶 ID 查詢角色和權限
            List<String> permissions = authService.getUserPermissions(userId);
            
            // 驗證是否擁有當前請求所需的權限
            String requiredPermission = getRequiredPermission(request);
            if (!permissions.contains(requiredPermission)) {
                throw new UnauthorizedException("沒有執行此操作的權限");
            }

        } catch (Exception e) {
        	throw new UnauthorizedException("未授權行為");
        }

        return true; 
    }
    
    private String getRequiredPermission(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String method = request.getMethod();
        return PermissionMapping.getPermission(method, uri);
    }

}
