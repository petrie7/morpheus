angular
    .module('morpheus.edit', [])
    .controller('EditTemplateCtrl', function ($scope, $http) {
        $scope.templates = [];
        var matrixTab = document.getElementById("devMatrix");
        var createTab = document.getElementById("createEmployee");
        var editTab = document.getElementById("editTemplates");
        var editTeamsTab = document.getElementById("editTeams");

        editTab.className = "active";
        matrixTab.className = "inactive";
        createTab.className = "inactive";
        editTeamsTab.className = "inactive";

        $http.get('template')
            .success(function (data) {
                $scope.templates = data;
                $scope.originalTemplates = angular.copy(data);
                $scope.selectedTemplateForNewField = $scope.templates[0].templateName;
            });

        $scope.persistTemplate = function () {
            $http.post('template', $scope.templates)
                .success(function(){
                    $.growl.notice({message: 'Template successfully saved'});
                    $scope.originalTemplates = angular.copy($scope.templates);
                });
        };

        $scope.isDirty = function () {
            return angular.equals($scope.templates, $scope.originalTemplates);
        };

        $scope.deleteField = function (template, field) {
            bootbox.confirm("Are you sure you want to delete " + field.fieldName + "?", function (result) {
                if (result === true) {
                    var selectedTemplate = $scope.templates.find(function (t) {
                        return t.templateName == template.templateName;
                    });
                    selectedTemplate.fields.remove(function (f) {
                        return f.fieldName == field.fieldName;
                    });
                    $scope.$apply();
                }
            });
        }

        $scope.addNewField = function () {
            var selectedTemplate = $scope.templates.find(function (t) {
                return t.templateName == $scope.selectedTemplateForNewField;
            });

            selectedTemplate.fields.push({
            fieldName: $scope.newField,
            fieldLevelDescription: [
                {level : "JuniorDeveloper", description : ""},
                {level : "MidDeveloper", description : ""},
                {level : "SeniorDeveloper", description : ""}
            ]
            });
            $scope.newField = null;
        }

        $scope.addNewTemplate = function () {
            $scope.templates.push({
                templateName: $scope.newTemplate,
                templateClass: $scope.newTemplate.toLowerCase().replace(" ", "-"),
                fields: []
            });
            $scope.newTemplate = null;

        }
    });