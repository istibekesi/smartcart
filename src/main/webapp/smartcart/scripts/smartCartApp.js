var app = angular.module('smartCartApp', ['ngResource', 'ui.select', 'ngSanitize', 'uiGmapgoogle-maps']);

app.controller('MainCtrl', function($scope, $resource) {

	 $scope.shopData = {
			 shops : [
			          {
			        	  brand : "TESCO",
			        	  name : "TESCO Veszprém Hipermarket",
			        	  address: "8200 Külső-Kádártai út",
			        	  lat: 47.103660,
			        	  lng: 17.934070
			          },
			          {
			        	  brand : "TESCO",
			        	  name : "TESCO Veszprém Szupermarket",
			        	  address: "8200 Mártírok útja 13.",
			        	  lat: 47.0843779,
			        	  lng: 17.9134511
			          },
			          {
			        	  brand : "INTERSPAR",
			        	  name : "Interspar",
			        	  address: "8200 Dornyai Béla u. 4",
			        	  lat: 47.084550,
			        	  lng: 17.925922
			          }
			          
			 ]
	 }
	 
	 $scope.selectedShop = {};
	 
	 $scope.map = { center: { latitude: 47.093837, longitude: 17.907022 }, zoom: 13 };
	 $scope.shopMarkers = [];
	 
	 $scope.addMarker = function () {
		 $scope.shopMarkers = [
		   {"latitude":$scope.selectedShop.lat,"longitude":$scope.selectedShop.lng,"id":0}  
		 ];
		 
	 }
	
});