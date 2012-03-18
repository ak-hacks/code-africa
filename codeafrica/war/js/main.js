codeafrica = (function () {

    var MAP_WIDTH = 630,
        MAP_HEIGHT = 380,
        X_OFFSET = 330,
        Y_OFFSET = 70,
        LONGITUDE_SHIFT = -22
        LATITUDE_SHIFT = -9,
        INSET_MAP_SCALE_FACTOR = 1.9;

    var api = {},
        $countrySelect,
        mainMap;

    api.commaFormat = function (number) {
    	var commaFormatted = "";
    	for (var i=number.length; i>0; i--) {
    		if ((number.length - i) % 3 === 0 &&
    			i != number.length) {
    			commaFormatted = "," + commaFormatted;
    		}
    		commaFormatted = number.charAt(i-1) + commaFormatted;
    	}
    	return commaFormatted;
    };

    var processLat = function (lat) {
        lat += LATITUDE_SHIFT;
        lat = lat * Math.PI / 180;  // convert from degrees to radians
        var y = Math.log(Math.tan((lat/2) + (Math.PI/4)));  // do the Mercator projection (w/ equator of 2pi units)
        y = (MAP_HEIGHT / 2) - (MAP_WIDTH * y / (2 * Math.PI));
        return Math.floor(y);
    };

    var processLon = function (lng) {
        // Make sure everything is positive
        var x = (MAP_WIDTH * (180 + lng) / 360) % MAP_WIDTH + LONGITUDE_SHIFT;
        if (x < 0) {
            x += MAP_WIDTH;
        }
        return Math.floor(x);
    };

    var pointsToPath = function (points) {
        var path = [];

        var processedPoints = _.map(points, function (point) {
            return point[0] + "," + point[1];
        });

        return "M " + processedPoints.join(" L ");
    };

    var populateCountryList = function (data) {
    	var list = "<% _.each(countries, function(name) { %> <option value='<%= name %>'><%= name %></option><% }); %>";
		var html = _.template(list, {countries : data.split("\n")});
		$countrySelect.append(html);
    };

    var handleReceivedData = function (data, xhr, err) {
    	console.log(data[0].propertyMap);
    };

    api.init = function () {
        $countrySelect = $("#country-select");
        $countrySelect.change(function () {
            var country = $countrySelect.val();
            if (!country) {
            	return;
            }
            $.get("sample/findsimilar.html", { c1 : country }, handleReceivedData, "json");

        });

        $.get("sample/findrecords.txt", populateCountryList, "text");

        mainMap = Raphael(X_OFFSET + $(".map").offset().left, Y_OFFSET, MAP_WIDTH, MAP_HEIGHT);
        detailMap = Raphael($(".map").offset().left, Y_OFFSET, 320, 320);
    };

    api.drawCountry = function (country) {
        mainMap.clear();
        detailMap.clear();
        var points = countries[country]['points'];

        // Creates circle at x = 50, y = 40, with radius 10
        var xyPoints = _.map(points, function (point) {
            return [processLon(point[0]), processLat(point[1])];
        });
        var countryPath = mainMap.path(pointsToPath(xyPoints));
        //var countryPath = detailMap.rect(0, 0, 320, 320)
        countryPath.attr("fill", "#f26522");

        // Draw the country on the map of Africa
        var xCenter = processLon(countries[country]["center"][0]);
        var yCenter = processLat(countries[country]["center"][1]);
        var detailPoints = _.map(points, function (point) {
            var x = (processLon(point[0]) - xCenter) * INSET_MAP_SCALE_FACTOR,
                y = (processLat(point[1]) - yCenter) * INSET_MAP_SCALE_FACTOR;

            return [Math.floor(x) + 150, Math.floor(y) + 120];
        });

        console.log(detailPoints);

        var detailPath = detailMap.path(pointsToPath(detailPoints));
        detailPath.attr("fill", "#00aeef");
    };

    return api;

})();

$(document).ready(codeafrica.init);