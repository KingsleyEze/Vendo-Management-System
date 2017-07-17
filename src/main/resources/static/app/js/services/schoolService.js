/**
 * Created by Samuel.Iheadindu on 19/11/2016.
 */
app.factory("schoolService", ['$http', function($http){
    $http.defaults.headers.post["Content-Type"] = "application/json";
    var schoolServiceFactory = {};

    var _addSchool = function(school){
        return $http.post("/subjects/add", school).then(function (results) {
            return results;
        });
    }

    schoolServiceFactory.addSchool = _addSchool;

    return schoolServiceFactory;
}]);