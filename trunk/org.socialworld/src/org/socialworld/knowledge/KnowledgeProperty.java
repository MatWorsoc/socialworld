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
package org.socialworld.knowledge;

import java.util.ArrayList;
import java.util.List;

import org.socialworld.calculation.Value;
import org.socialworld.collections.ValueArrayList;
import org.socialworld.conversation.Lexem;

public class KnowledgeProperty extends KnowledgeFact {

	private KnowledgeFact_Criterion criterion;
	private KnowledgeFact_Lexems lexems;
	
	public KnowledgeProperty(Value criterion, ValueArrayList values ) {
		this.criterion = KnowledgeFact_Criterion.getName( (int) criterion.getValue());
		List<Lexem> lexems = new ArrayList<Lexem>();
		for (int index =  0; index < values.size(); index++) {
			lexems.add(translateToLexem(values.get(index)));
		}
		this.lexems = new KnowledgeFact_Lexems(lexems);
	}

	public KnowledgeProperty(KnowledgeFact_Criterion criterion, KnowledgeFact_Lexems lexems ) {
		this.criterion = criterion;
		this.lexems = lexems;
	}
	
	protected KnowledgeProperty(KnowledgeProperty original) {
		if (original != null) {
			this.criterion  = original.getCriterion();
			this.lexems = new KnowledgeFact_Lexems(original.getLexems());
			this.setSource(original.getSource());
			this.setValid(original.isItemValid());
			this.resetAccessCount();
		}
	}
	
	KnowledgeFact copy() {
		return new KnowledgeProperty(this);
	}
	
	List<Lexem> getLexems() {
		return this.lexems.getLexems();
	}
	
	
	protected KnowledgeFact_Criterion getCriterion() {
		return criterion;
	}
	
	protected boolean equals(KnowledgeProperty b) {
		return ( this.criterion == b.criterion & this.lexems.equals(b.lexems) );
	}
	

}