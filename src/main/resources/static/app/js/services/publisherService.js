'use strict';
app.factory("publisherData", function () {
    return {};
});
app.factory('publisherService', ['$http', function ($http) {

        $http.defaults.headers.post["Content-Type"] = "application/json";
        var publisherServiceFactory = {};

        var _findAllPublishers = function () {
            return $http.get("/publishers/all").then(function (results) {
                return results;
            });
        };
        
          var _findAllExams = function () {
            return $http.get("/exams/all").then(function (results) {
                return results;
            });
        };

        var _findPublisherById = function (id) {
            return $http.get("/publishers/findById/" + id).then(function (results) {
                return results;
            });
        };

        var _addPublisher = function (newpublisher) {
            return $http.post("/publishers/add", newpublisher).then(function (results) {
                return results;
            });
        };

        var _updatePublisher = function (publisher) {
            return $http.put("/publishers/update/", publisher).then(function (results) {

                return results;
            });
        };

        var _deletePublisher = function (id) {
            return $http.delete("/publishers/delete/" + id).then(function (results) {
                return results;
            });
        };

        var _findPublisherByName = function (name) {
            return $http.post("/publishers/findByName", name).then(function (results) {
                return results;
            });
        };

        publisherServiceFactory.findAllPublishers = _findAllPublishers;
        publisherServiceFactory.findAllExams = _findAllExams;
        publisherServiceFactory.findPublisherById = _findPublisherById;
        publisherServiceFactory.addPublisher = _addPublisher;
        publisherServiceFactory.updatePublisher = _updatePublisher;
        publisherServiceFactory.deletePublisher = _deletePublisher;
        publisherServiceFactory.findPublisherByName = _findPublisherByName;

        return publisherServiceFactory;
    }]);

