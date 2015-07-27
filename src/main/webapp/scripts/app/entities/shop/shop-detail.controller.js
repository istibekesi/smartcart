'use strict';

angular.module('smartcartApp')
    .controller('ShopDetailController', function ($scope, $stateParams, Shop, Price) {
        $scope.shop = {};
        $scope.load = function (id) {
            Shop.get({id: id}, function(result) {
              $scope.shop = result;
            });
        };
        $scope.load($stateParams.id);
    });
