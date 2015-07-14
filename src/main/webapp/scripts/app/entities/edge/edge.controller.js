'use strict';

angular.module('smartcartApp')
    .controller('EdgeController', function ($scope, Edge, Product, EdgeSearch, ParseLinks) {
        $scope.edges = [];
        $scope.products = Product.query();
        $scope.page = 1;
        $scope.loadAll = function() {
            Edge.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.edges = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Edge.get({id: id}, function(result) {
                $scope.edge = result;
                $('#saveEdgeModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.edge.id != null) {
                Edge.update($scope.edge,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Edge.save($scope.edge,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Edge.get({id: id}, function(result) {
                $scope.edge = result;
                $('#deleteEdgeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Edge.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteEdgeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            EdgeSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.edges = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveEdgeModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.edge = {value: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
