package mikera.cljunit;

import java.util.ArrayList;
import java.util.List;

public class SelfClojureTest extends ClojureTest {
	@Override
	public List<String> namespaces() {
		@SuppressWarnings("unused")
		ArrayList<String> ns=new ArrayList<String>();
		ns.add("clojure.core");
		ns.add("mikera.cljunit.test-main");
		ns.add("mikera.cljunit.dummy-nonexistent-namespace");
		return ns;
	}
}
