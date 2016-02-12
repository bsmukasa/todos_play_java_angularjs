/* global angular */
/*jshint latedef: nofunc */

'use strict';

angular
  .module('clientApp')
  .controller('RegisterCtrl', RegisterCtrl);

RegisterCtrl.$inject = ['AuthenticationService', '$location', '$rootScope', 'FlashService'];
function RegisterCtrl(AuthenticationService, $location, $rootScope, FlashService) {
  var vm = this;

  vm.register = register;

  function register() {
    vm.dataLoading = true;
    AuthenticationService.Create(vm.user)
      .then(function (response) {
        if (response.success) {
          FlashService.Success('Registration successful', true);
          $location.path('/login');
        } else {
          FlashService.Error(response.message);
          vm.dataLoading = false;
        }
      });
  }
}
