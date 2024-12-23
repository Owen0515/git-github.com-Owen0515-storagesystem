package com.work.storagesystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.work.storagesystem.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
	
	@Query("SELECT r.roleName FROM Role r INNER JOIN UserRole ur ON r.id = ur.role.roleId WHERE ur.user.userId = :userId")
    List<String> findRolesByUserId(@Param("userId") Long userId);
	
	@Query("SELECT p.permissionName FROM Permission p JOIN RolePermission rp ON p.id = rp.permission.permissionId JOIN UserRole ur ON rp.role.roleId = ur.role.roleId WHERE ur.user.userId = :userId")
    List<String> findPermissionsByUserId(@Param("userId") Long userId);
}
