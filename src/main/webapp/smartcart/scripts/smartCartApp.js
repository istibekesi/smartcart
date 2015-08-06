var app = angular.module('smartCartApp', ['ngResource', 'ui.select', 'ngSanitize', 'uiGmapgoogle-maps']);

app.config(function(uiGmapGoogleMapApiProvider) {
    uiGmapGoogleMapApiProvider.configure({
        key: 'AIzaSyDUd-FQRUaYwsBWSSXEH9H9qSx5g-yPM88',
        v: '3.17',
        libraries: 'weather,geometry,visualization'
    });
})

app.controller('MainCtrl', function($scope, $resource, uiGmapGoogleMapApi) {

	 $scope.shopData = {
			 shops : []
	 }
	 
	 var updateMarkers = function () {
		 var markers = [];
		 $scope.shopData.shops.forEach(function(s) {
			 var r = { 
					 id: s.id, 
					 latitude : s.lat, 
					 longitude : s.lng,
					 title : s.name,
					 show : false
			 };
			 
			 r.onClick = function() {
			 	 r.show = !r.show;
			 };
			 markers.push(r);
		 });
		 return markers;
	 }
	 
	 $scope.map = { 
			 center: { latitude: 47.093837, longitude: 17.907022 }, 
			 zoom: 13,
			 options : {scrollwheel: false}};
	 $scope.shopMarkers = [];
	 
	 
	 $scope.selectMarker = function () {
		 if ($scope.selectedShop.id) {
			 $scope.markers.forEach(function(mark) {
				 
				 	console.log($scope.markers);
				 
					 if ($scope.selectedShop.id == mark.id) {
						 mark.show = true;
					 } else {
						 mark.show = false;
					 }
			 });
			 $scope.shopMarkers = [
			   {"latitude":$scope.selectedShop.lat,"longitude":$scope.selectedShop.lng,"id":0}  
			 ];
		 }
	 }
	 
	 var ShopResource = $resource('/api/shops/:shopId',
	 			 {shopId:'@shopId'}, {});

	 uiGmapGoogleMapApi.then(function(maps) {
		 var shopsPromise = ShopResource.query(function() {
			 if (shopsPromise) {
				 $scope.shopData.shops = [];
				 console.log("Shops loaded:" + shopsPromise.length)
				 
				 shopsPromise.forEach(function(s) {
					 $scope.shopData.shops.push({
						 id : s.id,
						 brand : s.brand.brand,
						 name : s.name,
						 address : s.address,
						 lat : s.lat,
						 lng : s.lng
					 });
				 });
				 
				 $scope.markers = updateMarkers();
				 $scope.selectedShop = $scope.shopData.shops[0];
				 
			 }
		 });
	 }); 
	 
	
});