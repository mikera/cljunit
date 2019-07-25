package mikera.cljunit;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import clojure.java.api.Clojure;
import clojure.lang.IFn;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClojureCore {
	private static final Logger log = LogManager.getLogger();

	private static final IFn REQUIRE = Clojure.var("clojure.core", "require");
	public static final IFn META = Clojure.var("clojure.core", "meta");
	public static final Object TEST_KEY = Clojure.read(":test");
	private static final Object PREFIX = Clojure.read(":prefix");

	static {
		require("clojure.test");
		require("mikera.cljunit.core");
	}
	
	private static final IFn GET_TEST_VAR_NAMES = Clojure.var("mikera.cljunit.core", "get-test-var-names");
	private static final IFn GET_TEST_NAMESPACE_NAMES = Clojure.var("mikera.cljunit.core", "get-test-namespace-names");
	private static final IFn INVOKE_TEST = Clojure.var("mikera.cljunit.core", "invoke-test");
	

	@SuppressWarnings("unchecked")
	static Collection<String> getTestVars(String namespace) {
		return (Collection<String>) GET_TEST_VAR_NAMES.invoke(namespace);
	}

	/**
	 * With java 9+ using URLClassloader.class.cast() can
	 * no longer be expected to work as in the past.
	 * The ClassGraph package provides tools for working with
	 * class and module loaders.
	 */
	private static void showClassInfo() {

		log.warn("show class info");
		try (ScanResult scanResult =
					 new ClassGraph()
							 .verbose()
							 .enableAllInfo()
							 .whitelistPackages("clojure.lang", "mikera.cljunit")
							 .scan())
		{
			log.info("string class info: {}", scanResult.getClasspath().length());
		}

	}
	static void require(String ns) {
		log.warn("required: {}", ns);
		try {
			log.info("require {}",
					REQUIRE.invoke(Clojure.read(ns)));

		} catch (Exception ex) {
			showClassInfo();
		} finally {
			log.warn("finished: {}", ns);
		}
	}

	@SuppressWarnings("unchecked")
	static List<String> getNamespaces() {
		return (List<String>) GET_TEST_NAMESPACE_NAMES.invoke();
	}

	@SuppressWarnings("unchecked")
	static List<String> getNamespaces(String filter) {
		final Map<Object,Object> hm = new HashMap<>();
		hm.put(PREFIX, filter);

		return (List<String>) GET_TEST_NAMESPACE_NAMES.invoke(hm);
	}

	static Object invokeTest(clojure.lang.Var testVar) {
		return INVOKE_TEST.invoke(testVar);
	}
}
