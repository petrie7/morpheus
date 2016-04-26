angular
    .module('morpheusApp', [])
    .controller('EmployeeCtrl', function ($scope, $http) {

        $scope.skillsTemplate;

        watchEmployeeController();
        getSkillsTemplateAndEmployee();
        retrieveAllEmployees();

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
                        $("#skills-matrix").show();
                        $("#save-button").show();
                        $("#save-employee").hide();
            });
            var username = document.getElementById('q').value;
            $scope.employee = $.grep($scope.employees, function(e){
                return e.username == username;
            })[0];
        };

        $scope.persistSkills = function() {
            $http.post('employee', $scope.employee)
                .success(function (data, status, headers, config) {
                    $("#update-success").fadeIn().delay(5000).fadeOut();
                });
        };

        $scope.persistEmployee = function() {
            $http.post('employee/create', $scope.employee)
                .success(function (data, status, headers, config) {
                    $("#create-success").fadeIn().delay(5000).fadeOut();
                });
        }

        $scope.updateSkill = function(property, value) {
            $scope.employee.skills.find(function (skill) {
                return skill.description === property;
            }).value = value;
        };

        $scope.createEmployee = function() {
            $("#createEmployeeForm").show();
            $("#search-bar").hide();
            $("#skills-matrix").show();
            $("#save-button").hide();
            $("#save-employee").show();

            $scope.skillsTemplate = [];

            $scope.template.forEach(function(skill){
                $scope.skillsTemplate.push({
                description: skill,
                value: 0
                })
            })

            $scope.employee.skills = $scope.skillsTemplate;
        }

        $scope.reinitializeMatrix = function() {
            $("#createEmployeeForm").hide();
            $("#search-bar").show();
            $("#skills-matrix").hide();
            $("#save-button").hide();
            $("#save-employee").hide();

            watchEmployeeController();
            getSkillsTemplateAndEmployee();

            retrieveAllEmployees();
        }

        function watchEmployeeController() {
        $scope.$watch('employee', function(newEmployee) {
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
        }

        function getSkillsTemplateAndEmployee() {
          $http.get('template')
          .success(function (data, status, headers, config) {
            $scope.template = data;
          $http.get('employee')
          .success(function (data, status, headers, config) {
            $scope.employee = data;
          });
        });
        }

        function extractEmployeeSkillValues(newEmployee, skill) {
        if (newEmployee.skills) {
            var result = $.grep(newEmployee.skills,
                function(employeeSkill){
                    return employeeSkill.description == skill;
                })
        }

        if(typeof result !== 'undefined' && result.length > 0) {
            return result[0].value
        }
            return 0;
        }

        function retrieveAllEmployees() {
          $http.get('employee/all')
          .success(function (data, status, headers, config) {
            $scope.employees = data;
            $( '#q' ).autocomplete({
              source: $scope.employees
            });
          });
        }
    });