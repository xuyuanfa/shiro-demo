package com.xxx.shiro.filter;

import java.util.Set;

public interface UserService {
	Set<String> findRoles(String username);
	
	Set<String> findPermissions(String username);
}
