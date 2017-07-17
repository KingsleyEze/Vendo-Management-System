'use strict';
app.controller("topicController", ['$scope', '$location', 'topicService', 'topicData', 'sectionService', 'Notification', function ($scope, $location, topicService, topicData, sectionService, Notification) {

        $scope.status;
        findAllTopics();
        findAllSections();
        $scope.topicData = topicData;

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

        function findAllSections() {
            sectionService.findAllSections().then(function (results) {
                if (results.data !== undefined && results.data.length > 0) {
                    $scope.sections = results.data;
                } else {
                    $scope.sections = [];
                }
            },
                    function (error) {
                        $scope.status = 'Unable to load topics data: ' + error.message;
                    });
        }
        ;

        function findAllSectionsForUpdate() {
            sectionService.findAllSections().then(function (results) {
                if (results.data !== undefined && results.data.length > 0) {
                    $scope.topicData.currTopic.sections = results.data;

                } else {
                    $scope.topicData.currTopic.sections = [];
                }
            },
                    function (error) {
                        $scope.status = 'Unable to load exam data: ' + error.message;
                    });
        }
        ;


        $scope.findTopicById = function (id) {
            topicService.findTopicById(id).then(function (results) {
                $scope.topicData.currTopic = results.data;
                $location.path('/view-topic');
            });
        };

        $scope.updateTopicClicked = function (newtopic) {
            $scope.topicData.currTopic = newtopic;
            console.log(newtopic);
            //$scope.topicData.currTopic.section.id = newtopic.section.id;
            findAllSectionsForUpdate();
            $location.path('/update-topic');
        };

        $scope.addTopic = function (topic) {
            topicService.addTopic(topic).then(function (results) {
                $scope.topic.name=null;
                findAllTopics();
                Notification.success({message: 'Topic added successfully', delay: 5000});
            },
                    function (error) {
                        $scope.status = 'Unable to add topic: ' + error.message;
                    });
        };

        $scope.deleteTopic = function (id) {
            var c = confirm("Are you sure?");
            if (c == true) {
                topicService.deleteTopic(id).then(function (results) {
                    findAllTopics();
                    Notification.success({message: 'Topic deleted successfully', delay: 5000});
                }, function (error) {
                    $scope.status = 'Unable to delete topic: ' + error.message;
                });
            } else {
                return;
            }
        };


    }]);

app.controller("editTopicController", ['$scope', '$location', 'topicService', 'topicData', 'Notification', function ($scope, $location, topicService, topicData, Notification) {

        $scope.topicData = topicData;

        $scope.updateTopic = function (updatedTopic) {
            topicService.updateTopic(updatedTopic).then(function (results) {
                $location.path('/add-topic');
                Notification.success({message: 'Topic update successfull', delay: 5000});
            },
                    function (error) {
                        $scope.status = 'Unable to update topic: ' + error.message;
                    });
        };
    }]);

