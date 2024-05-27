package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.RoleAlreadyExistException;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.service.IRoleServices;
import com.example.demo.service.RoelService;

@RestController
@RequestMapping("/roles")
public class RoleController {
	@Autowired
	private  IRoleServices roleService;
	
	

	public RoleController(IRoleServices roleService) {
		super();
		this.roleService = roleService;
	}

	@GetMapping("/all-roles")
	public ResponseEntity<List<Role>> getAllRoles() {
		return new ResponseEntity<>(roleService.getRoles(), HttpStatus.FOUND);
	}

	@PostMapping("/create-new-role")
	public ResponseEntity<String> createRole(@RequestBody Role role) {
		try {
			roleService.createRole(role);
			return ResponseEntity.ok("New role created Successfully");
		} catch (RoleAlreadyExistException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());

		}

	}

	@DeleteMapping("/delete/{roleId}")
	public void deleteAll(@PathVariable("roleId") Long roleId) {
		roleService.deleteRole(roleId);
	}

	@PostMapping("/remove-all-users-from-role/{roleId}")
	public Role removeAllUserFromRole(@PathVariable("roleId") Long roleId) {
		return roleService.removeAllUsersFromRole(roleId);

	}

	@PostMapping("/remove-user-from-role")
	public User removeUserFromRole(@RequestParam("userId") Long userId, @RequestParam("roleId") Long roleId) {
		return roleService.removeUserFromRole(roleId, userId);

	}

	@PostMapping("/assign-user-to-role")
	public User assignUserToRole(@RequestParam("userId") Long userId, @RequestParam("roleId") Long roleId) {
		return roleService.assignRoleToUser(userId, roleId);
	}
}
