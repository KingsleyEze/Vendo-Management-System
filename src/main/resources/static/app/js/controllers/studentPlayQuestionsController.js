app.controller("studentPlayQuestionsController", ['$scope', '$location','$routeParams', 'subjectService', 'sectionService', 'topicService','questionService', 'skillService' ,function($scope, $location, $routeParams,subjectService, sectionService, topicService,questionService, skillService) {
        console.log("Student Play Questions controller"+$routeParams.subjectId);

        fetchQuestionsBySubject($routeParams.subjectId);


          function fetchQuestionsBySubject(subjectId){

                      console.log("fetchQuestionsBySubject subject id:"+subjectId);
                      questionService.findQuestionsBySubjectId(subjectId).then(function(results){
                           if (results.data != undefined && results.data) {
                               $scope.questions = results.data;
                               $scope.question = results.data[0];
                           } else {
                               $scope.questions = [];
                           }
                      }, function(error){
                          console.log('Error : '+error);
                          });
          }


       $scope.selectedQuestion = function(questionId){
               console.log("question Id : "+questionId);
                angular.forEach($scope.questions, function(question){
                 if(question.id == questionId){
                     $scope.question = question;

                 }
                });


       }

}]);