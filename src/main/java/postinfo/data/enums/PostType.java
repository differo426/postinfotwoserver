package postinfo.data.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PostType {
    VIDEO,IMAGE;

    @JsonValue
    public int toValue(){
        return ordinal();
    }
}
