/**
 * 
 */

var ToDo = angular.module('TODO');
ToDo.controller('registerController', function($scope, registerService,
		$location) {

	$scope.registration = function() {
		console.log($scope.user);
		var http = registerService.registerUser($scope.user);
		http.then(function(response) {
			console.log("Registration successfull");
			$location.path('/login');
		}, function(response) {
			console.log("failed to register");

		});
	}
});
