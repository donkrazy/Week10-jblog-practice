package com.estsoft.jblog.vo;

import org.hibernate.validator.constraints.NotEmpty;

public class CategoryVo {
	int id;
	int blog_id;
	@NotEmpty(message="카테고리 이름이 비어있따")
	String name;
	@NotEmpty(message="카테고리 설명좀")
	String description;
	int count;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBlog_id() {
		return blog_id;
	}
	public void setBlog_id(int blog_id) {
		this.blog_id = blog_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "CategoryVo [id=" + id + ", blog_id=" + blog_id + ", name=" + name + ", description=" + description
				+ ", count=" + count + "]";
	}
}
