

function MainCtrl($scope, CidreService) {
    $scope.cidres = CidreService.query();
    $scope.delete = function(cidre) {
        $('#delete' + cidre.id).modal('hide');
        CidreService.delete({id:cidre.id}, function(data){
            $scope.cidres = CidreService.query();
        });
    }
}

function EditCtrl($scope, $routeParams, $location, CidreService) {
    $scope.cidre = CidreService.get({id:$routeParams.id});
    $scope.submitMessage = "Update cidre";
    $scope.saveCidre = function(cidre) {
        if ($scope.form.$invalid) {
            $scope.form.name.$dirty = true;
        } else {
            CidreService.update(cidre, function(data) {
                $location.path('/main');
            });
        }
    };
}

function NewCtrl($scope, $location, CidreService) {
    $scope.submitMessage = "Save cidre";
    $scope.saveCidre = function(cidre) {
        if ($scope.form.$invalid) {
            $scope.form.name.$dirty = true;
        } else {
            CidreService.save(cidre, function(data) {
                $location.path('/main');
            });
        }
    };
}

function LoginCtrl($scope, $http, UserService, $location) {

    $scope.submitUser = function(user) {
        $http.post("/login", user)
            .success(function() {
                UserService.addUser(user);
                $location.path("/");
            })
            .error(function() {
                $scope.messageError = "Login/password incorrect";
            })
    }

}


