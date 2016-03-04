'use strict';

angular.module('ProjetSportManager.Services.Security', ['ngResource']).
		factory('AccessKeys', function ($resource) {
			// Les AccessKeys
			return $resource('api/rest/resources/securities', {}, {
            	'query': { method: 'GET', isArray: false },
			});
		});
