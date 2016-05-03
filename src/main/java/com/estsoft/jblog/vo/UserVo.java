package com.estsoft.jblog.vo;

public class UserVo {
	private String name;
	private String password;
	private String reg_date;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	@Override
	public String toString() {
		return "UserVo [name=" + name + ", password=" + password + ", reg_date=" + reg_date + "]";
	}
}
