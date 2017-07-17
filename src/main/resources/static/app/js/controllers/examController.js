'use strict';
app.controller("examController", ['$scope', '$location', 'examService', 'examData', 'Notification', function ($scope, $location, examService, examData, Notification) {

        $scope.status;
        findAllExams();
        $scope.examData = examData;

        function findAllExams() {
            examService.findAllExams().then(function (results) {
                if (results.data !== undefined && results.data.length > 0) {
                    $scope.exams = results.data;
                } else {
                    $scope.exams = [];
                }
            },
                    function (error) {
                        $scope.status = 'Unable to load exams data: ' + error.message;
                    });
        }
        ;


        $scope.findExamById = function (id) {
            examService.findExamById(id).then(function (results) {
                $scope.examData.currExam = results.data;
                $location.path('/view-exam');
            });
        };

        $scope.updateExamClicked = function (newexam) {
            $scope.examData.currExam = newexam;
            $location.path('/update-exam');
        };

        $scope.addExam = function (exam) {
            examService.addExam(exam).then(function (results) {
                Notification.success({message: 'Exam added successfully', delay: 5000});
                findAllExams();
            },
                    function (error) {
                        $scope.status = 'Unable to add exam: ' + error.message;
                    });
        };

        $scope.deleteExam = function (id) {
            var c = confirm("Are you sure?");
            if (c === true) {
                examService.deleteExam(id).then(function (results) {
                    findAllExams();
                    Notification.success({message: 'Exam deleted successfully', delay: 5000});
                }, function (error) {
                    $scope.status = 'Unable to delete exam: ' + error.message;
                });
            } else {
                return;
            }
        };


        $scope.findExamByName = function (name) {
            examService.findExamByName(name).then(function (results) {
                console.log(name);
                $scope.examData.currExam = results.data;
                $location.path('/view-exam');
            },
                    function (error) {
                        $scope.status = 'Unable to load exams data: ' + error.message;
                    });
        };

    }]);

app.controller("editExamController", ['$scope', '$location', 'examService', 'examData', 'Notification', function ($scope, $location, examService, examData, Notification) {

        $scope.examData = examData;

        $scope.updateExam = function (updatedExam) {
            examService.updateExam(updatedExam).then(function (results) {
                $location.path('/add-exam');
                Notification.success({message: 'Exam update successfull', delay: 5000});
            },
                    function (error) {
                        $scope.status = 'Unable to update exam: ' + error.message;
                    });
        };
    }]);
