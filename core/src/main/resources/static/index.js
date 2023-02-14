angular.module('app', ['ngStorage']).controller('restProductController', function ($scope, $rootScope, $http, $localStorage) {
    const corePath = 'http://localhost:8189/market-core/api/v1';
    const cartPath = 'http://localhost:8190/market-carts/api/v1';


    if ($localStorage.springWebUser) {
        $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.springWebUser.token;
    }

    $scope.tryToAuth = function () {
        $http.post(corePath + '/auth', $scope.user)
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
            url: corePath + '/products',
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
        $http.delete(corePath + '/products/'+ id)
            .then(function () {
                $scope.loadProducts();
            });
    }

    $scope.addProduct = function() {
        $http.post(corePath + '/products', $scope.newProduct)
            .then(function () {
                $scope.loadProducts();
                $scope.newProduct = null;
            });
    }

    $scope.changePrice = function(id, name, price) {
        $http.put(corePath + '/products', {
            id: id,
            name: name,
            price: price
        })
            .then(function () {
                $scope.loadProducts();
            });
    }

    $scope.saveRandom = function () {
        $http.get(corePath + '/products/add_random')
            .then(function () {
                $scope.loadProducts();
            });
    }

    // $scope.loadCart = function () {
    //     $http.get(cartPath + '/products/cart')
    //         .then(function (response) {
    //             $scope.CartsPage = response.data;
    //             $scope.showTotalPrice();
    //         });
    // }

    $scope.loadCart = function () {
        $http.get(cartPath + '/products/cart')
            .then(function (response) {
                $scope.cart = response.data;
                $scope.showTotalPrice();
            });
    }

    $scope.addToCart = function(id, name, price) {
        $http.post(cartPath + '/products/cart', {
            id: id,
            name: name,
            price: price
        })
            .then(function () {
                $scope.loadCart();
            });
    }

    $scope.increaseQuantity = function (id) {
        $http.post(cartPath + '/products/cart/' + id, {
            id: id
        })
            .then(function () {
                $scope.loadCart();
            });
    }

    $scope.deleteProductFromCart = function (id) {
        $http.delete(cartPath + '/products/cart/'+ id)
            .then(function () {
                $scope.loadCart();
            });
    }

    $scope.clearCart = function () {
        $http.delete(cartPath + '/products/cart')
            .then(function () {
                $scope.loadCart();
            });
    }

    $scope.totalPrice = 0;

    $scope.showTotalPrice = function () {
        $http.get(cartPath + '/products/cart/total_price')
            .then(function (response) {
                $scope.totalPrice = response.data;
            });
    }

    $scope.createOrder = function () {
        $http({
            url: corePath + '/products/orders',
            method: 'POST'
        }).then(function () {
            $scope.loadOrders();
            $scope.loadCart();
        });
    }

    $scope.loadOrders = function () {
        $http.get(corePath + '/products/orders')
            .then(function (response) {
                $scope.myOrders = response.data;
            });
    };

    $scope.deleteOrder = function (id) {
        $http.delete(corePath + '/products/orders/'+ id)
            .then(function () {
                $scope.loadCart();
                $scope.loadOrders();
            });
    }

    $scope.loadProducts();
    $scope.loadCart();

});