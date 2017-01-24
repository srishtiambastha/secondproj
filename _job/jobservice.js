app.factory('jobservice',function($http){
	console.log('Entering job service')
	var jobservice={};
	var BASE_URL ="http://localhost:8081/newbackend"
		
	jobservice.saveJob=function(job)
	{
		return $http.post(BASE_URL + "/postJob" , job)
		.then(function(response){
			console.log('job service sucess')
			console.log(response.status)
			console.log(response.headers.location)
			return response.status
		},function(response){
			console.log('job service failure')
			console.log(response.status)
			return response.status
		})
	}
	
	
	jobservice.getAllJobs=function(){
		return $http.get(BASE_URL + "/getAllJobs");
	}
	return jobservice;
})