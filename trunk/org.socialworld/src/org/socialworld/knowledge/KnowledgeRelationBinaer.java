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

import java.util.ArrayList;
import java.util.List;

import org.socialworld.calculation.Value;
import org.socialworld.conversation.Lexem;
import org.socialworld.conversation.Numerus;
import org.socialworld.conversation.Word;

public class KnowledgeRelationBinaer extends KnowledgeRelation {

	private Lexem object;
	private Numerus numerusObject;

	public KnowledgeRelationBinaer(Value subject, Value verb, Value adverb, Value object) {
		super(subject, verb, adverb);
		this.object = translateToLexem(object);
	}
	
	KnowledgeRelationBinaer(KnowledgeRelationBinaer original) {
		super(original);
		if (original != null) {
			// TODO copy
		}
	}
	
	KnowledgeFact copy() {
		return new KnowledgeRelationBinaer(this);
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

	public Word getObject() {
		return object.getWord(numerusObject);
	}
	
	List<Lexem> getLexems() {
		List<Lexem> result = new ArrayList<Lexem>();
		result.add(getLexemSubject());
		result.add(this.object);
		
		return result;
	}



}