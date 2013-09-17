var services = angular.module('CidreServices', ['ngResource']);


services.factory('CidreService', function($resource, UserService) {
    return $resource('/cidre/:id?login=' + UserService.getUser().login
        + "&password=" + UserService.getUser().password,
        {id: "@id"},
        {update: {method:'PUT'}}
    );
});

services.factory('UserService', function() {
    var UserService = function() {
        this.getUser = function() {
            return JSON.parse(localStorage.getItem("user"));
        };
        this.addUser = function(user) {
            localStorage.setItem("user", JSON.stringify(user));
        };
        this.isLogged = function() {
            return this.getUser() !== null;
        }
    };

    return new UserService();
});