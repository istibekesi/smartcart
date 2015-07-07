'use strict';

angular.module('smartcartApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
