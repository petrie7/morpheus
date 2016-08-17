angular
    .module('morpheusApp', [
        'ngRoute',
        'morpheus.view',
        'morpheus.create-employee',
        'morpheus.create-team',
        'morpheus.edit-template'
    ])
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/matrix', {
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
            .otherwise({
                redirectTo: '/matrix'
            })
    }]);