'user strict';

var App = (function() {

    var name = 'webIndexerApp';
    var baseUrl = 'http://localhost:8080';

    var dependencies = [
        'ui.router',
        'ui.router.state.events',
        'oc.lazyLoad',
        'angular-loading-bar',
        'ngStorage',
        'ngAnimate'
    ];

    var registerModule = function(moduleName, dependencies) {
        angular.module(moduleName, dependencies || []);
        angular.module(name).requires.push(moduleName);
    };

    return {
        name: name,
        baseUrl: baseUrl,
        dependencies: dependencies,
        registerModule: registerModule
    }

})();
