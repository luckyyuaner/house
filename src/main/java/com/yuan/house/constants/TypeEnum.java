package com.yuan.house.constants;

/**
 * 返回类别枚举类
 */
public enum TypeEnum {
    T_result("result", "操作结果信息"),
    T_object("object", "一个对象"),
    T_list("list", "对象集合");

    private String typeCode;

    private String typeMsg;

    TypeEnum(String typeCode, String typeMsg) {
        this.typeCode = typeCode;
        this.typeMsg = typeMsg;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public String getTypeMsg() {
        return typeMsg;
    }
}
