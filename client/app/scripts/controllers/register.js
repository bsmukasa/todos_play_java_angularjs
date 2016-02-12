'use strict';

angular
  .module('clientApp')
  .controller('RegisterCtrl', RegisterCtrl);

RegisterController.$inject = ['AuthenticationService', '$location', '$rootScope', 'AlertService'];
function RegisterController(AuthenticationService, $location, $rootScope, AlertService) {
  var vm = this;

  vm.register = register;

  function register() {
    vm.dataLoading = true;
    AuthenticationService.Create(vm.user)
      .then(function (response) {
        if (response.success) {
          AlertService.Success('Registration successful', true);
          $location.path('/login');
        } else {
          AlertService.Error(response.message);
          vm.dataLoading = false;
        }
      });
  }
}
