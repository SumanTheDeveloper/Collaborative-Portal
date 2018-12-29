package com.niit.Dao;

import com.niit.Models.ProfilePicture;

public interface ProfilePictureDao 
{
	void saveOrUpdateProfilePicture(ProfilePicture profilePicture);
	ProfilePicture  getProfilePicture(String email);
}
