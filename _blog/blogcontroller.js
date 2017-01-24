app.controller('blogcontroller',function($scope,$location,blogservice){
	$scope.blogPost={};
	
	$scope.blogPosts=[]
	$scope.submit=function(){
		console.log('submit in blog controller to add new post')
		blogservice.addPost($scope.blogPost)
		.then(function(response){
			console.log(response.data);
			console.log(response.status);
			alert("successfully added")
			$location.path('/getBlogs')
			
		},function(response){
			console.log(response.data);
			console.log(response.status);
		})
	}
	$scope.blogPosts=blogservice.getBlogPosts()
	.then(function(response){
		console.log(response.status)
		console.log(response.data)
		$scope.blogPosts=response.data
	},function(response){
		console.log(response.status)
		console.log(response.data)
	})
	
})