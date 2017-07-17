'use strict';
app.factory("topicData", function () {
    return {};
});
app.factory('topicService', ['$http', function ($http) {

        $http.defaults.headers.post["Content-Type"] = "application/json";
        var topicServiceFactory = {};

        var _findAllTopics = function () {
            return $http.get("/topics/all").then(function (results) {
                return results;
            });
        };

        var _findAllSections = function () {
            return $http.get("/sections/all").then(function (results) {
                return results;
            });
        };

        var _findTopicById = function (id) {
            return $http.get("/topics/findById/" + id).then(function (results) {
                return results;
            });
        };

        var _addTopic = function (newtopic) {
            return $http.post("/topics/add", newtopic).then(function (results) {
                console.log(newtopic);
                return results;
            });
        };

        var _updateTopic = function (topic) {
            return $http.put("/topics/update/", topic).then(function (results) {

                return results;
            });
        };

        var _deleteTopic = function (id) {
            return $http.delete("/topics/delete/" + id).then(function (results) {
                return results;
            });
        };

        var _findTopicByName = function (name) {
            return $http.post("/topics/findByName", name).then(function (results) {
                return results;
            });
        };

        var _findTopicsBySection  = function(sectionId){
                return $http.get("/topics/getTopicsBySection/"+sectionId).then(function (results) {
                        return results;
                });
        }

        topicServiceFactory.findAllTopics = _findAllTopics;
        topicServiceFactory.findAllSections = _findAllSections;
        topicServiceFactory.findTopicById = _findTopicById;
        topicServiceFactory.addTopic = _addTopic;
        topicServiceFactory.updateTopic = _updateTopic;
        topicServiceFactory.deleteTopic = _deleteTopic;
        topicServiceFactory.findTopicByName = _findTopicByName;
        topicServiceFactory.findTopicsBySection = _findTopicsBySection;

        return topicServiceFactory;
    }]);





