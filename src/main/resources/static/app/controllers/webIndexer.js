'use strict';

angular
    .module(App.name)
    .controller('webIndexerCtrl', WebIndexerController);

WebIndexerController.$inject = ['$scope', 'WebIndexerService'];

function WebIndexerController($scope, WebIndexerService) {


    // Index
    $scope.indexerActive = true;
    $scope.clearing = false;
    $scope.pagesCount = 0;
    $scope.wordsCount = 0;
    $scope.btnIndexText = 'Index';
    $scope.btnClearText = 'Clear';
    $scope.finishIndex = false;
    $scope.finishClear = false;

    $scope.toIndexerView = function () {
        $scope.indexerActive = true;
        $scope.searcherActive = false;
    };

    $scope.toIndexProcess = function (isValid) {
        if (isValid) {
            indexUrl();
        }
    };

    function indexUrl() {
        $scope.indexing = true;
        $scope.btnIndexText = 'Indexing...';
        $scope.finishIndex = false;
        var promise = WebIndexerService.indexUrl($scope.url);
        promise.then(function (response) {
            console.log(response);
            $scope.pagesCount = response.data.result.pagesCount;
            $scope.wordsCount = response.data.result.wordsCount;
            $scope.url = '';
            $scope.indexing = false;
            $scope.btnIndexText = 'Index';
            $scope.finishIndex = true;
        })
        .catch(function (err) {
            console.log(err);
        });
    }

    $scope.clearUrl = function() {
        $scope.clearing = true;
        $scope.btnClearText = 'Clearing...';
        $scope.finishClear = false;
        var promise = WebIndexerService.clearUrl($scope.url);
        promise.then(function (response) {
            console.log(response);
            $scope.url = '';
            $scope.clearing = false;
            $scope.btnClearText = 'Clear';
            $scope.finishClear = true;
        })
            .catch(function (err) {
                console.log(err);
            });
    }

    // Search

    $scope.searcherActive = false;
    $scope.searching = false;
    $scope.btnSearchText = 'Search';
    $scope.finishSearch = false;
    $scope.page = 1;
    $scope.pages = [];

    $scope.toSearcherView = function () {
        $scope.indexerActive = false;
        $scope.searcherActive = true;
    };

    $scope.toSerchProcess = function (isValid) {
        if(isValid) {
            searchWord();
        }
    };

    function searchWord() {
        $scope.pages = [];
        $scope.searching = true;
        $scope.btnSearchText = 'Searching...';
        $scope.finishSearch = false;
        var promise = WebIndexerService.searchWord($scope.word, $scope.page);
        promise.then(function (response) {
            console.log(response);
            if(response.data.result)
                $scope.pages = response.data.result.searchResultPages;

            $scope.searching = false;
            $scope.btnSearchText = 'Search';
            $scope.finishSearch = true;
        })
            .catch(function (err) {
                console.log(err);
            });
    }
}