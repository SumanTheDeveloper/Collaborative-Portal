/**
 * 
 */
app.factory('FriendService',function($http){
	var friendService={}
	var BASE_URL="http://localhost:8081/Project2Middleware"
	friendService.getAllSuggestedUsers=function(){
		return $http.get(BASE_URL + "/suggestedusers")
	}
	
	friendService.sendFriendRequest=function(toId){//toId is user object
		return $http.post(BASE_URL + "/friendrequest",toId)
	}
	
	friendService.getPendingRequests=function(){
		return $http.get(BASE_URL + "/pendingrequests")
	}
	
	friendService.acceptRequest=function(friendRequest){
		return $http.put(BASE_URL +"/acceptrequest",friendRequest)
	}
	friendService.deleteRequest=function(friendRequest){
		return $http.put(BASE_URL + "/deleterequest",friendRequest)
	}
	
	friendService.getAllFriends=function(){
		return $http.get(BASE_URL + "/friends")
	}
	
	friendService.getMutualFriends=function(useremail){
    	return $http.get(BASE_URL + "/mutualfriends?useremail="+useremail)
    }
	
	return friendService;
})