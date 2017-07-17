'use strict';
app.factory("methodSecurityData", function () {
    return {};
});
app.factory('methodSecurityService', ['$http', function ($http) {

    $http.defaults.headers.post["Content-Type"] = "application/json";
    var methodSecurityServiceFactory = {};

    var _findAllMethodSecurities = function () {
        return $http.get("/methodSecurities/all").then(function (results) {
            return results;
        });
    };


    var _findMethodSecurityById = function (id) {
        return $http.get("/methodSecurities/findById/" + id).then(function (results) {
            return results;
        });
    };

    var _addMethodSecurity = function (newclassSecuritie) {
        return $http.post("/methodSecurities/add", newclassSecuritie).then(function (results) {
            return results;
        });
    };

    var _updateMethodSecurity = function (methodSecurity) {
        return $http.put("/methodSecurities/update/", methodSecurity).then(function (results) {
            return results;
        });
    };

    var _deleteMethodSecurity = function (id) {
        return $http.delete("/methodSecurities/delete/" + id).then(function (results) {
            return results;
        });
    };

    var _findMethodSecurityByName = function (name) {
        return $http.post("/methodSecurities/findByName", name).then(function (results) {
            return results;
        });
    };

    var _findMethodSecurityByClazz = function (clazzId) {
        return $http.post("/methodSecurities/findByClazz", clazzId).then(function (results) {
            return results;
        });
    };

    var _findMethodSecurityByMethod = function (name) {
        return $http.post("/methodSecurities/findByMethod", methodName).then(function (results) {
            return results;
        });
    };

    methodSecurityServiceFactory.findAllMethodSecurities = _findAllMethodSecurities;
    methodSecurityServiceFactory.findAllMethodSecurities = _findAllMethodSecurities;
    methodSecurityServiceFactory.findMethodSecurityById = _findMethodSecurityById;
    methodSecurityServiceFactory.addMethodSecurity = _addMethodSecurity;
    methodSecurityServiceFactory.updateMethodSecurity = _updateMethodSecurity;
    methodSecurityServiceFactory.deleteMethodSecurity = _deleteMethodSecurity;
    methodSecurityServiceFactory.findMethodSecurityByClazz = _findMethodSecurityByClazz;
    methodSecurityServiceFactory.findMethodSecurityByMethod = _findMethodSecurityByMethod;

    return methodSecurityServiceFactory;
}]);

