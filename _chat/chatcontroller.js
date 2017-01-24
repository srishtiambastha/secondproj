app.controller("chatctrl", function($scope,$rootScope ,chatservice) {
    $scope.messages = [];
    $scope.message = "";
    $scope.max = 140;
    
    $scope.addMessage = function() {
      chatservice.send($rootScope.currentUser.username + " : " + $scope.message);
      $scope.message = "";
    };
    
    chatservice.receive().then(null, null, function(message) {
      $scope.messages.push(message);
    });
  });