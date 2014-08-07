package mikera.cljunit;

import java.util.ArrayList;
import java.util.List;

public class ClojureTests extends ClojureTest {
	@Override
	public List<String> namespaces() {
		ArrayList<String> ns=new ArrayList<String>();
		ns.add("clojure.core");
		ns.add("mikera.cljunit.test-main");
		// ns.add("mikera.cljunit.dummy-nonexistent-namespace");
		return ns;
	}
}
