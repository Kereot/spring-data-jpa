angular.module('app', []).controller('productController', function ($scope, $http) {
    const contextPath = 'http://localhost:8080';

    $scope.loadProducts = function () {
        $http.get(contextPath + '/products')
            .then(function (response) {
                $scope.ProductsList = response.data;
            });
    };

    $scope.findAllByPriceGreaterThan = function (price) {
        $http.get(contextPath + '/products/min/'+ price)
            .then(function (response) {
                $scope.ProductsList = response.data;
            });
    }

    $scope.findAllByPriceLessThan = function (price) {
        $http.get(contextPath + '/products/max/'+ price)
            .then(function (response) {
                $scope.ProductsList = response.data;
            });
    }

    $scope.findAllByPriceBetween = function (priceMin, priceMax) {
        $http({
            url: contextPath + '/products/between',
            method: 'GET',
            params: {
                priceMin: priceMin,
                priceMax: priceMax
            }
        }).then(function (response) {
                $scope.ProductsList = response.data;
            });
    }

    $scope.saveRandom = function () {
        $http.get(contextPath + '/products/add_random')
            .then(function (response) {
                $scope.loadProducts();
            });
    };

    $scope.deleteProduct = function (id) {
        $http.delete(contextPath + '/products/delete/'+ id)
            .then(function (response) {
                $scope.loadProducts();
            });
    }

    $scope.addProduct = function (name, price) {
        var product = {
            'name': name,
            'price': price
        };
        var url = contextPath + '/products/add';
        $http.post(url, product).then(function (response) {
            $scope.loadProducts();
        });
    };

    $scope.loadProducts();
});