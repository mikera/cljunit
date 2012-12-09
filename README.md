# cljunit


JUnit test integration for Clojure.

The purpose of cljunit is to provide a convenient way to write JUnit tests that test Clojure namespaces. This is
very useful if, for example, you want to test Clojure code within a Java IDE like Eclipse that has integrated
JUnit testing facilities.

## Usage

Include cljunit as a dependency from clojars: (https://clojars.org/net.mikera/cljunit)

Then you should extend one of the cljunit classes to allow JUnit to test your Clojure files.

Examples below:

### Testing all Clojure namespaces:    

    public class ClojureTests extends ClojureTest {
    	// automatically test all Clojure namespaces in classpath
    }
    

### Testing a specific namespace:

    import mikera.cljunit.NamespaceTest;
    
    public class MyNamespaceTest extends NamespaceTest {
    	@Override
    	public String namespace() {
    		return "my.clojure.namespace";
    	}
    }

### Testing a specific subset of Clojure namespaces

Working example from vectorz-clj

    import mikera.cljunit.ClojureTest;
    
    public class ClojureTests extends ClojureTest {
    	@Override
    	public List<String> namespaces() {
    		return Arrays.asList(new String[] {
    			"mikera.vectorz.test-core",
    			"mikera.vectorz.test-matrix"			
    		});
    	}
    }