angular
    .module('morpheusApp', ['ngRoute', 'morpheus.create', 'morpheus.view', 'morpheus.edit', 'morpheus.edit-teams'])
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/matrix', {
                templateUrl: '/partials/view.html',
                controller: 'EmployeeCtrl'
            })
            .when('/create', {
                templateUrl: '/partials/create.html',
                controller: 'CreateEmployeeCtrl'
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