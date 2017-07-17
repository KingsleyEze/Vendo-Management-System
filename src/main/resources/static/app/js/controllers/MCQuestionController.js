/**
 * Created by abdulhakim on 11/11/16.
 */


'use strict';
app.controller("MCQuestionController", ['$scope', '$location', 'MCQuestionService', 'MCQuestionData', 'skillService', 'publisherService', 'topicService', function ($scope, $location, MCQuestionService, MCQuestionData, skillService, publisherService, topicService) {

    $scope.status;
    findAllQuestions();
    findAllTopics();
    findAllSkills();
    findAllPublishers();
    $scope.questionData = questionData;

    function findAllQuestions() {
        questionService.findAllQuestions().then(function (results) {
                if (results.data != undefined && results.data.length > 0) {
                    $scope.questions = results.data;
                } else {
                    $scope.questions = [];
                }
            },
            function (error) {
                $scope.status = 'Unable to load questions data: ' + error.message;
            });
    }
    ;

    function findAllSkills() {
        skillService.findAllSkills().then(function (results) {
                if (results.data != undefined && results.data.length > 0) {
                    $scope.skills = results.data;
                } else {
                    $scope.skills = [];
                }
            },
            function (error) {
                $scope.status = 'Unable to load skills data: ' + error.message;
            });
    }
    ;

    function findAllPublishers() {
        publisherService.findAllPublishers().then(function (results) {
                if (results.data != undefined && results.data.length > 0) {
                    $scope.publishers = results.data;
                } else {
                    $scope.publishers = [];
                }
            },
            function (error) {
                $scope.status = 'Unable to load publishers data: ' + error.message;
            });
    }
    ;

    function findAllTopics() {
        topicService.findAllTopics().then(function (results) {
                if (results.data != undefined && results.data.length > 0) {
                    $scope.topics = results.data;
                } else {
                    $scope.topics = [];
                }
            },
            function (error) {
                $scope.status = 'Unable to load topics data: ' + error.message;
            });
    }
    ;

    $scope.findQuestionById = function (id) {
        questionService.findQuestionById(id).then(function (results) {
            $scope.questionData.currQuestion = results.data;
            $location.path('/view-question');
        });
    };

    $scope.updateQuestionClicked = function (newquestion) {
        $scope.questionData.currQuestion = newquestion;
        $location.path('/update-question');
    };

    $scope.addQuestion = function (question) {
        questionService.addQuestion(question).then(function (results) {
                console.log(question);
                $location.path('/questions-list');
            },
            function (error) {
                $scope.status = 'Unable to add question: ' + error.message;
            });
    };

    $scope.deleteQuestion = function (id) {
        var c = confirm("Are you sure?");
        if (c == true) {
            questionService.deleteQuestion(id).then(function (results) {
                findAllQuestions();
            }, function (error) {
                $scope.status = 'Unable to delete question: ' + error.message;
            });
        } else {
            return;
        }
    };


}]);

app.controller("editQuestionController", ['$scope', '$location', 'questionService', 'questionData', function ($scope, $location, questionService, questionData) {

    $scope.questionData = questionData;

    $scope.updateQuestion = function (updatedQuestion) {
        questionService.updateQuestion(updatedQuestion).then(function (results) {
                $location.path('/questions-list');
            },
            function (error) {
                $scope.status = 'Unable to update question: ' + error.message;
            });
    };
}]);
