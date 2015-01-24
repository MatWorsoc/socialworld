/*
* Social World
* Copyright (C) 2014  Mathias Sikos
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


import org.socialworld.actions.AbstractAction;
import org.socialworld.actions.ActionMode;
import org.socialworld.actions.ActionType;
import org.socialworld.attributes.ActualTime;
import org.socialworld.attributes.Time;
import org.socialworld.calculation.Vector;
import org.socialworld.conversation.Talk_SentenceType;
import org.socialworld.core.EventByAction;
import org.socialworld.core.EventType;
import org.socialworld.objects.Human;
import org.socialworld.objects.SimulationObject;

/**
 * German:
 * Die Klasse ActionSay ist von der abstrakten Klasse AbstractAction abgeleitet.
 * Alle Aktionsobjekte, die Sprechen beschreiben, geh�ren zu dieser Klasse.
 * 
 * Zur Beschreibung des Sprechens f�hrt die Klasse die zus�tzlichen Eigenschaften
 *   die Frage, die im Falle eines Gespr�chs (Frage-Antwort-Wechsel) gestellt bzw. beantwortet wird,
 *   der Satz, der im Falle von Sagen/Reden gesprochen wird,
 *   den Gespr�chspartner,
 *   die Richtung.
 * Die Ausf�hrung der Aktion wird in der Klasse Say geregelt, 
 * von der ein Objekt als Eigenschaft der Klasse ActionSay abgelegt ist.
 * 
 * Die Klasse ActionSay dient der Verwaltung der Aktion.
 * Die zugeh�rige Klasse Say dient der Wirksamwerdung der Aktion, 
 *  n�mlich als Argument f�r das zur Aktion geh�rende Event.
 *
 *  In der Ausf�hrungsmethode perform() werden Frage bzw. Satz in den entsprechenden Instanzvariablen abgelegt.
 *   
 *  Danach wird das Ausf�hrungsobjekt der Klasse Say erzeugt.
 *
 *  Schlie�lich wird das Ereignis zur Aktion erzeugt, mit dem Ausf�hrungsobjekt als Argument.
 *  Das Ereignis wird in die Ereignisverwaltung (EventMaster) eingetragen.
 *  
 *  Der Name des Ereignis (EventType) 
 *   wird in Abh�ngigkeit von Aktionsmodus (ActionMode) ermittelt.
 *   
 *  Eine Aktion der Klasse ActionSay ist 
 *  a) das Sagen eines Satzes (Antwort oder Frage) in einem Gespr�ch 
 *  oder
 *  b) das Sagen eines Satzes ohne direkten Bezug eines Gespr�chs (also ohne Erwartung einer Erwiderung),
 *   aber ggf. durchaus zu einem Gespr�chsparnter, mit dem man gleichzeitig ein Gespr�ch f�hrt
 *  
 * 
 * @author Mathias Sikos
 *
 */
public class ActionSay extends AbstractAction {

	private Say say;
	
	private String question;
	private String sentence;
	
	private SimulationObject target;
	private Vector direction;
	
	public ActionSay(final ActionType type, final ActionMode mode,
			final SimulationObject target, final Vector direction,
			final float intensity, final Time minTime, final Time maxTime,
			final int priority, final long duration) {
		setBaseProperties(type,  mode,
				intensity,  minTime, maxTime,
				 priority,  duration);
			
			this.setDirection(direction);
			this.target = target;
	}
	
	public ActionSay(ActionSay original) {
		setBaseProperties(original);
		this.direction = original.direction;
	}


	public  void perform() {
		
		EventByAction event;
		final Human partner = (Human) target;
		
		switch (type) {
			case talk:
				
				switch (mode) {
					case answerNormal:
					case answerScream:
					case answerWhisper:
					
						question = ((Human) actor).getSentence(partner, Talk_SentenceType.partnersQuestion);
						if (question == null) return;
						
						break;
						
					case askNormal:
					case askScream:
					case askWhisper:
					
						question = ((Human) actor).getSentence(partner, Talk_SentenceType.myPlannedQuestion);
						if (question == null) return;
		
						break;
						
					default:
						
						return;
				}
				break;
				
			case say:
				
				switch (mode) {
					case normal:
						// TODO
						//sentence = 
						break;
					case scream:
						// TODO
						//sentence = 
						break;
					case whisper:
						// TODO
						//sentence = 
						break;
					default:
						return;
				}
				break;
				
			default:
				return;
		}

 		this.say = new Say(this);
  		
		event = new EventByAction(getEventType(type, mode),    actor /* as causer*/,  ActualTime.asTime(),
				actor.getPosition(),  say /* as performer */);

		addEvent(event);

	}



	private EventType getEventType(ActionType type, ActionMode mode) {
		switch (type) {
		case talk:
			
			switch (mode) {
				case answerNormal:
					return EventType.answerNormal;
				case answerScream:
					return EventType.answerScream;
				case answerWhisper:
					return EventType.answerWhisper;
				case askNormal:
					return EventType.askNormal;
				case askScream:
					return EventType.askScream;
				case askWhisper:
					return EventType.askWhisper;
					
				default:
					return EventType.nothing;
			}
			
		case say:
			
			switch (mode) {
				case normal:
					return EventType.sayNormal;
				case scream:
					return EventType.sayScream;
				case whisper:
					return EventType.sayWhisper;
				default:
					return EventType.nothing;
			}
			
		default:
			return EventType.nothing;
		}
		
	}
	



	/**
	 * @return the direction
	 */
	public Vector getDirection() {
		return this.direction;
	}

	/**
	 * @param direction
	 *            the direction to set
	 */
	public void setDirection(final Vector direction) {
		this.direction = direction;
	}

	public String getQuestion() {
		return question;
	}
	
	public SimulationObject getTarget() {
		return this.target;
	}
}
