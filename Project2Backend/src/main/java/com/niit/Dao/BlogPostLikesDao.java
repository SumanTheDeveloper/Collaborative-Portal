package com.niit.Dao;

import com.niit.Models.BlogPost;
import com.niit.Models.BlogPostLikes;

public interface BlogPostLikesDao 
{
	BlogPostLikes hasUserLikedBlogPost(int blogPostId,String email);

	BlogPost updateLikes(int blogPostId, String email);
}
