'use strict';
app.controller("subjectController", ['$scope', '$location', 'subjectService', 'subjectData', 'Notification', function ($scope, $location, subjectService, subjectData, Notification) {

        $scope.status;
        findAllSubjects();
        $scope.subjectData = subjectData;

        function findAllSubjects() {
            subjectService.findAllSubjects().then(function (results) {
                if (results.data != undefined && results.data.length > 0) {
                    $scope.subjects = results.data;
                } else {
                    $scope.subjects = [];
                }
            },
                    function (error) {
                        $scope.status = 'Unable to load subjects data: ' + error.message;
                    });
        }
        ;

        $scope.findSubjectById = function (id) {
            subjectService.findSubjectById(id).then(function (results) {
                $scope.subjectData.currSubject = results.data;
                $location.path('/view-subject');
            });
        };

        $scope.updateSubjectClicked = function (newsubject) {
            $scope.subjectData.currSubject = newsubject;
            $location.path('/update-subject');
        };

        $scope.addSubject = function (subject) {
            subjectService.addSubject(subject).then(function (results) {
                Notification.success({message: 'Subject added successfully', delay: 5000});
                findAllSubjects();
            },
                    function (error) {
                        $scope.status = 'Unable to add subject: ' + error.message;
                    });
        };

        $scope.deleteSubject = function (id) {
            var c = confirm("Are you sure?");
            if (c == true) {
                subjectService.deleteSubject(id).then(function (results) {
                    findAllSubjects();
                    Notification.success({message: 'Subject deleted successfully', delay: 5000});
                }, function (error) {
                    $scope.status = 'Unable to delete subject: ' + error.message;
                });
            } else {
                return;
            }
        };


        $scope.findSubjectByName = function (name) {
            subjectService.findSubjectByName(name).then(function (results) {
                console.log(name);
                $scope.subjectData.currSubject = results.data;
                //console.log($scope.subjectData);
                $location.path('/view-subject');
            },
                    function (error) {
                        $scope.status = 'Unable to load subjects data: ' + error.message;
                    });
        };

    }]);

app.controller("editController", ['$scope', '$location', 'subjectService', 'subjectData', 'Notification', function ($scope, $location, subjectService, subjectData, Notification) {

        $scope.subjectData = subjectData;

        $scope.updateSubject = function (updatedSubject) {

            subjectService.updateSubject(updatedSubject).then(function (results) {
                $location.path('/add-subject');
                Notification.success({message: 'Subject update successfull', delay: 5000});
            },
                    function (error) {
                        $scope.status = 'Unable to update subject: ' + error.message;
                    });
        };
    }]);
