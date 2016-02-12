/* global angular */
/*jshint latedef: nofunc */

(function () {
  'use strict';

  angular
    .module('clientApp')
    .controller('LoginCtrl', LoginCtrl);

  LoginCtrl.$inject = ['$location', 'AuthenticationService', 'FlashService'];

  function LoginCtrl($location, AuthenticationService, FlashService) {
    var vm = this;

    vm.login = login;

    (function initController() {
      AuthenticationService.ClearCookieCredentials();
    })();

    function login() {
      vm.dataLoading = true;
      AuthenticationService.Login(vm.username, vm.password, function (response) {
        if (response.success) {
          AuthenticationService.SetCookieCredentials(vm.username, vm.password);
          $location.path('/');
        } else {
          FlashService.Error(response.message);
          vm.dataLoading = false;
        }
      });
    }
  }
})();
