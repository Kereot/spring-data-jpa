angular.module('app').controller('registrationController', function ($scope, $http, $location, $localStorage) {
    const gatewayPath = 'http://localhost:8200/';

    $scope.registerUser = function () {
        $http.post(gatewayPath + 'auth/registration', $scope.regUser).then(function (response) {
            if (response.data.token) {
                $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                $localStorage.springWebUser = {username: $scope.regUser.username, token: response.data.token};
                $localStorage.regUser = null;
                $location.path("/")
            }
        });
    };
});