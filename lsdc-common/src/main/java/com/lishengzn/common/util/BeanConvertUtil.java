package com.lishengzn.common.util;


import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.beans.BeanCopier;

public class BeanConvertUtil {
	/**bean类型转换
	 * @param src 源对象
	 * @param target 目标对象
	 */
	public static void beanConvert(Object src,Object target){
		BeanCopier copier = BeanCopier.create(src.getClass(),target.getClass(),false);
		copier.copy(src, target, null);
	}

	/**bean类型转换
	 * @param src 源对象
	 * @param clazz 目标对象的类型
	 * @return 目标对象
	 */
	public static <T> T beanConvert(Object src,Class<T> clazz){
		BeanCopier copier = BeanCopier.create(src.getClass(),clazz,false);
		T target=null;
		try {
			target = clazz.newInstance();
			copier.copy(src, target, null);
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException("bean转换异常！");
		}
		return target;
	}
	
	/**bean类型转换
	 * @param list 源对象 list
	 * @param clazz 目标对象的类型
	 * @return  目标对象 list
	 */
	public static <T,E> List<T> beanConvert(List<E> list,Class<T> clazz){
		List<T> targetList = new ArrayList<T>();
		list.forEach((e)->{
			targetList.add(beanConvert(e,clazz));
		});
		
		return targetList;
		
	}
}
