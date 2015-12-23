package investWeb.model;


public class Permission {
	private Long nbr;
	private String actionDesc;
	private String permissionName;
	private String permissionDesc;
	private boolean checked;
	
	public Long getNbr() {
		return nbr;
	}
	public void setNbr(Long nbr) {
		this.nbr = nbr;
	}
	public String getActionDesc() {
		return actionDesc;
	}
	public void setActionDesc(String actionDesc) {
		this.actionDesc = actionDesc;
	}
	public String getPermissionName() {
		return permissionName;
	}
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}
	public String getPermissionDesc() {
		return permissionDesc;
	}
	public void setPermissionDesc(String permissionDesc) {
		this.permissionDesc = permissionDesc;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
}
