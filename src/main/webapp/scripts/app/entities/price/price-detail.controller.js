'use strict';

angular.module('smartcartApp')
    .controller('PriceDetailController', function ($scope, $stateParams, Price, Shop, Product) {
        $scope.price = {};
        $scope.load = function (id) {
            Price.get({id: id}, function(result) {
              $scope.price = result;
            });
        };
        $scope.load($stateParams.id);
    });
