'use strict';
app.controller("publisherController", ['$scope', '$location', 'publisherService', 'publisherData', 'examService', 'Notification', function ($scope, $location, publisherService, publisherData, examService, Notification) {

        $scope.status;
        findAllPublishers();
        findAllExams();
        $scope.publisherData = publisherData;

        function findAllPublishers() {
            publisherService.findAllPublishers().then(function (results) {
                if (results.data !== undefined && results.data.length > 0) {
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

        function findAllExamsForUpdate() {
            examService.findAllExams().then(function (results) {
                if (results.data !== undefined && results.data.length > 0) {
                    $scope.publisherData.currPublisher.exams = results.data;

                } else {
                    $scope.publisherData.currPublisher.exams = [];
                }
            },
                    function (error) {
                        $scope.status = 'Unable to load exam data: ' + error.message;
                    });
        }
        ;


        $scope.findPublisherById = function (id) {
            publisherService.findPublisherById(id).then(function (results) {
                $scope.publisherData.currPublisher = results.data;
                $location.path('/view-publisher');
            });
        };

        $scope.updatePublisherClicked = function (newpublisher) {
            $scope.publisherData.currPublisher = newpublisher;
            $scope.publisherData.currPublisher.exam.id = newpublisher.exam.id;
            findAllExamsForUpdate();
            $location.path('/update-publisher');
        };

        $scope.addPublisher = function (publisher) {
            publisherService.addPublisher(publisher).then(function (results) {
                Notification.success({message: 'Publisher added successfully', delay: 5000});
                findAllPublishers();
            },
                    function (error) {
                        $scope.status = 'Unable to add publisher: ' + error.message;
                    });
        };

        $scope.deletePublisher = function (id) {
            var c = confirm("Are you sure?");
            if (c === true) {
                publisherService.deletePublisher(id).then(function (results) {
                    findAllPublishers();
                    Notification.success({message: 'Publisher deleted successfully', delay: 5000});
                }, function (error) {
                    $scope.status = 'Unable to delete publisher: ' + error.message;
                });
            } else {
                return;
            }
        };


        $scope.findPublisherByName = function (name) {
            publisherService.findPublisherByName(name).then(function (results) {
                console.log(name);
                $scope.publisherData.currPublisher = results.data;
                $location.path('/view-publisher');
            },
                    function (error) {
                        $scope.status = 'Unable to load publishers data: ' + error.message;
                    });
        };

    }]);

app.controller("editPublisherController", ['$scope', '$location', 'publisherService', 'publisherData', 'Notification', function ($scope, $location, publisherService, publisherData, Notification) {

        $scope.publisherData = publisherData;

        $scope.updatePublisher = function (updatedPublisher) {
            publisherService.updatePublisher(updatedPublisher).then(function (results) {
                $location.path('/add-publisher');
                Notification.success({message: 'Publisher update successfull', delay: 5000});
            },
                    function (error) {
                        $scope.status = 'Unable to update publisher: ' + error.message;
                    });
        };
    }]);
