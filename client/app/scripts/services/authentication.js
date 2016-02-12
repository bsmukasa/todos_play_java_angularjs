'use strict';

angular
  .module('clientApp')
  .factory('AuthenticationService', AuthenticationService);

  AuthenticationService.$inject = ['$http', '$cookieStore', '$rootScope', '$timeout']
  function AuthenticationService($http, $query, $cookieStore, $rootScope, $timeout) {
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
      return $http.get('/api/users/current').then(handleSuccess, handleError);
    }

    function GetAll() {
      return $http.get('/api/users').then(handleSuccess, handleError);
    }

    function GetById(_id) {
      return $http.get('/api/users/' + _id).then(handleSuccess, handleError);
    }

    function GetByUsername(username) {
      return $http.get('/api/users/' + username).then(handleSuccess, handleError);
    }

    function Create(user) {
      return $http.post('/api/users', user).then(handleSuccess, handleError);
    }

    function Login(username, password, callback) {
      $http.post('api/authenticate', { username: username, password: password })
        .success(function (response) {
          callback(response);
        });
    }

    function SetCookieCredentials(username, password) {
      var authdata =CryptoJS.AES.encrypt(username + ':' + password, 'secret key BEN');

      $rootScope.globals = {
        currentUser: {
          username: username,
          authdata: authdata
        }
      };

      $http.defaults.headers.common['Authorization'] = 'Basic ' + authdata;
      $cookieStore.put('globals', $rootScope.globals);
    }

    function ClearCookieCredentials() {
      $rootScope.globals = {};
      $cookieStore.remove('globals');
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
