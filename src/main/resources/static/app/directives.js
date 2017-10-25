angular
    .module(App.name)
    .directive('a', preventClickDirective);

//Prevent click if href="#"
function preventClickDirective() {
    var directive = {
        restrict: 'E',
        link: link
    }
    return directive;

    function link(scope, element, attrs) {
        if (attrs.href === '#' || element.hasClass('prevent')) {
            element.on('click', function (event) {
                event.preventDefault();
            });
        }
    }
}