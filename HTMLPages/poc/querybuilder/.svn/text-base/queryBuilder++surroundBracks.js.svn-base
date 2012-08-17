/**
*Dependencies:
* The Html should include the following plugins:
*
**/

//Function to hide/unhide the contents of the next sibling tag
//The text content of this' child span tag is toggled between + and -  
toggleContent = function(e){

	targetContent = $(this).next();
	//debug('targetContent[' + targetContent.text() +']');
	if (targetContent.css('display') == 'none') {
		targetContent.slideDown(300);
		$(this).children('span').text('-');
	} else {
		targetContent.slideUp(300);
		$(this).children('span').text('+');
	}
	return false;
};			



/** Utility Function **/
//builds an object from json text
function getObject(json){
	if(json)
		return eval("("+ json +")");
	else 
		return null;
}


//gets an attribute value from json text
function getAttrValue(json, attrName){
	if(json && attrName){
		data = getObject(json);
		return eval("data." + attrName);
	}else
		return null;
}

/**********  Functions that add event handlers to the query builder html ************/
//The columns become draggable with ghosting
//and revert = true
//All columns are marked with class column
function makeColumnsDraggable(){
		$(".column")
			.attr("title", "Draggable column")
			.addClass("draggable")
			;

	$('.column').Draggable({
		zIndex: 	1000,
		ghosting:	true,
		opacity: 	0.4,
		revert: true
	});
}


//All the clause nodes under the query tree clause
//will be able to accept the draggable column
//Contains the ondrop call back that calls acceptDraggableToQueryBuilderTree  
function makeQueryTreeClauseNodesDroppable(){

	$('#columns,#filters,#groups,#sorts').Droppable({
		accept: 'column',
		hoverclass: 'droppablehover',
		ondrop:	function(drag){
				//alert('dropping ' + $(drag).text());
				func = new acceptDraggableToQueryBuilderTree(drag, this);
				func.invoke();
		}
	});
	
}

//Generic functionality to make a group of items sortable.
//The item can be moved around within the group.
//sortGroup_jquery: the jquery object that contains a group that accepts the sortable item (to be dragged around). 
//itemClass: the class  of the items that the sortGroup_jquery will accept as a droppable.
function makeSortable(sortGroup_jquery, itemClass){
	sortGroup_jquery.Sortable(
		{
			accept: itemClass,
			helperclass: 'sortHelper',
			activeclass : 	'sortableactive',
			hoverclass : 	'sortablehover',
			//handle: 'div.itemHeader',
			tolerance: 'pointer',
			onChange : function(ser){
				reValidateFilterElementsAfterSort(ser);
			},
			onStart : function(){
				$.iAutoscroller.start(this, document.getElementsByTagName('body'));
			},
			onStop : function(){
				$.iAutoscroller.stop();
			}
		}
	);
}
/**********  END Functions that add event handlers to the query builder html ************/


/*********** Functions manipulating the query builder dom *************************/

//If the passed clauseId allows duplicate column drop
function isDuplicateColAllowed(clauseId){
	return clauseId=='filters';	
}

//if the passed clauseId allows the user to sort it within its group
function isSortableAllowed(clauseId){
	//return true;
	return clauseId!='filters';
}

//If this clauseId represents the sort clause of the query builder tree
function isSortClause(clauseId){
	return clauseId == 'sorts';
}

//If this clauseId represents the filter clause of the query builder tree
function isFilterClause(clauseId){
	return clauseId == 'filters';
}

