angular
    .module('morpheus.view', [])
    .controller('EmployeeCtrl', function ($scope, $http) {

            var matrixTab = document.getElementById("devMatrix");
            matrixTab.className = "active";

            $http.get('authenticated')
                .success(function (data) {
                    $scope.authenticatedUser = data;

                    if ($scope.isManager()) {
                        var createTab = document.getElementById("createEmployee");
                        var editTab = document.getElementById("editTemplates");
                        createTab.className = "inactive";
                        editTab.className = "inactive";
                    }
                });

            $scope.editable = true;
            $('#updateCommentDialog').hide();

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
                        $scope.employeeRecords = data;
                        $scope.employee = data[0];
                        $("#skills-matrix").show();
                        $("#save-button").show();
                        $("#save-employee").hide();
                    })
                    .error(function (data) {
                        $.growl.error({message: data.message});
                    });
                var username = document.getElementById('q').value;
                $scope.employee = $.grep($scope.employees, function (e) {
                    return e == username;
                })[0];

                $scope.$apply();

            };

            $scope.persistSkills = function () {
                $http.post('employee', $scope.employee)
                    .success(function (data, status, headers, config) {
                        $scope.originalEmployee = angular.copy($scope.employee);
                        $.growl.notice({message: $scope.employee.username + ' successfully updated'});
                        $scope.performSearch();
                    });
            };

            $scope.updateSkill = function (property, value) {
                $scope.employee.skills.find(function (skill) {
                    return skill.description === property;
                }).value = value;
            };

            $scope.managerCanSee = function (property, value) {
                return !$scope.editable && !(property === value);
            };

            $scope.reinitializeMatrix = function () {
                $("#createEmployeeForm").hide();
                $("#search-bar").show();
                $("#save-button").hide();
                $("#save-employee").hide();

                getSkillsTemplateAndEmployee();

                retrieveAllEmployees();
                setActiveTab("devMatrix");
            };

            $scope.isDirty = function () {
                return angular.equals($scope.employee, $scope.originalEmployee);
            };

            $scope.descriptionFor = function (skill, levelNumber) {
                var level = $scope.levelFor(levelNumber);
                var thisSkill;
                $scope.templates.forEach(function (template) {
                    template.fields.filter(function (field) {
                        if (field.fieldName === skill) {
                            thisSkill = field;
                        }
                    })
                });
                var fieldLevel = thisSkill.fieldLevelDescription.filter(function (fieldLevelDescription) {
                    return fieldLevelDescription.level === level;
                });
                return fieldLevel[0].description;
            };

            $scope.levelFor = function (number) {
                if (number < 3) {
                    return 'JuniorDeveloper'
                }
                if (number < 6) {
                    return 'MidDeveloper';
                }
                return 'SeniorDeveloper';
            };

            $scope.updateCommentDialog = function (skill) {
                $('#commentTextArea').val(skill.comment);
                if ($scope.isManager() && $scope.isEditable()) {
                    $("#commentsBox").dialog({
                        resizable: false,
                        height: 300,
                        width: 300,
                        modal: true,
                        buttons: {
                            Confirm: function () {
                                var employeeSkill = $scope.employee.skills.filter(function (s) {
                                    return s.description === skill.fieldName;
                                })[0];
                                employeeSkill.comment = $('#commentTextArea').val();
                                skill.comment = $('#commentTextArea').val();
                                $scope.$apply();
                                $(this).dialog("close");
                            },
                            Cancel: function () {
                                $(this).dialog("close");
                            }
                        }
                    });
                }
                else {
                    $("#commentsBox").dialog({
                        resizable: false,
                        height: 300,
                        width: 300,
                        modal: true,
                        readonly: true,
                        buttons: {
                            Cancel: function () {
                                $(this).dialog("close");
                            }
                        }
                    });
                }
            };

            $scope.$watch('employee', function (newEmployee) {
                if (newEmployee) {
                    $scope.templates.forEach(function (template) {
                        template.fields.forEach(function (skill) {
                            skill['rating'] = extractEmployeeSkillValues(newEmployee, skill);
                            skill['comment'] = extractEmployeeSkillComments(newEmployee, skill);
                        });
                    });
                    $scope.originalEmployee = angular.copy(newEmployee);
                    renderSlider();
                    $('.skills-matrix').show();
                }
            });

            $scope.$watch('employeeRecords', function () {
                refreshSlider();
            });

            $scope.isManager = function () {
                return $scope.authenticatedUser.authorities[0].authority === 'ROLE_MANAGER';
            };

            function getSkillsTemplateAndEmployee() {
                $http.get('template')
                    .success(function (data, status, headers, config) {
                        $scope.templates = data;
                        $http.get('employee')
                            .success(function (data, status, headers, config) {
                                $scope.employeeRecords = data;
                                $scope.employee = data[0];
                            });
                    });
            }

            function extractEmployeeSkillValues(newEmployee, skill) {
                if (newEmployee.skills) {
                    var result = $.grep(newEmployee.skills,
                        function (employeeSkill) {
                            return employeeSkill.description == skill.fieldName;
                        })
                }

                if (typeof result !== 'undefined' && result.length > 0) {
                    return result[0].value;
                } else {
                    newEmployee.skills.push({'description': skill.fieldName, 'comment': '', 'value': 0});
                    return 0;
                }
            }

            function extractEmployeeSkillComments(newEmployee, skill) {
                if (newEmployee.skills) {
                    var result = $.grep(newEmployee.skills,
                        function (employeeSkill) {
                            return employeeSkill.description == skill.fieldName;
                        })
                }

                if (typeof result !== 'undefined' && result.length > 0) {
                    return result[0].comment;
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

            function renderSlider() {
                var date = new Date($scope.employee.lastUpdateDate);
                $("#timemachine").show();
                if ($scope.employeeRecords.length > 1) {
                    $("#slider-snap").show();
                    var snapSlider = document.getElementById('slider-snap');

                    noUiSlider.create(snapSlider, {
                        start: date.getTime(),
                        step: 1,
                        range: createRange()
                    });

                    $scope.slider = snapSlider;

                    snapSlider.noUiSlider.on('update', function (vals, handle) {
                        var position = $scope.employeeRecords.length - parseInt(vals[handle]);
                        var record = $scope.employeeRecords[position > 0 ? position - 1 : position];
                        $('#date').html(new Date(record.lastUpdateDate));
                        $scope.employee = record;
                        if ($scope.employeeRecords[0] != record) {
                            $scope.editable = false;
                        } else {
                            $scope.editable = true;
                        }
                        $scope.$apply();
                    });
                }
                else {
                    $('#date').html(date);
                }
            }

            function refreshSlider() {
                $scope.slider.noUiSlider.updateOptions({
                    start: $scope.employeeRecords.length - 1,
                    range: {
                        'min': 0,
                        'max': $scope.employeeRecords.length - 1
                    }
                });
            }

            function createRange() {
                return {
                    'min': 0,
                    'max': $scope.employeeRecords.length - 1
                }
            }

            $scope.isEditable = function () {
                return $scope.editable;
            };

            $("#q").keyup(function (e) {
                if (e.keyCode == 13) {
                    $scope.performSearch();
                }
            });
        }
    );