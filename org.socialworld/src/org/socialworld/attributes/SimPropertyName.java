package org.socialworld.attributes;

import org.socialworld.calculation.Type;

public enum SimPropertyName {

	unknown, 
	
	simobj_position,
	simobj_attributearray,
	simobj_directionMove, simobj_directionChest, simobj_directionView, simobj_directionActiveMove,
	simobj_inventory,
	simobj_knowledge,
	simobj_stateSeer,
	
	event_position,
	event_direction,
	
	action_position;
	
	public final static String SIMOBJPROP_POSITION = "position";
	public final static String SIMOBJPROP_ATTRIBUTEARRAY = "attributes";
	public final static String SIMOBJPROP_INVENTORY = "inventory";
	public final static String SIMOBJPROP_KNOWLEDGE = "knowledge";
	public final static String SIMOBJPROP_DIRECTION_MOVE = "direction_move";
	public final static String SIMOBJPROP_DIRECTION_CHEST = "direction_chest";
	public final static String SIMOBJPROP_DIRECTION_VIEW = "direction_view";
	public final static String SIMOBJPROP_DIRECTION_ACTIVEMOVE = "direction_activemove";
	public final static String SIMOBJPROP_STATE_SEER = "state_seer";
	
	public final static String EVENT_POSITION = "event_position";
	public final static String EVENT_DIRECTION = "event_direction";
	
	public final static String ACTION_POSITION = "action_position";
	
	public Type getType() {
		switch (this) {
		case unknown:
			return Type.nothing; 
		case simobj_attributearray:
			return Type.attributeArray;
		case simobj_position:
		case simobj_inventory:  
		case simobj_knowledge:
		case simobj_directionMove: 
		case simobj_directionChest: 
		case simobj_directionView: 
		case simobj_directionActiveMove: 
		case simobj_stateSeer: 
			return Type.simObjProp; 
		case event_position:
		case event_direction:
			return Type.eventProp; 
		case action_position:
			return Type.actionProp; 
		default:
			return Type.nothing;
		}
	}
	
	public SimPropertyName toType(Type propertyType) {
		if (getType() == propertyType ){
			return this;
		}
		switch (propertyType) {
		case simObjProp:
			return simobj_position;
		case eventProp:
			return event_position;
		case actionProp:
			return action_position;
		default:
			return unknown;
		}
	}
	
	public String toString() {
		
		switch (this) {
		case unknown: return "unknown"; 
		case simobj_position: return SIMOBJPROP_POSITION;
		case simobj_attributearray: return SIMOBJPROP_ATTRIBUTEARRAY;
		case simobj_inventory: return SIMOBJPROP_INVENTORY; 
		case simobj_knowledge: return SIMOBJPROP_KNOWLEDGE;
		case simobj_directionMove: return SIMOBJPROP_DIRECTION_MOVE;
		case simobj_directionChest: return SIMOBJPROP_DIRECTION_CHEST;
		case simobj_directionView: return SIMOBJPROP_DIRECTION_VIEW;
		case simobj_directionActiveMove: return SIMOBJPROP_DIRECTION_ACTIVEMOVE; 
		case simobj_stateSeer: return SIMOBJPROP_STATE_SEER; 
		
		case event_position: return EVENT_POSITION; 
		case event_direction: return EVENT_DIRECTION; 
		
		case action_position: return ACTION_POSITION; 
		
		default: return "";
		}
		
	}

	public static SimPropertyName forString(String name) {
		
		switch (name) {
		case "unknown": return unknown; 
		case SIMOBJPROP_POSITION: return simobj_position;
		case SIMOBJPROP_ATTRIBUTEARRAY: return simobj_attributearray;
		case SIMOBJPROP_INVENTORY: return simobj_inventory; 
		case SIMOBJPROP_KNOWLEDGE: return simobj_knowledge;
		case SIMOBJPROP_DIRECTION_MOVE: return simobj_directionMove;
		case SIMOBJPROP_DIRECTION_CHEST: return simobj_directionChest;
		case SIMOBJPROP_DIRECTION_VIEW: return simobj_directionView;
		case SIMOBJPROP_DIRECTION_ACTIVEMOVE: return simobj_directionActiveMove; 
		case SIMOBJPROP_STATE_SEER: return simobj_stateSeer; 
		
		case EVENT_POSITION: return event_position; 
		case EVENT_DIRECTION: return event_direction; 
		
		case ACTION_POSITION: return action_position;
		default: return unknown;
		}
	}
}