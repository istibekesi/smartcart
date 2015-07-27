'use strict';

angular.module('smartcartApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('price', {
                parent: 'entity',
                url: '/price',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'smartcartApp.price.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/price/prices.html',
                        controller: 'PriceController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('price');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('priceDetail', {
                parent: 'entity',
                url: '/price/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'smartcartApp.price.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/price/price-detail.html',
                        controller: 'PriceDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('price');
                        return $translate.refresh();
                    }]
                }
            });
    });
