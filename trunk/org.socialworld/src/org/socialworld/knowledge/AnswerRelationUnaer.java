/*
* Social World
* Copyright (C) 2016  Mathias Sikos
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
package org.socialworld.knowledge;

import org.socialworld.conversation.Lexem;
import org.socialworld.conversation.Numerus;
import org.socialworld.conversation.Word;

public class AnswerRelationUnaer extends KnowledgeRelationUnaer implements IAnswer{

	private KnowledgeRelationUnaer originalRelation;

	public AnswerRelationUnaer(KnowledgeRelationUnaer original) {
		super(original);
		this.originalRelation = original;
	}

	public KnowledgeFact_Type getType() { return KnowledgeFact_Type.relationUnaer; }

	
	public void changeSubject(Lexem subject) {
		setSubject(subject);
	}

	public void setSpeechRecognitionsSubjectWord(Word subject) {
		setSubject(subject.getLexem(), subject.getNumerus());
	}

	public void changeSource(KnowledgeSource source) {
		setSource( source);
	}

	private void setSubject(Lexem subject, Numerus numerus) {
		setSubjectsLexem(subject);
		setSubjectsNumerus(numerus);
	}

}
