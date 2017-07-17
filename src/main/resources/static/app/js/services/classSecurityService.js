'use strict';
app.factory("classSecurityData", function () {
    return {};
});
app.factory('classSecurityService', ['$http', function ($http) {

        $http.defaults.headers.post["Content-Type"] = "application/json";
        var classSecurityServiceFactory = {};

        var _findAllClassSecurities = function () {
            return $http.get("/classSecurities/all").then(function (results) {
                return results;
            });
        };
        

        var _findClassSecurityById = function (id) {
            return $http.get("/classSecurities/findById/" + id).then(function (results) {
                return results;
            });
        };

        var _addClassSecurity = function (newclassSecuritie) {
            return $http.post("/classSecurities/add", newclassSecuritie).then(function (results) {
                return results;
            });
        };

        var _updateClassSecurity = function (classSecurity) {
            return $http.put("/classSecurities/update/", classSecurity).then(function (results) {
                return results;
            });
        };

        var _deleteClassSecurity = function (id) {
            return $http.delete("/classSecurities/delete/" + id).then(function (results) {
                return results;
            });
        };

        var _findClassSecurityByName = function (name) {
            return $http.post("/classSecurities/findByName", name).then(function (results) {
                return results;
            });
        };

        classSecurityServiceFactory.findAllClassSecurities = _findAllClassSecurities;
        classSecurityServiceFactory.findAllClassSecurities = _findAllClassSecurities;
        classSecurityServiceFactory.findClassSecurityById = _findClassSecurityById;
        classSecurityServiceFactory.addClassSecurity = _addClassSecurity;
        classSecurityServiceFactory.updateClassSecurity = _updateClassSecurity;
        classSecurityServiceFactory.deleteClassSecurity = _deleteClassSecurity;
        classSecurityServiceFactory.findClassSecurityByName = _findClassSecurityByName;

        return classSecurityServiceFactory;
    }]);

