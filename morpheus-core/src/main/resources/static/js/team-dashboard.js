angular
    .module('morpheus.team-dashboard', [])
    .controller('TeamDashboardCtrl', function ($scope, $http) {
        $http.get('team/members').success(function(data){
            $scope.teamMembers = data;
        });
    });