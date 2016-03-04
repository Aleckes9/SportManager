'use strict';

angular.module('ProjetSportManager.Services', ['ngResource']).
factory('User', function ($resource) {
	// Les Users
	return $resource('api/rest/resources/users/:id', {}, {
		'query': { method: 'GET', isArray: false },
		'save': {method:'POST', isArray: false },
		'update': {method:'PUT', isArray: false },
		'delete': {method:'DELETE', isArray: false}
	});
}).factory('UserPicture',function($http, $q){
   return {
      updatePicture : function(url, requestData) {
        //Creating a deferred object
        var deferred = $q.defer();
	   
        //Calling Web API to fetch shopping cart items
        $http.post(
        	url,
		    requestData,
		    { headers: { 'Content-Type': undefined}, transformRequest: angular.identity }
		).success(
		     function(responseData) {
		        //Passing data to deferred's resolve function on successful completion
		        deferred.resolve(responseData);
		     }
		).error(
		     function(error) {		   
		        deferred.reject(error);
		      }
		);		   
		return deferred.promise;
      },
		 supprPicture : function(url) {
		   //Creating a deferred object
		   var deferred = $q.defer();
		   
		   //Calling Web API to fetch shopping cart items
		   $http.post(
		        	url,
		        	'',
		        	{ headers: {'X-HTTP-Method-Override':'DELETE'}}
		          ).success(
		        		function() {
		        			deferred.resolve("Suppression de l'image OK");
		        		}
		          )
		          .error(
		        		function() {		   
		        			deferred.reject("An error occured while fetching items");
		        		}
		          );		   
		        return deferred.promise;
		      },
		      deletePicture : function(url) {
			          //Creating a deferred object
			          var deferred = $q.defer();
			   
			          //Calling Web API to fetch shopping cart items
			          $http['delete'](
			        	url,
			        	'',
			        	{}
			          ).success(
			        		function() {
			        			deferred.resolve("Suppression de l'image OK");
			        		}
			          )
			          .error(
			        		function() {		   
			        			deferred.reject("An error occured while fetching items");
			        		}
			          );		   
			        return deferred.promise;
			      }
		      
		    }
}).		
factory('menuOptions', [ '$q', '$timeout', function($q, $timeout) {
        	var deferred = $q.defer();
        	// Definition des divers élements de menu (verticaux et horizontaux).
       		deferred.resolve({
        		"menuOptions" : {
        			"uListOptionItems" : [
        			   {"name": "Add", "heyding_icon": "+", "selected":"false", "relValue":"create_via_post"}
        			]
        		}
       		});

        	return deferred.promise.then(function(result) {
        		return result.menuOptions;
        	});
        }]).factory('notify', function($window) {
        	var msgs = [];
        	return function(msg) {
        		$window.alert(msg);
        	};
        });
