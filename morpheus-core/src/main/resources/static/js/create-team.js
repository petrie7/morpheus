angular
    .module('morpheus.create-team', [])
    .controller('CreateTeamCtrl', function ($scope, $http) {
        $scope.persistTeam = function () {
            $http.post('team/' + $scope.team.name)
                .success(function () {
                    $.growl.notice({message: $scope.team.name + ' successfully created'});
                })
                .error(function (data) {
                    $.growl.error({message: data.message});
                });
        };

        var createTeamTab = document.getElementById("createTeam");
        var matrixTab = document.getElementById("devMatrix");
        var createEmployeeTab = document.getElementById("createEmployee");
        var editTab = document.getElementById("editTemplates");
        var editTeamsTab = document.getElementById("editTeams");

        createTeamTab.className = "active";
        matrixTab.className = "inactive";
        editTab.className = "inactive";
        editTeamsTab.className = "inactive";
        createEmployeeTab.className = "inactive";
    });