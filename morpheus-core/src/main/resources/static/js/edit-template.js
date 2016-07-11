angular
    .module('morpheus.edit', [])
    .controller('EditTemplateCtrl', function ($scope, $http) {
        $scope.templates = [];
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
    });