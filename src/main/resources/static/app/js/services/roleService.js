'use strict';
app.factory("roleData", function () {
    return {};
});
app.factory('roleService', ['$http', function ($http) {

        $http.defaults.headers.post["Content-Type"] = "application/json";
        var roleServiceFactory = {};

        var _findAllRoles = function () {
            return $http.get("/roles/all").then(function (results) {
                return results;
            });
        };
        
          var _findAllPermissions = function () {
            return $http.get("/permissions/all").then(function (results) {
                return results;
            });
        };

        var _findRoleById = function (id) {
            return $http.get("/roles/findById/" + id).then(function (results) {
                return results;
            });
        };

        var _addRole = function (newrole) {
            return $http.post("/roles/add", newrole).then(function (results) {
                return results;
            });
        };

        var _updateRole = function (role) {
            return $http.put("/roles/update/", role).then(function (results) {

                return results;
            });
        };

        var _deleteRole = function (id) {
            return $http.delete("/roles/delete/" + id).then(function (results) {
                return results;
            });
        };

        var _findRoleByName = function (name) {
            return $http.post("/roles/findByName", name).then(function (results) {
                return results;
            });
        };

        roleServiceFactory.findAllRoles = _findAllRoles;
        roleServiceFactory.findAllPermissions = _findAllPermissions;
        roleServiceFactory.findRoleById = _findRoleById;
        roleServiceFactory.addRole = _addRole;
        roleServiceFactory.updateRole = _updateRole;
        roleServiceFactory.deleteRole = _deleteRole;
        roleServiceFactory.findRoleByName = _findRoleByName;

        return roleServiceFactory;
    }]);

