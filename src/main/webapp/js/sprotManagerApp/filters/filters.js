'use strict';

/* Filters */

angular.module('ProjetSportManager.Filters', [])
.filter('checkmark', function() {
	return function(input) {
		return input ? '\u2713' : '\u2718';
	};
})
.filter('FilterOptionsActionsDispo', function() {
	/** Utilisé pour filtrer les élements de menu vertical dispo.
    On regarde les link disponibles et on les compare aux actions disponibles 
    En entrée : 
    			ListOptionItems toutes les option disponibles
    			ListLinks les link remontés de l'appel REST
    En sortie : actionsDispo les actions dispo pour ce user.
 */
	return function(ListOptionItems, ListOptionItemsListLinks) {		
		var debug = false;
		var actionsDispo = [];
		
		if (debug) console.log("========== FilterOptionsActionsDispo: Deb =======================");
		if (ListOptionItems && ListOptionItemsListLinks) {
			if (debug) console.log("nb ListOptionItems : " + ListOptionItems.length);
			if (debug) console.log("nb ListOptionItemsListLinks : " + ListOptionItemsListLinks.length);
			angular.forEach(ListOptionItems, function(option) {
				var currentRelValue = option.relValue;
				if (debug) console.log("currentRelValue " + currentRelValue);
				angular.forEach(ListOptionItemsListLinks, function(aLink) {
					
					if (aLink.rel == currentRelValue) {
						if (debug) console.log("addAction " + option.name);
						actionsDispo.push(option);
					}
				});
			});
			if (debug) console.log("nb actions dispo " + actionsDispo.length);
		}
		if (debug) console.log("========== FilterOptionsActionsDispo: Fin =======================");			
		return actionsDispo;
		
	};
})
.filter('FilterNotAlreadyUse', function() {
		/** Utilisé pour filtrer les élements issus des tables de réference.
		    Si l'element est déjà utilisé alors il n'apparait pas dans la liste des éléments de la table de référence
		    En entrée : 
		    			refValues les elements de la table de référence dans leur totalite
		    			userValues les elements deja utilises
		    En sortie : NewArray les elements à utilsable (si il y en a).
		 */
		return function(refValues, userValues) {
			var debug = false;
			
			
			var exist = false;
			/** 
		    	Si il y des elements à traiter 
			 */
			if (refValues && userValues) {
				if (debug) console.log("========== FilterTechno: Deb =======================");
				if (debug) console.log("refValues:[" + refValues.length + "] userValues:[" + userValues.length +"]");
				/** 
				    Si il ya au moins un element utilisé 
				 */
				if (userValues.length > 0) {
					var NewArray = [];
					/** Traite les elements :
					    Pour tous les elements de la table de reference 					     
					 */
					angular.forEach(refValues, function(TabRefElement) {
						exist = false;
						if (debug) console.log("forEach refValues : TabRefElement.tech_id:" + TabRefElement.tech_id + " " + TabRefElement.name);
						/** on Cherche laRefValue dans les elements deja utilisés */
						angular.forEach(userValues, function(UserElement) {
							if (debug) console.log("forEach userValues : TabRefElement.tech_id:" + TabRefElement.tech_id + " " + UserElement.tech_id + "  " + UserElement.name);
							if ((TabRefElement.tech_id == UserElement.tech_id) && (exist == false)) {
								if (debug) console.log("==> Trouvé " + UserElement.tech_id);
								exist = true;
							}
						});
						
						/** si l'element n'est PAS deja utilisé alors il sera utilisable  
						 */
						if (exist == false) {
							if (debug) console.log("==> push UserElement.tech_id:" + TabRefElement.tech_id);
							NewArray.push(TabRefElement);
						}
					});
					if (debug)  console.log("========== FilterTechno: End =======================");
					return NewArray;													
				/** 
				    Sinon on retourne la liste complete 
				 */
				} else {
					if (debug) console.log("==> push All");
					if (debug) console.log("========== FilterTechno: End =======================");
					return refValues;
				}
			} else return [];
		};
})
.filter('isAPicture', function() {
	/** Filtre utilisé dans user-list.html
	    Déclaration function(arrayIn, param1, param2, ...)
	    En entrée donc le tableau de départ et la paramètres du filtre
	    En retour NewArray qui sera utilisé par le "ng-repeat".
	    Dans ce cas on ne retourne que les "picture"
	 */
	return function(inputs, testedValue) {
		var NewArray = [];
		angular.forEach(inputs, function(TabRefElement){
			if (TabRefElement.rel == testedValue) {
				NewArray.push(TabRefElement);
			}
		});
		return NewArray;				
	};
});
