var app = angular.module('CidreApp',
    ['CidreServices'])
    .config(['$routeProvider',function($routeProvider) {
        $routeProvider
            .when('/main', {
                templateUrl: 'partial/main.html',
                controller: MainCtrl,
                isFree: false
            })
            .when('/edit/:id', {
                templateUrl: 'partial/editOrNew.html',
                controller: EditCtrl,
                isFree: false
            })
            .when('/new', {
                templateUrl: 'partial/editOrNew.html',
                controller: NewCtrl,
                isFree: false
            })
            .when("/login", {
                templateUrl: 'partial/login.html',
                controller: LoginCtrl,
                isFree: true
            })
            .otherwise({
                redirectTo: '/main'
            })
    }]);

app.run(function($rootScope, UserService, $location) {
    $rootScope.$on("$routeChangeStart", function(event, next, current) {
        if (!next.isFree && !UserService.isLogged()) {
            $location.path("/login");
        }
    });
});