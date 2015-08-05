var map;
var markers = [];        
        
function initialize() {
  var veszpremOvarosTer = new google.maps.LatLng(47.093837, 17.907022);
  var mapOptions = {
    zoom: 13,
    center: veszpremOvarosTer
  };
  map = new google.maps.Map(document.getElementById('map-canvas'),
      mapOptions);

}
        
google.maps.event.addDomListener(window, 'load', initialize);