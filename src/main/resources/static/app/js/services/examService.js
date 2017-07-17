'use strict';
app.factory("examData", function () {
    return {};
});
app.factory('examService', ['$http', function ($http) {

        $http.defaults.headers.post["Content-Type"] = "application/json";
        var examServiceFactory = {};

        var _findAllExams = function () {
            return $http.get("/exams/all").then(function (results) {
                return results;
            });
        };
        
        var _findAllPublishers = function () {
            return $http.get("/publishers/all").then(function (results) {
                return results;
            });
        };

        var _findExamById = function (id) {
            return $http.get("/exams/findById/" + id).then(function (results) {
                return results;
            });
        };

        var _addExam = function (newexam) {
            return $http.post("/exams/add", newexam).then(function (results) {
                console.log(newexam);
                return results;
            });
        };

        var _updateExam = function (exam) {
            return $http.put("/exams/update/", exam).then(function (results) {

                return results;
            });
        };
        
         var _updatePublisher = function (publisher) {
            return $http.put("/publishers/update/", publisher).then(function (results) {

                return results;
            });
        };

        var _deleteExam = function (id) {
            return $http.delete("/exams/delete/" + id).then(function (results) {
                return results;
            });
        };

        var _findExamByName = function (name) {
            return $http.post("/exams/findByName", name).then(function (results) {
                return results;
            });
        };

        examServiceFactory.findAllExams = _findAllExams;
        examServiceFactory.findExamById = _findExamById;
        examServiceFactory.findAllPublishers = _findAllPublishers;
        examServiceFactory.addExam = _addExam;
        examServiceFactory.updateExam = _updateExam;
        examServiceFactory.updatePublisher = _updatePublisher;
        examServiceFactory.deleteExam = _deleteExam;
        examServiceFactory.findExamByName = _findExamByName;

        return examServiceFactory;
    }]);



