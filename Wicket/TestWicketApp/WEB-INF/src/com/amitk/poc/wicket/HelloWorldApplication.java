package com.amitk.poc.wicket;
import org.apache.wicket.protocol.http.WebApplication;

public class HelloWorldApplication extends WebApplication
{
    /**
     * Constructor.
     */
    public HelloWorldApplication()
    {

    }

    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    public Class getHomePage()
    {
        return HelloWorld.class;
    }
}