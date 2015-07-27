'use strict';

angular.module('smartcartApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('shop', {
                parent: 'entity',
                url: '/shop',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'smartcartApp.shop.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/shop/shops.html',
                        controller: 'ShopController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('shop');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('shopDetail', {
                parent: 'entity',
                url: '/shop/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'smartcartApp.shop.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/shop/shop-detail.html',
                        controller: 'ShopDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('shop');
                        return $translate.refresh();
                    }]
                }
            });
    });
