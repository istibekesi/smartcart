'use strict';

angular.module('smartcartApp')
    .controller('ProductDetailController', function ($scope, $stateParams, Product, Edge) {
        $scope.product = {};
        $scope.load = function (id) {
            Product.get({id: id}, function(result) {
              $scope.product = result;
            });
        };
        $scope.load($stateParams.id);
    });
