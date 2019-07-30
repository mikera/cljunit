package mikera.cljunit;

import java.util.List;

import org.junit.runner.RunWith;

@RunWith(ClojureRunner.class)
public abstract class ClojureTest {

	/**
	 * Gets a list of namespaces for testing
	 * @return
	 */
	public List<String> namespaces() {
		String filter=filter();
		if (filter==null) {
			return Clojure.getNamespaces();
		}
		return Clojure.getNamespaces(filter);
	}
	
	/**
	 * Specifies a prefix filter for namespaces to test, e.g. "my.organisation"
	 * @return
	 */
	public String filter() {
		return null;
	}
}
