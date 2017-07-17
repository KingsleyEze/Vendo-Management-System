'use strict';
app.factory("skillData", function () {
    return {};
});
app.factory('skillService', ['$http', function ($http) {

        $http.defaults.headers.post["Content-Type"] = "application/json";
        var skillServiceFactory = {};

        var _findAllSkills = function () {
            return $http.get("/skills/all").then(function (results) {
                return results;
            });
        };

        var _findAllTopics = function () {
            return $http.get("/topics/all").then(function (results) {
                return results;
            });
        };

        var _findSkillById = function (id) {
            return $http.get("/skills/findById/" + id).then(function (results) {
                return results;
            });
        };

        var _addSkill = function (newskill) {
            return $http.post("/skills/add", newskill).then(function (results) {
                console.log(newskill);
                return results;
            });
        };

        var _updateSkill = function (skill) {
            return $http.put("/skills/update/", skill).then(function (results) {

                return results;
            });
        };

        var _deleteSkill = function (id) {
            return $http.delete("/skills/delete/" + id).then(function (results) {
                return results;
            });
        };

        var _findSkillByName = function (name) {
            return $http.post("/skills/findByName", name).then(function (results) {
                return results;
            });
        };

        var _findSkillsByTopic = function (topicId) {
            return $http.get("/skills/getSkillsByTopic/"+topicId).then(function (results) {
                return results;
            });
        };

        skillServiceFactory.findAllSkills = _findAllSkills;
        skillServiceFactory.findAllTopics = _findAllTopics;
        skillServiceFactory.findSkillById = _findSkillById;
        skillServiceFactory.addSkill = _addSkill;
        skillServiceFactory.updateSkill = _updateSkill;
        skillServiceFactory.deleteSkill = _deleteSkill;
        skillServiceFactory.findSkillByName = _findSkillByName;
        skillServiceFactory.findSkillsByTopic = _findSkillsByTopic;

        return skillServiceFactory;
    }]);





