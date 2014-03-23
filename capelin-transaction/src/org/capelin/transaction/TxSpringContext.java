package org.capelin.transaction;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * 
 * <a href="https://github.com/Joe23/capelin-opac/">Capelin-opac</a>
 * License: GNU GPL 
 * 
 * Utilities that gets spring context.
 * Designed for data importer to import data or write the index.
 * 
 * @author Jing Xiao <jing.xiao@gmail.com>
 *
 */
public class TxSpringContext {

	private static BeanFactory bf;

	public static BeanFactory getBeanFactory() {
		if (null == bf) {
			//Resource res = new FileSystemResource("spring-dao.xml");
			
			//bf = new XmlBeanFactory(res);
			ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext(
					new String[] { "capelin-spring-dao.xml" });
			 //of course, an ApplicationContext is just a BeanFactory
			bf = (BeanFactory) appContext;
		}
		return bf;
	}
}
