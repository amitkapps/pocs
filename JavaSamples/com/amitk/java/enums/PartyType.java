package com.amitk.java.enums;

import org.junit.Test;

import java.util.EnumSet;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: Jul 24, 2010
 * Time: 12:46:19 PM
 * To change this template use File | Settings | File Templates.
 */
public enum PartyType implements CodeDescription{
    SHIPPER("02", "Shipper"),
    CONSIGNEE("03", "Consignee");

    private String code;
    private String description;

    PartyType(String partyTypeCode, String partyTypeDescription){
        this.code = partyTypeCode;
        this.description = partyTypeDescription;
    }

    public String getCode() {
        return code;
    }


    public String getDescription() {
        return description;
    }

    public static void main(String args[]){
        System.out.println(java.util.EnumSet.allOf(PartyType.class));
    }
}