//Adds the dragged column to the droppable specifid by the clauseNode
function addColumnToQueryBuilderTreeNode(clauseNode, column){
	clauseId = $(clauseNode).attr('id');
	//var parentGroupClass = clauseId + 'Group'; 
	clauseIdColumnItemClass = clauseId + "GroupItem draggable";
	ul_jquery = $(clauseNode).next();
	columnName = $(column).text();
	columnType = $(column).attr('type');
	newItemId = clauseId + columnName; 

	//dont add if it already exists
	if( !isDuplicateColAllowed(clauseId) && document.getElementById(newItemId) ){
		alert('Column "' + columnName + '" already in list.');
	}else{
		ul_jquery.append("<div>" +columnName+ "</div>")
			.children('div:last')
			.attr('id',newItemId)
			.addClass(clauseIdColumnItemClass)
			;
		newElem_jquery = ul_jquery.children('div:last');
		if(isSortClause(clauseId)){
			newElem_jquery.addClass('sortAscending');
			newElem_jquery.dblclick( function(){
										$(this).toggleClass('sortAscending');
										$(this).toggleClass('sortDescending');
									});
		}else if(isFilterClause(clauseId)){
			newElem_jquery.removeClass('draggable');//Filter items not draggable for now
			newElem_jquery.addClass('filterExprError');//we'll remove this once the filter is updated via the modal
			newElem_jquery.addClass('filterClause');
			newElem_jquery.attr("data","{filterField: '" + columnName + "', dataType: '" + columnType + "'}");
			newElem_jquery.click(function(){
									 $(this).toggleClass('selected');
				    			});
			newElem_jquery.dblclick( function(){
										filterCriteriaModal.open(this)
									 });
			//simulate dblclick and open update filter criteria
			newElem_jquery.dblclick();
			//bind callback to adjust filtersGroup on element remove
			//filtCritElem = new filterClauseItem(newElem_jquery.get());
			newElem_jquery.bind('deleteClauseElement', function(){
					new filterClauseItem(this).onDelete();
					//alert('deleting ' + $(this).text());
				});
		}
		//add the new item to sortable only if its not a filter
		if(isSortableAllowed(clauseId))
			ul_jquery.SortableAddItem(newElem_jquery.get(0));
			
		//add right click menu
		newElem_jquery.contextMenu('clauseItemMenu', { 
														bindings:{
															'delete': function(t){
																//call back for cleanup before deleting element
																//currently used only by the filter clause element
																$(t).trigger('deleteClauseElement', t.target);
																$(t).unbind().remove();
															}
														}
													});
	}
}

//The Drag item will be added under the 
//tree's clause node identified by drop
//This internally uses the transfer effect and sets up the complete callback
//to perform addColumnToQueryBuilderTreeNode when the transfer effect completes
function acceptDraggableToQueryBuilderTree(drag, drop){
	this.drag = drag;
	this.drop = drop
	me = this;
	this.invoke = function(){
		//this generates the transfer to effect
		//driven by the transferer1 class
		$(drag).TransferTo({
			to:me.drop,
			className:'transferEffect', 
			duration: 400,
			complete: function(){
				//call back that will be executed after the transfer
				//effect finishes
				addColumnToQueryBuilderTreeNode(me.drop, me.drag);
			}
		});
	};
}

/**********  Functions handling the Filter Criteria  ****************/

