angular
.module('morpheus.create', [])
.controller('CreateEmployeeCtrl', function($scope, $http){
    $scope.persistEmployee = function() {
            $http.post('employee/create', $scope.employee)
                .success(function (data, status, headers, config) {
                    $("#create-success").fadeIn().delay(5000).fadeOut();
                })
                .error(function () {
                    alert('User does not exist in Cauth');
                });
    };
});