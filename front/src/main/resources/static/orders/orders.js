angular.module('app').controller('ordersController', function ($scope, $rootScope, $http, $location) {
    const gatewayPath = 'http://localhost:8200/';

    $scope.loadOrders = function () {
        $http.get(gatewayPath + 'core/api/v1/products/orders')
            .then(function (response) {
                $scope.myOrders = response.data;
            });
    };

    $scope.deleteOrder = function (id) {
        $http.delete(gatewayPath + 'core/api/v1/products/orders/'+ id)
            .then(function () {
                $location.path('/cart');
            });
    }

    $scope.loadOrders();
});