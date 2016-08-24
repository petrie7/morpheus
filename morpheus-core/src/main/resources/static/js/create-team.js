angular
    .module('morpheus.create-team', [])
    .controller('CreateTeamCtrl', function ($scope, $http) {
        $scope.persistTeam = function () {
            $http.post('team/' + $scope.team.name)
                .success(function () {
                    $.growl.notice({message: $scope.team.name + ' successfully created'});
                })
                .error(function (data) {
                    $.growl.error({message: data.message});
                });
        };
    });