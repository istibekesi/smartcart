'use strict';

angular.module('smartcartApp')
    .controller('BrandDetailController', function ($scope, $stateParams, Brand, Shop) {
        $scope.brand = {};
        $scope.load = function (id) {
            Brand.get({id: id}, function(result) {
              $scope.brand = result;
            });
        };
        $scope.load($stateParams.id);
    });
