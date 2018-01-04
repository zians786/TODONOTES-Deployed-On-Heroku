var todo=angular.module("TODO");
todo.controller('noteController',function(toastr,$scope,noteService,loginService,labelService,$location,$mdDialog,$mdToast,$interval,$filter){

	var allNotes=[];
	   
	var getUserdetail=function(){
		var userInfo=loginService.UserInfo();
		userInfo.then(function(response){
			$scope.user=response.data;
			console.log($scope.user);
		},function(response){
			$location.path('login');
		});
	}
	getUserdetail();
	$scope.visible='hidden';

	
	
	

    // for Notification using toastr
    var reminderCheck=function(note){
    	
    	$interval(function(){
    		for(var i=0;i<note.length;i++){
    			if(note[i].reminder){
    				var date=new Date(note[i].reminder);
    				var currentDate=new Date();
    			
	            	if (((date.getTime()-10)<currentDate.getTime())&&(date.getTime()+10)>currentDate.getTime()) {
	            		console.log(note[i]);
	            		 toastr.success(note[i].title, note[i].description,{
	                		 closeButton: true	
	                	 });
	            		 note[i].reminder="";
	            		 noteService.update(note[i]);
	            	}	
    			}	
    
    		}	
    });
    }
	// for fetching all the User notes
	var read=noteService.read();
	read.then(function(response){
		console.log(response.data);
		$scope.readNotes=response.data;
		allNotes=$scope.readNotes;
		  reminderCheck($scope.readNotes);
		  },function(response){
		if(response.status==409)
			{
				$scope.error=response.data.responseMessage;
			}
		else
			{
				console.log("fail");
				$scope.error="Enter valid data";
			}
	});

	
	// for fetching all user labels
	var getAllLabel=function(){
	var getLabel=labelService.read();
	getLabel.then(function(response){
		$scope.readLabel=response.data;
	});
	}
	getAllLabel();
	
	
  
	// funtion to be called after any operation on notes
	var getNotes=function(){
	var read=noteService.read();
	read.then(function(response){
		console.log(response.data);
		$scope.readNotes=response.data;
		$scope.showPin=false;
		for(var i=0;i<$scope.readNotes.length;i++){
			if($scope.readNotes[i].pinned){
				console.log("inside pin fun");
				$scope.showPin=true;
			}
		}
		 reminderCheck($scope.readNotes);
});
	return $scope.readNotes;
	}
	
	
	
	
	$scope.mycolor=function(){
		console.log("dd");
	}
	
	

	
	// for deleting notes
	$scope.delete=function(note){
		console.log(note);
		var noteDelete=noteService.delete(note);
		noteDelete.then(function(response){
			console.log("note deleted..");
			getNotes();
		});
	}
	
	
	
	// for trash
	$scope.trash=function(note){
		console.log(note);
		var trashNote=noteService.trash(note);
		trashNote.then(function(response){
			console.log("note Trashed..");
			getNotes();
		});
	}
	
	
	// for restore from trash
	$scope.restore=function(note){
		console.log(note);
		var trashNote=noteService.trash(note);
		trashNote.then(function(response){
			console.log("note unTrashed..");
			getNotes();
		});
	}
	
	// for archived
	$scope.archive=function(note){
		console.log(note);
		var archiveNote=noteService.archive(note);
		archiveNote.then(function(response){
			console.log("note Trashed..");
			getNotes();
		});
	}
	
	

	// for pin
	    $scope.pin=function(note){
			console.log(note);
			var pinResp=noteService.pin(note);
				pinResp.then(function(response){
				console.log("note Pinned..");
				getNotes();
			});
		}
		    
	
	
	
	// for unArchived
	$scope.unArchive=function(note){
		console.log(note);
		var archiveNote=noteService.archive(note);
		archiveNote.then(function(response){
			console.log("note unArchived..");
			getNotes();
		});
	}
	
	
	// for logout
	$scope.logout=function(){
		localStorage.clear();
		$location.path('login');
		
	}
	
	
	
	
	
	// for adding user notes
	$scope.addNote=function(note){
		
		var add=noteService.add(note,$scope.error);
		add.then(function(response){
			console.log(response.data);
			getNotes();
			console.log("Note Added Successfully");
			$scope.showTitle=false;
			$scope.note.description="";
			$scope.note.title="";
			
	},function(response){
			if(response.status==409)
				{
					$scope.error=response.data.responseMessage;
				}
			else
				{
					console.log("fail");
					$scope.error="Enter valid data";
				}
		});
	}
	
	
	// for editing notes inside Dialog
	$scope.showDialog=function(editNote){
		console.log("inside showDialog ");
		$scope.notes= angular.copy(editNote);
		  $mdDialog.show({
			    contentElement: '#myStaticDialog',
			    parent: angular.element(document.body),
		      clickOutsideToClose:true
			  });
			}
	
    $scope.showLabelDialog=function(){
    	$mdDialog.show({
    		contentElement: '#myLabelDialog',
    		parent: angular.element(document.body),
    		clickOutsideToClose:true
    	});
    }

    
    // Dialog for Collaborators
    
    $scope.showCollab=function(note){
    	$scope.sharedNote=note;
    	
    	
    	 $mdDialog.show({
			    contentElement: '#myCollabDialog',
			    parent: angular.element(document.body),
		      clickOutsideToClose:true
			  });
    }
	
	
	// for updating notes
		    $scope.updateNote = function(note) {
		    	console.log("inside dialog controller");
		    	var updateResponse=noteService.update(note);
		    	updateResponse.then(function(response){
		    		$mdDialog.hide();
		    		getNotes();
		    	});
		      
		    };

	// for uploading image
		    $scope.uploadImage = function(type) {
				$scope.type = type;
				$('#image').trigger('click');
				return false;
		    }
		    
		    $scope.stepsModel = [];
		    $scope.imageUpload = function(element){
			    var reader = new FileReader();
			    console.log("ele"+element);
			    reader.onload = $scope.imageIsLoaded;
			    reader.readAsDataURL(element.files[0]);
			    console.log(element.files[0]);
			}
		
			$scope.imageIsLoaded = function(e){
			    $scope.$apply(function() {
			        $scope.stepsModel.push(e.target.result);
			        console.log(e.target.result);
			        var imageSrc=e.target.result;
			        if($scope.type ==='input')
		        	{
			        	$scope.addImg= imageSrc;
		        	}else if($scope.type ==='user'){
		        		$scope.user.picUrl=imageSrc;
		        		updateUser($scope.user);
		        	}else if($scope.type ==='update'){
		        		$scope.changeIamge.image=imageSrc;
		        		update($scope.changeIamge);
		        	}
			        else{
			        	
		        		$scope.type.image=imageSrc;
// console.log(e.target.result);
// console.log(imageSrc);
		        		console.log($scope.type)
		        		var updateResponse=noteService.update($scope.type);
		        		updateResponse.then(function(response){
		        			console.log(response);
		        			getNotes();	
		        		},
		        		function(response){
		        			console.log(response);
		        		});
//		        		
			        }
			    });
	};
		    
		    
		    
		    
	
	// for expanding
	$scope.showTitle=false;
	$scope.expandDiv=function(){
		$scope.showTitle=true;
	}
	
		
	
	// for list and grid view
	$scope.view="true";

	$scope.flex="30";
	$scope.align1="start";
	$scope.align2="start";
	$scope.changeView=function(){

		if($scope.view){
			$scope.flex="65";
			$scope.align1="center";
			$scope.align2="center";
			$scope.view=!$scope.view;
		}else
		{
			$scope.flex="30";
			$scope.align1="start";
			$scope.align2="start";
			$scope.view=!$scope.view;	
		}
		
		
	}
	
	// for color-picker
		$scope.options = ['transparent','#FF8A80', '#FFD180', '#FFFF8D', '#CFD8DC', '#80D8FF', '#A7FFEB', '#CCFF90'];
	    $scope.color = '#FF8A80';

	    $scope.colorChanged = function(newColor,note) {
	    	note.color=newColor;
	    	var colorResponse=noteService.color(note);
	    	colorResponse.then(function(response){
	    		console.log(response.data);
	    	});
	    }
	    
	
	    
	
	// for checking reminder
	    
	    
	// toggle navbar
	    $scope.showNav=true;
	    $scope.hideNav=function(){
	    	$scope.showNav=!$scope.showNav;
	    	}	    
	
	$scope.search=function(){
		$location.path('search');
	}
	    
	// for getting all archived notes
	    $scope.archivedNotes=function(){
	    	$location.path('archive');
	    }
	    
	// for getting all trash notes
	    $scope.trashNotes=function(){
	    	$location.path('trash');
	    }
	  
	    
	// for home notes
	    $scope.home=function(){
	    	$location.path('home');
	    }
	    
	    
	 // for setting reminder
	    $scope.reminder=function(note,remind){
	    	note.reminder=remind;
	    	console.log(note.reminder);
	    	var date=Date.parse(note.reminder);
	    	note.reminder=date;
	    	console.log(note);
	    	var updateResponse=noteService.update(note);
	    	updateResponse.then(function(response){
	    		getNotes();
	    	});
	    
	    }
	    
	    
	    $scope.remindSet=function(note,caseValue){
	    	var date=new Date();
    		date.setMinutes(0);
    		date.setSeconds(0);
	    	switch(caseValue){
	    	case 0:
	    		date.setHours(20);
	    		break;
	    	case 1:
	    		date.setDate(date.getDate()+1);
	    		date.setHours(8);
	    		break;
	    	case 2:
	    		date.setHours(8);
	    		var day=8-date.getDay();
	    		date.setDate(date.getDate()+day);
	    		break;
	    	}
	    	
	    	note.reminder=date;
	    	console.log(note);
	    	
	    }
	
	    
	    
	    
	    
	    $scope.reminderSet=function(note,caseValue){
	    	var date=new Date();
    		date.setMinutes(0);
    		date.setSeconds(0);
	    	switch(caseValue){
	    	case 0:
	    		date.setHours(20);
	    		break;
	    	case 1:
	    		date.setDate(date.getDate()+1);
	    		date.setHours(8);
	    		break;
	    	case 2:
	    		date.setHours(8);
	    		var day=8-date.getDay();
	    		date.setDate(date.getDate()+day);
	    		break;
	    	}
	    	
	    	note.reminder=date;
	    	console.log(note);
	    	var updateResponse=noteService.update(note);
	    	updateResponse.then(function(response){
	    		getNotes();
	    	});	
	    	
	    }
	    
	
	// Dialog for Label
	/*
	 * $scope.showLabelDialog=function(){ $mdDialog.show({
	 * templateUrl:"template/labelDialog.html", controller:'noteController',
	 * parent: angular.element(document.body), clickOutsideToClose:true }); }
	 */
	    
	// Adding Label
	    
	    $scope.addLabel=function(label){
	    	var addLabelResp=labelService.create(label);
	    	addLabelResp.then(function(response){
	    		console.log(response.data);
	    		getAllLabel();
	    		$scope.label.labelName="";
	    	},function(response){
	    		
	    	});
	    }
	    
	// deleting label
	    $scope.deleteLabel=function(label){
	    	var deleteLabelResp=labelService.delete(label);
	    	deleteLabelResp.then(function(response){
	    		console.log(response.data);
	    		getAllLabel();
	    	},function(response){
	    		
	    	});
	    }
	
	// updating label
	    $scope.updateLabel=function(label){
	    	var updateLabelResp=labelService.update(label);
	    	updateLabelResp.then(function(response){
	    		console.log(response.data);
	    		getAllLabel();
	    		 toastr.success("Success", "Label Updated to "+label.labelName);
	    	},function(response){
	    		
	    	});
	    }
	    
	    
	    
	// setting label to notes
	    $scope.addNoteLabel=function(labelId,noteId){
	    var setLabelResp=noteService.createLabel(labelId,noteId);
	    setLabelResp.then(function(response){
	    	console.log(response.data);
	    	getNotes();
	    },function(response){
	    	console.log(response.data);
	    });
	    
	 }
	 
	// for deleting the label
	    $scope.deleteNoteLabel=function(labelId,noteId){
	    	var delLabelResp=noteService.deleteLabel(labelId,noteId);
		    delLabelResp.then(function(response){
		    	console.log(response.data);
		    	getNotes();
		    },function(response){
		    	console.log(response.data);
		    });
	    }
	   
	    
	// for redirecting label
	    $scope.showLabels=function(){
	    	   $location.path('label'); 
	    }
	    
	       
	    
	// for gettingNoteByLabel 
	    $scope.getNoteByLabel=function(labelName){
	    	$scope.allNotesByLabel=[];var index=0;
	    	$scope.matchLabel=labelName;
	    	for(var i=0;i<allNotes.length;i++){
	    		if(allNotes[i].label){
	    			var allLabel=allNotes[i].label;
	    			for(var j=0;j<allLabel.length;j++){
	    				if(allLabel[j].labelName==labelName){
	    					$scope.allNotesByLabel[index++]=allNotes[i];
	    				}
	    			}
	    		}
	    	}
	    	
	    }
	    
	    
	// for socail Share using feed
	    
	/*
	 * $scope.socialShare=function(title,note){
	 * noteService.shareOnFB(title,note); }
	 */
	// using open-graph
	    
		$scope.socialShare = function(note) {
			FB.init({
				appId : '1585966938157751',
				status : true,
				cookie : true,
				xfbml : true,
				version : 'v2.4'
			});
			FB.ui({
				method : 'share_open_graph',
				action_type : 'og.likes',
				action_properties : JSON.stringify({
					object : {
						'og:title' : note.title,
						'og:description' : note.description
					}
				})
			}, function(response) {
				alert('Posting Successfull..');
			}, function(error) {
				alert('Somthing went Wrong,Posting of fb Unsuccessfull..');
			});
		};
		
		
		$scope.chen=function(){
			console.log("ITS working");
		}
		
	// for Sharing note (Collaborator)
		
		$scope.shareNote=function(email,noteId){
			var shareResp=noteService.shareNote(email,noteId);
			shareResp.then(function(response){
				$scope.sharedNote=response.data;
				getNotes();
				console.log(response.data);
			})
		}
		
	// for Removing Shared Note
		
		$scope.removeShared=function(userId,noteId){
			var removeResp=noteService.removeShared(userId,noteId);
			removeResp.then(function(response){
				console.log(response.data);
				$scope.sharedNote=response.data;
				getNotes();
			},function(response){
				console.log(response.data);
				
			});
	
		}
		
		
		$scope.searchAll=function(text){
			var result=[];
			$scope.searchNotes=result;
			if(text.length>0){
			var notes=allNotes;
			var index=0;
			var result=[];
			for(var i=0;i<notes.length;i++){
				if((notes[i].title.toLowerCase()).search(text)>-1){
				result[index++]=notes[i];
				}
				else if((notes[i].description.toLowerCase()).search(text)>-1){
					result[index++]=notes[i];
				}
				else if(notes[i].label){
					var label=notes[i].label;
					for(var j=0;j<label.length;j++){
						if((label[j].labelName.toLowerCase()).search(text)>-1){
							result[index++]=notes[i];
						}
					}
				}else if(notes[i].sharedUser){
					console.log("inside share");
					var shared=notes[i].sharedUser;
					for(var k=0;j<shared.length;k++){
						if((shared[k].email.toLowerCase()).search(text)>-1){
							result[index++]=notes[i];
						}else if((shared[k].userName.toLowerCase()).search(text)>-1){
							result[index++]=notes[i];
						}
					}
				}
			}
			console.log(result);
			$scope.searchNotes=result;
			}
			return result;
		}
		

});