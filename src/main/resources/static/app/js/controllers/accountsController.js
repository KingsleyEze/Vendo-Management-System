/**
 * Created by abdulhakim on 11/9/16.
 */

'use strict';
app.controller("accountsController", ['$scope',   function ($scope ) {


    $scope.logout=  function logout() {
console.log("Logging out of Studylabcentral");
        localStorage.clearAll();
          // $window.location.href="/logout";
        window.location = '/logout';
    };

    $scope.accounts=  function accounts() {
        console.log("Redirecting to Dashboard");


        window.location = '/accounts';
    };


}]);

