'use strict';
app.factory("subjectData", function () {
    return {};
});
app.factory('subjectService', ['$http', function ($http) {

        $http.defaults.headers.post["Content-Type"] = "application/json";
        var subjectServiceFactory = {};

        var _findAllSubjects = function () {
            return $http.get("/subjects/all").then(function (results) {
                return results;
            });
        };

        var _findSubjectById = function (id) {
            return $http.get("/subjects/findById/" + id).then(function (results) {
                return results;
            });
        };

        var _addSubject = function (newsubject) {
            return $http.post("/subjects/add", newsubject).then(function (results) {
                return results;
            });
        };

        var _updateSubject = function (subject) {
            return $http.put("/subjects/update/", subject).then(function (results) {

                return results;
            });
        };

        var _deleteSubject = function (id) {
            return $http.delete("/subjects/delete/" + id).then(function (results) {
                return results;
            });
        };

        var _findSubjectByName = function (name) {
            return $http.post("/subjects/findByName", name).then(function (results) {
                console.log("It Reached Here");
                return results;
            });
        };

        subjectServiceFactory.findAllSubjects = _findAllSubjects;
        subjectServiceFactory.findSubjectById = _findSubjectById;
        subjectServiceFactory.addSubject = _addSubject;
        subjectServiceFactory.updateSubject = _updateSubject;
        subjectServiceFactory.deleteSubject = _deleteSubject;
        subjectServiceFactory.findSubjectByName = _findSubjectByName;

        return subjectServiceFactory;
    }]);

