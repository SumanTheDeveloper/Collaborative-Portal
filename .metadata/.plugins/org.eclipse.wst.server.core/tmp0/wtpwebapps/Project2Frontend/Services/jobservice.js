/**
 * 
 */
app.factory('JobService',function($http){
	var jobService={}
	var BASE_URL="http://localhost:8081/Project2Middleware"
	
	jobService.addJob=function(job){
	 return	$http.post(BASE_URL+ "/addjob",job)
	}
	
	jobService.getAllJobs=function(){
		return $http.get(BASE_URL + "/alljobs")
	}
	
	jobService.deleteJob=function(id){
	 return $http['delete'](BASE_URL + "/deletejob/"+id)
	}
	return jobService;
})