package daka.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**@version:
 * @Description: 
 * @author: sun
 * @date: 2018年12月22日 上午11:25:07
 *
 */
public class HibernateUtil {
	private static SessionFactory factory;

	// hibernate把可以预见的异常都转成了运行时异常。
	static {
		try {
			Configuration cfg = new Configuration();
			cfg.configure();
			factory = cfg.buildSessionFactory();
		} catch (ExceptionInInitializerError e) {
			throw new ExceptionInInitializerError("初始化sessionfactory失败，请检查配置文件");
		}
	}
	/**
	 * @Description:获取一个新的session对象
	 * 每次使用openSession方法，都会得到一个新的session
	 * @return
	 */
	public static Session openSession(){
		return factory.openSession();
	}
	/**
	 * @Description:从当前线程上获取session对象
	 * @return
	 */
	public static Session getCurrentSession(){
		return factory.getCurrentSession(); //只有配置了吧session和线程绑定之后，才能使用此方法，否则返回值是null
	}
	public static void main(String[] args) {
		getCurrentSession();
	}
}
