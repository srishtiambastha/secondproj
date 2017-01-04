app.factory('blogservice',function($http){
	var blogservice=this;
	blogservice.addPost=function(blogPost){
		console.log('addpost in service')
		return $http.post("http://localhost:8081/newbackend/blog",blogPost);
	}
	
	blogservice.getBlogPosts=function(){
		console.log('getBlogposts in service')
		return $http.get("http://localhost:8081/newbackend/blog/list")
	}
	blogservice.getBlogDetail=function(id){
		console.log('getBlogDetails')
		return $http.get("http://localhost:8081/newbackend/blog/get/"+ id)
	}
	
	blogservice.addComment=function(comment){
		return $http.post("http://localhost:8081/newbackend/blog/comment",comment)
	}
	blogservice.getComments=function(blogId){
		console.log('getcomments -- service')
		return $http.get("http://localhost:8081/newbackend/blog/getcomments/"+blogId)
	}
	return blogservice;
})