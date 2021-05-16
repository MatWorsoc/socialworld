/*
* Social World
* Copyright (C) 2020  Mathias Sikos
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
package org.socialworld.objects.concrete.eatable.fruits;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.attributes.Direction;
import org.socialworld.attributes.percipience.Percipience;
import org.socialworld.attributes.percipience.PercipienceType;
import org.socialworld.objects.State;
import org.socialworld.objects.concrete.StateAppearance;
import org.socialworld.objects.concrete.StateComposition;
import org.socialworld.objects.concrete.StateEatable;
import org.socialworld.objects.concrete.StatePerceptible;
import org.socialworld.objects.concrete.eatable.Fruit;
import org.socialworld.objects.properties.IThrowable;
import org.socialworld.tools.Generation;
import org.socialworld.tools.StringPair;

public class Apple extends Fruit implements IThrowable {

///////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////meta information    ////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	private static Apple singletonDummyForGenerationTools;
	private static StringPair[] propertiesMetaInfos = new StringPair[]{
			} ;

	public static Apple getInstance(Generation calledFromGeneration) {
		if (singletonDummyForGenerationTools == null) {
			singletonDummyForGenerationTools = new Apple(calledFromGeneration);
		}
		return singletonDummyForGenerationTools;
	}
	
	protected Apple(Generation calledFromGeneration) {
		super(calledFromGeneration);
		listOfPropertyMetaInfo = super.getPropertiesMetaInfos();
		for (int indexAdd = 0; indexAdd < propertiesMetaInfos.length; indexAdd++) {
			listOfPropertyMetaInfo.add(propertiesMetaInfos[indexAdd]);
		}
	}
	

///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////    creating instance for simulation    //////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	public Apple() {
		super();
	}

	protected int getLexemID() {
		// TODO set lexemID
		return 0;
	}

	public  float getTemperature() {return 20; };
	public  float getConsistence() {return 20; };
	public  float getFirmness() {return 20; };
	

	public float getGrip() { return 20; }
	public float getMass()  { return 20; }
	public float getVolume()  { return 20; }
	public float getForm()  { return 20; }

	
	protected State getInitState(String stateClassName) {
		if (stateClassName.equals(StatePerceptible.class.getName())) {
			Percipience percipience = new Percipience(PercipienceType.simobject, 100);
			return new StatePerceptible(percipience);
		}
		else if (stateClassName.equals(StateEatable.class.getName())) {
			return new StateEatable();
		}
		else if (stateClassName.equals(StateAppearance.class.getName())) {
			return new StateAppearance();
		}
		else if (stateClassName.equals(StateComposition.class.getName())) {
			return new StateComposition();
		}
		
		return null;
		
	}
	
///////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////    checking for interface  ///////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////

	protected boolean isInterface(String nameInterface) {
	
		boolean result;
		
		switch (nameInterface) {
			case IThrowable.NAME:
				result = (this instanceof IThrowable);
				break;
			default:
				result = super.isInterface(nameInterface);
		}
		
		return  result;
	
	}
	
}
