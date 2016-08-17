angular
    .module('morpheus.create-employee', [])
    .controller('CreateEmployeeCtrl', function ($scope, $http) {
        var matrixTab = document.getElementById("devMatrix");
        var createEmployeeTab = document.getElementById("createEmployee");
        var createTeamTab = document.getElementById("createTeam");
        var editEmployeeTab = document.getElementById("editEmployee");
        var editTemplateTab = document.getElementById("editTemplates");

        matrixTab.className = "inactive";
        createEmployeeTab.className = "active";
        createTeamTab.className = "inactive";
        editTemplateTab.className = "inactive";
        editEmployeeTab.className = "inactive";

        $http.get('team').success(function(data){
            $scope.teams = data;
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