package ru.ydn.wicket.javassist;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.wicket.Application;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.application.DefaultClassResolver;
import org.apache.wicket.application.IClassResolver;
import org.apache.wicket.util.collections.UrlExternalFormComparator;

import javassist.CannotCompileException;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Modifier;

public class JAClassResolver implements IClassResolver{
	private final ConcurrentMap<String, WeakReference<Class<?>>> classes = new ConcurrentHashMap<>();
	
	private static final ThreadLocal<ClassPool> POOLS = new ThreadLocal<ClassPool>(){
		protected ClassPool initialValue() {
			ClassPool pool = new ClassPool(true);
			ClassClassPath ccpath = new ClassClassPath(WicketApplication.class);
			pool.insertClassPath(ccpath);
			return pool;
		};
	}; 
	
	@Override
	public final Class<?> resolveClass(final String className) throws ClassNotFoundException
	{
		Class<?> clazz = null;
		WeakReference<Class<?>> ref = classes.get(className);

		// Might be garbage-collected between getting the WeakRef and retrieving
		// the Class from it.
		if (ref != null)
		{
			clazz = ref.get();
		}
		if (clazz == null)
		{
			switch (className)
			{
				case "byte":
					clazz = byte.class;
					break;
				case "short":
					clazz = short.class;
					break;
				case "int":
					clazz = int.class;
					break;
				case "long":
					clazz = long.class;
					break;
				case "float":
					clazz = float.class;
					break;
				case "double":
					clazz = double.class;
					break;
				case "boolean":
					clazz = boolean.class;
					break;
				case "char":
					clazz = char.class;
					break;
				default:
					// synchronize on the only class member to load only one class at a time and
					// prevent LinkageError. See above for more info
					synchronized (classes)
					{
						clazz = className.endsWith("Page")?
								instrumentPage(className) :Class.forName(className, false, getClassLoader());
						if (clazz == null)
						{
							throw new ClassNotFoundException(className);
						}
					}
					classes.put(className, new WeakReference<Class<?>>(clazz));
					break;
			}
		}
		return clazz;
	}
	
	private Class<?> instrumentPage(String className) {
		System.out.println("Instrumenting: "+className);
		
		try {
			ClassPool pool = POOLS.get();
			CtClass cc = pool.get(className);
			//Instrumenting all methods
			CtMethod[] methods = cc.getMethods();
			for (CtMethod oldMethod : methods) {
				if((oldMethod.getModifiers() & (Modifier.ABSTRACT | Modifier.NATIVE | Modifier.FINAL))>0) continue;
				if(!oldMethod.getDeclaringClass().equals(cc)) {
					oldMethod = CtNewMethod.delegator(oldMethod, cc);
					cc.addMethod(oldMethod);
				}
				instrumentCall(oldMethod);
			}
			//Instrumenting only declared constructors
			CtConstructor[] constructors = cc.getDeclaredConstructors();
			for (CtConstructor constructor : constructors) {
				instrumentCall(constructor);
			}
			return cc.toClass();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private void instrumentCall(CtBehavior method) throws CannotCompileException {
		method.insertBefore("ru.ydn.wicket.javassist.SimpleProfiler.push(\""+method.getLongName()+"\", java.lang.System.nanoTime());");
		method.insertAfter("ru.ydn.wicket.javassist.SimpleProfiler.pop(\""+method.getLongName()+"\", java.lang.System.nanoTime());");
	}
	
	
	@Override
	public Iterator<URL> getResources(final String name)
	{
		Set<URL> resultSet = new TreeSet<>(new UrlExternalFormComparator());

		try
		{
			// Try the classloader for the wicket jar/bundle
			Enumeration<URL> resources = Application.class.getClassLoader().getResources(name);
			loadResources(resources, resultSet);

			// Try the classloader for the user's application jar/bundle
			resources = Application.get().getClass().getClassLoader().getResources(name);
			loadResources(resources, resultSet);

			// Try the context class loader
			resources = getClassLoader().getResources(name);
			loadResources(resources, resultSet);
		}
		catch (Exception e)
		{
			throw new WicketRuntimeException(e);
		}

		return resultSet.iterator();
	}

	private void loadResources(Enumeration<URL> resources, Set<URL> loadedResources)
	{
		if (resources != null)
		{
			while (resources.hasMoreElements())
			{
				final URL url = resources.nextElement();
				loadedResources.add(url);
			}
		}
	}
	
	@Override
	public ClassLoader getClassLoader()
	{
		return POOLS.get().getClassLoader();
	}
}
