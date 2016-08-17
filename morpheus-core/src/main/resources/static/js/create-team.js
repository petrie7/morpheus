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

        var matrixTab = document.getElementById("devMatrix");
        var createEmployeeTab = document.getElementById("createEmployee");
        var createTeamTab = document.getElementById("createTeam");
        var editTemplateTab = document.getElementById("editTemplates");

        matrixTab.className = "inactive";
        createEmployeeTab.className = "inactive";
        createTeamTab.className = "active";
        editTemplateTab.className = "inactive";
    });