package com.amitk.java.serialization;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: Jul 30, 2010
 * Time: 7:56:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class Department implements Serializable{
    String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;

        Department that = (Department) o;

        if (!code.equals(that.code)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }
}
