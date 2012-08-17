package com.amitk.java.internationalization.timezone;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: 4/28/11
 * Time: 1:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class TimezoneTest {
    public static Logger log = LoggerFactory.getLogger(TimezoneTest.class);

    public static void main(String[] args) throws ParseException {
        log.info("{}", new SimpleDateFormat("yyyy-MM-dd").parse("0001-01-01"));
    }
}
