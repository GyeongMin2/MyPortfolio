package net.haebup.dto.board;

public class MyPostDTO {
    private int postIdx;
    private String postType;
    private String postWriter;
    private String postTitle;
    private String postRegdate;
    private String postContent;
    private String postCategory;
    
    
    
	public String getPostCategory() {
		return postCategory;
	}
	public void setPostCategory(String postCategory) {
		this.postCategory = postCategory;
	}
	public int getPostIdx() {
		return postIdx;
	}
	public void setPostIdx(int postIdx) {
		this.postIdx = postIdx;
	}
	public String getPostType() {
		return postType;
	}
	public void setPostType(String postType) {
		this.postType = postType;
	}
	public String getPostWriter() {
		return postWriter;
	}
	public void setPostWriter(String postWriter) {
		this.postWriter = postWriter;
	}
	public String getPostTitle() {
		return postTitle;
	}
	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}
	public String getPostRegdate() {
		return postRegdate;
	}
	public void setPostRegdate(String postDate) {
		this.postRegdate = postDate;
	}
	public String getPostContent() {
		return postContent;
	}
	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}
    
    
}
