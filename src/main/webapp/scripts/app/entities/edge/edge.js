'use strict';

angular.module('smartcartApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('edge', {
                parent: 'entity',
                url: '/edge',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'smartcartApp.edge.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/edge/edges.html',
                        controller: 'EdgeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('edge');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('edgeDetail', {
                parent: 'entity',
                url: '/edge/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'smartcartApp.edge.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/edge/edge-detail.html',
                        controller: 'EdgeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('edge');
                        return $translate.refresh();
                    }]
                }
            });
    });
