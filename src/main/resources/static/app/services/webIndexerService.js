angular.module(App.name)
    .factory('WebIndexerService', function ($http, $q) {
            var factory = {};

            factory.indexUrl = function (url) {
                var deffered = $q.defer();
                $http.get(App.baseUrl + '/indexer?url=' + url, {}).then(function (response) {
                    deffered.resolve(response);
                }, function (response) {
                    console.error('Gists error', response.status, response.data);
                    deffered.reject("Failed");
                });

                return deffered.promise;
            };

            factory.clearUrl = function (url) {
                var deffered = $q.defer();
                $http.get(App.baseUrl + '/clearer?url=' + url, {}).then(function (response) {
                    deffered.resolve(response);
                }, function (response) {
                    console.error('Gists error', response.status, response.data);
                    deffered.reject("Failed");
                });

                return deffered.promise;
            };

            factory.searchWord = function (word, page) {
                var deffered = $q.defer();
                $http.get(App.baseUrl + '/searcher?word=' + word + '&page=' + page, {}).then(function (response) {
                    deffered.resolve(response);
                }, function (response) {
                    console.error('Gists error', response.status, response.data);
                    deffered.reject("Failed");
                });

                return deffered.promise;
            };

            return factory;
        }
    );