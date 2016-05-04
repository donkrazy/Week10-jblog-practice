package com.estsoft.jblog.vo;

public class PostVo {
	int id;
	int category_id;
	String title;
	String content;
	String reg_date;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCategory_id() {
		return category_id;
	}
	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	@Override
	public String toString() {
		return "PostVo [id=" + id + ", category_id=" + category_id + ", title=" + title + ", content=" + content
				+ ", reg_date=" + reg_date + "]";
	}
}
