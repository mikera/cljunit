package mikera.cljunit;

import java.io.FileNotFoundException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClojureCore {
	private static final Logger log = LogManager.getLogger();

	public static final IFn REQUIRE = Clojure.var("clojure.core", "require");
	public static final IFn META = Clojure.var("clojure.core", "meta");
	public static final Object TEST_KEY = Clojure.read(":test");
	public static final Object PREFIX = Clojure.read(":prefix");

	static {
		require("clojure.test");
		require("mikera.cljunit.core");
	}
	
	public static final IFn GET_TEST_VAR_NAMES = Clojure.var("mikera.cljunit.core", "get-test-var-names");
	public static final IFn GET_TEST_NAMESPACE_NAMES = Clojure.var("mikera.cljunit.core", "get-test-namespace-names");
	public static final IFn INVOKE_TEST = Clojure.var("mikera.cljunit.core", "invoke-test");
	
	@SuppressWarnings("unchecked")
	public static Collection<String> getTestVars(String namespace) {
		return (Collection<String>) GET_TEST_VAR_NAMES.invoke(namespace);
	}

	private static void show(ClassLoader cl) {

		log.warn("classloader: {}", cl);
		for (ClassLoader clx = cl;
			 null != clx; clx = clx.getParent())
		{
			if (!(clx instanceof URLClassLoader)) {
				log.warn("non-URL class loader type: {}", clx);
				continue;
			}

			for (URL url : URLClassLoader.class.cast(clx).getURLs()) {
				log.warn("path: {}", url.getFile());
			}
			log.warn("end paths: {}", clx);
		}

	}
	public static void require(String ns) {
		log.warn("required: {}", ns);
		try {
			log.info("require {}",
					REQUIRE.invoke(Clojure.read(ns)));

		} catch (Exception ex) {
			show(Thread.currentThread().getContextClassLoader());
			show(System.class.getClassLoader());
			show(ClojureCore.class.getClassLoader());
		} finally {
			log.warn("finished: {}", ns);
		}
	}

	@SuppressWarnings("unchecked")
	public static List<String> getNamespaces() {
		return (List<String>) GET_TEST_NAMESPACE_NAMES.invoke();
	}
	
	@SuppressWarnings("unchecked")
	public static List<String> getNamespaces(String filter) {
		final Map<Object,Object> hm = new HashMap<>();
		hm.put(PREFIX, filter);
		
		return (List<String>) GET_TEST_NAMESPACE_NAMES.invoke(hm);
	}

	public static Object invokeTest(Object testVar) {
		return INVOKE_TEST.invoke(testVar);
	}
}
