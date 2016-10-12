'use strict';
accountApp.controller('AccountController', ['$scope', 'AccountService', function($scope, AccountService) {
			var self = this;
			self.account = {id:null,firstName:'', lastName: '', dateOfBirth: '', email: ''}
			self.accounts = [];
			$scope.sortType     = 'id';
			$scope.sortReverse  = false;
			
			self.getAllAccounts = function() {
				AccountService.getAllAccounts().then(
					function(acc) {
						self.accounts = acc;
					},
					function(errResponse){
						console.error(errMessage.getAllAccounts);
					}
                );
			};
			self.createAccount = function(account) {
				AccountService.createAccount(account).then(
					self.getAllAccounts,
					function(errResponse){
						console.error(errMessage.createAccount);
					}
                );
			};
			self.editAccount = function(account, id) {
				AccountService.editAccount(account, id).then(
					self.getAllAccounts,
					function(errResponse){
						console.error(errMessage.editAccount);
					}
                );
			};
			self.deleteAccount = function(accId) {
				AccountService.deleteAccount(accId).then(
					self.getAllAccounts,
					function(errResponse){
						console.error(errMessage.deleteAccount);
					}
                );
			};
			self.getAllAccounts();
			self.edit = function(id) {
				for (var i = 0; i < self.accounts.length; i++) {
					if(self.accounts[i].id === id) {
						self.account = angular.copy(self.accounts[i]);
						break;
					}
				}
			};
			self.submit = function() {
				if(self.account.id === null) {
					self.createAccount(self.account);
				} else {
					self.editAccount(self.account, self.account.id);
				}
				self.reset();
			};
			self.reset = function() {
				self.account = {id:null,firstName:'', lastName: '', dateOfBirth: '', email: ''}
				$scope.createAccountForm.$setPristine();
			};
			self.remove = function(id) {
				if(self.account.id === id) {
					self.reset();
				}
				self.deleteAccount(id);
			};
		}
	]
);