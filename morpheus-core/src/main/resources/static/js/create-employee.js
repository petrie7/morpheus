angular
    .module('morpheus.create-employee', [])
    .controller('CreateEmployeeCtrl', function ($scope, $http) {
        $scope.employee = {
            level: null
        };

        $http.get('team').success(function(data){
            $scope.teams = data;
        });

        $http.get('employee/levels')
            .success(function(data) {
                $scope.levels = data;
                $scope.employee.level = $scope.levels[0];
        });

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