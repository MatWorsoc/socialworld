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
package org.socialworld.calculation;


import org.socialworld.attributes.AttributeArray;
import org.socialworld.collections.ValueArrayList;

/**
 * The class is an implementation of an
 *         expression. The expression is part of a term that consists of
 *         mathematical comparisons and (mathematical) operations which are
 *         executed dependent to the evaluation result of the comparison. An
 *         expression is a comparison to a constant OR addition of a constant 
 *          OR a multiplication OR a replacement 
 *         If the expression is a comparison there are given two
 *         further expressions for the boolean evaluation result of the
 *         comparison. So a expression evaluates an expression recursively.

 * @author Mathias Sikos (tyloesand)
 */
public class Expression {
	int ID;
	
	Expression_Function operation;
	Expression_ConditionOperator operator;
	
	
	
	// because of circular relations (expressions can be members to functions (see FunctionsByExpression))
	// at first the function id is set to the expressions
	// later the function id will be mapped to a function object that will be set to the expression
	private int func_id;
	FunctionBase function;
	
	Value value;
	
	Expression expression1;
	Expression expression2;
	Expression expression3;
	
		
	private boolean isValid;
	
	protected Calculation calculation;
	protected Functions functions;


	public Expression() {
		calculation = Calculation.getInstance();
		functions = Functions.getInstance();
		operation = Expression_Function.nothing;
		isValid = false;
	}
	
	public void setValid() {
		isValid = true;
	}

	public boolean isValid() { 
		return isValid;
	}
	
	public void setOperation(Expression_Function operation) {
		if (!isValid) this.operation = operation;
	}

	public void setOperator(Expression_ConditionOperator operator) {
		if (!isValid) this.operator = operator;
	}

	public void setFuncID(int func_id) {
		if (!isValid) this.func_id = func_id;
	}
	
	public int getFuncID() {
		return this.func_id;
	}
	
	public void setFunction(FunctionBase function) {
		if (!isValid) this.function = function;
	}
	
	public void setExpression1(Expression expression) {
		if (!isValid) this.expression1 = expression;
	}
	
	public void setExpression2(Expression expression) {
		if (!isValid) this.expression2 = expression;
	}

	public void setExpression3(Expression expression) {
		if (!isValid) this.expression3 = expression;
	}

	
	public void setTrueExpression(Expression expressionForTrue) {
		if (!isValid) setExpression2(expressionForTrue);
	}

	public void setFalseExpression(Expression expressionForFalse) {
		if (!isValid) setExpression3(expressionForFalse);
	}

	public void setValue(Value value) {
		if (!isValid) this.value = value;
	}

	protected Value getValue() {
		return value;
	}
	
		
	Value evaluateExpression() {
		ValueArrayList noArguments = null;
		return evaluateExpression(noArguments);
	}

	Value evaluateExpression(ValueArrayList arguments) {
		
		Value tmp;
		String name;
		int index;
		
		if (this.isValid()) {
			
		
			switch (this.operation) {
			case nothing:
				//return invalid dummy-Value
				return Calculation.getNothing();
			case value:
				return calculation.copy(value);
				
			case attributeValue:
				AttributeArray attributeArray;
				attributeArray = (AttributeArray) get( arguments, Type.attributeArray, 1);
				index = (int) value.getValueCopy();
				return calculation.createValue(
					Type.integer,
					attributeArray.get(index ));
					
			case argumentValueByName:
				return arguments.getValue( (String) value.getValueCopy());
				
			case branching:
				tmp = expression1.evaluateExpression(arguments);
				if (tmp.isTrue()  ) 
					tmp =  expression2.evaluateExpression(arguments);
				else
					tmp = expression3.evaluateExpression(arguments);
				return tmp;
				
			case condition:
				
				switch (operator) {
				case or:
					return calculation.or(
							expression1.evaluateExpression(arguments) ,
							expression2.evaluateExpression(arguments)   );
				case and:
					return calculation.and(
							expression1.evaluateExpression(arguments) ,
							expression2.evaluateExpression(arguments)   );
				default:
					return calculation.createValue(Type.bool,  false);
				}
				
				
			case comparison :
				
				switch (operator) {
				case equal:
					return calculation.compareEqual(
							expression1.evaluateExpression(arguments) ,
							expression2.evaluateExpression(arguments)   );
				case notEqual:
					return calculation.compareNotEqual(
							expression1.evaluateExpression(arguments) ,
							expression2.evaluateExpression(arguments)   );
				case greater:
					return calculation.compareGreater(
							expression1.evaluateExpression(arguments) ,
							expression2.evaluateExpression(arguments)   );
				case greaterEqual:
					return calculation.compareGreaterEqual(
							expression1.evaluateExpression(arguments) ,
							expression2.evaluateExpression(arguments)   );
				case less:
					return calculation.compareLess(
							expression1.evaluateExpression(arguments) ,
							expression2.evaluateExpression(arguments)   );
				case lessEqual:
					return calculation.compareLessEqual(
							expression1.evaluateExpression(arguments) ,
							expression2.evaluateExpression(arguments)   );
				default:
					return calculation.createValue(Type.bool,  false);
				}
				
			case addition:
				return calculation.addition(
						expression1.evaluateExpression(arguments) ,
						expression2.evaluateExpression(arguments)   );
				
			case multiplication:
				return calculation.multiplication(
						expression1.evaluateExpression(arguments) ,
						expression2.evaluateExpression(arguments)   );
				
				
			case function:
				ValueArrayList calculateArguments = new ValueArrayList();
				calculateArguments.add( expression1.evaluateExpression(arguments) );
				calculateArguments.add( expression2.evaluateExpression(arguments) );
				calculateArguments.add( expression3.evaluateExpression(arguments) );
				return function.calculate(calculateArguments);
				
			case sequence:
				expression1.evaluateExpression(arguments);
				expression2.evaluateExpression(arguments);
				tmp = expression3.evaluateExpression(arguments);
				return tmp;
				
			case replacement:
				name = (String) value.getValueCopy();
				tmp = expression1.evaluateExpression(arguments);
				index = arguments.findValue(name);
				if (index >= 0) {
					if (tmp.getName().isEmpty()) {
						tmp.setName(name);
					}
					arguments.set(index, tmp);
				}	
				return tmp;
			
			case create:
				
				Type type;
				Value createdValue;
				
				type = Type.getName((int)value.getValueCopy());
				
				switch (type) {
				case action:
					createdValue = createValue(type, arguments);
					break;
				case time:
					createdValue = calculation.createValue(type, expression1.evaluateExpression().getValueCopy());
					break;
				default:
					createdValue = Calculation.getNothing();
				}
				
				return createdValue;
				
			default:
				return Calculation.getNothing();
			}
		
		}
		else
			return Calculation.getNothing();

		
	}
	
	

	private Object get(ValueArrayList arguments, Type type, int wantedOccurence) {
		
		Value value;
		value = arguments.getValue(type, wantedOccurence);
		
		if (value.isValid()) {
			return value.getValue();
		}
		else {
			return null;
		}
		
	}

	// will be overrided in inherited Expressions dedicated to creating values
	protected Value createValue(Type valueType, ValueArrayList arguments) {
		return new Value();
	}
	

	protected void evaluateExpression1(ValueArrayList arguments) {
		expression1.evaluateExpression(arguments);
	}

}
