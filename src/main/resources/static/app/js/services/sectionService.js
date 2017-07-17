'use strict';
app.factory("sectionData", function () {
    return {};
});
app.factory('sectionService', ['$http', function ($http) {

        $http.defaults.headers.post["Content-Type"] = "application/json";
        var sectionServiceFactory = {};

        var _findAllSections = function () {
            return $http.get("/sections/all").then(function (results) {
                return results;
            });
        };
        
          var _findAllSubjects = function () {
            return $http.get("/subjects/all").then(function (results) {
                return results;
            });
        };

        var _findSectionsBySubjectId = function(subjectId){
            return $http.get("/sections/getSectionsBySubject/"+subjectId).then(function(results){
                return results;
            });
        }

        var _findSectionById = function (id) {
            return $http.get("/sections/findById/" + id).then(function (results) {
                return results;
            });
        };

        var _addSection = function (newSection) {
            return $http.post("/sections/add", newSection).then(function (results) {
                return results;
            });
        };

        var _updateSection = function (section) {
            return $http.put("/sections/update/", section).then(function (results) {

                return results;
            });
        };

        var _deleteSection = function (id) {
            return $http.delete("/sections/delete/" + id).then(function (results) {
                return results;
            });
        };

        var _findSectionByName = function (name) {
            return $http.post("/sections/findByName", name).then(function (results) {
                console.log("It Reached Here");
                return results;
            });
        };

        var _findSectionsBySubjectId = function(subjectId){
            return $http.get("/sections/getSectionsBySubject/"+subjectId).then(function(results){
                return results;
            });
        }

        sectionServiceFactory.findSectionsBySubjectId = _findSectionsBySubjectId;
        sectionServiceFactory.findAllSections = _findAllSections;
        sectionServiceFactory.findAllSubjects = _findAllSubjects;
        sectionServiceFactory.findSectionById = _findSectionById;
        sectionServiceFactory.addSection = _addSection;
        sectionServiceFactory.updateSection = _updateSection;
        sectionServiceFactory.deleteSection = _deleteSection;
        sectionServiceFactory.findSectionByName = _findSectionByName;
        sectionServiceFactory.findSectionsBySubjectId = _findSectionsBySubjectId;

        return sectionServiceFactory;
    }]);

