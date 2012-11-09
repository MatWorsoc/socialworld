/**
 * 
 */
package org.socialworld.core;


import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.ListIterator;

import org.socialworld.objects.SimulationObject;

/**
 * Manages the actions of an {@link SimulationObject}.
 * 
 * @author Mathias Sikos (tyloesand)
 */
public class ActionHandler  {

	public static final int ACTIONHANDLER_RETURN_ACTIONDONE = 0;
	public static final int ACTIONHANDLER_RETURN_ACTIONISGOINGON = 1;
	public static final int ACTIONHANDLER_RETURN_ACTIONYETEXECUTED = 2;
	public static final int ACTIONHANDLER_RETURN_NOACTION = -1;

	/**
	 * the simulation object whose actions are managed
	 */
	private SimulationObject object;

	/**
	 * the list where all object's actions are inserted (ordered by action's
	 * time and priority)
	 */
	private List<Action> actionList;

	/**
	 * the action that actually is handled (executed).
	 */
	private Action actualAction;

	/**
	 * the action that has been executed at last.
	 * needed for decision whether 2 (or more) actions belong together (linked actions) 
	 */
	private Action lastExecutedAction;

	/**
	 * the second of the actual minute.
	 *  it is used for decision whether this action handler has yet executed an action within the actual time step
	 */
	private byte secondOfTheActualMinute;
	
	
	/**
	 * the latest time (in milliseconds) when the actual action's execution should start (later it would become invalid)
	 * 
	 */
	private long latestExecutionTime;
	
	public ActionHandler(final SimulationObject simulationObject) {
		this.actualAction = null;
		this.actionList = new ArrayList<Action>();
		this.object = simulationObject;
	}



	/**
	 * The method gets the first action element from action list and lets the according
	 * object execute the action.
	 * 
	 * @param secondOfTheActualMinute
	 * 
	 * @return int (code for execution state)
	 */
	public int doActualAction(byte actualSecond ) {
		Action action;
		
		
		if (this.actionList.size() > 0)		
			action = this.actionList.get(1);
		else
			return ACTIONHANDLER_RETURN_NOACTION;

		// if there has been executed an action in the actual time step
		// then determine, whether there are linked action
		// if there are no linked actions return without execution of a further action
		if (actualSecond == this.secondOfTheActualMinute) {
			if (action == this.lastExecutedAction.getLinkedAction() )
				;
			else	return ACTIONHANDLER_RETURN_ACTIONYETEXECUTED;
		}
		
		this.actualAction = action;

		// execute the actual action
		if (this.actualAction != null) {
			this.object.doAction(this.actualAction);
			// if the actual action is executed completely
			// then assign it to the last executed action member
			// and remove it from the list
			if (this.actualAction.getRemainedDuration() == 0) {
				this.lastExecutedAction = this.actualAction;
				this.actionList.remove(this.actualAction);
				return ACTIONHANDLER_RETURN_ACTIONDONE;
			}
			else
				return ACTIONHANDLER_RETURN_ACTIONISGOINGON;
		}
		return ACTIONHANDLER_RETURN_NOACTION;

	}

	/**
	 * The method adds an action element to the action list of a simulation
	 * object.
	 *  Therefore it compares the new action element to the priority and time of the list's actions
	 *  and inserts the new action elements according to the action list's order
	 * 
	 * @param newAction
	 */
	public void insertAction(final Action newAction) {
		
		long minTimeInMilliseconds;
		long maxTimeInMilliseconds;
		int priority;
		long duration;
		
		Action listedAction;
		
		ListIterator<Action> iterator;
		int currentIndex;
		
		if (newAction == null ) return;
		
		iterator = this.actionList.listIterator();
		
		minTimeInMilliseconds = newAction.getMinTime().getTotalMilliseconds();
		maxTimeInMilliseconds = newAction.getMaxTime().getTotalMilliseconds();
		priority = newAction.getPriority();
		duration = newAction.getDuration();
		
		while (iterator.hasNext()) {
			currentIndex = iterator.nextIndex() ;
			listedAction = iterator.next();
			
			if ( listedAction.getPriority() < priority ) 
				if ( (listedAction.getMaxTime().getTotalMilliseconds() + listedAction.getRemainedDuration()) <
						minTimeInMilliseconds )
					continue;
				else
					;
			else if ( listedAction.getPriority() == priority ) 
				if ( (listedAction.getMaxTime().getTotalMilliseconds() + listedAction.getRemainedDuration()) <
						maxTimeInMilliseconds )
					continue;
				else
					if (currentIndex == 1) 
						if (this.actionList.indexOf(this.actualAction) == 1)
							continue;
			else // if ( listedAction.getPriority() > priority )
				if ( listedAction.getMinTime().getTotalMilliseconds()  <=
						( maxTimeInMilliseconds + duration ) )
					continue;
				else
					if (currentIndex == 1) 
						if (this.actionList.indexOf(this.actualAction) == 1)
							continue;

			// insert the new action element at the determined position (index)
			this.actionList.add(currentIndex, newAction);
			
			// if the new action element is inserted at start (that means rated as best (execute as first))
			// the action handler reports itself to the action master
			if (currentIndex == 1) {
				latestExecutionTime = maxTimeInMilliseconds;
				ActionMaster.getInstance().reportBetterAction(this, latestExecutionTime, priority);
			}
			return;
		}
		this.actionList.add(newAction);
		
	}

	/**
	 * The method searches for an action element that meets the properties of an
	 * action description. The description holds information for what attributes
	 * list is searched. If all given search attributes are found in one action
	 * element the action element will be returned.
	 * 
	 * @param actionDescription
	 *            (search criteria)
	 * @return action element that meets the search criteria
	 */
	public Action findAction(final SearchActionDescription actionDescription) {
		Action action;
		Action noAction;
		Iterator<Action> iterator;

		noAction = null;
		
		iterator = this.actionList.iterator();
		
		while (iterator.hasNext()) {
			action = iterator.next();
			if (action == null) {
				break;
			}

			if (actionDescription.isSearchByType()) {
				if (action.getType() != actionDescription.getType()) {
					continue;
				}
			}
			if (actionDescription.isSearchByMode()) {
				if (action.getMode() != actionDescription.getMode()) {
					continue;
				}
			}
			if (actionDescription.isSearchByTarget()) {
				if (action.getTarget() != actionDescription.getTarget()) {
					continue;
				}
			}
			if (actionDescription.isSearchByIntensity()) {
				if (action.getIntensity() != actionDescription.getIntensity()) {
					continue;
				}
			}
			if (actionDescription.isSearchByPriority()) {
				if (action.getPriority() != actionDescription.getPriority()) {
					continue;
				}
			}
			if (actionDescription.isSearchByMinTime()) {
				if (action.getMinTime() != actionDescription.getMinTime()) {
					continue;
				}
			}
			if (actionDescription.isSearchByMaxTime()) {
				if (action.getMaxTime() != actionDescription.getMaxTime()) {
					continue;
				}
			}
			if (actionDescription.isSearchByDirection()) {
				if (action.getDirection() != actionDescription.getDirection()) {
					continue;
				}
			}
			if (actionDescription.isSearchByDuration()) {
				if (action.getDuration() != actionDescription.getDuration()) {
					continue;
				}
			}

			return action;
		}

		return noAction;
	}
}