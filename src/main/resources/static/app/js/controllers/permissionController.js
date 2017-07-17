'use strict';
app.controller("permissionController", ['$scope', '$location', 'permissionService', 'permissionData', 'Notification', function ($scope, $location, permissionService, permissionData, Notification) {

        $scope.status;
        findAllPermissions();
        $scope.permissionData = permissionData;

        function findAllPermissions() {
            permissionService.findAllPermissions().then(function (results) {
                if (results.data !== undefined && results.data.length > 0) {
                    $scope.permissions = results.data;
                } else {
                    $scope.permissions = [];
                }
            },
            function (error) {
                $scope.status = 'Unable to load permissions data: ' + error.message;
            });
        };

        function findAllPermissions() {
            permissionService.findAllPermissions().then(function (results) {
                if (results.data !== undefined && results.data.length > 0) {
                    $scope.permissions = results.data;
                } else {
                    $scope.permissions = [];
                }
            },
                    function (error) {
                        $scope.status = 'Unable to load permissions data: ' + error.message;
                    });
        };

        $scope.findPermissionById = function (id) {
            permissionService.findPermissionById(id).then(function (results) {
                $scope.permissionData.currPermission = results.data;
                $location.path('/view-permission');
            });
        };

        $scope.updatePermissionClicked = function (newpermission) {
            $scope.permissionData.currPermission = newpermission;
            /*$scope.permissionData.currPermission.id = newpermission.id;
            findAllPermissionsForUpdate();*/
            $location.path('/update-permission');
        };

        $scope.addPermission = function (permission) {
            permissionService.addPermission(permission).then(function (results) {
                Notification.success({message: 'Permission added successfully', delay: 5000});
                findAllPermissions();
            },
                    function (error) {
                        $scope.status = 'Unable to add permission: ' + error.message;
                    });
        };

        $scope.deletePermission = function (id) {
            var c = confirm("Are you sure?");
            if (c === true) {
                permissionService.deletePermission(id).then(function (results) {
                    findAllPermissions();
                    Notification.success({message: 'Permission deleted successfully', delay: 5000});
                }, function (error) {
                    $scope.status = 'Unable to delete permission: ' + error.message;
                });
            } else {
                return;
            }
        };


        $scope.findPermissionByName = function (name) {
            permissionService.findPermissionByName(name).then(function (results) {
                console.log(name);
                $scope.permissionData.currPermission = results.data;
                $location.path('/view-permission');
            },
                    function (error) {
                        $scope.status = 'Unable to load permissions data: ' + error.message;
                    });
        };

    }]);

app.controller("editPermissionController", ['$scope', '$location', 'permissionService', 'permissionData', 'Notification', function ($scope, $location, permissionService, permissionData, Notification) {

        $scope.permissionData = permissionData;

        $scope.updatePermission = function (updatedPermission) {
            permissionService.updatePermission(updatedPermission).then(function (results) {
                $location.path('/add-permission');
                Notification.success({message: 'Permission update successful', delay: 5000});
            },
                    function (error) {
                        $scope.status = 'Unable to update permission: ' + error.message;
                    });
        };
    }]);
