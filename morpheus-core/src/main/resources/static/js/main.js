angular
    .module('morpheusApp', [])
    .controller('EmployeeCtrl', function ($scope, $http) {

        $('#updateCommentDialog').hide();
        $scope.skillsTemplate;

        watchEmployeeController();
        getSkillsTemplateAndEmployee();
        retrieveAllEmployees();

        $scope.range = function (min, max, step) {
            step = step || 1;
            var input = [];
            for (var i = min; i <= max; i += step) {
                input.push(i);
            }
            return input;
        };

        $scope.performSearch = function () {
            $http.get('employee/' + document.getElementById('q').value)
                .success(function (data, status, headers, config) {
                    $scope.employee = data[0];
                    $("#skills-matrix").show();
                    $("#save-button").show();
                    $("#timemachine").show();
                    var date = new Date($scope.employee.lastUpdateDate);
                    var snapSlider = document.getElementById('slider-snap');
                    debugger;
                    noUiSlider.create(snapSlider, {
                        start: date.getTime(),
                        snap: true,
                        range: {
                            'min': 0,
                            'max': date.getTime()
                        }
                    });
                    snapSlider.noUiSlider.on('update', function( vals, handle ) {
                        var date = new Date(0);
                        date.setUTCMilliseconds(vals[handle]);
                        $('#date').html(date);
                    });
                    $("#save-employee").hide();
                });
            var username = document.getElementById('q').value;
            $scope.employee = $.grep($scope.employees, function (e) {
                return e == username;
            })[0];

        };

        $scope.persistSkills = function () {
            $http.post('employee', $scope.employee)
                .success(function (data, status, headers, config) {
                    $("#update-success").fadeIn().delay(5000).fadeOut();
                });
        };

        $scope.persistEmployee = function () {
            $http.post('employee/create', $scope.employee)
                .success(function (data, status, headers, config) {
                    $("#create-success").fadeIn().delay(5000).fadeOut();
                });
        };

        $scope.updateSkill = function (property, value) {
            $scope.employee.skills.find(function (skill) {
                return skill.description === property;
            }).value = value;
        };

        $scope.createEmployee = function () {
            $("#createEmployeeForm").show();
            $("#search-bar").hide();
            $("#skills-matrix").show();
            $("#save-button").hide();
            $("#save-employee").show();

            $scope.skillsTemplate = [];

            resetEmployeeData();

            $scope.template.forEach(function (skill) {
                $scope.skillsTemplate.push({
                    description: skill,
                    value: 0
                })
            });

            $scope.employee.skills = $scope.skillsTemplate;

            setActiveTab('createEmployee');
        };

        $scope.reinitializeMatrix = function () {
            $("#createEmployeeForm").hide();
            $("#search-bar").show();
            $("#save-button").hide();
            $("#save-employee").hide();

            $scope.skillsTemplate = [];

            watchEmployeeController();
            getSkillsTemplateAndEmployee();

            retrieveAllEmployees();
            setActiveTab("devMatrix");
        };

        $scope.updateCommentDialog = function (skill) {
            $('#commentTextArea').val(skill.comment);
            $("#commentsBox").dialog({
                resizable: false,
                height: 300,
                width: 300,
                modal: true,
                buttons: {
                    Confirm: function () {
                        skill.comment = $('#commentTextArea').val();
                        $scope.$apply();
                        $(this).dialog("close");
                    },
                    Cancel: function () {
                        $(this).dialog("close");
                    }
                }
            });
        };

        function watchEmployeeController() {
            $scope.$watch('employee', function (newEmployee) {
                if (newEmployee) {
                    $scope.skillsTemplate = [];
                    $scope.template.forEach(function (skill) {
                        $scope.skillsTemplate.push({
                            description: skill,
                            value: extractEmployeeSkillValues(newEmployee, skill),
                            comment: extractEmployeeSkillComments(newEmployee, skill)
                        })
                    });
                    $scope.employee.skills = $scope.skillsTemplate;
                }
            });
        }

        function getSkillsTemplateAndEmployee() {
            $http.get('template')
                .success(function (data, status, headers, config) {
                    $scope.template = data;
                    $http.get('employee')
                        .success(function (data, status, headers, config) {
                            $scope.employee = data[0];
                        });
                });
        }

        function extractEmployeeSkillValues(newEmployee, skill) {
            if (newEmployee.skills) {
                var result = $.grep(newEmployee.skills,
                    function (employeeSkill) {
                        return employeeSkill.description == skill;
                    })
            }

            if (typeof result !== 'undefined' && result.length > 0) {
                return result[0].value
            }
            return 0;
        }

        function extractEmployeeSkillComments(newEmployee, skill) {
            if (newEmployee.skills) {
                var result = $.grep(newEmployee.skills,
                    function (employeeSkill) {
                        return employeeSkill.description == skill;
                    })
            }

            if (typeof result !== 'undefined' && result.length > 0) {
                return result[0].comment
            }
            return "";
        }

        function retrieveAllEmployees() {
            $http.get('employee/all')
                .success(function (data, status, headers, config) {
                    $scope.employees = data;
                    $('#q').autocomplete({
                        source: $scope.employees
                    });
                });
        }

        function setActiveTab(tabToBeActivated) {
            var matrixTab = document.getElementById("devMatrix");
            var createTab = document.getElementById("createEmployee");
            if (tabToBeActivated === 'createEmployee') {
                matrixTab.className = "inactive";
                createTab.className = "active";
            }

            else {
                matrixTab.className = "active";
                createTab.className = "inactive";
            }
        }

        function resetEmployeeData() {
            $scope.employee.username = null;
            $scope.employee.role = null;
            $scope.employee.level = null;
        }
    });