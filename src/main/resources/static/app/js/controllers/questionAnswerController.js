'use strict';
app.controller("questionAnswerController", ['$scope', '$location', 'questionAnswerService', 'questionAnswerData', function ($scope, $location, questionAnswerService, questionAnswerData) {

        $scope.status;


    $scope.qas = [];


    $scope.qaToAdd = [{
        description: '',
        correct: '',
        point: 1
    }];
    console.log("QA Array " + $scope.qaToAdd);

    $scope.add = function(itemToAdd) {

        var index = $scope.qaToAdd.indexOf(itemToAdd);

        $scope.qaToAdd.splice(index, 1);

        $scope.qas.push(angular.copy(itemToAdd))
    }

    $scope.addNew = function() {

        $scope.qaToAdd.push({
            description: '',
            correct: '',
            point: 1
        })
    }

        findAllQuestionAnswers();
        
        $scope.questionAnswerData = questionAnswerData;

        function findAllQuestionAnswers() {
            questionAnswerService.findAllQuestionAnswers().then(function (results) {
                if (results.data != undefined && results.data.length > 0) {
                    $scope.questionAnswers = results.data;
                
                } else {
                    $scope.questionAnswers = [];
                }
            },
                    function (error) {
                        $scope.status = 'Unable to load questions data: ' + error.message;
                    });
        }
        ;

        

        $scope.findQuestionAnswerById = function (id) {
            questionAnswerService.findQuestionAnswerById(id).then(function (results) {
                $scope.questionAnswerData.currQuestionAnswer = results.data;
                $location.path('/view-questionanswer');
            });
        };

        $scope.updateQuestionAnswerClicked = function (newquestionanswer) {
            $scope.questionAnswerData.currQuestionAnswer = newquestionanswer;
            $location.path('/update-questionanswer');
        };
        
        

        $scope.addQuestionAnswer = function (questionAnswer) {
            questionAnswerService.addQuestionAnswer(questionAnswer).then(function (results) {
                console.log(questionAnswer);
                $location.path('/questionanswers-list');
            },
                    function (error) {
                        $scope.status = 'Unable to add question: ' + error.message;
                    });
        };

        $scope.deleteQuestionAnswer = function (id) {
            var c = confirm("Are you sure?");
            if (c == true) {
                questionAnswerService.deleteQuestionAnswer(id).then(function (results) {
                    findAllQuestionAnswers();
                }, function (error) {
                    $scope.status = 'Unable to delete question: ' + error.message;
                });
            } else {
                return;
            }
        };


    }]);

app.controller("editQuestionAnswerController", ['$scope', '$location', 'questionAnswerService', 'questionAnswerData', function ($scope, $location, questionAnswerService, questionAnswerData) {

        $scope.questionAnswerData = questionAnswerData;

        $scope.updateQuestionAnswer = function (updatedQuestionAnswer) {
            questionAnswerService.updateQuestionAnswer(updatedQuestionAnswer).then(function (results) {
                $location.path('/questionanswers-list');
            },
                    function (error) {
                        $scope.status = 'Unable to update question: ' + error.message;
                    });
        };
    }]);


