package myclassloader;

import org.junit.Test;

/**
 * 热部署
 */
public class ClassLoaderDemo3 {

	/**
	 * 同一个类被同一个类加载器加载，只会被加载一次，加载出来的对象是同一个
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void test1() throws Exception {
		MyFileClassLoader classLoader1 = new MyFileClassLoader("D:/");
		MyFileClassLoader classLoader2 = new MyFileClassLoader("D:/", classLoader1);

		// 双亲委派，递归调用父类加载器(AppClassLoader->ExtClassLoader->BootStrapClassLoader)，一直找不到，则自己加载
		Class cls1 = classLoader1.loadClass("jvm.Test");
		// 指定了父类加载器是classLoader1，则先看classLoader1有没有加载过。已经加载过了，则直接取出来返回
		Class cls2 = classLoader2.loadClass("jvm.Test");

		System.out.println(cls1.hashCode());
		System.out.println(cls2.hashCode());

		// 所有cls1和cls2是同一个类
		System.out.println(cls1 == cls2);
	}

	/**
	 * 同一个类被不同类加载器加载，会被加载多次，加载出来的对象是不同的
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void test2() throws Exception {
		MyFileClassLoader classLoader1 = new MyFileClassLoader("D:/");
		MyFileClassLoader classLoader2 = new MyFileClassLoader("D:/", classLoader1);
		
		// 避开双亲委派模式，从而实现一个类被多次加载，实现热部署
		Class cls1 = classLoader1.findClass("jvm.Test");
		Class cls2 = classLoader2.findClass("jvm.Test");
		System.out.println(cls1.hashCode());
		System.out.println(cls2.hashCode());

		// 所有cls1和cls2不是同一个类
		System.out.println(cls1 == cls2);
	}

}
