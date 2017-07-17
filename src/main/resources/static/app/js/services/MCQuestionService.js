'use strict';
app.factory("MCQuestionData", function () {
    return {};
});
app.factory('MCQuestionService', ['$http', function ($http) {

        $http.defaults.headers.post["Content-Type"] = "application/json";
        var questionServiceFactory = {};

        var _findAllQuestions = function () {
            return $http.get("/mcquestions/all").then(function (results) {
                return results;
            });
        };

        var _findAllPublishers = function () {
            return $http.get("/publishers/all").then(function (results) {
                return results;
            });
        };

        var _findAllTopics = function () {
            return $http.get("/topics/all").then(function (results) {
                return results;
            });
        };

        var _findAllSkills = function () {
            return $http.get("/skills/all").then(function (results) {
                return results;
            });
        };


        var _findQuestionById = function (id) {
            return $http.get("/questions/findById/" + id).then(function (results) {
                return results;
            });
        };

        var _addQuestion = function (newquestion) {
            return $http.post("/questions/add", newquestion).then(function (results) {
                return results;
            });
        };

        var _updateQuestion = function (question) {
            return $http.put("/questions/update/", question).then(function (results) {

                return results;
            });
        };

        var _deleteQuestion = function (id) {
            return $http.delete("/questions/delete/" + id).then(function (results) {
                return results;
            });
        };

        var _findQuestionByName = function (name) {
            return $http.post("/questions/findByName", name).then(function (results) {
                return results;
            });
        };

        questionServiceFactory.findAllQuestions = _findAllQuestions;
        questionServiceFactory.findAllPublishers = _findAllPublishers;
        questionServiceFactory.findAllTopics = _findAllTopics;
        questionServiceFactory.findAllSkills = _findAllSkills;
        questionServiceFactory.findQuestionById = _findQuestionById;
        questionServiceFactory.addQuestion = _addQuestion;
        questionServiceFactory.updateQuestion = _updateQuestion;
        questionServiceFactory.deleteQuestion = _deleteQuestion;
        questionServiceFactory.findQuestionByName = _findQuestionByName;

        return questionServiceFactory;
    }]);

