angular
    .module('morpheusApp', [
        'ngRoute',
        'morpheus.create-employee',
        'morpheus.create-team',
        'morpheus.view',
        'morpheus.edit',
        'morpheus.edit-teams'
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
            .when('/edit-teams', {
                templateUrl: '/partials/edit-teams.html',
                controller: 'EditTeamsCtrl'
            })
            .otherwise({
                redirectTo: '/matrix'
            })
    }]);