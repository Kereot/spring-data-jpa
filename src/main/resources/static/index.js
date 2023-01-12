angular.module('app', []).controller('restProductController', function ($scope, $http) {
    const contextPath = 'http://localhost:8080/api/v1';

    $scope.currentPage = 1;
    $scope.pageSize = 10;

    $scope.loadProducts = function (pageIndex = 1) {
        $http({
            url: contextPath + '/products',
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
        $http.delete(contextPath + '/products/'+ id)
            .then(function (response) {
                $scope.loadProducts();
            });
    }

    $scope.addProduct = function() {
        $http.post(contextPath + '/products', $scope.newProduct)
            .then(function (response) {
                $scope.loadProducts();
                $scope.newProduct = null;
            });
    }

    $scope.changePrice = function(id, name, price) {
        $http.put(contextPath + '/products', {
            id: id,
            name: name,
            price: price
        })
            .then(function (response) {
                $scope.loadProducts();
            });
    }

    $scope.saveRandom = function () {
        $http.get(contextPath + '/products/add_random')
            .then(function (response) {
                $scope.loadProducts();
            });
    }

    $scope.loadCart = function () {
        $http.get(contextPath + '/products/cart')
            .then(function (response) {
                $scope.CartsPage = response.data;
            });
    }

    $scope.addToCart = function(id, name, price) {
        $http.post(contextPath + '/products/cart', {
            id: id,
            name: name,
            price: price
        })
            .then(function (response) {
                $scope.loadCart();
            });
    }

    $scope.deleteProductFromCart = function (id) {
        $http.delete(contextPath + '/products/cart/'+ id)
            .then(function (response) {
                $scope.loadCart();
            });
    }

    $scope.loadProducts();
    $scope.loadCart();

});