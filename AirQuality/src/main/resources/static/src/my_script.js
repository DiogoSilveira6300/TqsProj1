function httpGet(){
    var request = new XMLHttpRequest();

    var city = $("#city").val();
    var state = $("#state").val();
    var country = $("#country").val();

    request.open('GET', 'http://localhost:8080/city/' + city + '/state/' + state + '/country/' + country, true);

    request.onload = function(){
        var data = JSON.parse(this.response);
        $("#data").empty();
        if (data.name != null){
            $("#data").append("<div class=\"row\"><div class=\"col-sm\"><b>City: </b></div><div class=\"col-sm\" id=\"city\">" + data.name + "</div></div>" );
            $("#data").append("<div class=\"row\"><div class=\"col-sm\"><b>State: </b></div><div class=\"col-sm\" id=\"state\">" + data.state + "</div></div>" );
            $("#data").append("<div class=\"row\"><div class=\"col-sm\"><b>Country: </b></div><div class=\"col-sm\" id=\"country\">" + data.country + "</div></div>" );
            $("#data").append("<div class=\"row\"><div class=\"col-sm\"><b>Latitude: </b></div><div class=\"col-sm\" id=\"lat\">" + data.coords.lat + "</div></div>" );
            $("#data").append("<div class=\"row\"><div class=\"col-sm\"><b>Longitude: </b></div><div class=\"col-sm\" id=\"lon\">" + data.coords.lon + "</div></div>" );
            $("#data").append("<div class=\"row\"><div class=\"col-sm\"><b>Air Quality Index: </b></div><div class=\"col-sm\" id=\"aqi\">" + data.aqi + "</div></div>" );
            $("#data").append("<div class=\"row\"><div class=\"col-sm\"><b>Main Problem: </b></div><div class=\"col-sm\" id=\"mainProblem\">" + data.mainProblem + "</div></div>" );

            var dateTime = data.timeStamp.replace("T", " ").replace("Z", "").split(".")[0];

            $("#data").append("<div class=\"row\"><div class=\"col-sm\"><b>Date: </b></div><div class=\"col-sm\" id=\"date\">" + dateTime.split(" ")[0] + "</div></div>" );
            $("#data").append("<div class=\"row\"><div class=\"col-sm\"><b>Time: </b></div><div class=\"col-sm\" id=\"time\">" + dateTime.split(" ")[1] + "</div></div>" );
            $("#data").append("<div class=\"row\"><div class=\"col-sm\"><b>Cached: </b></div><div class=\"col-sm\" id=\"cached\">" + data.cached + "</div></div>" );
        }
        city = null; state = null; country = null;
    };

    request.send();
}

function httpGetFor(path){
    var request = new XMLHttpRequest();

    request.open('GET', 'http://localhost:8080' + path, true);

    request.onload = function(){
        var data = JSON.parse(this.response);
        $("#data").empty();
        if (data.name != null){
            $("#data").append("<div class=\"row\"><div class=\"col-sm\"><b>City: </b></div><div class=\"col-sm\" id=\"city\">" + data.name + "</div></div>" );
            $("#data").append("<div class=\"row\"><div class=\"col-sm\"><b>State: </b></div><div class=\"col-sm\" id=\"state\">" + data.state + "</div></div>" );
            $("#data").append("<div class=\"row\"><div class=\"col-sm\"><b>Country: </b></div><div class=\"col-sm\" id=\"country\">" + data.country + "</div></div>" );
            $("#data").append("<div class=\"row\"><div class=\"col-sm\"><b>Latitude: </b></div><div class=\"col-sm\" id=\"lat\">" + data.coords.lat + "</div></div>" );
            $("#data").append("<div class=\"row\"><div class=\"col-sm\"><b>Longitude: </b></div><div class=\"col-sm\" id=\"lon\">" + data.coords.lon + "</div></div>" );
            $("#data").append("<div class=\"row\"><div class=\"col-sm\"><b>Air Quality Index: </b></div><div class=\"col-sm\" id=\"aqi\">" + data.aqi + "</div></div>" );
            $("#data").append("<div class=\"row\"><div class=\"col-sm\"><b>Main Problem: </b></div><div class=\"col-sm\" id=\"mainProblem\">" + data.mainProblem + "</div></div>" );

            var dateTime = data.timeStamp.replace("T", " ").replace("Z", "").split(".")[0];

            $("#data").append("<div class=\"row\"><div class=\"col-sm\"><b>Date: </b></div><div class=\"col-sm\" id=\"date\">" + dateTime.split(" ")[0] + "</div></div>" );
            $("#data").append("<div class=\"row\"><div class=\"col-sm\"><b>Time: </b></div><div class=\"col-sm\" id=\"time\">" + dateTime.split(" ")[1] + "</div></div>" );
            $("#data").append("<div class=\"row\"><div class=\"col-sm\"><b>Cached: </b></div><div class=\"col-sm\" id=\"cached\">" + data.cached + "</div></div>" );
        }
    };

    request.send();
}

