'use strict';
app.factory("questionData", function () {
    return {};
});
app.factory('questionService', ['$http', function ($http) {

        $http.defaults.headers.post["Content-Type"] = "application/json";
        var questionServiceFactory = {};

        var _findAllQuestions = function () {
            return $http.get("/questions/all").then(function (results) {
                return results;
            });
        };

        var _findAllPublishers = function () {
            return $http.get("/publishers/all").then(function (results) {
                return results;
            });
        };

        var _findAllTopics = function () {
            return $http.get("/topics/all").then(function (results) {
                return results;
            });
        };

        var _findAllSkills = function () {
            return $http.get("/skills/all").then(function (results) {
                return results;
            });
        };
        
        var _findAllSubjects = function () {
            return $http.get("/subjects/all").then(function (results) {
                return results;
            });
        };
        
        var _findAllSections = function () {
            return $http.get("/sections/all").then(function (results) {
                return results;
            });
        };

        var _getSubjectSections= function(subjectId){
            return $http.get("/sections/getSectionsBySubject/" + subjectId ).then(function (results) {
                //console.log(results);
                return results;
            });
        };

    var _getSectionTopics= function(sectionId){
        return $http.get("/topics/getTopicsBySection/" + sectionId ).then(function (results) {
            return results;
        });
    };


    var _getTopicSkills= function(topicId){
        return $http.get("/skills/getSkillsByTopic/" + topicId ).then(function (results) {
            return results;
        });
    };


    var _findQuestionById = function (id) {
            return $http.get("/questions/findById/" + id).then(function (results) {
                return results;
            });
        };

        var _addQuestion = function (newquestion) {
            return $http.post("/questions/add", newquestion).then(function (results) {
                return results;
            });
        };

        var _updateQuestion = function (question) {
            return $http.put("/questions/update/", question).then(function (results) {

                return results;
            });
        };

        var _deleteQuestion = function (id) {
            return $http.delete("/questions/delete/" + id).then(function (results) {
                return results;
            });
        };

        var _findQuestionByName = function (name) {
            return $http.post("/questions/findByName", name).then(function (results) {
                return results;
            });
        };

        var _findQuestionsBySubjectId = function (subjectId) {
             return $http.get("/questions/findAllBySubjectId/"+subjectId).then(function (results) {
                 return results;
             });
        };

        var _findQuestionBySkillIdAndLevelId = function (skillId,levelId) {
                    return $http.get("/questions/findBySkillIdAndLevelId/"+skillId+"/"+levelId).then(function (results) {
                        return results;
                    });
        };

        questionServiceFactory.findAllQuestions = _findAllQuestions;
        questionServiceFactory.findAllPublishers = _findAllPublishers;
        questionServiceFactory.findAllTopics = _findAllTopics;
        questionServiceFactory.findAllSkills = _findAllSkills;
        questionServiceFactory.findAllSubjects = _findAllSubjects;
        questionServiceFactory.findAllSections = _findAllSections;
        questionServiceFactory.findQuestionById = _findQuestionById;
        questionServiceFactory.addQuestion = _addQuestion;
        questionServiceFactory.updateQuestion = _updateQuestion;
        questionServiceFactory.deleteQuestion = _deleteQuestion;
        questionServiceFactory.findQuestionByName = _findQuestionByName;
        questionServiceFactory.findQuestions =_findQuestionBySkillIdAndLevelId;
        questionServiceFactory.findQuestionsBySubjectId = _findQuestionsBySubjectId;


    questionServiceFactory.getSubjectSections = function(subjectId){
       // var sections = ($filter('filter')(statelist, {countryId: countryId}));
        var sections =  _getSubjectSections(subjectId);
        return sections;
    };


    questionServiceFactory.getSectionTopics = function(sectionId){
        //var items = ($filter('filter')(citylist, {stateId: stateId}));
        console.log("The section ID given is " + sectionId);
        var topics = _getSectionTopics(sectionId);
        console.log(topics);
        return topics;
    };
    questionServiceFactory.getTopicSkills = function(topicId){

        console.log("The topic ID given is " + topicId);
        var skills = _getTopicSkills(topicId);
        console.log(skills);
        return skills;
    };
        return questionServiceFactory;
    }]);

