'use strict';

accountApp.factory('AccountService', ['$http', '$q', function($http, $q){
	return {
		getAllAccounts: function(page, sort, reverse) {
			var direction = 'asc';
			if (reverse) {
				direction = 'desc'
			};
			return $http.get(restServURL + '?size=5&page=' + page + "&sort=" + sort + "," + direction).then(
				function(response){
					return response.data;
				}, 
				function(errResponse){
					return $q.reject(errResponse);
				}
			);
		},
		createAccount: function(account) {
			return $http.post(restServURL,account).then(
				function(response){
					return response.data;
				}, 
				function(errResponse){
					return $q.reject(errResponse);
				}
			);
		},
		editAccount: function(account, id) {
			return $http.put(restServURL+'/'+id,account).then(
				function(response){
					return response.data;
				}, 
				function(errResponse){
					return $q.reject(errResponse);
				}
			)
		},
		deleteAccount: function(id) {
			return $http.delete(restServURL+'/'+id).then(
				function(response){
					return response.data;
				}, 
				function(errResponse){
					return $q.reject(errResponse);
				}
			)
		}
	}
}]);