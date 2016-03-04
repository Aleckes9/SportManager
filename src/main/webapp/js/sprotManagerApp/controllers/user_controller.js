'use strict';


angular.module('ProjetSportManager.Controllers.User_controller',[]).
	controller("UserPictureController",
			["$scope", "$location", "UserPicture",
			 function($scope, $location, UserPicture) {				
				// Barre de recherche masquée
				$scope.setListe(false);
	
				// Stokage des fichiers dans un tableau pour pouvoir afficher la taille et le nom du fichier dans user-picture.html
				// Appelée sur le onChange du champ input (chemin de la photo) de user-picture.html
				// Permet aussi au travers "files.length" d'afficher le bouton "Upload" et le span d'information.
			    $scope.setFiles = function(element) {
			    	$scope.$apply(function($scope) {
			    		// 	Stockage du fichier dans un tableau
			    		$scope.files = []
			    		for (var i = 0; i < element.files.length; i++) {
			    			$scope.files.push(element.files[i]);
			    		}
			    	});
			    };
			    
			    
			    // Mise à jour de l'image sur le serveur.
				// Appelée qd click sur bouton "Upload" de user-picture.html
				$scope.uploadFile = function(user) {
					var fd = new FormData();
					
					// Recup des informations stockées grace à "$scope.setFiles"
					var tst = angular.copy($scope.files[0]);
					fd.append("user_picture",$scope.files[0]);
					
					
					// Sauvegarde 
					var tmp = angular.copy($scope.currentUser.links);
					// Récupération du lien et de la ethode pour "action_picture_update" 
			    	$scope.setupLinksComponent($scope.currentUser.links, "picture");
			    	
			    	// Si on a le droit : ($scope.url != null) && ($scope.method != null) alors on met à jour sur le serveur
			    	if ($scope.url) {		    		
			    		UserPicture.updatePicture($scope.url, fd).then(
			    			function(data) {
			    				console.log("OK update picture " + $scope.method);
			    				$scope.setReloadWindow(true);
			    				$location.path("/user/" + $scope.currentUser.tech_id);
			    		   	},
			    		   	function(data){
				       			console.log("Erreur " + ":" + " Label:" + data.label + "/ Detail message:" + data.detailMessage);
				       			$scope.callNotify("Erreur " + ":" + " Label:" + data.label + "/ Detail message:" + data.detailMessage);
				       			$location.path("/user/" + $scope.currentUser.tech_id);
			    		   	}
			    		);			    		
			    	}	    	
				}
			    
				$scope.gotoUserPage = function() {
					$location.path("/user/" + $scope.currentUser.tech_id);
				}
			}]
	).
	controller("UserListController",
			["$scope", "$location", "User", "menuOptions", "usersListData", 
			 function($scope, $location, User, menuOptions, usersListData) {
				
				/** Pour Init gestion des tris
				 */
				$scope.ordering(true);

				/** Quand click sur les flèches de tri
				 */
				$scope.triUser = function(name,ascending) {
					$scope.predicate = name;
					$scope.reverse= ascending;
					$scope.ordering(ascending);
				}
				
				/** Pour afficher la barre de recherche
				 */
				$scope.setListe(true);
					
				/** Récupération du menu Gauche
				 * Modifié car avec les nouvelles version d'Angular le retour sont des "Promises" donc à traiter en tant que telle !
				 */				
	        	menuOptions.then(function(result) {
	        		$scope.menuOptions = result;
	        	});
				
				/** Recup la liste de Users à afficher avec 3 item par page et on commance sur la première page */
//				$scope.users = User.query({size: 3,page:(0)}, function (lstUsers) {
//					console.log(lstUsers);
					/** On init la pagination */
					/** attention pour les ressource la prem page est 0 pour Angular c'est 1 !! */
//					$scope.setCurrentPage(lstUsers.page.number + 1);
//					$scope.setNbOfPages(lstUsers.page.totalPages);
//					$scope.setTotalItems(lstUsers.page.totalElements);
//				});

	        	
	        	$scope.isCollapse = [];
	        	var i = 0;
        		angular.forEach(usersListData.content, function(user) {
        			user.close = true;
        			i++;
        		})
	        	$scope.users = usersListData;

	        	
				$scope.setSelectedPage(usersListData.page.number + 1);
				$scope.setNbOfPages(usersListData.page.totalPages);
				$scope.setTotalItems(usersListData.page.totalElements);					

				/** Pour la gestion de la pagination */
				$scope.setPage = function () {
					User.query({size: 3,page:($scope.selectedPage-1)},
						function(data) {
							/** Close tout les détails de User */
			        		var i = 0;
			        		angular.forEach(data.content, function(user) {
			        			user.close = true;
			        			i++;
			        		});
			        		$scope.users = data;
						},
						function (data, headers) { // Erreur
				    		$scope.callNotify("Erreur " + ":" + " Label:" + data.data.label + "/ Detail message:" + data.data.detailMessage);
				    		console.log("Erreur " + ":" + " Label:" + data.data.label + "/ Detail message:" + data.data.detailMessage);
				    	}						
					);
				};
				
				
				
				console.log("$scope.users :" + $scope.users);

				/** Pour récupérer le Detail au niveau de la liste des Users (click sur le petit triangle)
				 */
				$scope.getGetUserDetail = function (user) {
			    	console.log("getGetUserDetail");
			    	user.close = !user.close;
			    	if (!user.close) {
						/** See PocSimone for detail
			        	user.userManagedProjects = UserManagedProjects.query({id:user.tech_id}, function (managedProjects) {
			        	});		
			    		user.userInterestedInTechnologies = UserTechnologies.query({id:user.tech_id}, function (userTechnologies) {
			    		});
			    		user.userDevelopedProjects = UserDevelopedProjects.query({id:user.tech_id}, function (developedProjects) {
			    		});
						*/
			    	}
			   };	
			   
			   
			   $scope.detailUser = function(indice) {
				 console.log("detailUser1 :" + indice);
			   };
			   
			   /** Quand click sur bouton "add" dans user-list.html
			    */
			   $scope.gotoUserNewPage = function () {
				   /** Récupération du liens et de la methode disponible pour "action_create"
				       Si l'utilisateur n'a pas le droit de création $scope.url et $scope.method seront vide
				       et donc le POST ne pourra pas être déclanché au niveau du UserNewController $scope.submit !!
				    */ 
			    	$scope.setupLinksComponent($scope.users.links, "create_via_post");
			   };
			    

			/** Quand click sur bouton "del" dans user-list.html
			*/
			   $scope.deleteUser = function (myUser) {
			    	console.log('Delete Person ' + myUser.tech_id);

			    	/** Récupération du User à supprimer pour avoir son id 
			    	*/
					var toDeleteUser = User.get({id:myUser.tech_id}, function (user) {

						/** Init du user courrant
						 */
						$scope.setCurrentUser(user);
						
						/** Récupération du liens et de la methode disponible pour "action_delete"
					        Si l'utilisateur n'a pas le droit de suppression $scope.url et $scope.method seront vide
					        et donc le DELETE ne pourra pas être déclanché. 
					    */ 
						user.$delete({'id':user.tech_id}, function () {
		       				console.log("OK delete" + status);
		       				// Mise à jour du modèle qui grâce au "binding" va mettre à jour la vue. 
		       				$scope.users.content = $scope.delElement($scope, $scope.users.content, user);
				        },
				    	function (data, headers) { // Erreur
				    		$scope.callNotify("Erreur " + ":" + " Label:" + data.data.label + "/ Detail message:" + data.data.detailMessage);
				    		console.log("Erreur " + ":" + " Label:" + data.data.label + "/ Detail message:" + data.data.detailMessage);
				    	});													
					});
			   };
			}]
	).
	controller("UserDetailController",
			["$scope", "$window", "$resource", "$routeParams", "$location", "notify", "User", "UserPicture",
			 function($scope, $window, $resource, $routeParams, $location, notify, User, UserPicture) {
				
				/** Pour masquer la barre de recherche
				 */				
				$scope.setListe(false);

				
				/** Si l'image a été modifiee alors reload de la page pour afficher la nouvelle
				 */				
				if ($scope.reloadWindow == true) {
					$scope.setReloadWindow(false);
					$window.location.reload();
				}
				
				
				
				/** Récuperation des informations du user selectionné
				 */
				$scope.user = User.get({id:$routeParams.id}, function (user) {
					/** Traitement de l'mail 
					 */
					var tmp = user.mail;
					if (tmp === undefined) {
					} else {
						var posArob = tmp.indexOf("@");
						user.mail =  tmp.substring(0, posArob);

					}
					
					/** Init des info du user courrant
					 */
					$scope.setCurrentUser(user);
					console.log('Current user : ' + $scope.currentUser.firstName + " " + $scope.currentUser.lastName + " picture:" + $scope.userPicture);
					/** Stockage du user courrant pour la fonction UNDO en cas de problème 
					 */
					$scope.savUser = angular.copy(user);		 
					
					console.log("init: " + $scope.savUser.firstName + " " + $scope.savUser.lastName);
				});
				
				
				
				/** Page de modif de l'image
				 */
				$scope.gotoUserPicturePage = function(user) {
					$location.path("/user/" + user.tech_id + "/picture");
				}

				
				/** Suppression de la photo de l'utilisateur
				 */
				$scope.deleteUserPicture = function(user) {
					/** Init des info du user courrant
					 */					
					$scope.setCurrentUser(user);
					
					/** Récupération du liens et de la methode disponible pour "action_picture_delete"
			        	Si l'utilisateur n'a pas le droit de suppression $scope.url et $scope.method seront vide
			        	et l'action ne pourra pas être déclanchee. 
					 */ 
					$scope.setupLinksComponent(user.links, "picture");					
					console.log("Delete picture :" + user.lastName + " first:" + user.firstName);
			    	if ($scope.url) {
			    		UserPicture.deletePicture($scope.url).then(
				    		function(okMessage) {
				    			console.log("OK  " + okMessage);
				    			$scope.setReloadWindow(true);
				    	   	},
			    		   	function(data){
				       			console.log("Erreur " + ":" + " Label:" + data.label + "/ Detail message:" + data.detailMessage);
				       			$scope.callNotify("Erreur " + ":" + " Label:" + data.label + "/ Detail message:" + data.detailMessage);
			    		   	}
				    	);			    		
			    	} else {
			    	}
				}
				
				/** Modif des attributs du user (hors tableaux)
				 */
				$scope.change = function(user, oldValue) {
					var em = "";
					if (user.mail != "") {
	
					}
					

					if (angular.isUndefined($scope.currentUser.firstName) || ($scope.currentUser.firstName.length == 0) || 
							angular.isUndefined($scope.currentUser.lastName) || ($scope.currentUser.lastName.length == 0)) {
						notify("FirstName and/or LastName can not be empty !!");
						/** Fonction UNDO si on venait à supprimer le nom et prenom de l'utilisateur qui sont des 
						 	informations obligatoires. 
						 */
						$scope.$watch('user', function(value) {
						    if (value && value !== '') {
						    	value.firstName = oldValue.firstName;
						    	value.lastName = oldValue.lastName;
						    }
						}, true);
						return;
					}
					
					
					/** Init des info du user courrant
					 */					
					$scope.setCurrentUser(user);
					
					/** Récupération du liens et de la methode disponible pour "action_update"
		        		Si l'utilisateur n'a pas le droit de suppression $scope.url et $scope.method seront vide
		        		et l'action ne pourra pas être déclanchee. 
					 */ 
					console.log("update :" + user.lastName + " first:" + user.firstName);
			    	if (true) {
						var stTMp = '';
						stTMp = '{'  +
							'"tech_id":' + $scope.currentUser.tech_id + ',' +
							'"firstName":"' + $scope.currentUser.firstName + '",' +
							'"lastName":"' + $scope.currentUser.lastName + '",' +
							'"mail":"' + em + '"' +
						'}';
						
						User.update(			    			
					    	{id:$scope.currentUser.tech_id}, // Params
					    	stTMp, // Data
					    	function (data, headers) { // OK
					    		console.log("udate OK");
					    	},
					    	function (data, headers) { // Erreur
					    		$scope.callNotify("Erreur " + ":" + " Label:" + data.data.label + "/ Detail message:" + data.data.detailMessage);
					    		console.log("Erreur " + ":" + " Label:" + data.data.label + "/ Detail message:" + data.data.detailMessage);
					    		/** UNDO la mise à jour du modele */
					    		$scope.currentUser.firstName = oldValue.firstName;
					    		$scope.currentUser.lastName = oldValue.lastName;
					    	}
				    	);			    					    							    				    		
			    	} else {
			    	}
				}
				
				
				/** See PocSimone for detail
				$scope.userManagedProjects = UserManagedProjects.query({id:$routeParams.id}, function (managedProjects) {
				});		
				$scope.ManagedProjects = Projects.query({}, function (projects) {
				});
				$scope.userDevelopedProjects = UserDevelopedProjects.query({id:$routeParams.id}, function (developedProjects) {
				});
				$scope.DevelopedProjects = Projects.query({}, function (projects) {
				});
				$scope.Technologies = Technologies.query({}, function (technologies) {
				});
				$scope.userInterestedInTechnologies = UserTechnologies.query({id:$routeParams.id}, function (userTechnologies) {
				});
				*/
				
				/** +/- sur les techno du user */
				/** See PocSimone for detail
			    $scope.updateUserTechnology = function (technology, operationType) {
			    	console.log(operationType + ' technology ' + technology.name + ' du user id ' + $scope.currentUser.tech_id);
			    	switch (operationType) {
						case 'delete':			
					    	$scope.userInterestedInTechnologies.content = $scope.delElement($scope, $scope.userInterestedInTechnologies.content, technology);
						break;
						case 'add':
							$scope.addElement($scope.userInterestedInTechnologies.content, technology, $scope.Technologies.content);
						break;
						default:
						break;
					}    	
				    	

			    	var stTMp = "";
			    	var tabData = []
			    	angular.forEach($scope.userInterestedInTechnologies.content, function(element){
			    		stTMp = '{'  +
			    			'"tech_id":"' + element.tech_id + '",' +
			    			'"name":"' + element.name + '",' +
			    			'"description":"' + element.description + '",' +
			    			'"priority":"' + element.priority + '"' +
			    			'}';
			    		tabData.push(angular.fromJson(stTMp));
			    	});
			    		
		    		UserTechnologies.update(			    			
				    	{id:$scope.currentUser.tech_id}, // Params
				    	angular.toJson(tabData), // Data
				    	function (data, headers) { // OK
				    		console.log(" OK");
				    	},
				    	function (data, headers) { // Erreur
				    		console.log("Erreur " + " Label:" + data.data.label + "/ Detail message:" + data.data.detailMessage);
				    		$scope.callNotify("Erreur " + " Label:" + data.data.label + "/ Detail message:" + data.data.detailMessage);
				    	}
				    );			    					    							    				    		
			    };
				*/

				/** +/- sur les Projet du user */
				/** See PocSimone for detail
			    $scope.updateUserDevelopedProject = function (developedProject, operationType) {			    	
			    	console.log(operationType + ' DevelopedProject ' + developedProject.name + ' du user id ' + $scope.currentUser.tech_id);			    	
			    	switch (operationType) {
						case 'delete':			
							$scope.userDevelopedProjects.content = $scope.delElement($scope, $scope.userDevelopedProjects.content, developedProject);
						break;
						case 'add':
							$scope.addElement($scope.userDevelopedProjects.content, developedProject, $scope.DevelopedProjects.content);
						break;
						default:
						break;
					}
		    		
		    		var stTMp = "";
			    	var tabData = []
			    	angular.forEach($scope.userDevelopedProjects.content, function(element){
			    		stTMp = '{'  +
			    			'"tech_id":"' + element.tech_id + '",' +
			    			'"date":"' + element.date + '",' +
			    			'"description":"' + element.description + '",' +
			    			'"name":"' + element.name + '"' +
			    			'}';
			    		tabData.push(angular.fromJson(stTMp));
			    	});
			    				    	
			    	UserDevelopedProjects.update(			    			
				    	{id:$scope.currentUser.tech_id}, // Params
				    	angular.toJson(tabData), // Data
				    	function (data, headers) { // OK
				    		console.log(" OK");
				    	},
				    	function (data, headers) { // Erreur
				    		$scope.callNotify("Erreur " + " Label:" + data.data.label + "/ Detail message:" + data.data.detailMessage);
				    		console.log("Erreur " + " Label:" + data.data.label + "/ Detail message:" + data.data.detailMessage);
				    	}
		    		);			    					    							    				    						    		
			    };
				*/
			    
			    
				/** +/- sur les Managed Projet du user */
				/** See PocSimone for detail
			    $scope.updateUserManagedProject = function (managedProject, operationType) {
			    	var savUserTab = angular.copy($scope.userManagedProjects.content);
				    switch (operationType) {
						case 'delete':			
							$scope.userManagedProjects.content = $scope.delElement($scope, $scope.userManagedProjects.content, managedProject);
						break;
							case 'add':
							$scope.addElement($scope.userManagedProjects.content, managedProject, $scope.ManagedProjects.content);
						break;
						default:
						break;
					}
			    		
			    	var stTMp = "";
			    	var tabData = []
			    	angular.forEach($scope.userManagedProjects.content, function(element){
			    		stTMp = '{'  +
			    			'"tech_id":"' + element.tech_id + '",' +
			    			'"date":"' + element.date + '",' +
			    			'"description":"' + element.description + '",' +
			    			'"name":"' + element.name + '"' +
			    			'}';
			    		tabData.push(angular.fromJson(stTMp));
			    	});

			    	UserManagedProjects.update(			    			
				    	{id:$scope.currentUser.tech_id}, // Params
				    	angular.toJson(tabData), // Data
				    	function (data, headers) { // OK
				    		console.log($scope.method + " OK");
				    	},
				    	function (data, headers) { // Erreur
				    		$scope.callNotify("Erreur " + $scope.method + ":" + " Label:" + data.data.label + "/ Detail message:" + data.data.detailMessage);
				    		console.log("Erreur " + $scope.method + ":" + " Label:" + data.data.label + "/ Detail message:" + data.data.detailMessage);
				    	}
		    		);			    					    							    				    		
			    };
				*/
			    $scope.gotoUserListPage = function () {
			    	$location.path("/user/list");
			    };			    
			}]
	).
	controller("UserNewController",
			["$scope", "User", "$resource", "$routeParams", "$location",
			 function($scope, User, $resource, $routeParams, $location) {
				
				/** Pas d'affichage de la partie recherche
				*/
				$scope.setListe(false);
				
				/** Retour à la liste des User
			     */
			    $scope.gotoUserListPage = function () {
			    	$location.path("/user/list");
			    };
			    
			    
			    
			    /** Sauvegarde du User
				 */
				$scope.submit = function (user) {
					$scope.setCurrentUser(user);

					
					if (angular.isUndefined(user.firstName) || angular.isUndefined(user.lastName) || ($scope.user.firstName.length == 0) || ($scope.user.lastName.length == 0)) {
						$scope.callNotify("User first Name and/or last Name can not be empty !!");
						alert("User first Name and/or last Name can not be empty !!");
						return;
					}

			    	console.log ("Submit url:" + $scope.url + "  method:" + $scope.method);
			    				    	
			    	/** Récupération du liens et de la methode re,seignés au niveau de UserListController $scope.gotoUserNewPage !! */ 			    	
			    	if ($scope.url && $scope.method) {
			    		User.save(			    			
			    			{id:$scope.currentUser.tech_id}, // Params
			    			user, // Data
			    			function (data, headers) { // OK
			    				console.log($scope.method + " OK");
			    				var tmp = headers('Location');
			    				var tabUrl = tmp.split("/");
			    				var tech_id = tabUrl[tabUrl.length - 1];
			    				console.log(" OK " + status + " user tech_id " + tech_id);       				
			    				$location.path("/user/" + tech_id);
			    			},
			    			function (data, headers) { // Erreur
			    				$scope.callNotify("Erreur " + $scope.method + ":" + " Label:" + data.data.label + "/ Detail message:" + data.data.detailMessage);
			    				console.log("Erreur " + $scope.method + ":" + " Label:" + data.data.label + "/ Detail message:" + data.data.detailMessage);
			    			}
			    		);			    					    							    				    		
			    	} else $scope.callNotify("Erreur : url ou methode non trouvé ! " + $scope.url + " " + $scope.method);
			    };
			}]
	);