//And object representation of the filter Clause element
//contains methods to copy data between the modal and the filter criteria clause
function filterClauseItem(element){
	me = this;

	this.element = element;

	this.filterElementData = getObject($(this.element).attr('data'));//this contains all the filter element's attributes

	this.updateFromFilterCriteriaModal = function (trigger){
			if($(trigger).val() == 'Accept'){
			
				if(! (filterCriteriaModal.validateInput()) ){
					return false;
				}
				
				filterElementDataJson = filterCriteriaModal.serializeFilterCriteria();
				me.filterElementData = getObject(filterElementDataJson);
				$(me.element).attr('data', filterElementDataJson);
				$(me.element).attr('title', me.toString()); 
				$(me.element).text( filterCriteriaModal.getShortFilterOperator(me.filterElementData.filterOperator) + '  ' + me.filterElementData.filterField );
				$(me.element).removeClass('filterExprError');
				//prepend a conjunction
				if(!me.isFirstFilter())
					$(me.element).before(me.getDefaultConjunctionHtml());
			}else{
				if(!(me.filterElementData.filterOperator))
					$(me.element).remove();//remove the element if its not initialized && modal update has been cancelled
			}
			return true;
		}

	//static
	this.getDefaultConjunctionHtml = function(){
		return "<div class='filtersGroupItem filterConjunction'>AND</div>";
	}

	//static
	this.getDefaultOpenBracketHtml = function(){
		return "<div class='filtersGroupItem filterBracket filterBracketOpen'>(</div>";
	}

	//static
	this.getDefaultCloseBracketHtml = function(){
		return "<div class='filtersGroupItem filterBracket filterBracketClose'>)</div>";
	}

	//static
	this.surroundSelectedFilterClausesWithBracket = function(){
		selectedFilters$ = $('#filtersGroup > .filterClause').filter('.selected');
		if(selectedFilters$.size() < 2){
			alert('Select 2 filters to successfully surround with ( )');
		}else{
			selectedFilters$.filter(':first')
				.before(  $(me.getDefaultOpenBracketHtml())
							.contextMenu('clauseItemMenu', { 
												bindings:{
													'delete': function(t){
														me.getCorrBracketPair$(t).unbind().remove();
														$(t).unbind().remove();
													}
												}
										 })
					   );

			selectedFilters$.filter(':last')
				.after(  $(me.getDefaultCloseBracketHtml())
							.contextMenu('clauseItemMenu', { 
												bindings:{
													'delete': function(t){
														me.getCorrBracketPair$(t).unbind().remove();
														$(t).unbind().remove();
													}
												}
										 })
				      );
			//toggle selection from selected items
			selectedFilters$.click();
		}
		
	}

	this.getCorrBracketPair$ = function(bracketElement){
		bkt$ = $(bracketElement);
		if(!bkt$.is('.filterBracket'))
			return null;
		else{
			moveForward = false;
			finalCounter = 1;
			if(bkt$.is('.filterBracketOpen')){
				moveForward = true;
				finalCounter = -1;
			}

				counter = 0;
				currElem$ = bkt$;
				
				//iterate until we find the bracket pair or we run out of siblings (shouldn't happen!)
				while (    currElem$.size() != 0
						&& counter != finalCounter 
				      ){

					//get the next element
					if(moveForward)
						currElem$ = currElem$.next();
					else
						currElem$ = currElem$.prev();

					//check if its open or close bracket
					if(currElem$.is('.filterBracketOpen'))
						counter++;
					if(currElem$.is('.filterBracketClose'))
						counter--;

				}
				return currElem$;
				
		}
	}

	this.updateToFilterCriteriaModal = function (){
			filterCriteriaModal.deSerializeFilterCriteria(me.filterElementData);
		}
	
	this.toString = function(){
		title = me.filterElementData.filterField + ' ' + me.filterElementData.filterOperator 
		if(me.filterElementData.fieldValue1)
			title += ' ' + me.filterElementData.fieldValue1;
		if(me.filterElementData.fieldValue2)//with between operator
			title += ' AND ' + me.filterElementData.fieldValue2;
		
		return title;
	}

	this.isFirstFilter = function(){
		return me.getPreviousFilterClause$().size() == 0;
	}

	this.isLastFilter = function(){
		return me.getNextFilterClause$().size() == 0;
	}

	
	//call back for when the filter element is deleted
	this.onDelete = function(){
		if(me.isFirstFilter()){
			me.getNextConjunction$().remove();
		}else{
			me.getPreviousConjunction$().remove();
		}
	}
	
	//callback for when the filter element dragged/sorted
	this.onSort = function(){
		alert('element moved');
	}
	
	this.getPreviousConjunction$ = function(){
		currFilterElem$ = $(me.element).prev();
		while(currFilterElem$.size() > 0){
			if( currFilterElem$.is('.filterConjunction') )
				return currFilterElem$;
			currFilterElem$ = currFilterElem$.prev();
		}
		return currFilterElem$;
	}

	this.getNextConjunction$ = function(){
		currFilterElem$ = $(me.element).next();
		while(currFilterElem$.size() > 0){
			if( currFilterElem$.is('.filterConjunction') )
				return currFilterElem$;
			currFilterElem$ = currFilterElem$.next();
		}
		return currFilterElem$;
	}


	this.getPreviousFilterClause$ = function(){
		currFilterElem$ = $(me.element).prev();
		while(currFilterElem$.size() > 0){
			if( currFilterElem$.is('.filterClause') )
				return currFilterElem$;
			currFilterElem$ = currFilterElem$.prev();
		}
		return currFilterElem$;
	}


	this.getNextFilterClause$ = function(){
		currFilterElem$ = $(me.element).next();
		while(currFilterElem$.size() > 0){
			if( currFilterElem$.is('.filterClause') )
				return currFilterElem$;
			currFilterElem$ = currFilterElem$.next();
		}
		return currFilterElem$;
	}


	this.getNextConjunctionOrFilter$ = function(){
		currFilterElem$ = $(me.element).next();
		while(currFilterElem$.size() > 0){
			if( currFilterElem$.is('.filterClause') ||  currFilterElem$.is('.filterConjunction'))
				return currFilterElem$;
			currFilterElem$ = currFilterElem$.next();
		}
		return currFilterElem$;
	}
	
}


