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
package org.socialworld.actions.hear;

import org.socialworld.actions.AbstractAction;
import org.socialworld.actions.ActionMode;
import org.socialworld.actions.ActionType;
import org.socialworld.attributes.ActualTime;
import org.socialworld.attributes.Time;
import org.socialworld.conversation.PunctuationMark;
import org.socialworld.conversation.Talk_SentenceType;
import org.socialworld.core.EventByAction;
import org.socialworld.core.EventType;
import org.socialworld.objects.Human;
import org.socialworld.objects.SimulationObject;

/**
 * @author Mathias Sikos
 * 
 * German:
 * Die Klasse ActionHear ist von der abstrakten Klasse AbstractAction abgeleitet.
 * Alle Aktionsobjekte, die Zuh�ren und Verstegen beschreiben, geh�ren zu dieser Klasse.
 * Zur Beschreibung des H�rens f�hrt die Klasse die zus�tzlichen Eigenschaften
 * f�r den geh�rten Satz und das Zielobjekt.
 * Die Ausf�hrung der Aktion wird in der Klasse Hear geregelt, 
 * von der ein Objekt als Eigenschaft der Klasse ActionHear abgelegt ist.
 * 
 * Die Klasse ActionHear dient der Verwaltung der Aktion.
 * Die zugeh�rige Klasse Hear dient der Ausf�hrung der Aktion, 
 *  n�mlich als Argument f�r das zur Aktion geh�rende Event.
 *
 *  In der Ausf�hrungsmethode perform() wird der vom Zielobjekt gesprochene Satz ermittelt
 *   und in der Instanzvariablen sentence abgelegt. 
 *  Dabei wird im Falle des Zuh�rens direkt beim Zielobjekt ausgelesen, 
 *   im Falle des Verstehens in der eigenen Stuktur (Talk) des Akteurs.
 *  Danach wird das Ausf�hrungsobjekt der Klasse Hear erzeugt.
 *  Schlie�lich wird das Ereignis zur Aktion erzeugt, mit dem Ausf�hrungsobjekt als Argument.
 *  Das Ereignis wird in die Ereignisverwaltung (EventMaster) eingetragen.
 *  
 *  Der Name des Ereignis (EventType) 
 *   wird in Abh�ngigkeit von Aktionsmodus (ActionMode) und Satz ermittelt.
 *   
 *  Eine Aktion der Klasse ActionHear ist 
 *  a) das Zuh�ren (das Aufnehmen eines Satzes)
 *  oder
 *  b) das Verstehen eines Satzes (Information extrahieren und als Wissen ablegen)
 *
 */
public class ActionHear extends AbstractAction {

	private Hear hear;

	private String sentence;

	private SimulationObject target;

	public ActionHear(final ActionType type, final ActionMode mode,
			final float intensity, final Time minTime, final Time maxTime,
			final int priority, final long duration) {
		
		super(type,  mode,
			intensity,  minTime, maxTime,
			 priority,  duration);
		
	}
	
	public ActionHear(final ActionType type, final ActionMode mode,
			final SimulationObject target,
			final float intensity, final Time minTime, final Time maxTime,
			final int priority, final long duration) {
		
		super(type,  mode,
			intensity,  minTime, maxTime,
			 priority,  duration);
		
		this.setTarget(target);
	}
	
	public ActionHear(ActionHear original) {
		setBaseProperties(original);
	}


	public  void perform() {
		Human partner;
		EventByAction event;
		EventType eventType;
		
		partner = (Human) getTarget();

		switch (mode) {
		case listenTo:
			
			sentence = partner.getLastSaidSentence();
			eventType = getEventType( mode, sentence);
			
			break;
			
		case understand:

			sentence = ((Human) actor).getSentence(partner, Talk_SentenceType.partnersSentence);
			eventType = getEventType( mode, sentence);

			break;
			
		default:
			return;
		}
		
		if (eventType == EventType.nothing) return;
		
  		this.hear = new Hear(this);
  		
		event = new EventByAction(eventType,    actor /* as causer*/,  ActualTime.asTime(),
				actor.getPosition(),  hear /* as performer */);

		addEvent(event);
	
	}

	private EventType getEventType(ActionMode mode, String sentence) {
		
		switch (mode) {
		case listenTo:
			switch (PunctuationMark.getPunctuationMark(sentence)) {
			case dot: 
				return EventType.listenToStatement;
			case question:
				return EventType.listenToQuestion;
			case exclamation:
				return EventType.listenToInstruction;
			default:
				return EventType.nothing;
			}
		case understand:
			return EventType.understand;
		default:
			return EventType.nothing;
		}
	}
	

	public void setTarget(SimulationObject target) {
		this.target = target;
	}
	
	public SimulationObject getTarget() {
		return this.target;
	}
	
	public String getSentence() {
		return sentence;
	}
}
