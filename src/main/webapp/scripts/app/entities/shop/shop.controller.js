'use strict';

angular.module('smartcartApp')
    .controller('ShopController', function ($scope, Shop, Price, ShopSearch, ParseLinks) {
        $scope.shops = [];
        $scope.prices = Price.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Shop.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.shops = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Shop.get({id: id}, function(result) {
                $scope.shop = result;
                $('#saveShopModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.shop.id != null) {
                Shop.update($scope.shop,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Shop.save($scope.shop,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Shop.get({id: id}, function(result) {
                $scope.shop = result;
                $('#deleteShopConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Shop.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteShopConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            ShopSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.shops = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveShopModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.shop = {name: null, address: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