function reValidateFilterElementsAfterSort(ser){
	//alert('modified filter sequence');

	$('#filtersGroup').children()
		.each(function(index, elem){
				if($(this).is('.filterClause')){
					thisFilterClauseItem = new filterClauseItem(this);
					first = thisFilterClauseItem.isFirstFilter();
					last = thisFilterClauseItem.isLastFilter();
					//if this is the first filter
					if(first){
					 //remove previous conjunction
					 thisFilterClauseItem.getPreviousConjunction$().remove();
					}
					//if this is the last filter
					else if(last){
					 //remove next conjunction
					 thisFilterClauseItem.getNextConjunction$().remove();
					}
					//if we dont have a conjunction after the current filter element insert one
					if(!last && thisFilterClauseItem.getNextConjunctionOrFilter$().is('.filterClause')){
						$(this).after(thisFilterClauseItem.getDefaultConjunctionHtml());
					}
				}			  	
			 });

}

/*********** Functions related to the Modal ********************/
filterCriteriaModal = {

	//Copies the filter element from the filter element
	//to the modal screen
	deSerializeFilterCriteria: function (filterElementData){
	
		index = 0;
		filterCriteriaModal.clear();
		$('#filterCriteriaModal #filterField').val(filterElementData.filterField);
		$('#filterCriteriaModal #filterField').attr('dataType', filterElementData.dataType);
		operator = filterElementData.filterOperator;
		if(operator)
			$('#filterCriteriaModal #filterOperator > option')
				.each(function(i){
					if( $(this).text() == operator)
						$(this).attr('selected','selected');
					else
						$(this).removeAttr('selected');
				});
		else{
			$('#filterCriteriaModal #filterOperator > option')
				.each(function(){
					if($(this).text() == '=')
						$(this).attr('selected','selected');
				});
		}
		value1 = filterElementData.fieldValue1;
		if(value1)
			$('#filterCriteriaModal #fieldValue1').val(value1);
		else
			$('#filterCriteriaModal #fieldValue1').val('');
		
		value2 = filterElementData.fieldValue2;
		if(value2)
			$('#filterCriteriaModal #fieldValue2').val(value2);
		else
			$('#filterCriteriaModal #fieldValue2').val('');
		
	
		//change modal based on field changes
		filterCriteriaModal.updateModalOnFilterFieldChange();
		filterCriteriaModal.updateModalOnFilterOperatorChange();
		return true;				
	},
	
	updateModalOnFilterFieldChange: function(){
		
		if( $('#filterCriteriaModal #filterField').attr('dataType') == 'date' ){
			$('a.dp-choose-date').show();
			$('#filterCriteriaModal #fieldValue1, #filterCriteriaModal #fieldValue2').attr('readonly','readonly');
		}else{
			$('a.dp-choose-date').hide();
			$('#filterCriteriaModal #fieldValue1, #filterCriteriaModal #fieldValue2').removeAttr('readonly');
		}
	},
	
	updateModalOnFilterOperatorChange: function(){
		//alert('updating modal on op change "' + $("#filterCriteriaModal #filterOperator>option:selected").text() + '"');
		operator = $("#filterCriteriaModal #filterOperator>option:selected").text();
		//only enable fieldValue2 for operator BETWEEN
		if( operator == 'BETWEEN'){
			$("#filterCriteriaModal #fieldValue1, #filterCriteriaModal #fieldValue2").dpSetDisabled(false);
			//show the input fields
			$('#filterCriteriaModal #fieldValue1, #filterCriteriaModal #fieldValue2').show();
		} else if(    ( operator == 'IS NULL')
				   || ( operator == 'IS NOT NULL')
		         ){
			$("#filterCriteriaModal #fieldValue1, #filterCriteriaModal #fieldValue2").dpSetDisabled(true);
			//hide the input fields
			$("#filterCriteriaModal #fieldValue1, #filterCriteriaModal #fieldValue2").hide();
			
		} else{
			$('#filterCriteriaModal #fieldValue1').dpSetDisabled(false);
	        $('#filterCriteriaModal #fieldValue2').dpSetDisabled(true);
	        $('#filterCriteriaModal #fieldValue1').show();
	        $('#filterCriteriaModal #fieldValue2').hide();
		}
	
	},
	
	validateInput: function(){
		//alert('validating');
		isSuccess = false;
		operator = $('#filterCriteriaModal #filterOperator > option:selected').text();
		if( operator == 'IS NULL' || operator == 'IS NOT NULL')
			isSuccess = true;
		else {
			isSuccess = filterCriteriaModal.validateInputField($('#filterCriteriaModal #fieldValue1').get());
			if( operator == 'BETWEEN'){
				isSuccess = filterCriteriaModal.validateInputField($('#filterCriteriaModal #fieldValue2').get());
			}
		}
		
		return isSuccess;
	},
	
	validateInputField: function(element){
		validationSuccess = false;
		//mandatory check
		if($(element).val() == ''){
			$(element).addClass('validationError');
			$('#validationErrorDetail').text('Missing required Value');
		}
		else{
			validationSuccess = true;
			$(element).removeClass('validationError');
			$('#validationErrorDetail').text('');
		}
		
		return validationSuccess;
	},
	
	//Converts the filter element on the modal to a string representation
	serializeFilterCriteria: function(){
	
		field = $('#filterCriteriaModal #filterField').val();
		dataType = $('#filterCriteriaModal #filterField').attr('dataType');
		operator = $('#filterCriteriaModal #filterOperator > option:selected').text();
		value1 = $("#filterCriteriaModal #fieldValue1").val();
		value2 = $("#filterCriteriaModal #fieldValue2").val();
		
		serializedFilterCriteria =  "{filterField: '" + field +
									"', dataType: '" + dataType + 
									"', filterOperator: '" + operator;
									
		if( $('#filterCriteriaModal #fieldValue1').parent().css('display') != 'none' ) 
			serializedFilterCriteria +=		"', fieldValue1: '" + value1;

		if( $('#filterCriteriaModal #fieldValue2').parent().css('display') != 'none' ) 
			serializedFilterCriteria +=		"', fieldValue2: '" + value2;

		serializedFilterCriteria += "'}";
		
		return serializedFilterCriteria;
	},


	getShortFilterOperator: function(longOperator){
		longOperators = ['BETWEEN','IS NOT NULL','IS NULL'];
		shortOperators = ['BT','!NU','NU'];
		index = longOperators.indexOf(longOperator);
		
		if(index >= 0)
			return shortOperators[index];
		else
			return longOperator;
	},


	//Creates a filter criteria element and copies data to the modal
	//Adds a callback to the modal/close to copy the updated data
	//back to the filter item under the filter clause
	open: function(filterCriteriaElement){
		thisFilterClauseItem = new filterClauseItem(filterCriteriaElement);
		thisFilterClauseItem.updateToFilterCriteriaModal();
		$('#accept, #cancel')
			.click(function(){
				if(thisFilterClauseItem.updateFromFilterCriteriaModal(this)){
					$('#filterCriteriaModal').jqmHide();
					$('#accept, #cancel').unbind();//we're done, so unbind
				}
			});
		
		$("#filterCriteriaModal").jqm({modal: true}).jqmShow();
	},


	initialSetup: function(){
		//attach datepicker to filterCriteriaModal's inputs
		$('#filterCriteriaModal #fieldValue1,#filterCriteriaModal #fieldValue2').datePicker();
		//show/hide field value inputs on operator change
		//$('#filterCriteriaModal #filterOperator').change(filterCriteriaModal.updateModalOnFilterOperatorChange);
		$('#filterCriteriaModal #filterOperator').change(filterCriteriaModal.updateModalOnFilterOperatorChange);
		
	},
	
	clear: function(){
		$('#filterCriteriaModal #validationErrorDetail').text('');
		$('#filterCriteriaModal #fieldValue1').removeClass('validationError');
		$('#filterCriteriaModal #fieldValue2').removeClass('validationError');
		$('#filterCriteriaModal #fieldValue1').text('');
		$('#filterCriteriaModal #fieldValue2').text('');
	}
}

