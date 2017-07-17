'use strict';
app.factory("permissionData", function () {
    return {};
});
app.factory('permissionService', ['$http', function ($http) {

        $http.defaults.headers.post["Content-Type"] = "application/json";
        var permissionServiceFactory = {};

        var _findAllPermissions = function () {
            return $http.get("/permissions/all").then(function (results) {
                return results;
            });
        };
        

        var _findPermissionById = function (id) {
            return $http.get("/permissions/findById/" + id).then(function (results) {
                return results;
            });
        };

        var _addPermission = function (newpermission) {
            return $http.post("/permissions/add", newpermission).then(function (results) {
                return results;
            });
        };

        var _updatePermission = function (permission) {
            return $http.put("/permissions/update/", permission).then(function (results) {

                return results;
            });
        };

        var _deletePermission = function (id) {
            return $http.delete("/permissions/delete/" + id).then(function (results) {
                return results;
            });
        };

        var _findPermissionByName = function (name) {
            return $http.post("/permissions/findByName", name).then(function (results) {
                return results;
            });
        };

        permissionServiceFactory.findAllPermissions = _findAllPermissions;
        permissionServiceFactory.findAllPermissions = _findAllPermissions;
        permissionServiceFactory.findPermissionById = _findPermissionById;
        permissionServiceFactory.addPermission = _addPermission;
        permissionServiceFactory.updatePermission = _updatePermission;
        permissionServiceFactory.deletePermission = _deletePermission;
        permissionServiceFactory.findPermissionByName = _findPermissionByName;

        return permissionServiceFactory;
    }]);

