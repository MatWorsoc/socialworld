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
package org.socialworld.actions;

import java.util.List;

import org.socialworld.calculation.IObjectReceiver;
import org.socialworld.calculation.ObjectRequester;
import org.socialworld.calculation.SimulationCluster;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.descriptions.Action2PerformerAssignment;
import org.socialworld.calculation.descriptions.Action2PerformerDescription;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.core.IEventParam;
import org.socialworld.objects.SimulationObject;

/**
 * @author Mathias Sikos
 * 
 * German:
 * ActionPerformer ist die Basisklasse (abstrakte Klasse) für die Wirksamwerdung von Aktionen der Simlationsobjekte.
 * Sie implementiert das Interface IEventParam
 *  und funktioniert damit als Eigenschaft von aus Aktionen abgeleiteten Ereignissen.
 * 
 * Die Ausführung von Aktionen besteht aus 2 Schritten
 * a) Einleitung der Ausführung
 * b) Wirksamwerden der Aktion
 * 
 * a) Einleitung der Ausführung:
 * Der ActionMaster führt die ActionHandler aller Simulationsobjekte
 *  und weist den jeweiligen ActionHandler an, mit der Ausführung einer Aktion zu beginnen bzw. eine Aktion fortzusetzen.
 * Der ActionHandler sorgt dafür, 
 *  dass für das auszuführende Aktionsobjekt (Ableitung von AbstractAction) die Methode perform() aufgerufen wird.
 * Die Methode perform() ist abstract und muss in allen Ableitungen implementiert werden/sein.
 * Die Methode perform() führt Vorabprüfungen der Daten zur Aktion durch, 
 *  erzeugt das zugehörige Performer-Objekt von Unterklassen von ActionPerformer (siehe Schritt b),
 *  erzeugt die auszulösenden Ereignisse, 
 *  fügt den Ereignissen das Performerobjekt als Ereigniseigenschaft hinzu,
 *  und trägt diese in die Ereignisverwaltung (EventMaster) ein (siehe Schritt b).
 *  
 * b)  Wirksamwerden der Aktion
 * Es gilt der Grundsatz, dass alle Aktionen durch ihre Ereignisverarbeitung wirksam werden.
 * Im Schritt a) wurden Ereignisse zu den Aktionen in die Ereignisverwaltung eingetragen.
 * Die Ereignisverwaltung arbeitet die Ereignisse nach ihren Regeln ab.
 * Für jedes Event der Klasse EventToCandidates, also von Aktionen ausgelöste Ereignisse, 
 *  wird die evaluate-Methode des dem Ereignis zugeordenten Performers (Ableitung der Klasse ActionPerformer) aufgerufen.
 * Diese wiederum ruft die (in der Klasse ActionPerformer abstrakte) Methode perform() im Performerobjekt auf.
 * Diese Methode ermittelt die für die Ereignisverarbeitung benötigten Werte 
 * 	aus dem Aktionsobjekt, dem ausführenden Objekt (also dem Akteur) und ggf. dem Zielobjekt. 
 * Diese Werte werden standardisiert in einer Liste abgelegt 
 *  und können vom Ereignis über Standardmethoden ausgelesen werden.
 * Schließlich werden für die Ereignisse ihre Wirkung auf die Simulationsobjekte und ggf. Reaktionen ermittelt.
 *  
 * Die Klasse ActionPerformer ist die Basisklasse für die Aktionsobjekte des Schrittes b), 
 *  enthält also die Daten zur von der Ereignisverarbeitung ausgelösten Berechnung der Auswirkungen.
 *  
 * Die Daten werden in der Liste eventParams (eine ValueArrayList (List<Value>)) abgelegt.
 * Jeder Value dieser List hat einen Namen, über den das Event genau auf den gewünschten Wert zugreifen kann.
 * Damit kann die Ereignisverarbeitung standardisiert auf notwendige Daten zur auslösenden Aktion zugreifen.
 *
 */
public abstract class ActionPerformer implements IEventParam , IObjectReceiver{


    private ValueArrayList eventParams;
    private boolean evaluated = false;
    
    private boolean actionPropertiesAreRequested = false;
    private ValueArrayList actionAndActorProperties;
    		
    private AbstractAction action;
    
	protected int requestValueID = 0;
	protected ObjectRequester objectRequester = new ObjectRequester();

    public ActionPerformer (AbstractAction action) {
    	this.action = action;
    	this.actionAndActorProperties = new ValueArrayList();
    	this.eventParams = new ValueArrayList();
    }
    
    protected abstract void perform();
    
	public int getPriority() {
		return this.action.getPriority();
	}

	public void answerPropertiesRequest(ValueArrayList properties) {
		
		if (actionPropertiesAreRequested) {
			choosePropertiesFromPropertyList(properties);
		}
		
	}
    
    protected abstract void choosePropertiesFromPropertyList(ValueArrayList properties);
    
    protected void addProperty(Value value) {
    	actionAndActorProperties.add(value);
    }
    
	/* (non-Javadoc)
	 * @see org.socialworld.core.IEventParam#evaluate()
	 */
	@Override
   public final void evaluate() {
		
		
		if (!this.isEvaluated()) {
		
			SimulationObject actor =  this.action.getActor();
			ActionMode actionMode = this.action.getMode();
			
			actionPropertiesAreRequested = true;
			actor.requestPropertyList(SimulationCluster.action, this);
			this.action.requestPropertyList(this);
			actionPropertiesAreRequested = false;
			
			Action2PerformerDescription descriptionForMode =
					Action2PerformerAssignment.getInstance().getAction2PerformerDescription(actionMode);
			
			addParam(new Value(Type.simulationObject, Value.VALUE_BY_NAME_EVENT_CAUSER, actor));
			
			for (int i = 0; i < descriptionForMode.countFunctions(); i++) {
				addParam(descriptionForMode.getFunction(i).calculate(actionAndActorProperties));
			}
			
	    	perform();
	    	
		}
		
    }
    
	/* (non-Javadoc)
	 * @see org.socialworld.core.IEventParam#isValid()
	 */
	@Override
    public boolean isEvaluated() {
		return evaluated;
	}
	
	public Value getParamListAsValue() {
		return new Value(Type.valueList, Value.VALUE_BY_NAME_EVENT_PARAMS, eventParams);
	}
	
	public ValueArrayList getParamList() {
		return this.eventParams;
	}

	public Value getParam(String name) {
		return eventParams.getValue(name);
	}

	/* (non-Javadoc)
	 * @see org.socialworld.core.IEventParam#getParam(int)
	 */
	@Override
	public Value getParam(Type type) {
		// 1 ... always first occurence
		return eventParams.getValue(type, 1);
	}

	protected  void addParam(Value param) {
		this.eventParams.add(param);
	}

	protected void setParam(Value param) {
		int indexParam = findParam(param.getName());
		if (indexParam >= 0)
			setParam(indexParam, param);
		else
			addParam( param);
	}
	
	private int findParam(String name) {
		return this.eventParams.findValue(name);
	}
	
	/* (non-Javadoc)
	 * @see org.socialworld.core.IEventParam#find(org.socialworld.calculation.Type)
	 */
	private void setParam(int index, Value param) {
			this.eventParams.set(index, param);
	}
	
	
	protected void setEvaluated() {
		this.evaluated = true;
	}
	
	protected AbstractAction getOriginalActionObject() {
		return this.action;
	}

	
    public abstract List<SimulationObject> getTargets();

	@Override
	public int receiveObject(int requestID, Object object) {
		objectRequester.receive(requestID, object);
		return 0;
	}

}
