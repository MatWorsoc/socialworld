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
package org.socialworld.knowledge;

//import java.util.ArrayList;
import java.util.List;

import org.socialworld.calculation.Value;
//import org.socialworld.collections.ValueArrayList;
import org.socialworld.conversation.Lexem;

public abstract class KnowledgeFact extends KnowledgeItem {
	
	
	
	abstract List<Lexem> getLexems();
	abstract KnowledgeFact_Criterion getCriterion();

	
	protected Lexem translateToLexem(Value value) {
		Lexem result = null;
		// TODO translate from value to lexem
		return result;
	}
	
	/*
	protected List<Lexem> translateToLexems(ValueArrayList values) {
		List<Lexem> result = new ArrayList<Lexem>();
		// TODO translate from values to lexems
		return result;
	}
*/

}