/*********** END Function related to Modal *********************/

/********** END Functions handling the Filter Criteria  ****************/


/** Some basic rules using livequery **/
function filterElementRulesLiveQuery(){
	//all filter elements with filterExprError class will show invalid filter criteria
	//title
//	$('.filterExprError')
//		.livequery( function(){
//						$(this).attr('title', 'Invalid filter criteria!');
//				    },
//				    function(){
//				    	$(this).removeAttr('title');
//				    }
//				   );

	//allow filters to be selectable
	
	//ERROR! Doesn't work with IE after the element has been sorted.
	//$('.filterClause')
	//	.livequery( 'click',
	//				function(event){
	//					$(event.target).toggleClass('selected');
	//					alert('clicked');
	//			    }
	//			  );

	//toggle conjunction
	$('.filterConjunction')
		.livequery( 'dblclick',
					function(event){
						if($(event.target).text() == 'AND')
							$(event.target).text('OR');
						else
							$(event.target).text('AND');
				    }
				  );
	//all columns/groups/sorts group items are sortable
	$('.columnsGroupItem, .groupsGroupItem, .sortsGroupItem')
		.livequery(function(){
					$(this).attr('title','You may drag this item to re-sequence it inside this group');
				});

}
/** END Some basic rules using livequery **/
/*********** END Functions manipulating the query builder dom *************************/
