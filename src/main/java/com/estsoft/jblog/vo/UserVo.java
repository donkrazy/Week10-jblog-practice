package com.estsoft.jblog.vo;

import org.hibernate.validator.constraints.NotEmpty;

public class UserVo {
	
	@NotEmpty(message="이름이 비어있다")
	private String name;
	@NotEmpty(message="패스워드가 비어있다")
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
