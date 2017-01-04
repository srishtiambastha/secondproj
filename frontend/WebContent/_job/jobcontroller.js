app.controller('jobcontroller',function($scope,$location,jobservice){
	$scope.job={jobTitle:'',jobDescription:'',skillsRequired:'',salary:'',location:''}
	
	$scope.saveJob=function()
	{
		console.log('entering save job in job controller')
		jobservice.saveJob($scope.job)
		.then(function(response){
			console.log('entered sucess job')
			console.log("job success" + response.status)
			console.log("successfully inserted job details");
			alert("Posted job successfully");
			$location.path('/home');
		},function(response){
			console.log('Entered failure job')
			console.log("failure " +response.status);
			console.log(response.data.message)
			$location.path('/postJob')
		})
	}
	
	
	function getAllJobs(){
		console.log('entering get All jobs')
		jobservice.getAllJobs()
		.then(function(response){
			console.log(response.status); //200
			$scope.jobs=response.data;  //List<Job>
			
		},function(response){
			console.log(response.status)
		})
	}
	getAllJobs();
})