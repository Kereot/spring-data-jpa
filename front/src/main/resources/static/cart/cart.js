angular.module('app').controller('cartController', function ($scope, $rootScope, $http, $location) {
    const gatewayPath = 'http://localhost:8200/';

    // $scope.loadCart = function () {
    //     $http.get(gatewayPath + 'cart/products/cart')
    //         .then(function (response) {
    //             $scope.CartsPage = response.data;
    //             $scope.showTotalPrice();
    //         });
    // }

    $scope.loadCart = function () {
        $http.get(gatewayPath + 'cart/api/v1/products/cart')
            .then(function (response) {
                $scope.cart = response.data;
                $scope.showTotalPrice();
            });
    }

    $scope.increaseQuantity = function (id) {
        $http.post(gatewayPath + 'cart/api/v1/products/cart/' + id, {
            id: id
        })
            .then(function () {
                $scope.loadCart();
            });
    }

    $scope.deleteProductFromCart = function (id) {
        $http.delete(gatewayPath + 'cart/api/v1/products/cart/'+ id)
            .then(function () {
                $scope.loadCart();
            });
    }

    $scope.clearCart = function () {
        $http.delete(gatewayPath + 'cart/api/v1/products/cart')
            .then(function () {
                $scope.totalPrice = 0;
                $scope.loadCart();
            });
    }

    $scope.totalPrice = 0;

    $scope.showTotalPrice = function () {
        $http.get(gatewayPath + 'cart/api/v1/products/cart/total_price')
            .then(function (response) {
                $scope.totalPrice = response.data;
            });
    }

    $scope.createOrder = function () {
        if ($scope.totalPrice !== 0) {
            $http({
                url: gatewayPath + 'core/api/v1/products/orders',
                method: 'POST'
            }).then(function () {
                $location.path('/orders');
            });
        }
    }

    $scope.loadCart();

});