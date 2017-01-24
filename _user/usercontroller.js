app.controller('usercontroller',function($scope,$rootScope,$location,userservice){
	console.log('entering the user controller')
	$scope.users;
	$scope.user={id:'',username:'',password:'',email:'',role:'',isOnline:'',enabled:''};
	$scope.message;
	function fetchAllUsers()
	{
		console.log('entering fetchall users  in controller')
		userservice.fetchAllUsers()
		.then(
				function(d)
				{
					console.log('entered to d')
					$scope.users=d;
				},
				function(error)
				{
					console.log(error);
				}
				)
	}
	fetchAllUsers();
	$scope.submit=function(){
		console.log('Entering - submit function in usercontroller')
		
		console.log('Entering controller login')
		userservice.authenticate($scope.user)
		.then(function(response){
			console.log('entering success login')
				$scope.user=response.data;
				$rootScope.currentUser=$scope.user;
				$location.path("/home");
		},
		function(response){//invalid user name and password - failure 
			 console.log('invalid username and password')
			  $scope.message="Invalid Username and Password!!";
			  $location.path("/login");
		})
	}
	
	$scope.registerUsers=function()
	{
		console.log('entering function save in user controller')
	
		userservice.registerUsers($scope.user)
		.then(
		  function(response)
		  {
			  //console.log(d.status) 
			  console.log("registration success" + response.status)
				$location.path("/home");
			  //fetchAllPersons();
			  //redirect
			 // $location.path('/listOfPerson')
		  },
		  function(response)
		  {
			  //console.log(d.status)
			  console.log("registration failed" + response.status)
			console.log(response.data)
			$scope.errorMessage="Registration failed...." + response.data.message
			$location.path("/register")
		  });
	}
	
	$rootScope.logout=function(){
		console.log('logout function')
		delete $rootScope.currentUser;
		
		userservice.logout()
		.then(function(response){
			console.log("logged out successfully..");
			$scope.message="Logged out Successfully";
			$location.path('/login')
		},
		function(response){
			console.log(response.status);
		})
		
	}
	$rootScope.logout=function(){
		console.log('logout function')
		delete $rootScope.currentUser;
		
		userservice.logout()
		.then(function(response){
			console.log("logged out successfully..");
			$scope.message="Logged out Successfully";
			$location.path('/login')
		},
		function(response){
			console.log(response.status);
		})
		
	}
	
	$rootScope.hasRole=function(role){
		if($rootScope.currentUser.role==undefined)
			return false;
		return $rootScope.currentUser.role==role;
	}
	
	
	
	/** To Send FriendRequest from listOfUsers*/
	$scope.friendRequest=function(username){
		alert('friendRequest in userontroller')
		console.log('friendrequest function')
		userservice.friendRequest(username)
		.then(function(response){
			console.log(response.status);
			alert('Friend request Send')
			getAllUsers();
			$location.path('/getAllUsers')
		},
		function(response){
			console.log(response.status);
		}
		)
	}
	
	
	
	function getAllUsers(){
		console.log('entering get all users ')
		userservice.getAllUsers()
		.then(function(response){
		console.log(response.status)
		console.log(response.data)
		$scope.users=response.data
		},function(response){
			console.log(response.status)
		}
		)
	}
	getAllUsers()
	/*$scope.registerUser=function(){
		console.log('entering registerUser')
		UserService.registerUser($scope.user)
		.then(function(response){ //success 
			console.log("registration success" + response.status)
			$location.path("/home");
		},function(response){//failure
			console.log("registration failed" + response.status)
			console.log(response.data)
			$scope.errorMessage="Registration failed...." + response.data.message
			$location.path("/register")
		})
	}*/
})