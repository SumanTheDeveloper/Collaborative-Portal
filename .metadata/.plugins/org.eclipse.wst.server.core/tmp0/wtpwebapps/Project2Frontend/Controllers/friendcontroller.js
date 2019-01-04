/**
 * 
 */
app.controller('FriendCtrl',function($scope,$location,FriendService){
	function getAllSuggestedUsers(){
	FriendService.getAllSuggestedUsers().then(function(response){
		$scope.suggestedUsers=response.data //List<User> in JSON representation
	},function(response){
		if(response.status==401)
			$location.path('/login')
	})
	}
	$scope.sendFriendRequest=function(toId){//toId is user object , it is the value for toId property in Friend entity
		FriendService.sendFriendRequest(toId).then(
				function(response){
					//S in (A - (B U C))
					getAllSuggestedUsers()
				},function(response){
					if(response.status==401)
						$location.path('/login')
				})
	}
	
	function getPendingRequests(){
		FriendService.getPendingRequests().then(function(response){
			$scope.pendingrequests=response.data
		},function(response){
			$scope.error=response.data
			if(response.status==401)
				$location.path('/login')
		})
		}
		
		$scope.acceptRequest=function(friendRequest){
			//friendRequest is an object of type Friend, it has friendId,fromId,toId,status='P'
			FriendService.acceptRequest(friendRequest).then(
					function(response){
						getPendingRequests()
					},function(response){
						$scope.error=response.data
						if(response.status==401)
							$location.path('/login')
					})
		}
		
		$scope.deleteRequest=function(friendRequest){
			FriendService.deleteRequest(friendRequest).then(
					function(response){
						getPendingRequests()
					},
					function(response){
						$scope.error=response.data
						if(response.status==401)
							$location.path('/login')
					})
		}
		
		//A-(B U C)and pendingrequest query will always gets executed
		getPendingRequests()
		getAllSuggestedUsers()//call the function
		FriendService.getAllFriends().then(
		function(response){
			$scope.friends=response.data
		},
		function(response){
			$scope.error=response.data
			if(response.status==401)
				$location.path('/login')
		})
		
		 $scope.getMutualFriends=function(useremail){
	    	FriendService.getMutualFriends(useremail).then(function(response){
	    		$scope.mutualfriends=response.data
	    		$scope.forUser=useremail
	    	},function(response){
	    		if(response.status==401)
					$location.path('/login')
	    	})
	    }
})
