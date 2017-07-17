'use strict';
app.controller("roleController", ['$scope', '$location', 'roleData', 'roleService', 'permissionService', 'Notification', function ($scope, $location,  roleData, roleService, permissionService, Notification) {

    $scope.status;
    findAllRoles();
    $scope.roleData = roleData;

    function findAllRoles() {
        roleService.findAllRoles().then(function (results) {
                if (results.data !== undefined && results.data.length > 0) {
                    $scope.roles = results.data;
                } else {
                    $scope.roles = [];
                }
            },
            function (error) {
                $scope.status = 'Unable to load roles data: ' + error.message;
            });
    }
    ;
    

    $scope.findRoleById = function (id) {
        roleService.findRoleById(id).then(function (results) {
            $scope.roleData.currRole = results.data;
            $location.path('/view-role');
        });
    };

    $scope.updateRoleClicked = function (newrole) {
        $scope.roleData.currRole = newrole;
        //findAllPermissionsForUpdate();
        $location.path('/update-role');
    };

    $scope.addRole = function (role) {
        roleService.addRole(role).then(function (results) {
                Notification.success({message: 'Role added successfully', delay: 5000});
                findAllRoles();
            },
            function (error) {
                $scope.status = 'Unable to add role: ' + error.message;
            });
    };

    $scope.deleteRole = function (id) {
        var c = confirm("Are you sure?");
        if (c === true) {
            roleService.deleteRole(id).then(function (results) {
                findAllRoles();
                Notification.success({message: 'Role deleted successfully', delay: 5000});
            }, function (error) {
                $scope.status = 'Unable to delete role: ' + error.message;
            });
        } else {
            return;
        }
    };


    $scope.findRoleByName = function (name) {
        roleService.findRoleByName(name).then(function (results) {
                console.log(name);
                $scope.roleData.currRole = results.data;
                $location.path('/view-role');
            },
            function (error) {
                $scope.status = 'Unable to load roles data: ' + error.message;
            });
    };

}]);

app.controller("editRoleController", ['$scope', '$location', 'roleService', 'roleData', 'Notification', function ($scope, $location, roleService, roleData, Notification) {

    $scope.roleData = roleData;

    $scope.updateRole = function (updatedRole) {
        roleService.updateRole(updatedRole).then(function (results) {
                $location.path('/add-role');
                Notification.success({message: 'Role update successful', delay: 5000});
            },
            function (error) {
                $scope.status = 'Unable to update role: ' + error.message;
            });
    };
}]);
