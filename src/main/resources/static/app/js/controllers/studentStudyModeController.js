app.controller("studentStudyModeController", ['$scope', '$location','$sce', 'subjectService', 'sectionService', 'topicService','questionService', 'skillService' ,function($scope, $location, $sce,subjectService, sectionService, topicService,questionService, skillService) {
        console.log("Student Study Mode controller");
        findAllSubjects();

        $scope.questionCriteria = {};

        function findAllSubjects() {
            $scope.showDifficultySection=false;
            $scope.showSectionSection=false;
            $scope.showSkillLevelSection=false;
            subjectService.findAllSubjects().then(function(results){
                if (results.data != undefined && results.data.length > 0) {
                     $scope.subjects = results.data;
                } else {
                     $scope.subjects = [];
                }

            }, function(error){

            });
        }

        $scope.subjectSelected = function(subjectId){
            $scope.subjectId = subjectId;
            $scope.showDifficultySection=false;
            $scope.showSkillLevelSection=false;
            $scope.showSectionSection=true;
            sectionService.findSectionsBySubjectId(subjectId).then(function(results){
                if (results.data != undefined && results.data.length > 0) {
                   var sections = results.data;
                   $scope.topics = {};
                   //$scope.sections = results.data;
                   $scope.sections = sections;

                   angular.forEach(sections, function(section){
                          topicService.findTopicsBySection(section.id).then(function(results){
                                $scope.topics[section.id] = results.data;
                          });
                   });
                   $scope.sections = sections;
                   $scope.questionCriteria = {"subjectId":subjectId};
                   $scope.fetchQuestionsByCriteria();
                } else {
                   $scope.sections = [];
                }
            }, function(error){
            });
        }

        $scope.topicSelected = function(topicId){
            $scope.questionCriteria = {"subjectId":$scope.subjectId, "topicId":topicId};
            $scope.topicId = topicId;
            $scope.showDifficultySection=false;
            $scope.showSkillLevelSection=true;
            skillService.findSkillsByTopic(topicId).then(function(results){
                if (results.data != undefined && results.data) {
                   $scope.skills = results.data;
                } else {
                   $scope.skills = [];
                }
             }, function(error){

             });
         }

         $scope.skillSelected = function(skillId){
             console.log("skill selected as "+skillId);
             $scope.showDifficultySection=true;
             $scope.skillId=skillId;

          }
          function fetchQuestionsBySubject(subjectId){
              console.log("fetchQuestionsBySubject subject id:"+subjectId);
              questionService.findQuestionsBySubjectId(subjectId).then(function(results){
                   if (results.data != undefined && results.data) {
                       $scope.questions = results.data;
                   } else {
                       $scope.questions = [];
                   }
              }, function(error){
                  console.log('Error : '+error);
              });
          }

         $scope.fetchQuestions = function(difficultyLevel){
             $scope.showDifficultySection=true;
             questionService.findQuestions($scope.skillId,difficultyLevel).then(function(results){
                  if (results.data != undefined && results.data) {
                      $scope.questions = results.data;
                  } else {
                      $scope.questions = [];
                  }
             }, function(error){
                 console.log('Error : '+error);
                 });
         }

         $scope.fetchQuestionsByCriteria = function(){
            console.log("Criteria ");
            console.log($scope.questionCriteria);
            if($scope.questionCriteria.subjectId){
                if($scope.questionCriteria.topicId){
                    console.log("Fetch questions by topic id");
                } else {
                    console.log("Fetch questions by subject id");
                    fetchQuestionsBySubject($scope.questionCriteria.subjectId);
                }
            }
         }

         $scope.sanitizeQuestion = function(questionText){
            var decodedText = unescapeHTML(questionText);
            return decodedText;
         }

         var escape = document.createElement('textarea');
         function escapeHTML(html) {
            escape.textContent = html;
            return escape.innerHTML;
         }

         function unescapeHTML(html) {
            escape.innerHTML = html;
            return escape.textContent;
         }


}]);

