package com.niit.Dao;

import java.util.List;

import com.niit.Models.Friend;
import com.niit.Models.User;

public interface FriendDao 
{
	List<User> getAllSuggestedUsers(String email);

	void friendRequest(Friend friend);
	List<Friend> pendingRequests(String email);

	void deleteRequest(Friend friendRequest);

	void acceptRequest(Friend friendRequest);

	List<User> listOfFriends(String email);
}
