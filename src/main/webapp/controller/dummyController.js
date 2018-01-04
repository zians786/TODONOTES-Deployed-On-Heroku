var ToDo = angular.module('TODO');
ToDo.controller('dummyController',function($scope,loginService,$location){
	var tokenResp=loginService.socialLogin();
	tokenResp.then(function(response){
		console.log(response.data);
		localStorage.setItem('token',response.data.message);
		$location.path('home');
	});
});
	