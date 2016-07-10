angular
    .module('morpheus.edit', [])
    .controller('EditTemplateCtrl', function ($scope, $http) {
        var matrixTab = document.getElementById("devMatrix");
        var createTab = document.getElementById("createEmployee");
        var editTab = document.getElementById("editTemplates");

        editTab.className = "active";
        matrixTab.className = "inactive";
        createTab.className = "inactive";

        $http.get('template')
            .success(function (data) {
                $scope.templates = data;
                $scope.originalTemplates = angular.copy(data);
            });

        $scope.persistTemplate = function() {
            $http.post('template', $scope.templates);
        };

        $scope.isDirty = function () {
            return angular.equals($scope.templates, $scope.originalTemplates);
        };
    });