'use strict';

angular.module('smartcartApp')
    .controller('EdgeDetailController', function ($scope, $stateParams, Edge, Product) {
        $scope.edge = {};
        $scope.load = function (id) {
            Edge.get({id: id}, function(result) {
              $scope.edge = result;
            });
        };
        $scope.load($stateParams.id);
    });
