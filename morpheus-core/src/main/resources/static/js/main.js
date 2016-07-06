angular
    .module('morpheusApp', ['ngRoute', 'morpheus.create', 'morpheus.view'])
    .config(['$routeProvider', function($routeProvider)
    {
    $routeProvider
    .when('/matrix', {
        templateUrl: '/partials/view.html',
        controller: 'EmployeeCtrl'
    })
    .when('/create', {
        templateUrl: '/partials/create.html',
        controller: 'CreateEmployeeCtrl'
    })
    .otherwise({
        redirectTo: '/matrix'
    })
}]);