'use strict';

angular
  .module('clientApp')
  .controller('LoginCtrl', LoginCtrl);

LoginCtrl.$inject = ['$location', 'AuthenticationService', 'AlertService'];
function LoginCtrl($location, AuthenticationService, AlertService) {
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
        AlertService.Error(response.message);
        vm.dataLoading = false;
      }
    });
  }
}
