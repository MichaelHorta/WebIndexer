'use strict';
angular
    .module(App.name)
    .config(function ($stateProvider, $urlRouterProvider, $ocLazyLoadProvider) {

        $ocLazyLoadProvider.config({
            // Set to true if you want to see what and when is dynamically loaded
            debug: true
        });

        $stateProvider
            .state('webindexer', {
                url: '/',
                templateUrl: 'templates/webindexer.html',
                controller: 'webIndexerCtrl',
                resolve: {
                    loadCSS: ['$ocLazyLoad', function ($ocLazyLoad) {
                        return $ocLazyLoad.load([{
                            serie: true,
                            name: 'Font Awesome',
                            files: ['bower_components/fontawesome/css/font-awesome.min.css']
                        }]);
                    }]
                }
            });
        $urlRouterProvider.otherwise('/');


    }).config(['cfpLoadingBarProvider', function (cfpLoadingBarProvider) {
    cfpLoadingBarProvider.includeSpinner = true;
}]);

