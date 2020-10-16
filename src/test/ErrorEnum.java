package test;

public enum ErrorEnum {
	HasError(1),			//	c
	SUBJECTVERBCHECKER ( 2),		//  rw
	SADHUCHOLITCHECKER ( 4),		//	rw
	CHOLIT ( 8),					//	rw
	NONEWORDCHECKER ( 16),			//	nw
	UNKNOWNCHECKER ( 32),			//	?
	NIRDESHOKCHECKER ( 64),			//	rw
	VALIDSPACEMISSINGCHECKER ( 128),	//	rw	
	NOSPACENEEDEDCHECKER ( 256);		//	rw
	
	private final int value;

	ErrorEnum(final int newValue) {
        value = newValue;
    }

    public int getValue() { return value; }
}
