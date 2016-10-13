'use strict';
accountApp.controller('AccountController', ['$scope', 'AccountService', function($scope, AccountService) {
			var self = this;
			self.account = {id:null,firstName:'', lastName: '', dateOfBirth: '', email: ''}
			self.accounts = [];
			self.page = 0;
			self.totalPages = 0;
			self.size = 5;
			self.sort = 'id';
			self.reverse = false;
			
			self.getAllAccounts = function(p, s, r) {
				AccountService.getAllAccounts(p, s, r).then(
					function(response) {
						self.accounts = response.content;
						self.totalPages = response.totalPages-1;
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
					self.getAllAccounts(self.page, self.sort, self.reverse),
					function(errResponse){
						console.error(errMessage.editAccount);
					}
                );
			};
			self.deleteAccount = function(accId) {
				AccountService.deleteAccount(accId).then(
					self.getAllAccounts(self.page, self.sort, self.reverse),
					function(errResponse){
						console.error(errMessage.deleteAccount);
					}
                );
			};
			self.getAllAccounts(self.page, self.sort, self.reverse);
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
			self.nextPage = function() {
				if(self.page < self.totalPages) {
					self.page++;
				}
				self.getAllAccounts(self.page, self.sort, self.reverse);
			};
			self.previousPage = function() {
				if(self.page > 0) {
					self.page--;
				}
				self.getAllAccounts(self.page, self.sort, self.reverse);
			};
		}
	]
);