package com.lishengzn.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TranslateUtil {
	public static void translateEntity(Object src){
		Field[] fields =src.getClass().getDeclaredFields();
		for(int i=0;i<fields.length;i++){
			Field field = fields[i];
			Translate translate =field.getDeclaredAnnotation(Translate.class);
			if(translate !=null){
				try {
					field.setAccessible(true);
					Object value=field.get(src);
					
					Method method =translate.cacheKey().getMethod("getDescription", int.class);
					Object o = method.invoke(null, value);
					String setNameMethodStr="set"+translate.target().substring(0,1).toUpperCase()+translate.target().substring(1);
					Method setNameMethod = src.getClass().getDeclaredMethod(setNameMethodStr, String.class);
					setNameMethod.invoke(src, o);
				} catch (Exception e) {
					throw new RuntimeException("编码翻译异常！",e);
				} 
			}
			
		}
	}
}
