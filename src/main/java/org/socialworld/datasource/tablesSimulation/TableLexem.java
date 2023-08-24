package org.socialworld.datasource.tablesSimulation;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.socialworld.datasource.mariaDB.Table;

public class TableLexem extends Table {

	public final  String 	ALL_COLUMNS 		=	" lexem_id, subjectable, type ";
	public final  int 		SELECT_ALL_COLUMNS 	= 1;
	
	int lexem_id[];
	int type[];
	int subjectable[];
	
	@Override
	protected String getTableName() {
		return "sw_lexem";
	}

	@Override
	protected String getSelectList(int selectList) {
		switch (selectList) {
		case SELECT_ALL_COLUMNS:
			return  ALL_COLUMNS;
		default:
			return ALL_COLUMNS;
		}
	}

	@Override
	public void select(String statement) {
		ResultSet rs;
		
		rs = connection.executeQuery(statement);
		
		
		switch (selectList) {
		
		case SELECT_ALL_COLUMNS:
			selectAllColumns(rs);

			break;
		default:
			selectAllColumns(rs);
		}
		setPK1(lexem_id);

	}

	public void insert(int lexem_id,  int subjectable,  int type) {
		String statement;
			
		if (lexem_id > 0) {
	

			statement 	= "INSERT INTO sw_lexem (lexem_id, subjectable, type) VALUES (" + 
					lexem_id +  ", " + subjectable + ", " + type  + ")";
			
			insert(statement);
		}
	}

	public void delete(int lexem_id) {
		String statement;
			
		if (lexem_id > 0) {
	
			statement 	= "DELETE FROM sw_lexem WHERE lexem_id = " + lexem_id  ;
			
			delete(statement);
		}
	}	
	
	private void selectAllColumns(ResultSet rs) {
		int row = 0;
		lexem_id = new int[rowCount];
		subjectable = new int[rowCount];
		type = new int[rowCount];

		try {
			while (rs.next()) {
				
				lexem_id[row] = rs.getInt(1);
				subjectable[row] = rs.getInt(2);
				type[row] = rs.getInt(3);
				
				row++;
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
			return;
		}

	}	
	
	public int getLexemID(int index) {
		return lexem_id[index];
	}


	public int getSubjectable(int index) {
		return subjectable[index];
	}
	
	public int getType(int index) {
		return type[index];
	}	
	
}