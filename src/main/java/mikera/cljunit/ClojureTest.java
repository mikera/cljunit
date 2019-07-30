package mikera.cljunit;

import java.util.List;

import org.junit.runner.RunWith;

@RunWith(ClojureRunner.class)
public abstract class ClojureTest {

	/**
	 * Returns a list of namespaces for testing. 
	 * 
	 * Subclasses may override this to provide a specific list of namespaces to test. The default is to 
	 * get all namespaces which start with the String returned by the `filter` method in this class.
	 * 
	 * @return A list of strings giving the names of Clojure namespaces to test, in the form "my.thing.foo"
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
	 * 
	 * @return A prefix string with which to filter namespaces, or null to match all namespaces.
	 */
	public String filter() {
		return null;
	}
}
