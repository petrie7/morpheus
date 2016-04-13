angular
    .module('morpheusApp', [])
    .controller('EmployeeCtrl', function ($scope, $http) {
        $http.get('employee')
        .success(function (data, status, headers, config) {
            $scope.employee = data;
        });

        $scope.range = function(min, max, step) {
            step = step || 1;
            var input = [];
            for (var i = min; i <= max; i += step) {
                input.push(i);
            }
            return input;
        };

        $scope.performSearch = function() {
            $http.get('employee/'+ document.getElementById("q").value)
                    .success(function (data, status, headers, config) {
                        $scope.employee = data;
            });
            var username = document.getElementById("q").value;
            $scope.employee = $.grep($scope.employees, function(e){
                return e.username == username;
            })[0];
        };

        $http.get('employee/all')
            .success(function (data, status, headers, config) {
                $scope.employees = data;
                $( "#q" ).autocomplete({
                    source: $scope.employees
                });
        });
    });