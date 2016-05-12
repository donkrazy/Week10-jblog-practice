package com.estsoft.jblog.vo;

import org.hibernate.validator.constraints.NotEmpty;

public class BlogVo {
	int id;
	String name;
	@NotEmpty(message="제목이 비어있따")
	String title;
	String logo;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	@Override
	public String toString() {
		return "BlogVo [id=" + id + ", name=" + name + ", title=" + title + ", logo=" + logo + "]";
	}
}
