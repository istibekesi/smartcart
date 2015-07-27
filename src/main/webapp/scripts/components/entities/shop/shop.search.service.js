'use strict';

angular.module('smartcartApp')
    .factory('ShopSearch', function ($resource) {
        return $resource('api/_search/shops/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
