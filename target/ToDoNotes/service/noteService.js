var ToDo = angular.module('TODO');

ToDo.factory('noteService',function($http,$location){
	var userNote ={};
	
	
	userNote.add = function(note){
	
		return $http({
			method :"POST",
			url :'notes/create',
			data : note,
			headers: { 'accToken': localStorage.getItem('token')
					}
		});
	}
	
	userNote.read=function(){
		return $http({
			method:"POST",
			url:'notes/read',
			headers:{ 'accToken':localStorage.getItem('token')
				
			}
		});
	}

	
	userNote.delete=function(note){
		return $http({
			method:"POST",
			url:"notes/delete",
			data: note,
			headers: { 'accToken': localStorage.getItem('token')
			}
		});
		
		
	}
	
	
	userNote.update=function(note){
		
		return $http({
			method:"POST",
			url:"notes/update",
			data:note,
			headers: {	'accToken':localStorage.getItem('token')		
			}
		});
	}
	
	
	userNote.pin=function(note){
		return $http({
			method:"POST",
			url:"notes/pin",
			data:note,
			headers: {'accToken': localStorage.getItem('token')
			}
		});
	}
	
	
	
	userNote.trash=function(note){
		return $http({
			method:"POST",
			url:"notes/trash",
			data:note,
			headers: {'accToken': localStorage.getItem('token')
			}
		});
	}
	
	

	userNote.archive=function(note){
		return $http({
			method:"POST",
			url:"notes/archive",
			data:note,
			headers: {'accToken': localStorage.getItem('token')
			}
		});
	}
	
	userNote.color=function(note){
			return $http({
				method:"POST",
				url:"notes/color",
				data:note,
				headers: {'accToken': localStorage.getItem('token')
				}
			});
	}
	
	
	userNote.reminder=function(note){
		return $http({
			method:"POST",
			url:"notes/reminder",
			data:note,
			headers: {'accToken': localStorage.getItem('token')
			}
		});
	}
	
	userNote.createLabel=function(labelId,noteId){
		return $http({
			method:"PUT",
			url:"notes/label/"+labelId+'/note/'+noteId,
			headers: {'accToken': localStorage.getItem('token')
			}
		});
	}
	
	
	userNote.deleteLabel=function(labelId,noteId){
		return $http({
			method:"DELETE",
			url:"notes/label/"+labelId+'/note/'+noteId,
			headers: {'accToken': localStorage.getItem('token')
			}
		});
	}
	
	
	userNote.shareOnFB=function(title,note){
		return $http({
			method:"GET",
			url:"socialShare/title/"+title+"/data/"+note,
		});
	}
	
	userNote.shareNote=function(email,noteId){
		return $http({
			method:"POST",
			url:"notes/share/email/"+email+"/note/"+noteId,
			headers: {'accToken': localStorage.getItem('token')
			}
		});
	}

	
	userNote.removeShared=function(userId,noteId){
		return $http({
			method:"POST",
			url:"notes/removeShare/user/"+userId+"/note/"+noteId,
			headers: {'accToken': localStorage.getItem('token')
			}
		});
		
	}
	
	return userNote;
});
