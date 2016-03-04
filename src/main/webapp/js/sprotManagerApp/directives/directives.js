'use strict';


angular.module('ProjetSportManager.Directives', [])
.directive('onblur', function() {
	return {
		// A = attribute, E = Element, C = Class and M = HTML Comment
		// Si onblur alors "A"ttribut. si input alors "E"lement !!!
		restrict:'A',
		require: 'ngModel',
		// The link function is responsible for registering DOM listeners as well as updating the DOM.
        link: function(scope, elm, attr, ngModelCtrl) {
            if (attr.type === 'radio' || attr.type === 'checkbox' || attr.type === 'date') return;	            
            elm.unbind('input').unbind('keydown').unbind('change');
            elm.bind('blur', function() {
                scope.$apply(function() {
                    ngModelCtrl.$setViewValue(elm.val());
                });         
            });
        }
	};
})
.directive('responsive', function() {
	return {
		// A = attribute, E = Element, C = Class and M = HTML Comment
		// Si onblur alors "A"ttribut. si input alors "E"lement !!!
		restrict:'A',
		require: 'ngModel',
		// The link function is responsible for registering DOM listeners as well as updating the DOM.
        link: function(scope, elm, attr, ngModelCtrl) {
        	var tmp = elm.css('color');
        	// Attention : On ne doit pas setter cette valeur dans le controleur. sinon fonctionne pas !
        	if (tmp == "rgb(170, 0, 0)") {
        		console.log('Couleur :' + tmp);
        		scope.responsive = true;
        	} else scope.responsive = false;
        }
	};
})
.directive('calendrierpicker', function($parse) {
  var directiveDefinitionObject = {
    restrict: 'A',
    require: 'ngModel',
    link: function postLink(scope, iElement, iAttrs, ngModelCtrl) {
      iElement.datepicker({
        dateFormat: 'yy-mm-dd',
        onSelect: function(dateText, inst) {
          scope.$apply(function(scope){
            $parse(iAttrs.ngModel).assign(scope, dateText);
            ngModelCtrl.$setViewValue(dateText);
          });
        }
      });
    }
  };
  return directiveDefinitionObject;
});