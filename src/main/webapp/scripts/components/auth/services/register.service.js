'use strict';

angular.module('smartcartApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


