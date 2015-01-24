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
package org.socialworld.actions.say;

import org.socialworld.actions.ActionMode;
import org.socialworld.actions.ActionPerformer;
import org.socialworld.attributes.AttributeArray;
import org.socialworld.calculation.Type;
import org.socialworld.calculation.Value;
import org.socialworld.calculation.Vector;
import org.socialworld.knowledge.Acquaintance;
import org.socialworld.knowledge.Acquaintance_Attribute;
import org.socialworld.knowledge.Answer;
import org.socialworld.objects.Human;

/**
 * German:
 * Die Klasse Say ist von der abstrakten Klasse ActionPerformer abgeleitet.
 * 
 * Die Klasse Say dient der Wirksamwerdung der Aktion,
 *  n�mlich als Argument f�r das zur Aktion geh�rende Ereignis.
 *
 *  In der Ausf�hrungsmethode perform() werden im Falle einer Antwort
 *   - der (Gespr�chs)partner (ein Objekt der Klasse Human)
 *   - die Richtung (in die gesprochen wird)
 *   - die Antwort
 *   f�r den Standardzugriff aus dem Ereignis heraus bereitgestellt.
 *   
 *  In der Ausf�hrungsmethode perform() werden im Falle einer Frage
 *   - der (Gespr�chs)partner (ein Objekt der Klasse Human)
 *   - die Richtung (in die gesprochen wird)
 *   - die Frage (als Satz (also String))
 *   f�r den Standardzugriff aus dem Ereignis heraus bereitgestellt.
 *   
 *   F�r die Bereitstellung der Parameter ist es unerheblich, ob die Antwort bzw. die Frage
 *    normal gesprochen, gefl�stert oder geschrien wird.
 *     Diese Unterscheidung steckt bereits im EventType des Ereignisses.
 *    
 * Die Antwort auf eine Frage wird in Abh�ngigkeit der Beziehung zum Gespr�chspartner
 *  (also die Qualit�t der Bekanntschaft) manipuliert.
 *  Dadurch wird erreicht, dass der Antworter auf eine Frage nicht grunds�tzlich gleich antwortet.
 *  
 * @author Mathias Sikos
 *
 */
public class Say extends ActionPerformer {

    public Say (ActionSay action) {
    	super(action);
    }

	/* (non-Javadoc)
	 * @see org.socialworld.actions.ActionPerformer#perform()
	 */
	@Override
	public void perform() {
		
		
		if (!isValid()) {
	 		ActionSay originalAction;
			Human actor;
			Human partner;
			ActionMode mode;
			
			Vector direction;
			String question;
			
			originalAction = (ActionSay) getOriginalActionObject();
			actor = (Human) originalAction.getActor();
			mode = originalAction.getMode();
			partner = (Human) originalAction.getTarget();
	
			direction = actor.getPosition().getDirectionTo(partner.getPosition());
			
			switch (mode) {
				case answerNormal:
				case answerScream:
				case answerWhisper:
					
					Answer answer;
				
					question = originalAction.getQuestion();
	
					answer =  actor.getAnswerForQuestion(question);
					manipulateAnswer(actor, answer, partner);
		
					setMaxParam(3);
					setParam(0, new Value(Type.vector, "direction", direction));
					setParam(1, new Value(Type.simulationObject, "partner", partner));
					setParam(2, new Value(Type.answer, "answer", answer));
					
					setValid();
					
					break;
					
				case askNormal:
				case askScream:
				case askWhisper:
				
					question = originalAction.getQuestion();

					setMaxParam(3);
					setParam(0, new Value(Type.vector, "direction", direction));
					setParam(1, new Value(Type.simulationObject, "partner", partner));
					setParam(2, new Value(Type.string, "question", question));
					
					setValid();
							
					break;
					
				default:
					
			}
		}
	}

	private void manipulateAnswer(final Human actor, Answer answer, final Human partner) {
		
		Acquaintance acquaintance;
		acquaintance = actor.getAcquaintance(partner);
		
		// TODO
		// more complex, please
		// here only an example for an easy decision
		if (acquaintance.isAttributeValueLessThan(Acquaintance_Attribute.sympathy, AttributeArray.VALUE_MIDDLE) ) 
			answer.reduceToFactWithMinAccessCount();
		else if (acquaintance.isAttributeValueGreaterThan(Acquaintance_Attribute.sympathy, AttributeArray.VALUE_MIDDLE) ) 
			answer.sortBySource();
		else answer.reduceToFactWithMaxAccessCount();
	}

}
