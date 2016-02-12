'use strict';

angular
  .module('clientApp')
  .factory('AlertService', AlertService);

function AlertService($rootScope) {
  var service = {};

  service.Success = Success;
  service.Error = Error;

  initService();

  return service;

  function initService() {
    $rootScope.$on('$locationChangeStart', function () {
      clearAlertMessage();
    });

    function clearAlertMessage() {
      var alert = $rootScope.flash;

      if (alert) {
        if (!alert.keepAfterLocationChange) {
          delete $rootScope.flash;
        } else {
          alert.keepAfterLocationChange = false;
        }
      }
    }
  }

  function Success(message, keepAfterLocationChange) {
    $rootScope.flash = {
      message: message,
      type: 'success',
      keepAfterLocationChange: keepAfterLocationChange
    };
  }

  function Error(message, keepAfterLocationChange) {
    $rootScope.flash = {
      message: message,
      type: 'danger',
      keepAfterLocationChange: keepAfterLocationChange
    };
  }
}
