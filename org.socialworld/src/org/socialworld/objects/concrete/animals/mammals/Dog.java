/*
* Social World
* Copyright (C) 2019  Mathias Sikos
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
package org.socialworld.objects.concrete.animals.mammals;

import org.socialworld.attributes.percipience.Percipience;
import org.socialworld.attributes.percipience.PercipienceType;
import org.socialworld.objects.State;
import org.socialworld.objects.concrete.StatePerceptible;
import org.socialworld.objects.concrete.animals.Mammal;
import org.socialworld.objects.concrete.animals.StateSeer;

public class Dog extends Mammal {

	protected int getLexemID() {
		// TODO set lexemID
		return 0;
	}

	protected State getInitState(String stateClassName) {
		if (stateClassName.equals(StatePerceptible.class.getName())) {
			Percipience percipience = new Percipience(PercipienceType.simobject);
			return new StatePerceptible(percipience);
		}
		else if (stateClassName.equals(StateSeer.class.getName())) {
			return new StateSeer();
		}
		
		return null;
		
	}

}