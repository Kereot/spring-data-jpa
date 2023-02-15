angular.module('app', ['ngStorage']).controller('restProductController', function ($scope, $rootScope, $http, $localStorage) {
    const gatewayPath = 'http://localhost:8200/';


    if ($localStorage.springWebUser) {
        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.springWebUser.token;
    }

    $scope.tryToAuth = function () {
        $http.post(gatewayPath + 'auth/auth', $scope.user)
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

    $scope.addToCart = function(id, name, price) {
        $http.post(gatewayPath + 'cart/api/v1/products/cart', {
            id: id,
            name: name,
            price: price
        })
            .then(function () {
                $scope.loadCart();
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
        $http({
            url: gatewayPath + 'core/api/v1/products/orders',
            method: 'POST'
        }).then(function () {
            $scope.loadOrders();
            $scope.loadCart();
        });
    }

    $scope.loadOrders = function () {
        $http.get(gatewayPath + 'core/api/v1/products/orders')
            .then(function (response) {
                $scope.myOrders = response.data;
            });
    };

    $scope.deleteOrder = function (id) {
        $http.delete(gatewayPath + 'core/api/v1/products/orders/'+ id)
            .then(function () {
                $scope.loadCart();
                $scope.loadOrders();
            });
    }

    $scope.loadProducts();
    $scope.loadCart();

});