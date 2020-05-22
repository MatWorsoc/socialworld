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

public class KnowledgeRelationTrinaer extends KnowledgeRelation {

	private Lexem object1;
	private Numerus numerusObject1;

	private Lexem object2;
	private Numerus numerusObject2;
	
	public KnowledgeRelationTrinaer() {
		
	}

	public KnowledgeRelationTrinaer(KnowledgeRelationTrinaer original) {
		if (original != null) {
			// TODO
		}
	}
	
	KnowledgeFact copy() {
		return new KnowledgeRelationTrinaer(this);
	}

	@Override
	public Word getVerb() {
		return getLexemVerb().getWord(getTense());
	}

	@Override
	public Word getAdverb() {
		return getLexemAdverb().getWord();
	}
	
	@Override
	public Word getSubject() {
		return getLexemSubject().getWord(getNumerusSubject());
	}

	public Word getObject1() {
		return object1.getWord(numerusObject1);
	}

	public Word getObject2() {
		return object2.getWord(numerusObject2);
	}
	
	Lexem[] getValues() {
		Lexem[] result = new Lexem[3];
		result[0] = getLexemSubject();
		result[1] = this.object1;
		result[2] = this.object2;
		
		return result;
	}

}