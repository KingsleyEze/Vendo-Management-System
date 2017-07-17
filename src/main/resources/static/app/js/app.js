/*
 $( document ).ready(function() {
 $(".can_hide").hide();
 $("#usertype").change(function(){
 var selected = $(this).val();
 if(selected == "school_owner"){
 $(".can_hide").show();
 }else{
 $(".can_hide").hide();
 }
 });
 */

var app = angular.module('studylabsApp', ['ngRoute', 'ui.bootstrap','ngFileUpload', 'angularTrix', 'ui-notification']);

app.config(function ($routeProvider, $locationProvider) {
    $routeProvider
            .when("/", {
                templateUrl: "/views/subjects/index.html",
                controller: 'subjectController'
            })
            .when("/subjects-list", {
                templateUrl: "/views/subjects/subjects-list.html",
                controller: 'subjectController'
            })
            .when("/add-subject", {
                templateUrl: "/views/subjects/add-subject.html",
                controller: 'subjectController'
            })
            .when("/view-subject", {
                templateUrl: "/views/subjects/view-subject.html",
                controller: 'subjectController'
            })
            .when("/update-subject", {
                templateUrl: "/views/subjects/update-subject.html",
                controller: 'editController'
            })





            /* Section service Module*/

            .when("/list", {
                templateUrl: "/views/sections/list.html",
                controller: 'sectionController'
            })
            .when("/add-section", {
                templateUrl: "/views/sections/add-section.html",
                controller: 'sectionController'
            })




            .when("/accounts/changeMyPassword", {
                templateUrl: "/accounts/changeMyPassword"

            })


            .when("/view-section", {
                templateUrl: "/views/sections/view-section.html",
                controller: 'sectionController'
            })
            .when("/update-section", {
                templateUrl: "/views/sections/update-section.html",
                controller: 'editSectionController'
            })






            /* Exam Service Module*/

            .when("/exams-list", {
                templateUrl: "/views/exams/exams-list.html",
                controller: 'examController'
            })
            .when("/add-exam", {
                templateUrl: "/views/exams/add-exam.html",
                controller: 'examController'
            })
            .when("/view-exam", {
                templateUrl: "/views/exams/view-exam.html",
                controller: 'examController'
            })
            .when("/update-exam", {
                templateUrl: "/views/exams/update-exam.html",
                controller: 'editExamController'
            })



            /* Topic Service Module*/



            .when("/topics-list", {
                templateUrl: "/views/topics/topics-list.html",
                controller: 'topicController'
            })
            .when("/add-topic", {
                templateUrl: "/views/topics/add-topic.html",
                controller: 'topicController'
            })
            .when("/view-topic", {
                templateUrl: "/views/topics/view-topic.html",
                controller: 'topicController'
            })
            .when("/update-topic", {
                templateUrl: "/views/topics/update-topic.html",
                controller: 'editTopicController'
            })




            /* Skill Service Module*/
            .when("/skills-list", {
                templateUrl: "/views/skills/skills-list.html",
                controller: 'skillController'
            })
            .when("/add-skill", {
                templateUrl: "/views/skills/add-skill.html",
                controller: 'skillController'
            })
            .when("/view-skill", {
                templateUrl: "/views/skills/view-skill.html",
                controller: 'skillController'
            })
            .when("/update-skill", {
                templateUrl: "/views/skills/update-skill.html",
                controller: 'editSkillController'
            })




            /* Publisher Service Module*/
            .when("/publishers-list", {
                templateUrl: "/views/publishers/publishers-list.html",
                controller: 'publisherController'
            })
            .when("/add-publisher", {
                templateUrl: "/views/publishers/add-publisher.html",
                controller: 'publisherController'
            })
            .when("/view-publisher", {
                templateUrl: "/views/publishers/view-publisher.html",
                controller: 'publisherController'
            })
            .when("/update-publisher", {
                templateUrl: "/views/publishers/update-publisher.html",
                controller: 'editPublisherController'
            })



            /* Question Service Module*/
            .when("/questions-list", {
                templateUrl: "/views/questions/questions-list.html",
                controller: 'questionController'
            })
            .when("/add-question", {
                templateUrl: "/views/questions/add-question.html",
                controller: 'questionController'
            })
            .when("/view-question", {
                templateUrl: "/views/questions/view-question.html",
                controller: 'questionController'
            })
            .when("/update-question", {
                templateUrl: "/views/questions/update-question.html",
                controller: 'editQuestionController'
            })

            /* School Module */
            .when("/add-school", {
                templateUrl: "/views/schools/add-school.html",
                controller: "schoolController"
            })



        /*Role Service Module */
        .when("/roles-list", {
            templateUrl: "/views/roles/roles-list.html",
            controller: 'roleController'
        })
        .when("/add-role", {
            templateUrl: "/views/roles/add-role.html",
            controller: 'roleController'
        })
        .when("/view-role", {
            templateUrl: "/views/roles/view-role.html",
            controller: 'roleController'
        })
        .when("/update-role", {
            templateUrl: "/views/roles/update-role.html",
            controller: 'editRoleController'
        })


        /*Permission Service Module */
        .when("/permissions-list", {
            templateUrl: "/views/permissions/permissions-list.html",
            controller: 'permissionController'
        })
        .when("/add-permission", {
            templateUrl: "/views/permissions/add-permission.html",
            controller: 'permissionController'
        })
        .when("/view-permission", {
            templateUrl: "/views/permissions/view-permission.html",
            controller: 'permissionController'
        })
        .when("/update-permission", {
            templateUrl: "/views/permissions/update-permission.html",
            controller: 'editPermissionController'
        })


        /*Class Security Service Module */
        .when("/class-security-list", {
            templateUrl: "/views/classSecurities/class-security-list.html",
            controller: 'classSecurityController'
        })
        .when("/add-class-security", {
            templateUrl: "/views/classSecurities/add-class-security.html",
            controller: 'classSecurityController'
        })
        .when("/view-class-security", {
            templateUrl: "/views/classSecurities/view-class-security.html",
            controller: 'classSecurityController'
        })
        .when("/update-class-security", {
            templateUrl: "/views/classSecurities/update-class-security.html",
            controller: 'editClassSecurityController'
        })


        /*Method Security Service Module */
        .when("/method-security-list", {
            templateUrl: "/views/methodSecurities/method-security-list.html",
            controller: 'methodSecurityController'
        })
        .when("/add-method-security", {
            templateUrl: "/views/methodSecurities/add-method-security.html",
            controller: 'methodSecurityController'
        })
        .when("/view-method-security", {
            templateUrl: "/views/methodSecurities/view-method-security.html",
            controller: 'methodSecurityController'
        })
        .when("/update-method-security", {
            templateUrl: "/views/methodSecurities/update-method-security.html",
            controller: 'editMethodSecurityController'
        })

        .otherwise('/');


    $locationProvider.html5Mode(
            {
                enabled: true,
                requireBase: false
            }
    );
});

//Directive to add jqMath functionality to an html element

app.directive('jqMath', function () {
    return {
        restrict: 'A',
        controller: ["$scope", "$element", "$attrs", function ($scope, $element, $attrs) {
                $scope.$watch($attrs.jqMath, function (value) {
                    jqMath.parseMath(document.body);
                           
                });

            }]

    };
});



