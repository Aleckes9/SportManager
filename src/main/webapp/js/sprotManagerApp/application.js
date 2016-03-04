'use strict';

/* App Module */
var AppProjetSportManager = angular.module('ProjetSportManager',
		['ngRoute',
		 'ui.bootstrap',
		 'ProjetSportManager.Controllers.Global_controller',
		 'ProjetSportManager.Controllers.User_controller',
		 'ProjetSportManager.Filters',
		 'ProjetSportManager.Services',
		 'ProjetSportManager.Services.Security',
		 'ProjetSportManager.Directives'
		 ])
	.config(['$routeProvider', function($routeProvider) {
   $routeProvider.
      when('/user/list', {
    	  templateUrl:'views/user-list.html',
    	  controller:'UserListController',
    	  reloadOnSearch: true,
    	  resolve: {
    		  // Permet de ne basculer vers la liste des users QUE quand les informations sont dispo. Pas avant ! 
    		  usersListData : function (User) {
    			  var UListData = User.query({size: 3,page:(0)});
    			  return UListData.$promise;
    		  }
          }    	  
      }).
      when('/user/new', {
    	  templateUrl:'views/user-new.html',
    	  controller:'UserNewController'
      }).
      when('/user/:id/picture', {
    	  templateUrl:'views/user-picture.html',
    	  controller:'UserPictureController'
      }).
      when('/user/:id', {
    	  templateUrl:'views/user-detail.html',
    	  controller:'UserDetailController'
      }).
      when('/403', {
    	  templateUrl:'views/access401.html', 
    	  controller:'globalCtrl'
      }).
      when('/home', {
    	  templateUrl:'views/home.html', 
    	  controller:'globalCtrl'
      }).
      otherwise({redirectTo: '/home'});
}]).config(['$httpProvider', function($httpProvider) {    
    var interceptor = ['$rootScope', '$q', function($rootScope, $q) {
      function success(response) {
        return response;
      }
 
      function error(response) {
        if ( (response.status === 403) || (response.status === 401) ) {
        	window.location = "./index.html#/403";
        }
        
        // otherwise, default behaviour
        return $q.reject(response);
      }
 
      return function(promise) {
        return promise.then(success, error);
      };
 
    }];
    $httpProvider.responseInterceptors.push(interceptor);
  }]);

