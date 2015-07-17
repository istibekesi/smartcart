var app = angular.module('initDbApp', ['ngResource']);

app.controller('MainCtrl', function($scope, $resource) {
  $scope.name = 'SmartCart';
  $scope.loading = false;

  
  var ReloadDbResource = $resource('/api/initDb', {}, {
	  doInit: { method:"POST"}
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
	  //var initDb = new ReloadDbResource();
	  //initDb.doInit(
		//	  {}, function success() {}, function err() {}	  
	  //);
  }
  

  
});
