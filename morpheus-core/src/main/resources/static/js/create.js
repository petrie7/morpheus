angular
    .module('morpheus.create', [])
    .controller('CreateEmployeeCtrl', function ($scope, $http) {
        $scope.persistEmployee = function () {
            $http.post('employee/create', $scope.employee)
                .success(function (data, status, headers, config) {
                    $.growl.notice({message: $scope.employee.username + ' successfully created'});
                })
                .error(function (data) {
                    $.growl.error({message: data.message});
                });
    };

         var matrixTab = document.getElementById("devMatrix");
         var createTab = document.getElementById("createEmployee");
         var editTab = document.getElementById("editTemplates");

         matrixTab.className = "inactive";
         editTab.className = "inactive";
         createTab.className = "active";
});