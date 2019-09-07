package com.valutac.spring.boot.jpa.search.specification;

@SuppressWarnings({"unused", "WeakerAccess"})
public class SearchCriteria {

    private String key;
    private String operation;
    private Object value;
    private String dataType;

    public SearchCriteria(String key, String operation, Object value) {
        this.key = key;
        this.operation = operation;
        this.value = value;
        this.dataType = "String";
    }

    public SearchCriteria(String key, String operation, Object value, String dataType) {
        this.key = key;
        this.operation = operation;
        this.value = value;
        this.dataType = dataType;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public static class Operator {

        public static final String EQUALS = "e";
        public static final String NOT_EQUALS = "ne";
        public static final String GREATER_THAN = "gt";
        public static final String GREATER_THAN_EQUALS = "gte";
        public static final String IN = "in";
        public static final String LESS_THAN = "lt";
        public static final String LESS_THAN_EQUALS = "lte";
        public static final String LIKE = "like";
        public static final String NULL = "null";
        public static final String NOT_NULL = "notnull";

    }

}
