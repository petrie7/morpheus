angular
    .module('morpheusApp', [])
    .controller('EmployeeCtrl', function ($scope, $http) {

        $scope.skillsTemplate;

        $scope.$watch('employee', function(newEmployee){
            if(newEmployee) {
                $scope.skillsTemplate = [];
                $scope.template.forEach(function(skill){
                $scope.skillsTemplate.push({
                    description: skill,
                    value: extractEmployeeSkillValues(newEmployee, skill)
                })

            })
                $scope.employee.skills = $scope.skillsTemplate;
            };
        });


        extractEmployeeSkillValues = function(newEmployee, skill) {
            var result = $.grep(newEmployee.skills,
                function(employeeSkill){
                    return employeeSkill.description == skill;
                })

                if(result.length > 0) {
                    return result[0].value
                }

                return 0;
        }


        $http.get('template')
        .success(function (data, status, headers, config) {
            $scope.template = data;
             $http.get('employee')
             .success(function (data, status, headers, config) {
                $scope.employee = data;
             });
        });

        $scope.range = function(min, max, step) {
            step = step || 1;
            var input = [];
            for (var i = min; i <= max; i += step) {
                input.push(i);
            }
            return input;
        };

        $scope.performSearch = function() {
            $http.get('employee/'+ document.getElementById('q').value)
                    .success(function (data, status, headers, config) {
                        $scope.employee = data;
            });
            var username = document.getElementById('q').value;
            $scope.employee = $.grep($scope.employees, function(e){
                return e.username == username;
            })[0];
        };

        $http.get('employee/all')
            .success(function (data, status, headers, config) {
                $scope.employees = data;
                $( '#q' ).autocomplete({
                    source: $scope.employees
                });
        });

        $scope.persistSkills = function() {
            $http.post('employee', $scope.employee)
                .success(function (data, status, headers, config) {
                    $("#success").fadeIn().delay(5000).fadeOut();
                });
        };

        $scope.updateSkill = function(property, value) {
            $scope.employee.skills.find(function (skill) {
                return skill.description === property;
            }).value = value;
        };
    });