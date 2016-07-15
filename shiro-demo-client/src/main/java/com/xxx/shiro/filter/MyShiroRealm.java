package com.xxx.shiro.filter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 本地验证用户，示例模拟，不查数据库，写死数据
 */
public class MyShiroRealm extends AuthorizingRealm {
	private static Logger logger = LoggerFactory.getLogger(MyShiroRealm.class
			.getName());

	private Map<String, String> usernamePasswords;
	private Map<String, List<String>> usernameRoles;
	private Map<String, List<String>> usernamePermissions;

//	@Autowired
//	UserMapper userMapper;

	public MyShiroRealm() {
		// 测试示例，用户密码写死在代码中，实际上应该保存在数据库查询获取
		usernamePasswords = new HashMap<String, String>();
		usernameRoles = new HashMap<String, List<String>>();
		usernamePermissions = new HashMap<String, List<String>>();

		usernamePasswords.put("admin", "password");
		usernamePasswords.put("register", "password");

		usernameRoles.put("admin", Arrays.asList("Register", "Admin"));
		usernameRoles.put("register", Arrays.asList("Register"));

		// 资源类型：操作：资源ID 
		// printer:print 等价于 printer:print:*
		// printer 等价于 printer:*:*
		usernamePermissions.put("admin", Arrays.asList("printer:print"));

	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		String username = (String) principals.getPrimaryPrincipal();

		// Authentication 成功后查询用户授权信息.
		List<String> roles = queryRoles(username);
		List<String> permissions = queryPermissions(username);

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

		logger.debug("Username: {}, Roles: {}", username, roles);
		logger.debug("Username: {}, Permissions: {}", username, permissions);

		if (roles != null && !roles.isEmpty()) {
			info.addRoles(roles);
			info.addStringPermissions(permissions);
		}

		return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String username = upToken.getUsername(); // 通过表单接收的用户名

		if (username.equals("test")) {
			throw new LockedAccountException("Account is locked"); // 帐号锁定
		}

		// 取得预先定义的用户名密码对
		return new SimpleAuthenticationInfo(username, queryPassword(username),
				getName());
	}

	private String queryPassword(String username) {
		return usernamePasswords.get(username);
	}

	private List<String> queryRoles(String username) {
		return usernameRoles.get(username);
	}

	private List<String> queryPermissions(String username) {
		return usernamePermissions.get(username);
	}
}