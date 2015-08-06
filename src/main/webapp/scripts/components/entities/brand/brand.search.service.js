'use strict';

angular.module('smartcartApp')
    .factory('BrandSearch', function ($resource) {
        return $resource('api/_search/brands/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
