var app = angular.module('smartCartApp', ['ngResource']);

app.controller('MainCtrl', function($scope, $resource) {

	 $scope.shopData = {
			 shops : [
			          {
			        	  brand : "TESCO",
			        	  name : "TESCO Veszprém Hipermarket",
			        	  address: "8200 Külső-Kádártai út",
			        	  lat: 47.1065305,
			        	  lng: 17.9269768
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
			        	  lat: 47.0843483,
			        	  lng: 17.9129987
			          }
			          
			 ]
	 }
	
});