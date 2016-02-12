/* global angular */
/*jshint latedef: nofunc */
/*jshint unused: false */

(function () {
  'use strict';

  angular
    .module('clientApp')
    .factory('AuthenticationService', AuthenticationService);

    AuthenticationService.$inject = ['$http', '$cookies', '$rootScope', '$timeout'];
    function AuthenticationService($http, $query, $cookies, $rootScope, $timeout) {
      var service = {};

      // User Creation and Retrieval
      service.GetCurrent = GetCurrent;
      service.GetAll = GetAll;
      service.GetById = GetById;
      service.GetByUsername = GetByUsername;
      service.Create = Create;

      // User Authentication
      service.Login = Login;
      service.SetCookieCredentials = SetCookieCredentials;
      service.ClearCookieCredentials = ClearCookieCredentials;

      return service;

      function GetCurrent() {
        return $http.get('/api/v1/users/current').then(handleSuccess, handleError);
      }

      function GetAll() {
        return $http.get('/api/v1/users').then(handleSuccess, handleError);
      }

      function GetById(_id) {
        return $http.get('/api/v1/users/' + _id).then(handleSuccess, handleError);
      }

      function GetByUsername(username) {
        return $http.get('/api/v1/users/' + username).then(handleSuccess, handleError);
      }

      function Create(user) {
        return $http.post('/api/v1/users', user).then(handleSuccess, handleError);
      }

      function Login(username, password, callback) {
        $http.post('api/v1/authenticate', { username: username, password: password })
          .success(function (response) {
            callback(response);
          });
      }

      function SetCookieCredentials(username, password) {
        var authdata = username + ':' + password + 'secret key BEN';

        $rootScope.globals = {
          currentUser: {
            username: username,
            authdata: authdata
          }
        };

        $http.defaults.headers.common.Authorization = 'Basic ' + authdata;
        //$cookies.put('globals', $rootScope.globals);
        $cookies.username = username;
        $cookies.authdata = authdata;
      }

      function ClearCookieCredentials() {
        $rootScope.globals = {};
        delete $cookies.username;
        delete $cookies.authdata;
        $http.defaults.headers.common.Authorization = 'Basic';
      }

      // Private Functions

      function handleSuccess(result) {
        return result.data;
      }

      function handleError(result) {
        return $query.reject(result.data);
      }
    }
})();
