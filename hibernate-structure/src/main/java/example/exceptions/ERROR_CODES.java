package example.exceptions;


public enum ERROR_CODES {
	
	METHDO_NOT_SUPPORTED (1),
	METHDO_NOT_SUPPORTED_IN_THIS_MODE (2),
	DUPLICATED_FIELDS (3),
	MIGRATION_IS_PROCESSING (4),
	OBJECT_NOT_FOUND_OR_ISNT_ALLOWED (5),
	UNCHANGED_ENTITY(6),
	NO_RIGHT(7),
	CONFLICT_IN_BUSINESS_LOGIC (8),
	PROPERTY_MISSING (9),
	CONFLICT_IN_DB (10),
	USER_NOT_ACTIVATED (11),
	INNER_ERROR (12),
	INVALID_VALUE (13),
	BAD_JSON (14);

	private final int code;
	
	private ERROR_CODES(int code){
		this.code = code;
	}
	
	public int getValue(){
		return code;
	}

}