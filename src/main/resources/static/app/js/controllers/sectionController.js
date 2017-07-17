'use strict';
app.controller("sectionController", ['$scope', '$location', 'sectionService', 'sectionData', 'subjectService', 'Notification', function ($scope, $location, sectionService, sectionData, subjectService, Notification) {

        $scope.status;
        findAllSections();
        findAllSubjects();
        $scope.sectionData = sectionData;

        function findAllSections() {
            sectionService.findAllSections().then(function (results) {
                console.log(results.data._embedded);
                if (results.data != undefined && results.data.length > 0) {
                    $scope.sections = results.data;
                } else {
                    $scope.sections = [];
                }
            },
                    function (error) {
                        $scope.status = 'Unable to load sections data: ' + error.message;
                    });
        }
        ;

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

        function findAllSubjectsForUpdate() {
            sectionService.findAllSubjects().then(function (results) {
                if (results.data !== undefined && results.data.length > 0) {
                    $scope.sectionData.currSection.subjects = results.data;

                } else {
                    $scope.sectionData.currSection.subjects = [];
                }
            },
                    function (error) {
                        $scope.status = 'Unable to load exam data: ' + error.message;
                    });
        }
        ;

        $scope.findSectionById = function (id) {
            sectionService.findSectionById(id).then(function (results) {
                $scope.sectionData.currSection = results.data;
                $location.path('/section/view');
            });
        };

        $scope.updateSectionClicked = function (newsection) {
            $scope.sectionData.currSection = newsection;
            findAllSubjectsForUpdate();
            $location.path('/update-section');
        };

        $scope.addSection = function (section) {
            sectionService.addSection(section).then(function (results) {
                findAllSections();
                Notification.success({message: 'Section added successfully', delay: 5000});
            },
                    function (error) {
                        $scope.status = 'Unable to add section: ' + error.message;
                    });
        };

        $scope.deleteSection = function (id) {
            var c = confirm("Are you sure?");
            if (c === true) {
                sectionService.deleteSection(id).then(function (results) {
                    findAllSections();
                    Notification.success({message: 'Section deleted successfully', delay: 5000});
                }, function (error) {
                    $scope.status = 'Unable to delete section: ' + error.message;
                });
            } else {
                return;
            }
        };


        $scope.findSectionByName = function (name) {
            sectionService.findSectionByName(name).then(function (results) {
                console.log(name);
                $scope.sectionData.currSection = results.data;
                $location.path('/sections/view');
            },
                    function (error) {
                        $scope.status = 'Unable to load sections data: ' + error.message;
                    });
        };

    }]);

app.controller("editSectionController", ['$scope', '$location', 'sectionService', 'sectionData', 'Notification', function ($scope, $location, sectionService, sectionData, Notification) {

        $scope.sectionData = sectionData;

        $scope.updateSection = function (updatedSection) {
            sectionService.updateSection(updatedSection).then(function (results) {
                $location.path('add-section');
                Notification.success({message: 'Section update successfull', delay: 5000});
            },
                    function (error) {
                        $scope.status = 'Unable to update section: ' + error.message;
                    });
        };
    }]);
