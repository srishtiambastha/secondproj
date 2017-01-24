app.factory('userservice',function($http){
	console.log('entering userservice')
	var BASE_URL="http://localhost:8081/newbackend"
	var userservice=this;
	
	
	userservice.fetchAllUsers=function(){
		console.log('entering fetchallusers in service')
		return $http.get("http://localhost:8081/newbackend/getUsers")
		.then(function(response){
			//response is an object returened from the back end
			//data,status,headers,statustext
			//data-list of person
			console.log(response.data)
			console.log(response.status)
			return response.data
		},
		function(response)
		{
			console.log(response.data)
			return response.data
		})
	};
	
	userservice.authenticate=function(users){
		console.log('entering servce login')
		return $http.post(BASE_URL + "/login",users);
		
	
	};
	
	
	
	userservice.registerUsers=function(users){
		console.log('entering userservice register page')
		return $http.post(BASE_URL + "/register",users) 
		.then(function(response){
			console.log(response.status)
			console.log(response.headers.location)
			return response.status
		},function(response){
			console.log(response.status)
			return response.status
		})
	}
	
	
	
	userservice.logout=function(){
		console.log('entering logout service')
		return $http.put(BASE_URL + "/logout")
	};
	
	
	userservice.getAllUsers=function(){
		console.log('entering getallusers in user service')
		return $http.get(BASE_URL +"/getUsers")
	}
	
	userservice.friendRequest=function(username){
		console.log('service --- friendRequest');
		return $http.post(BASE_URL+ '/friendRequest',username);
	}
	
	return userservice;
})