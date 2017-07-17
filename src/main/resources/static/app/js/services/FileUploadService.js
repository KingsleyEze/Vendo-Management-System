'use strict';
app.factory("FileUploadData", function () {
    return {};
});
app.factory('FileUploadService', ['$http', function ($http) {
   // $http.defaults.headers.post["Content-Type"] = "application/json";
    var FileUploadServiceFactory = {};


    var _uploadFileToUrl = function(file, uploadUrl){
        var fd = new FormData();
        fd.append('file', file);

        $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        })

            .success(function(){
            })

            .error(function(){
            });
    }
    FileUploadServiceFactory.uploadFileToUrl = _uploadFileToUrl;

    return FileUploadServiceFactory;


}]);