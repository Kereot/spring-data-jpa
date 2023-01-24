angular.module('app', ['ngStorage']).controller('restProductController', function ($scope, $rootScope, $http, $localStorage) {
    const contextPath = 'http://localhost:8080/api/v1';

    if ($localStorage.springWebUser) {
        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.springWebUser.token;
    }

    $scope.tryToAuth = function () {
        $http.post(contextPath + '/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.springWebUser = {username: $scope.user.username, token: response.data.token};

                    $scope.user.username = null;
                    $scope.user.password = null;

                    $scope.loadProducts();
                }
            }, function errorCallback(response) {
            });
    };

    $scope.tryToLogout = function () {
        $scope.clearUser();
        if ($scope.user.username) {
            $scope.user.username = null;
        }
        if ($scope.user.password) {
            $scope.user.password = null;
        }
    };

    $scope.clearUser = function () {
        delete $localStorage.springWebUser;
        $http.defaults.headers.common.Authorization = '';
        $scope.ProductsPage = null;
        $scope.CartsPage = null;
    };

    $rootScope.isUserLoggedIn = function () {
        return !!$localStorage.springWebUser;
    };

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