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
package org.socialworld.datasource.db;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Mathias Sikos
 *
 */
public class TableReactionByEvent extends Table {

	String allColumns;
	
	int event[][];
			
	public TableReactionByEvent() {
		super();
		
		int i;
		
		allColumns = " ";
		for (i=1; i<256; i++) {
			allColumns = allColumns + "event" + i + ", ";
		}
		allColumns = allColumns + "event256 ";

	}
	@Override
	protected String getTableName() {
		return "sw_reactionbyevent";
	}

	@Override
	protected String getSelectList(int eventType) {
		String result;

		// eventType 0 means complete selectlist (all event types)
		if (eventType == 0) 	result = allColumns;
		else		result = " event" + eventType + " ";
		
		return result;
	}

	@Override
	public void select(String statement) {
		ResultSet rs;
		int row = 0;
		int column;
		
		rs = connection.executeQuery(statement);

		if (selectList == 0) {
			event = new int[rowCount][256];

			try {
				while (rs.next()) {
					
					for (column = 0; column < 256; column++) {
						event[row][column] = rs.getInt(column + 1);
					}
					
					row++;
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
				return;
			}
			
		}
		else {
			try {
				while (rs.next()) {
					
					event[row][0] = rs.getInt(1);
					row++;
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
				return;
			}
		}
	}

	public void insert(int id, int events[]) {
		String statement;
		int i;
		int count;
		
		
		if (id > 0) {
	
			count = events.length;
			statement = "INSERT INTO sw_reactionbyevent (";
			
			for (i = 1; i <= count; i++) {
				statement = statement + "event" + i + ", ";
			}
			statement = statement + "id) VALUES (";
			
			for (i = 0; i < count; i++) {
				statement = statement + events[i] +  ", ";
			}
			statement 	= statement	+ id + ")";
			
			insert(statement);
		}
	}
	
	public void update(int id, int eventType, int reactionType) {
		String statement;
		
		if ( (id > 0) & (eventType > 0) ) {
			
			statement 	= "UPDATE sw_reactionbyevent SET " +
					"event" + eventType + " = " + reactionType +
					" WHERE id = " + id  ;
			
			update(statement);
		}
	}	
	
	public void delete(int id) {
		String statement;
			
		if (id > 0) {
	
			statement 	= "DELETE FROM sw_reactionbyevent WHERE id = " + id  ;
			
			delete(statement);
		}
	}

}