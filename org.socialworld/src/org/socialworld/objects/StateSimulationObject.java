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
package org.socialworld.objects;

import org.socialworld.attributes.Position;
import org.socialworld.calculation.Vector;
import org.socialworld.calculation.application.PositionCalculator;
import org.socialworld.core.Event;
import org.socialworld.objects.access.GrantedAccessToProperty;
import org.socialworld.objects.access.HiddenSimulationObject;
import org.socialworld.propertyChange.ListenedBase;

/**
 * @author Mathias Sikos
 *
 */
public class StateSimulationObject extends ListenedBase {
	
	private SimulationObject object;
	private	WriteAccessToSimulationObject guard;
	
	private 	Position 		position;
	private		Vector directionMove;
	private 	float powerMove;
	
	private	int				influenceTypeByEventType[];
	private	int				reactionTypeByEventType[];
	private   int				state2ActionType;
	
	private GrantedAccessToProperty grantAccessToPropertyPosition[];
	
	public StateSimulationObject() {
		
		grantAccessToPropertyPosition = new GrantedAccessToProperty[1];
		grantAccessToPropertyPosition[0] = GrantedAccessToProperty.position;

	}
	
	final void setObject (SimulationObject object, WriteAccessToSimulationObject guard) {
		if (checkGuard(guard)) this.object = object;
	}
	
	final protected HiddenSimulationObject getMeWritableButHidden(GrantedAccessToProperty properties[]) {
		return guard.getMeHidden(properties);
	}
	
	final protected StateSimulationObject getMeReadableOnly() {
		return this;
	}
	
	final protected SimulationObject getObject() {
		return object;
	}
	
	final void setWriteAccess(WriteAccessToSimulationObject guard) {
		if (this.guard == null)  this.guard = guard;
	}

	final boolean checkGuard(WriteAccessToSimulationObject guard) {
		return (this.guard == guard);
	}

	final void setPosition(Position position, WriteAccessToSimulationObject guard) {
		if (checkGuard(guard)) {
			this.position = position;
		}
	}
	
	final public Position getPosition() {
		return new Position(this.position);
	}
	
	final void setMove(Vector direction, float power, WriteAccessToSimulationObject guard) {
		if (checkGuard(guard)) {
			this.directionMove = direction;
			this.powerMove = power;
		}
		
	}
	
	final public Vector getDirectionMove() {
		return new Vector(directionMove);
	}
	
	
	final public float getPowerMove() {
		return powerMove;
	}
	
	final void setInfluenceTypes (int types[], WriteAccessToSimulationObject guard) {
		if (checkGuard(guard)) {
			this.influenceTypeByEventType = types;
		}
	}

	final public int getInfluenceType(int eventType) {
		 return this.influenceTypeByEventType[eventType];
	} 

	final void setReactionTypes (int types[], WriteAccessToSimulationObject guard) {
		if (checkGuard(guard)) {
			this.reactionTypeByEventType = types;
		}
	}

	final public int getReactionType(int eventType) {
		 return this.reactionTypeByEventType[eventType];
	} 

	final void setState2ActionType (int type, WriteAccessToSimulationObject guard) {
		if (checkGuard(guard)) {
			this.state2ActionType = type;
		}
	}

	final public int getState2ActionType() {
		 return this.state2ActionType;
	} 

	void calculateEventInfluence(Event event) {
		
		// TODO react to the calculation methods return code
		PositionCalculator.calculatePositionChangedByEvent(event, getMeReadableOnly(), getMeWritableButHidden(grantAccessToPropertyPosition));
		
	}
	
}
