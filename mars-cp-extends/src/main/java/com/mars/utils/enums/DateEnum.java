/**
 * DateEnum.java
 * com.mili.app.core.enums
 *
 * @author zhouslin
 * @date 2016-10-26 15:40
 * 版权所有
 */
package com.mars.utils.enums;

/**
 *  日期
 *
 */
public enum DateEnum {

    Y("Y", "年"),
    M("M", "月"),
    D("D", "日"),
    H("H", "时"),
    F("F", "分"),
    S("S", "秒");

    /**
     * @Fields code 编号
     */
    private String code;

    /**
     * @Fields value 值
     */
    private String value;

    /**
     * 枚举构造方法
     *
     * @param code
     * @param value
     */
    DateEnum(String code, String value) {
        this.code = code;
        this.value = value;

    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    /**
     * 根据code 查询枚举
     *
     * @param code code
     * @return CouponTypeEnum
     */
    public static DateEnum getEnumByCode(String code) {
        for (DateEnum type : DateEnum.values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        return null;
    }

    /**
     * 根据value查询枚举
     *
     * @param value value
     * @return CouponTypeEnum
     */
    public static DateEnum getEnumByValue(String value) {
        for (DateEnum type : DateEnum.values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return null;
    }
}
