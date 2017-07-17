/**
 * Created by Samuel.Iheadindu on 19/11/2016.
 */
app.controller("schoolController", ['$scope', '$location', 'schoolService', function($scope, $location, schoolService){

    $scope.school = {};

    $scope.addSchool = function(school){
        schoolService.addSchool(school).then(function(result){
            $location.path("/school");
        }, function(error){
            $scope.status = "Unable to add school";
        });
    }

    $scope.findAll = function(){
        //
    }

    $scope.findSchool = function(school){
        //
    }

    $scope.editSchool = function(school){
        //
    }

    $scope.deleteSchool = function(school){
        //
    }

    //end of controller
}]);