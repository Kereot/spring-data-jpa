angular.module('app').controller('marketController', function ($scope, $rootScope, $http, $localStorage) {
    const gatewayPath = 'http://localhost:8200/';

    $scope.currentPage = 1;
    $scope.pageSize = 10;

    $scope.loadProducts = function (pageIndex = 1) {
        $http({
            url: gatewayPath + 'core/api/v1/products',
            method: 'GET',
            params: {
                page: $scope.currentPage,
                name: $scope.filter ? $scope.filter.name : null,
                min_price: $scope.filter ? $scope.filter.min_price : null,
                max_price: $scope.filter ? $scope.filter.max_price : null
            }
        }).then(function (response) {
            $scope.ProductsPage = response.data;
        });
    }

    $scope.resetFilter = function () {
        $scope.filter.min_price = null;
        $scope.filter.max_price = null;
        $scope.filter.name = null;
        $scope.loadProducts();
    }

    $scope.changePage = function (page) {
        $scope.currentPage = page;
        $scope.loadProducts();
    }

    $scope.deleteProduct = function (id) {
        $http.delete(gatewayPath + 'core/api/v1/products/'+ id)
            .then(function () {
                $scope.loadProducts();
            });
    }

    $scope.addProduct = function() {
        $http.post(gatewayPath + 'core/api/v1/products', $scope.newProduct)
            .then(function () {
                $scope.loadProducts();
                $scope.newProduct = null;
            });
    }

    $scope.changePrice = function(id, name, price) {
        $http.put(gatewayPath + 'core/api/v1/products', {
            id: id,
            name: name,
            price: price
        })
            .then(function () {
                $scope.loadProducts();
            });
    }

    $scope.saveRandom = function () {
        $http.get(gatewayPath + 'core/api/v1/products/add_random')
            .then(function () {
                $scope.loadProducts();
            });
    }

    $scope.addToCart = function(id, name, price) {
        $http.post(gatewayPath + 'cart/api/v1/products/cart/' + $localStorage.springMarketGuestCartId, {
            id: id,
            name: name,
            price: price
        })
            .then(function () {});
    }

    $scope.loadProducts();

});