package mikera.cljunit;

import java.util.Collection;
import java.util.List;
import clojure.lang.Keyword;
import clojure.lang.RT;
import clojure.lang.Symbol;
import clojure.lang.Var;

public class Clojure {
	public static final Var REQUIRE=RT.var("clojure.core", "require");
	public static final Var META=RT.var("clojure.core", "meta");
	public static final Keyword TEST_KEY=Keyword.intern("test");
	static {
		require("clojure.test");
		require("mikera.cljunit.core");
	}
	
	public static final Var GET_TEST_VAR_NAMES=RT.var("mikera.cljunit.core", "get-test-var-names");
	public static final Var GET_TEST_NAMESPACE_NAMES=RT.var("mikera.cljunit.core", "get-test-namespace-names");
	public static final Var INVOKE_TEST=RT.var("mikera.cljunit.core", "invoke-test");
	
	public static Collection<String> getTestVars(String namespace) {
		return (Collection<String>) GET_TEST_VAR_NAMES.invoke(namespace);
	}

	public static void require(String ns) {
		REQUIRE.invoke(Symbol.intern(ns));
	}

	public static List<String> getNamespaces() {
		return (List<String>) GET_TEST_NAMESPACE_NAMES.invoke();
	}

	public static Object invokeTest(Var testVar) {
		Object o=INVOKE_TEST.invoke(testVar);
		return o;
	}
}
