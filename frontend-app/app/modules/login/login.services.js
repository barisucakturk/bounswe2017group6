(function(){
    angular
        .module("interestHub")
        .factory("LoginService", LoginService);

    function LoginService($http) {
        var api = {
            getToken: getToken

        };
        return api;

        
        function getToken(user){
            var credentials = {"username": user.username, "password":user.password};
            return $http.post('https://limitless-sands-55256.herokuapp.com/http://34.209.230.231:8000/login/',credentials);
        }

    }
})();