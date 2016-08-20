angular
    .module('morpheus.view', [])
    .controller('EmployeeCtrl', function ($scope, $http, $routeParams) {
            console.log($routeParams.username);

            var matrixTab = document.getElementById("devMatrix");
            matrixTab.className = "active";

            $http.get('employee/levels')
                .success(function (data) {
                    $scope.levels = data;
                });

            $http.get('team')
                .success(function (data) {
                    $scope.teams = data;
                });

            $http.get('employee/authenticated')
                .success(function (data) {
                    $scope.authenticatedUser = data;
                    if ($scope.isManager()) {
                        var createEmployeeTab = document.getElementById("createEmployee");
                        var createTeamTab = document.getElementById("createTeam");
                        var editTemplatesTab = document.getElementById("editTemplates");

                        createEmployeeTab.className = "inactive";
                        createTeamTab.className = "inactive";
                        editTemplatesTab.className = "inactive";
                        retrieveAllEmployees();
                        getTemplates();
                    } else {
                        getSkillsTemplateAndEmployee();
                    }
                });

            $scope.editable = true;
            $('#updateCommentDialog').hide();

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
                        $scope.employeeDetails = data.employeeDetails;
                        $scope.employeeRecords = data.employeeRecords;
                        $scope.employee = $scope.employeeRecords[0];
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
                $http.post('employee/record', $scope.employee)
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

            $scope.isNotDirty = function () {
                return angular.equals($scope.employee, $scope.originalEmployee) &&
                    angular.equals($scope.employeeDetails, $scope.originalEmployeeDetails);
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
                    $scope.originalEmployeeDetails = angular.copy($scope.employeeDetails);
                    renderSlider();
                    $('.skills-matrix').show();
                }
            });

            $scope.$watch('employeeRecords', function () {
                refreshSlider();
            });

            $scope.isManager = function () {
                if ($scope.authenticatedUser) {
                    return $scope.authenticatedUser.authorities[0].authority === 'ROLE_MANAGER';
                } else {
                    return false;
                }
            };

            function getSkillsTemplateAndEmployee() {
                getTemplates(getEmployee);
            }

            function getEmployee() {
                if (!$scope.isManager()) {
                    debugger;
                    $http.get('employee')
                        .success(function (data, status, headers, config) {
                            $scope.employeeDetails = data.employeeDetails;
                            $scope.employeeRecords = data.employeeRecords;
                            $scope.employee = $scope.employeeRecords[0];
                        });
                }
            }

            function getTemplates(callback) {
                $http.get('template')
                    .success(function (data, status, headers, config) {
                        $scope.templates = data;
                        typeof callback === 'function' && callback();
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
                    newEmployee.skills.push({'description': skill.fieldName, 'comment': '', 'value': null});
                    return null;
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
                        $scope.employees = data.map(function (employeeDetail) {
                            return employeeDetail.username;
                        });
                        $('#q').autocomplete({
                            source: $scope.employees
                        });
                    });
            }

            function renderSlider() {
                $("#timemachine").show();
                if ($scope.employeeRecords.length > 1) {
                    var date = new Date($scope.employee.lastUpdateDate);
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
                    if ($scope.employee.lastUpdateDate) {
                        $('#date').html(new Date($scope.employee.lastUpdateDate));
                    } else {
                        $('#date').html('There are no records to display');
                    }
                }
            }

            function refreshSlider() {
                if ($scope.slider) {
                    $scope.slider.noUiSlider.updateOptions({
                        start: $scope.employeeRecords.length - 1,
                        range: {
                            'min': 0,
                            'max': $scope.employeeRecords.length - 1
                        }
                    });
                }
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