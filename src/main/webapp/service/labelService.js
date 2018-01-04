var ToDo = angular.module('TODO');
ToDo.factory('labelService',function($http,$location){
	var label={};
	
	label.create = function(label){
		
		return $http({
			method :"POST",
			url :'label/create',
			data : label,
			headers: { 'accToken': localStorage.getItem('token')
					}
		});
	}
	
	label.read=function(){
		return $http({
			method:"POST",
			url:'label/read',
			headers:{ 'accToken':localStorage.getItem('token')
				
			}
		});
	}

	
	label.delete=function(label){
		return $http({
			method:"POST",
			url:"label/delete",
			data: label,
			headers: { 'accToken': localStorage.getItem('token')
			}
		});
		
		
	}
	
	
	label.update=function(label){
		
		return $http({
			method:"POST",
			url:"label/update",
			data:label,
			headers: {	'accToken':localStorage.getItem('token')		
			}
		});
	}
	
	return label;
});