package com.amitk.xml.xstream.hello;

import java.lang.reflect.Constructor;

import com.thoughtworks.xstream.XStream.InitializationException;
import com.thoughtworks.xstream.core.JVM;
import com.thoughtworks.xstream.core.util.ClassLoaderReference;
import com.thoughtworks.xstream.mapper.ArrayMapper;
import com.thoughtworks.xstream.mapper.AttributeAliasingMapper;
import com.thoughtworks.xstream.mapper.AttributeMapper;
import com.thoughtworks.xstream.mapper.CachingMapper;
import com.thoughtworks.xstream.mapper.ClassAliasingMapper;
import com.thoughtworks.xstream.mapper.DefaultImplementationsMapper;
import com.thoughtworks.xstream.mapper.DefaultMapper;
import com.thoughtworks.xstream.mapper.DynamicProxyMapper;
import com.thoughtworks.xstream.mapper.EnumMapper;
import com.thoughtworks.xstream.mapper.FieldAliasingMapper;
import com.thoughtworks.xstream.mapper.ImmutableTypesMapper;
import com.thoughtworks.xstream.mapper.ImplicitCollectionMapper;
import com.thoughtworks.xstream.mapper.Mapper;
import com.thoughtworks.xstream.mapper.MapperWrapper;
import com.thoughtworks.xstream.mapper.OuterClassMapper;

public class MyMapper extends DefaultMapper{

	private MyMapper() {
		super(MyMapper.class.getClassLoader());
	}
	private static transient JVM jvm = new JVM();
	private static transient ClassLoaderReference classLoaderReference = new ClassLoaderReference(MyMapper.class.getClassLoader());

	public static Mapper getMyMapperInstance(){
        Mapper mapper = new MyMapper();
        mapper = new ClassAliasingMapper(mapper);
        mapper = new FieldAliasingMapper(mapper);
        mapper = new AttributeAliasingMapper(mapper);

        mapper = new AttributeMapper(mapper);
        
        mapper = new ImplicitCollectionMapper(mapper);
        if (jvm.loadClass("net.sf.cglib.proxy.Enhancer") != null) {
           mapper = buildMapperDynamically(
                    "com.thoughtworks.xstream.mapper.CGLIBMapper",
                    new Class[]{Mapper.class}, new Object[]{mapper});
        }
        mapper = new DynamicProxyMapper(mapper);
        if (JVM.is15()) {
            mapper = new EnumMapper(mapper);
        }
        mapper = new OuterClassMapper(mapper);
        mapper = new ArrayMapper(mapper);
        mapper = new DefaultImplementationsMapper(mapper);
        mapper = new ImmutableTypesMapper(mapper);
        mapper = wrapMapper((MapperWrapper)mapper);
        mapper = new CachingMapper(mapper);
        return mapper;

	}
	
    protected static MapperWrapper wrapMapper(MapperWrapper next) {
        return next;
    }

	
    private static Mapper buildMapperDynamically(
            String className, Class[] constructorParamTypes,
            Object[] constructorParamValues) {
        try {
            Class type = Class.forName(className, false, classLoaderReference.getReference());
            Constructor constructor = type.getConstructor(constructorParamTypes);
            return (Mapper)constructor.newInstance(constructorParamValues);
        } catch (Exception e) {
            throw new InitializationException("Could not instatiate mapper : " + className, e);
        }
    }
	

    public String serializedMember(Class type, String memberName) {
    	return getHyphenatedString(memberName);
    }
	
	public String realMember(Class type, String serialized) {
		// TODO Auto-generated method stub
		return getInHungarianNotation(serialized);
	}
    
    private static String getHyphenatedString(String nonHyphenated){
		char[] chars = nonHyphenated.toCharArray();
		StringBuffer hyphenatedStringBuffer = new StringBuffer();
		boolean isStarted = false;
		boolean wasLastSmall = false;
		boolean isCurrentCaps = false;
		char currChar;
		for (int i = 0; i < chars.length; i++) {
			currChar = chars[i];
			isCurrentCaps = Character.isUpperCase(currChar);
			
			if(isStarted && wasLastSmall && isCurrentCaps )
				hyphenatedStringBuffer.append("-");
			
			hyphenatedStringBuffer.append(currChar);
			
			isStarted = true;
			wasLastSmall = !isCurrentCaps;
			
		}
		return hyphenatedStringBuffer.toString().toLowerCase();
	}

    
    private static String getInHungarianNotation(String hyphenatedString){
		char[] chars = hyphenatedString.toCharArray();
		StringBuffer resultStringBuffer = new StringBuffer();
		boolean isStarted = false;
		boolean wasLastHyphen = false;
		boolean isCurrentHyphen = false;
		char currChar;
		for (int i = 0; i < chars.length; i++) {
			currChar = chars[i];
			isCurrentHyphen = '-' == currChar;
			
			if(isStarted && wasLastHyphen )
				resultStringBuffer.append(Character.toUpperCase(currChar));
			else if(! isCurrentHyphen)
				resultStringBuffer.append(currChar);
			
			isStarted = true;
			wasLastHyphen = isCurrentHyphen;
			
		}
		return resultStringBuffer.toString();
	}
    
	public static void main(String[] args) {
		System.out.println(getHyphenatedString("HelloHowAreYou"));
		System.out.println(getInHungarianNotation("hello-how-are-you"));
	} 
}
