angular
    .module('morpheus.edit-employee', [])
    .controller('EditEmployeeCtrl', function ($scope, $http) {

        var matrixTab = document.getElementById("devMatrix");
        var createEmployeeTab = document.getElementById("createEmployee");
        var createTeamTab = document.getElementById("createTeam");
        var editEmployeeTab = document.getElementById("editEmployee");
        var editTemplateTab = document.getElementById("editTemplates");

        matrixTab.className = "inactive";
        createEmployeeTab.className = "inactive";
        createTeamTab.className = "inactive";
        editEmployeeTab.className = "active";
        editTemplateTab.className = "inactive";
    });