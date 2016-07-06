angular
    .module('morpheus.create', [])
    .controller('CreateEmployeeCtrl', function ($scope, $http) {
        $scope.persistEmployee = function () {
            $http.post('employee/create', $scope.employee)
                .success(function (data, status, headers, config) {
                    $.growl.notice({message: $scope.employee.username + ' successfully created'});
                })
                .error(function (data) {
                    $.growl.error({message: data.message});
                });
        };
    });