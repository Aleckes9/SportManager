'use strict';
angular.module('ProjetSportManager.Controllers.Global_controller',[]).
	controller("globalCtrl",
		["$scope", "$location", "notify", "AccessKeys", "paginationConfig", "menuOptions",
		 function($scope, $location, notify, AccessKeys, paginationConfig, menuOptions) {

			/** Gestion datePicker */
			$scope.datePicker = {opened:false};
			$scope.open = function($event) {
				$event.preventDefault();
				$event.stopPropagation();
				$scope.DatePickerOpened = true;
				/** bug : il faut cette notation 'Objet' */
				$scope.datePicker = {opened:true};
			};

			
			/** -------- Pour la pagination -------- */
			/** readOnly */
			/** Nombre total max de pages visible dans le bandeau sup */
        	paginationConfig.maxSize = 7;
			/** Texte des boutons */
			paginationConfig.previousText = "Prec";
			paginationConfig.nextText = "Suiv";
			paginationConfig.firstText = "Deb";
			paginationConfig.lastText = "Fin";
			/** Nombre de lignes par page */
			paginationConfig.itemsPerPage = 3;

			
			

			/** Nombre de pages total */
			//$scope.nbOfPages = 1;
			/** Num de page courrante */
			$scope.selectedPage = 1;
			/** Nombre total d'items */
			$scope.totalItems = 0;			
			/** ------------------------------------- */
			
			
			// Pour la gestion de l'affichage la barre de recherche.
			$scope.liste = false;
			
			/* Variables globales */ 
			$scope.url = "";
			$scope.method = "";	
			$scope.userPicture = "";
			$scope.currentUser = null;
			$scope.currentTechnology = null;
			$scope.currentPoc = null;
			$scope.currentProject = null;

			
			/** Gestion des droits d'acces */
			$scope.defaultAccess = "READWRITE";
			AccessKeys.query({}, function (securities) {					
				$scope.AccessList = securities.accessKeyConfiguration; 		
			});
			
			
			
			// Tri par defaut
			$scope.ascending = true;


			/** Un reload de la page est il nécessaire (lors de l'upgrade de l'image)
			 */
			$scope.reloadWindow = false;
			

			
			/**                                         */
			/** ============= Les setters ============= */
			/** Pour les variables globales             */
			
			/** Pour la gestion de la pagination */
			$scope.setSelectedPage = function(value) {
				$scope.selectedPage = value;
			}			
			$scope.setNbOfPages = function(value) {
				$scope.nbOfPages = value;
			}
			$scope.setTotalItems = function(value) {
				$scope.totalItems = value;
				$scope.bigTotalItems = value;
			}
			/** -------------------------------- */

			/** Rechargement de la fenetre */
			$scope.setReloadWindow = function(value) {
				$scope.reloadWindow = value;
			}

			/** Affichage de la barre de recherche dans le cas des listes */			
			$scope.setListe = function (value) {
				$scope.liste = value;
			}

			/** Init des informations de l'utilisateur courrant
		    	Informations utilisees pour le reaffichage lors d'une mise à jour de la photo
			 */
			$scope.setCurrentUser = function (user) {
				var tmp = angular.copy($scope.currentUser);
				$scope.currentUser = user;
				if (angular.isUndefined(user.links) == false) {
					$scope.setupLinksComponent(user.links, "picture");
					$scope.userPicture = angular.copy($scope.url);							
				}
			};	
		
			$scope.setCurrentTechnology = function (technology) {
				$scope.currentTechnology = technology;			
			};	
			$scope.setCurrentProject = function (project) {
				$scope.currentProject = project;			
			};	
			$scope.setCurrentPoc = function (poc) {
				$scope.currentPoc = poc;			
			};
			
			/** ======================================= */

			
			
			/** Gestion des langues */			
			$scope.setLanguage = function (lang) {
				//TODO with $translate
			}
			
			
			/** Permet le gestion du ng-show si on a le droit de supprimer unenregistrement */			
			$scope.suppressionAutorisee = function (arrayLinks) {
//				console.log("suppressionAutorisee " + arrayLinks.length);
				var supprPossible = false;
				angular.forEach(arrayLinks, function(aLink){
//					console.log("aLink " + aLink.rel);
					if (aLink.rel == "delete_via_delete") {
						supprPossible = true;
//						console.log("suppressionAutorisee YES " + aLink.href);
					}
				});
				return supprPossible;
			}
			
			/** Fonctions globales */
			
			// Permet la récupération de l'url et de la methode associées à une action.
			// Si l'utilisateur n'a pas le droit d'éxécuter cette action alors $scope.url et $scope.method seront vide.  
			$scope.setupLinksComponent = function (tabLinks, action) {
				var lg = -1;
				var partieGauche = "";
				$scope.url = "";
				$scope.method = "";

				/** Pour supprimer la partie host et port */
				angular.forEach(tabLinks, function(element) {
					if (element.rel === action) {
						var tmp = angular.copy($location);
						
						partieGauche = "http://" + tmp.host();
						lg = partieGauche.length;
						var posPremSlash = element.href.substring(lg).indexOf("/");
						$scope.url = element.href.substring(lg + posPremSlash);
						
			
//						partieGauche = ("http://" + tmp.host() + ":" + tmp.port());
//						console.log("tmp.host " + tmp.host() + " > tmp.port " + tmp.port());
//						if ( (angular.isUndefined(tmp.port()) == false) && (tmp.port() != "80") ) {				
//						} else partieGauche = ("http://" + tmp.host());
//						lg = partieGauche.length;
//						$scope.url = element.href.substring(lg);
//						console.log("Url calculée: lg " + lg + " >" + $scope.url + "  >" + element.href + " >" + partieGauche);
						console.log("Url extraite : " + $scope.url + "  > " + element.href);
						$scope.method = element.rel;
					}
				});
			};	
			/**                 -----------                             */


			/* setter de la variable de l'affichage croissant/decroissant des listes */
			$scope.ordering = function (value) {
				$scope.ascending = value;
			}
			
			/* Permet la suppression d'un element dans une liste retour dans un tableau */
			/* userArray tableau de départ */
			/* elementToDel element à supprimer */
			/* NewArray nouveau tableau en sortie */
			$scope.delElement = function($scope, userArray, elementToDel) {
				$scope.NewArray = [];
				angular.forEach(userArray, function(element){
					if (elementToDel.tech_id != element.tech_id) {
						$scope.NewArray.push(element);
					}
				});
				return $scope.NewArray;	
			};

			/* Permet l'ajout d'un element d'une table de référence tabRef dans un tableau userArray. */
			/* tabRef tableau contenant les éléments de référence */
			/* userArray tableau à compléter en E/S */
			/* elementToAdd élément à ajouter */
			$scope.addElement = function (userArray, elementToAdd, tabRef) {
				angular.forEach(tabRef, function(element){
					if (elementToAdd.tech_id === element.tech_id) {
						userArray.push(element);
					}
				});
				
			};

			/* Fonction équivalent au "alert" de javascript */
			$scope.callNotify = function(msg) {
		        notify(msg);
		    };
		}]
	).controller("UserOptionListCtrl",
			["$scope", "$location",
			 function($scope, $location) {
				$scope.OptionItems = [
				   {"name": "Add","link": "gotoUserNewPage()"}
				];	
			}]
	).controller("MenuVListCtrl",
			["$scope",
			 function($scope) {
				$scope.MenusVItems = [
             	     {"name": "Home","link": "#"},
             	     {"name": "Menu 1","link": "#"},
             	     {"name": "Menu 2","link": "#"},
             	     {"name": "Menu 3","link": "#"},
             	     {"name": "Menu 4","link": "#"},
             	     {"name": "Menu 5","link": "#"},
             	     {"name": "Menu 6","link": "#"},
             	     {"name": "Menu 7","link": "#"}
             	];
			}]
	).controller("MenuHListCtrl",
			["$scope", "menuOptions", function($scope, menuOptions) {
				$scope.menuOptions = menuOptions;
			}]
	);
