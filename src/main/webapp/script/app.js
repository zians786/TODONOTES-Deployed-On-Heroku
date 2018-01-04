var todo = angular.module('TODO', [ 'ui.router','ngMessages', 'ngSanitize', 'ngAnimate',
		'ngMaterial','tb-color-picker','ngFileUpload','ngMaterialDatePicker','ngTagsInput','toastr','ngAvatar']);

todo.config([ '$stateProvider', '$urlRouterProvider',
		function($stateProvider, $urlRouterProvider) {
			
			$stateProvider.state('login', {
				url : '/login',
				templateUrl : 'template/login.html',
				controller : 'loginController'
			});
			
			
			
			$stateProvider.state('register',{
				
				url:'/register',
				templateUrl : 'template/register.html',
				controller : 'registerController'
			});
			
			
			$stateProvider.state('forgot',{
				url:'/forgot',
				templateUrl :'template/forgot.html',
				controller:'loginController'
				});
		
			
			$stateProvider.state('resetPassword',{
				url:'/resetPassword',
				templateUrl :'template/resetPassword.html',
				controller:'loginController'
				});
			
			$stateProvider.state('home',{
				url:'/home',
				templateUrl :'template/home.html',
				controller:'noteController'
				});
			
		
			$stateProvider.state('search',{
				url:'/search',
				templateUrl :'template/search.html',
				controller:'noteController'
			});


			$stateProvider.state('label',{
				url:'/label',
				templateUrl :'template/label.html',
				controller:'noteController'
			});

			
			$stateProvider.state('archive',{
				url:'/archive',
				templateUrl :'template/archive.html',
				controller:'noteController'
			});
			

			$stateProvider.state('trash',{
				url:'/trash',
				templateUrl :'template/trash.html',
				controller:'noteController'
			});
			
			
			$stateProvider.state('reminder',{
				url:'/reminder',
				templateUrl :'template/reminder.html',
				controller:'noteController'
			});
			
			$stateProvider.state('dummy',{
				url:'/dummy',
				templateUrl :'template/dummy.html',
				controller:'dummyController'
			});
			
			$urlRouterProvider.otherwise('login');
			
		} ]);
