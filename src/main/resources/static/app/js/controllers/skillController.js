'use strict';
app.controller("skillController", ['$scope', '$location', 'skillService', 'skillData', 'topicService', 'Notification', function ($scope, $location, skillService, skillData, topicService, Notification) {

        $scope.status;
        findAllSkills();
        findAllTopics();
        $scope.skillData = skillData;

        function findAllSkills() {
            skillService.findAllSkills().then(function (results) {
                if (results.data !== undefined && results.data.length > 0) {
                    $scope.skills = results.data;
                } else {
                    $scope.skills = [];
                }
            },
                    function (error) {
                        $scope.status = 'Unable to load skills data: ' + error.message;
                    });
        }
        ;

        function findAllTopics() {
            topicService.findAllTopics().then(function (results) {
                if (results.data !== undefined && results.data.length > 0) {
                    console.log(results.data);
                    $scope.topics = results.data;
                } else {
                    $scope.topics = [];
                }
            },
                    function (error) {
                        $scope.status = 'Unable to load skills data: ' + error.message;
                    });
        }
        ;

        function findAllTopicsForUpdate() {
            topicService.findAllTopics().then(function (results) {
                if (results.data !== undefined && results.data.length > 0) {
                    $scope.skillData.currSkill.topics = results.data;

                } else {
                    $scope.skillData.currSkill.topics = [];
                }
            },
                    function (error) {
                        $scope.status = 'Unable to load exam data: ' + error.message;
                    });
        }
        ;


        $scope.findSkillById = function (id) {
            skillService.findSkillById(id).then(function (results) {
                $scope.skillData.currSkill = results.data;
                $location.path('/view-skill');
            });
        };

        $scope.updateSkillClicked = function (newskill) {
            $scope.skillData.currSkill = newskill;
            findAllTopicsForUpdate()
            $location.path('/update-skill');
        };

        $scope.addSkill = function (skill) {
            skillService.addSkill(skill).then(function (results) {
                skill.name =null;
                findAllSkills();
                //$location.path('/skills-list');
                Notification.success({message: 'Skill added successfully', delay: 5000});
            },
                    function (error) {
                        $scope.status = 'Unable to add skill: ' + error.message;
                    });
        };

        $scope.deleteSkill = function (id) {
            var c = confirm("Are you sure?");
            if (c === true) {
                skillService.deleteSkill(id).then(function (results) {
                    findAllSkills();
                    Notification.success({message: 'Skill deleted successfully', delay: 5000});
                }, function (error) {
                    $scope.status = 'Unable to delete skill: ' + error.message;
                });
            } else {
                return;
            }
        };


    }]);

app.controller("editSkillController", ['$scope', '$location', 'skillService', 'skillData', 'Notification', function ($scope, $location, skillService, skillData, Notification) {

        $scope.skillData = skillData;

        $scope.updateSkill = function (updatedSkill) {
            skillService.updateSkill(updatedSkill).then(function (results) {
                $location.path('/add-skill');
                Notification.success({message: 'Skill update successfull', delay: 5000});
            },
                    function (error) {
                        $scope.status = 'Unable to update skill: ' + error.message;
                    });
        };
    }]);



