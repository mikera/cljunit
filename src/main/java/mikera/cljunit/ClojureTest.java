package mikera.cljunit;

import java.util.List;

import org.junit.runner.RunWith;

@RunWith(ClojureRunner.class)
public abstract class ClojureTest {

	public List<String> namespaces() {
		return Clojure.getNamespaces();
		
	}
}
