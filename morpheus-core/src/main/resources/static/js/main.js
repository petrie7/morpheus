angular
    .module('morpheusApp', [
        'ngRoute',
        'morpheus.view',
        'morpheus.create-employee',
        'morpheus.create-team',
        'morpheus.edit-template',
        'morpheus.team-dashboard'
    ])
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/matrix', {
                templateUrl: '/partials/view.html',
                controller: 'EmployeeCtrl'
            })
            .when('/matrix/:username', {
                templateUrl: '/partials/view.html',
                controller: 'EmployeeCtrl'
            })
            .when('/create-employee', {
                templateUrl: '/partials/create-employee.html',
                controller: 'CreateEmployeeCtrl'
            })
            .when('/create-team', {
                templateUrl: '/partials/create-team.html',
                controller: 'CreateTeamCtrl'
            })
            .when('/edit-template', {
                templateUrl: '/partials/edit-template.html',
                controller: 'EditTemplateCtrl'
            })
            .when('/team-dashboard', {
                templateUrl: '/partials/team-dashboard.html',
                controller: 'TeamDashboardCtrl'
            })
            .otherwise({
                redirectTo: '/matrix'
            })
    }])
    .run(['$rootScope', '$location', function ($rootScope, $location) {
           var path = function () {
             return $location.path();
         };

         $rootScope.$watch(path, function (newVal, oldVal) {
             $rootScope.activetab = "/" + newVal.split("/")[1];
           });
         }]);