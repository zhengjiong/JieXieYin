package org.namofo.bean;

/**
 * 帖子MODEL
 * @author Administrator
 *
 */
public class PostModel {

	private int id;
	
	private String title; //帖子标题
	
	private String content; //帖子内容
	
	private String author; //帖子作者
	
	private int count; //评论数量
	
	public PostModel(int id, String title, String content, String author, int count){
		this.id = id;
		this.title = title;
		this.content = content;
		this.author = author;
		this.count = count;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	
	
	

}
