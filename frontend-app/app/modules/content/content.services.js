(function(){
    angular
        .module("interestHub")
        .factory("ContentService", ContentService);

    function ContentService($http) {
        var api = {
            createContent: createContent,
            updateContent: updateContent,
            deleteContent: deleteContent,
            getContent: getContent,
            getAllContents: getAllContents
        };
        return api;

        
        function createContent(Content){
            
            return $http.Content('https://cors-anywhere.herokuapp.com/http://34.209.230.231:8000/content/',Content);
        }

        function updateContent(ContentID, Content){
            return $http.put('https://cors-anywhere.herokuapp.com/http://34.209.230.231:8000/content/'+ContentID, Content);
        }

        function deleteContent(ContentID){
            return $http.delete('https://cors-anywhere.herokuapp.com/http://34.209.230.231:8000/content/'+ContentID);
        }

        function getContent(ContentID){
            return $http.get('https://cors-anywhere.herokuapp.com/http://34.209.230.231:8000/content/'+ContentID);
        }
        
        function getAllContents(){
            return $http.get('https://cors-anywhere.herokuapp.com/http://34.209.230.231:8000/content/');
        }

    }
})();