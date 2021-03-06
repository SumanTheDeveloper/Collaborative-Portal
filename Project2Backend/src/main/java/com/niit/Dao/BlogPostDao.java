package com.niit.Dao;

import java.util.List;

import com.niit.Models.BlogPost;

public interface BlogPostDao 
{
	void addBlogPost(BlogPost blogPost);
	List<BlogPost> getBlogsWaitingForApproval();
	List<BlogPost> getBlogsApproved();
	BlogPost getBlog(int blogId);
	void approveblogPost(int blogId);
	void rejectblogPost(BlogPost blogPost);
	void updateapprovedBlog(BlogPost blogPost);
}
