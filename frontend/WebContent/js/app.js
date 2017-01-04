var app=angular.module("myApp",['ngRoute'])
app.config(function($routeProvider){
	console.log('entering configuration')
	$routeProvider
	.when('/login',{
		controller:'usercontroller',
		templateUrl:'_user/login.html'
	})
	.when('/register',{
		controller:'usercontroller',
		templateUrl:'_user/register.html'
	})
	.when('/home',{
		templateUrl:'_home/home.html'
	})
	.when('/listOfUsers',{
		controller:'usercontroller',
		templateUrl:'_user/listOfUsers.html'
	})
	.when('/postJob',{
		controller:'jobcontroller',
		templateUrl:'_job/createJob.html'
	})
	.when('/getAllJobs',{
		controller:'jobcontroller',  // write a function to get all jobs from the backend => JobService
		templateUrl:'_job/jobTitles.html'  // to display the job titles in html page
	})
	
	.when('/uploadPicture',{
		templateUrl:'_user/uploadFile.html'
})

.when('/friendsList',{
		controller:'friendcontroller',
		templateUrl:'_friend/listOfFriends.html'
	})
	.when('/pendingRequest',{
		controller:'friendcontroller',
		templateUrl:'_friend/pendingRequest.html'
		
	})
	.when('/getAllUsers',{
		controller:'usercontroller',
		templateUrl:'_user/listOfUsers.html'
		
	})
	
	
	.when('/addPost',{
		controller:'blogcontroller',
		templateUrl:'_blog/newPost.html'
	})
	.when('/getAllBlogs',{
		controller:'blogcontroller',
		templateUrl:'_blog/blogList.html'
	})
       .when('/getBlogDetail/:id',{
		controller:'blogdetailcontroller',
		templateUrl:'_blog/getBlogDetail.html'
	})
})