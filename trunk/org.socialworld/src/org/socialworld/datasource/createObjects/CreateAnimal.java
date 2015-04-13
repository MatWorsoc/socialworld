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
package org.socialworld.datasource.createObjects;

import org.socialworld.datasource.pool.AttributeArrayPool;
import org.socialworld.datasource.pool.AttributeCalculatorMatrixPool;
import org.socialworld.objects.Animal;
import org.socialworld.objects.StateAnimal;
import org.socialworld.objects.WriteAccessToAnimal;

/**
 * @author Mathias Sikos
 *
 */
public class CreateAnimal extends CreateSimulationObjects {

	@Override
	public  Animal getObject(int objectID) {
		StateAnimal state = new StateAnimal();
		initState(state);
		
		Animal createdAnimal = new Animal(objectID, state);
		WriteAccessToAnimal animal = new WriteAccessToAnimal(createdAnimal, state);
		initObject(animal, objectID);	
		
		return createdAnimal;
	}

	protected void initObject(WriteAccessToAnimal object, int objectID) {
		
		super.initObject(object, objectID);

		int indexACMP;
		double gauss_value;

		gauss_value = random.nextGaussian();
		indexACMP = mapGaussToIndex(gauss_value, AttributeCalculatorMatrixPool.CAPACITY_ACMP_ARRAY);

		object.setMatrix(	
				AttributeCalculatorMatrixPool.getInstance().getMatrix(indexACMP),
				this);
		
	}

	protected void initState(StateAnimal state) {

		super.initState(state);		

		int indexAAP;
		double gauss_value;

		gauss_value = random.nextGaussian();
		indexAAP = mapGaussToIndex(gauss_value, AttributeArrayPool.CAPACITY_AAP_ARRAY);
		state.setAttributes(
				AttributeArrayPool.getInstance().getArray(indexAAP));
		
	}

}
