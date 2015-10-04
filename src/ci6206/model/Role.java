package ci6206.model;

import java.util.List;

/**
 *Role Domain.
 *
 * Author Qiao Guo Jun
 * Date Oct 1, 2015
 * Version 1.0 
 */
public class Role {
	private Long nbr;
	private String roleName;
	private String roleDesc;
	private List<Permission> permissionList;
	private boolean selected;
	
	public Long getNbr() {
		return nbr;
	}
	public void setNbr(Long nbr) {
		this.nbr = nbr;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	public List<Permission> getPermissionList() {
		return permissionList;
	}
	public void setPermissionList(List<Permission> permissionList) {
		this.permissionList = permissionList;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	
}
