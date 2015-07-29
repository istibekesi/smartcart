var app = angular.module('smartAdminApp', ['ngResource']);

app.controller('MainCtrl', function($scope, $resource) {
  $scope.name = 'SmartCart';
  $scope.loading = false;
  $scope.loadingPng = false;
  
  $scope.graphImageUrl = "smartGraph.png";

  
  var ReloadDbResource = $resource('/api/initDb', {}, {
	  doInit: { method:"POST"}
  });
  
  var ReloadGraphPngResource = $resource('/api/graphPng', {}, {
	  doGraph: { method:"POST"}
  });
  
  $scope.initDb = function () {
	  
	  $scope.loading = true;
	  
	  ReloadDbResource.doInit(
			  function () { 
				  $scope.loading = false;
				  alert ('success');
			  },
			  function () {
				  $scope.loading = false;
				  alert ('error');
			  }
	  );
	  
  }
  
  $scope.reloadGraphPng = function () {
	  
	  $scope.loadingPng = true;
	  
	  ReloadGraphPngResource.doGraph(
			  function () { 
				  $scope.loadingPng = false;
				  $scope.graphImageUrl = "smartGraph.png" + "?nocache=" + new Date().getTime();
			  },
			  function () {
				  $scope.loadingPng = false;
			  }
	  );
	  
  }
  

  
});
