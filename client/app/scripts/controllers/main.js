'use strict';

angular
  .module('clientApp')
  .controller('MainCtrl', MainCtrl);

function MainController(AuthenticationService) {
  var vm = this;

  vm.user = null;

  initController();

  function initController() {
    AuthenticationService.GetCurrent().then(function (user) {
      vm.user = user;
    });
  }
}
