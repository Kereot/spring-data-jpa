(function () {
    angular
        .module('app', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);

    function config($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'welcome/welcome.html',
                controller: 'welcomeController'
            })
            .when('/market', {
                templateUrl: 'market/market.html',
                controller: 'marketController'
            })
            .when('/cart', {
                templateUrl: 'cart/cart.html',
                controller: 'cartController'
            })
            .when('/orders', {
                templateUrl: 'orders/orders.html',
                controller: 'ordersController'
            })
            .when('/registration', {
                templateUrl: 'registration/registration.html',
                controller: 'registrationController'
            })
            .otherwise({
                redirectTo: '/'
            });
    }

    function run($rootScope, $http, $localStorage) {
        if ($localStorage.springWebUser) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.springWebUser.token;
        }
        if (!$localStorage.springMarketGuestCartId) {
            $http.get('http://localhost:8200/cart/api/v1/products/cart/generate_uuid')
                .then(function successCallback(response) {
                    $localStorage.springMarketGuestCartId = response.data.value;
                });
        }
    }
})();

angular.module('app').controller('restProductController', function ($scope, $rootScope, $http, $localStorage, $location, $route) {
    const gatewayPath = 'http://localhost:8200/';

    $scope.tryToAuth = function () {
        $http.post(gatewayPath + 'auth/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.springWebUser = {username: $scope.user.username, token: response.data.token};

                    $scope.transferItemsInCart();

                    $scope.user.username = null;
                    $scope.user.password = null;

                    $route.reload();
                }
            }, function errorCallback(response) {
            });
    };

    $scope.tryToLogout = function () {
        $scope.clearUser();
        $route.reload();
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

    $scope.transferItemsInCart = function () {
        $http.get(gatewayPath + 'cart/api/v1/products/cart/merge/' + $localStorage.springMarketGuestCartId)
            .then(function () {

            });
    }

});