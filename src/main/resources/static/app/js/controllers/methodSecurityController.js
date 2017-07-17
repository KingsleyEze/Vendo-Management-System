'use strict';
app.controller("methodSecurityController", ['$scope', '$location', 'methodSecurityService', 'methodSecurityData', 'Notification', function ($scope, $location, methodSecurityService, methodSecurityData, Notification) {

        $scope.status;
        findAllMethodSecurities();
        $scope.methodSecurityData = methodSecurityData;

        function findAllMethodSecurities() {
            methodSecurityService.findAllMethodSecurities().then(function (results) {
                if (results.data !== undefined && results.data.length > 0) {
                    $scope.methodSecurities = results.data;
                } else {
                    $scope.methodSecurities = [];
                }
            },
            function (error) {
                $scope.status = 'Unable to load methodSecurities data: ' + error.message;
            });
        };

        function findAllMethodSecurities() {
            methodSecurityService.findAllMethodSecurities().then(function (results) {
                if (results.data !== undefined && results.data.length > 0) {
                    $scope.methodSecurities = results.data;
                } else {
                    $scope.methodSecurities = [];
                }
            },
                    function (error) {
                        $scope.status = 'Unable to load methodSecurities data: ' + error.message;
                    });
        };

        $scope.findMethodSecurityById = function (id) {
            methodSecurityService.findMethodSecurityById(id).then(function (results) {
                $scope.methodSecurityData.currMethodSecurity = results.data;
                $location.path('/view-methodSecurity');
            });
        };

        $scope.updateMethodSecurityClicked = function (newmethodSecurity) {
            $scope.methodSecurityData.currMethodSecurity = newmethodSecurity;
            /*$scope.methodSecurityData.currMethodSecurity.id = newmethodSecurity.id;
            findAllMethodSecuritiesForUpdate();*/
            $location.path('/update-methodSecurity');
        };

        $scope.addMethodSecurity = function (methodSecurity) {
            methodSecurityService.addMethodSecurity(methodSecurity).then(function (results) {
                Notification.success({message: 'MethodSecurity added successfully', delay: 5000});
                findAllMethodSecurities();
            },
                    function (error) {
                        $scope.status = 'Unable to add methodSecurity: ' + error.message;
                    });
        };

        $scope.deleteMethodSecurity = function (id) {
            var c = confirm("Are you sure?");
            if (c === true) {
                methodSecurityService.deleteMethodSecurity(id).then(function (results) {
                    findAllMethodSecurities();
                    Notification.success({message: 'MethodSecurity deleted successfully', delay: 5000});
                }, function (error) {
                    $scope.status = 'Unable to delete methodSecurity: ' + error.message;
                });
            } else {
                return;
            }
        };


        /*$scope.findMethodSecurityByClazz = function (clazz) {
            methodSecurityService.findMethodSecurityByClazz(name).then(function (results) {
                console.log(clazz);
                $scope.methodSecurityData.currMethodSecurity = results.data;
                $location.path('/view-methodSecurity');
            },
                    function (error) {
                        $scope.status = 'Unable to load methodSecurities data: ' + error.message;
                    });
        };*/


        $scope.findMethodSecurityByMethod = function (methodName) {
            methodSecurityService.findMethodSecurityByName(methodName).then(function (results) {
                    console.log(methodName);
                    $scope.methodSecurityData.currMethodSecurity = results.data;
                    $location.path('/view-methodSecurity');
                },
                function (error) {
                    $scope.status = 'Unable to load methodSecurities data: ' + error.message;
                });
        };

    }]);

app.controller("editMethodSecurityController", ['$scope', '$location', 'methodSecurityService', 'methodSecurityData', 'Notification', function ($scope, $location, methodSecurityService, methodSecurityData, Notification) {

        $scope.methodSecurityData = methodSecurityData;

        $scope.updateMethodSecurity = function (updatedMethodSecurity) {
            methodSecurityService.updateMethodSecurity(updatedMethodSecurity).then(function (results) {
                $location.path('/add-methodSecurity');
                Notification.success({message: 'MethodSecurity update successful', delay: 5000});
            },
                    function (error) {
                        $scope.status = 'Unable to update methodSecurity: ' + error.message;
                    });
        };
    }]);
