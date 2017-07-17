'use strict';
app.controller("classSecurityController", ['$scope', '$location', 'classSecurityService', 'classSecurityData', 'Notification', function ($scope, $location, classSecurityService, classSecurityData, Notification) {

        $scope.status;
        findAllClassSecurities();
        $scope.classSecurityData = classSecurityData;

        function findAllClassSecurities() {
            classSecurityService.findAllClassSecurities().then(function (results) {
                if (results.data !== undefined && results.data.length > 0) {
                    $scope.classSecurities = results.data;
                } else {
                    $scope.classSecurities = [];
                }
            },
            function (error) {
                $scope.status = 'Unable to load classSecurities data: ' + error.message;
            });
        };

        function findAllClassSecurities() {
            classSecurityService.findAllClassSecurities().then(function (results) {
                if (results.data !== undefined && results.data.length > 0) {
                    $scope.classSecurities = results.data;
                } else {
                    $scope.classSecurities = [];
                }
            },
                    function (error) {
                        $scope.status = 'Unable to load classSecurities data: ' + error.message;
                    });
        };

        $scope.findClassSecurityById = function (id) {
            classSecurityService.findClassSecurityById(id).then(function (results) {
                $scope.classSecurityData.currClassSecurity = results.data;
                $location.path('/view-classSecurity');
            });
        };

        $scope.updateClassSecurityClicked = function (newclassSecurity) {
            $scope.classSecurityData.currClassSecurity = newclassSecurity;
            /*$scope.classSecurityData.currClassSecurity.id = newclassSecurity.id;
            findAllClassSecuritiesForUpdate();*/
            $location.path('/update-classSecurity');
        };

        $scope.addClassSecurity = function (classSecurity) {
            classSecurityService.addClassSecurity(classSecurity).then(function (results) {
                Notification.success({message: 'ClassSecurity added successfully', delay: 5000});
                findAllClassSecurities();
            },
                    function (error) {
                        $scope.status = 'Unable to add classSecurity: ' + error.message;
                    });
        };

        $scope.deleteClassSecurity = function (id) {
            var c = confirm("Are you sure?");
            if (c === true) {
                classSecurityService.deleteClassSecurity(id).then(function (results) {
                    findAllClassSecurities();
                    Notification.success({message: 'ClassSecurity deleted successfully', delay: 5000});
                }, function (error) {
                    $scope.status = 'Unable to delete classSecurity: ' + error.message;
                });
            } else {
                return;
            }
        };


        $scope.findClassSecurityByName = function (name) {
            classSecurityService.findClassSecurityByName(name).then(function (results) {
                console.log(name);
                $scope.classSecurityData.currClassSecurity = results.data;
                $location.path('/view-classSecurity');
            },
                    function (error) {
                        $scope.status = 'Unable to load classSecurities data: ' + error.message;
                    });
        };

    }]);

app.controller("editClassSecurityController", ['$scope', '$location', 'classSecurityService', 'classSecurityData', 'Notification', function ($scope, $location, classSecurityService, classSecurityData, Notification) {

        $scope.classSecurityData = classSecurityData;

        $scope.updateClassSecurity = function (updatedClassSecurity) {
            classSecurityService.updateClassSecurity(updatedClassSecurity).then(function (results) {
                $location.path('/add-classSecurity');
                Notification.success({message: 'ClassSecurity update successful', delay: 5000});
            },
                    function (error) {
                        $scope.status = 'Unable to update classSecurity: ' + error.message;
                    });
        };
    }]);
