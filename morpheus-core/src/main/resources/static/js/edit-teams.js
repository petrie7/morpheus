angular
    .module('morpheus.edit-teams', [])
    .controller('EditTeamsCtrl', function ($scope, $http) {
        dragula([
            document.querySelector('#sonique'),
            document.querySelector('#lando'),
            document.querySelector('#five-oh')
        ]).on('drop', function (el, container) {
            alert(el.innerText + ' moved to ' + container.id);
        });

        var matrixTab = document.getElementById("devMatrix");
        var createTab = document.getElementById("createEmployee");
        var editTab = document.getElementById("editTemplates");
        var editTeamsTab = document.getElementById("editTeams");

        editTeamsTab.className = "active";
        matrixTab.className = "inactive";
        createTab.className = "inactive";
        editTab.className = "inactive";
    });