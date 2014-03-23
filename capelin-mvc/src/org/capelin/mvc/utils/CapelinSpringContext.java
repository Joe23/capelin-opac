package org.capelin.mvc.utils;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
/**
 * Utilities that gets spring context out of the servlet container.
 * Designed for data importer to import data or write the index.
 * 
 * @author Jing Xiao <jing.xiao@gmail.com>
 *
 */
public class CapelinSpringContext {

	private static BeanFactory bf;

	public static BeanFactory getBeanFactory() {
		if (null == bf) {
			Resource res = new FileSystemResource("capelin-mvc/WebContent/WEB-INF/conf/capelin-spring-mvc.xml");			
			bf = new XmlBeanFactory(res);
		}
		return bf;
	}
}
