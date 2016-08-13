angular
    .module('morpheus.edit-teams', [])
    .controller('EditTeamsCtrl', function ($scope, $http) {
        $http.get('team').success(function(data){
            $scope.teams = data;
        });

        dragula([
            document.querySelector('#sonique'),
            document.querySelector('#lando'),
            document.querySelector('#five-oh')
        ]).on('drop', function (el, container) {
            alert(el.innerText + ' moved to ' + container.id);
        });

        var matrixTab = document.getElementById("devMatrix");
        var createEmployeeTab = document.getElementById("createEmployee");
        var createTeamTab = document.getElementById("createTeam");
        var editTab = document.getElementById("editTemplates");
        var editTeamsTab = document.getElementById("editTeams");

        editTeamsTab.className = "active";
        matrixTab.className = "inactive";
        createEmployeeTab.className = "inactive";
        createTeamTab.className = "inactive";
        editTab.className = "inactive";
    });