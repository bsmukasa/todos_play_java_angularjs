/* global angular */
/*jshint latedef: nofunc */

(function () {

  'use strict';

  angular
    .module('clientApp')
    .factory('FlashService', FlashService);

  function FlashService($rootScope) {
    var service = {};

    service.Success = Success;
    service.Error = ErrorMsg;

    initService();

    return service;

    function initService() {
      $rootScope.$on('$locationChangeStart', function () {
        clearFlashMessage();
      });

      function clearFlashMessage() {
        var flash = $rootScope.flash;

        if (flash) {
          if (!flash.keepAfterLocationChange) {
            delete $rootScope.flash;
          } else {
            flash.keepAfterLocationChange = false;
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

    function ErrorMsg(message, keepAfterLocationChange) {
      $rootScope.flash = {
        message: message,
        type: 'error',
        keepAfterLocationChange: keepAfterLocationChange
      };
    }
  }
})();
