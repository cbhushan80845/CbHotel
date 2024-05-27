package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.exception.RoleAlreadyExistException;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

@Service
public class RoelService implements IRoleServices {
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRepository userRepository;
	
	

	public RoelService(RoleRepository roleRepository, UserRepository userRepository) {
		super();
		this.roleRepository = roleRepository;
		this.userRepository = userRepository;
	}


	public List<Role> getRoles() {
		return roleRepository.findAll();
	}

	
	public Role createRole(Role theRole) {
		String roleName = "ROLE_" + theRole.getName().toUpperCase();
		Role role = new Role(roleName);
		if (roleRepository.existsByName(roleName)) {
			throw new RoleAlreadyExistException(theRole.getName() + " role already exists");
		}
		return roleRepository.save(role);
	}
	@Override
	public void deleteRole(Long roleId) {
		this.removeAllUsersFromRole(roleId);
		roleRepository.deleteById(roleId);

	}
	@Override
	public Role findByName(String name) {
		return roleRepository.findByName(name).get();
	}
	@Override
	public User removeUserFromRole(Long roleId, Long userId) {
		Optional<User> user = userRepository.findById(userId);
		Optional<Role> role = roleRepository.findById(roleId);
		if (role.isPresent() && role.get().getUsers().contains(user.get())) {
			role.get().removeUserFromRole(user.get());
			roleRepository.save(role.get());
			return user.get();
		}
		throw new UsernameNotFoundException("User not found");
	}
	@Override
	public User assignRoleToUser(Long userId, Long roleId) {
		Optional<User> user = userRepository.findById(userId);
		Optional<Role> role = roleRepository.findById(roleId);
		if (user.isPresent() && user.get().getRoles().contains(role.get())) {
			throw new UserAlreadyExistsException(
					user.get().getFirstName() + " is already assigned to the" + role.get().getName() + " role");
		}
		if (role.isPresent()) {
			role.get().assignRoleToUser(user.get());
			roleRepository.save(role.get());
		}
		return user.get();
	}
	@Override
	public Role removeAllUsersFromRole(Long roleId) {
		Optional<Role> role = roleRepository.findById(roleId);
		role.ifPresent(Role::removeAllUsersFromRole);
		return roleRepository.save(role.get());
	}

}
