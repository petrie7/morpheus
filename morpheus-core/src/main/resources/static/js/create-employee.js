angular
    .module('morpheus.create-employee', [])
    .controller('CreateEmployeeCtrl', function ($scope, $http) {
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

        var matrixTab = document.getElementById("devMatrix");
        var createEmployeeTab = document.getElementById("createEmployee");
        var createTeamTab = document.getElementById("createTeam");
        var editTab = document.getElementById("editTemplates");
        var editTeamsTab = document.getElementById("editTeams");

        createEmployeeTab.className = "active";
        matrixTab.className = "inactive";
        editTab.className = "inactive";
        editTeamsTab.className = "inactive";
        createTeamTab.className = "inactive";
    });