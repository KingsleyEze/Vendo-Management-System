'use strict';
app.factory("questionAnswerData", function () {
    return {};
});
app.factory('questionAnswerService', ['$http', function ($http) {

        $http.defaults.headers.post["Content-Type"] = "application/json";
        var questionAnswerServiceFactory = {};

        var _findAllQuestionAnswers = function () {
            return $http.get("/questionAnswers/all").then(function (results) {
                return results;
            });
        };


        var _findQuestionAnswerById = function (id) {
            return $http.get("/questionAnswers/findById/" + id).then(function (results) {
                return results;
            });
        };

        var _addQuestionAnswer = function (newquestion) {
            return $http.post("/questionAnswers/add", newquestion).then(function (results) {
                return results;
            });
        };

        var _updateQuestionAnswer = function (question) {
            return $http.put("/questionAnswers/update/", question).then(function (results) {

                return results;
            });
        };

        var _deleteQuestionAnswer = function (id) {
            return $http.delete("/questionAnswers/delete/" + id).then(function (results) {
                return results;
            });
        };

        var _findQuestionAnswerByName = function (name) {
            return $http.post("/questionAnswers/findByName", name).then(function (results) {
                return results;
            });
        };

        questionAnswerServiceFactory.findAllQuestionAnswerss = _findAllQuestionAnswers;
        questionAnswerServiceFactory.findQuestionAnswerById = _findQuestionAnswerById;
        questionAnswerServiceFactory.addQuestionAnswer = _addQuestionAnswer;
        questionAnswerServiceFactory.updateQuestionAnswer = _updateQuestionAnswer;
        questionAnswerServiceFactory.deleteQuestionAnswer = _deleteQuestionAnswer;

        return questionAnswerServiceFactory;

    }]);

