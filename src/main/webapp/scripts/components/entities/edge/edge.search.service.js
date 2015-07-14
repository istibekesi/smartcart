'use strict';

angular.module('smartcartApp')
    .factory('EdgeSearch', function ($resource) {
        return $resource('api/_search/edges/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
