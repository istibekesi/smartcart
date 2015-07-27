'use strict';

angular.module('smartcartApp')
    .controller('PriceController', function ($scope, Price, Shop, Product, PriceSearch, ParseLinks) {
        $scope.prices = [];
        $scope.shops = Shop.query();
        $scope.products = Product.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Price.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.prices = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Price.get({id: id}, function(result) {
                $scope.price = result;
                $('#savePriceModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.price.id != null) {
                Price.update($scope.price,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Price.save($scope.price,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Price.get({id: id}, function(result) {
                $scope.price = result;
                $('#deletePriceConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Price.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePriceConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            PriceSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.prices = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#savePriceModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.price = {price: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
