/*
* Social World
* Copyright (C) 2015  Mathias Sikos
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; either version 2
* of the License, or (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.  
*
* or see http://www.gnu.org/licenses/gpl-2.0.html
*
*/
package org.socialworld.actions.useHands;

import org.socialworld.actions.AbstractAction;
import org.socialworld.actions.ActionMode;
import org.socialworld.actions.ActionProperty;
import org.socialworld.actions.ActionType;
import org.socialworld.attributes.ActualTime;
import org.socialworld.attributes.Attribute;
import org.socialworld.attributes.Time;
import org.socialworld.calculation.Vector;
import org.socialworld.core.Event;
import org.socialworld.objects.SimulationObject;
import org.socialworld.objects.Human;

/**
 * @author Mathias Sikos
 *
 */
public class ActionAttack extends AbstractAction {
	private Attack attack;
	
	public ActionAttack(final ActionType type, final ActionMode mode,
			final SimulationObject target, 
			final float intensity, final Time minTime, final Time maxTime,
			final int priority, final long duration) {
		setBaseProperties(type,  mode,
				target, 
				intensity,  minTime, maxTime,
				 priority,  duration);
			
	}
	
	public ActionAttack(ActionAttack original) {
		setBaseProperties(original);
	}

	/* (non-Javadoc)
	 * @see org.socialworld.actions.AbstractAction#getConcreteProperty(org.socialworld.actions.ActionProperty)
	 */
	@Override
	public Object getConcreteProperty(ActionProperty prop) {
			return null;
		
	}

	/* (non-Javadoc)
	 * @see org.socialworld.actions.AbstractAction#perform()
	 */
	@Override
	public void perform() {
		IWeapon weapon;
		Vector directionChest;
		Vector directionView;
		Vector directionHit;

   		switch (type) {
		case useWeaponLeft:
			weapon = ((Human) actor).getRightHandWeapon();
			break;
		case useWeaponRight:
			weapon = ((Human)actor).getRightHandWeapon();
			break;
		default:
			weapon = null;
		}
		
   		if (weapon == null) return;

   		
    	// TODO
	  	directionChest = ((Human) actor).getDirectionChest();
    	directionView = ((Human) actor).getDirectionView();
    	directionHit = ((Human) actor).getDirectionView();

    	// TODO calculation intensity?
  		this.attack = new Attack(weapon, 
  								directionChest, directionView, directionHit,
  								intensity, ((Human)actor).getAttributes().get(Attribute.power));
   		
  		attack.perform();
	
		Event event;
  		
		event = new Event( getEventType(type, mode),    actor /* as causer*/,  ActualTime.asTime(),
						actor.getPosition(),  attack /* as optional parameter */);
		addEvent(event);
		
	}


	private int getEventType(ActionType type, ActionMode mode) {
		int eventType = 0;
		// TODO
		
	  	if (mode == ActionMode.club) {
	  		eventType = 1;
    	}
    	else    	
    		switch (type) {
    		case useWeaponLeft:
    			
    			switch (mode) {
    			case stab:
    				eventType = 2;
    				break;
    			case stroke:
    				eventType = 3;
    				break;
    			case backhand:
    				eventType = 4;
    				break;
    			default:	
    			}
    			
    			break;
    		case useWeaponRight:
       			switch (mode) {
    			case stab:
    				eventType = 5;
    				break;
    			case stroke:
    				eventType = 6;
    				break;
    			case backhand:
    				eventType = 7;
    				break;
    			default:
       			}
       			
    			break;
    		default:
    			
    		}

	
	  	return eventType;
	}
}
