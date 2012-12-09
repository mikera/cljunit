cljunit
=======

JUnit test integration for Clojure.

The purpose of cljunit is to provide a convenient way to write JUnit tests that test Clojure namespaces. This is
very useful if, for example, you want to test Clojure code within a Java IDE like Eclipse that has integrated
JUnit testing facilities.

## Usage

Include cljunit as a dependency from clojars: (https://clojars.org/net.mikera/cljunit)

Then you can extend one of the cljunit classes to allow JUnit to test your Clojure files as below:

### Testing a specific namespace:

    import mikera.cljunit.NamespaceTest;
    
    public class MyNamespaceTest extends NamespaceTest {
    	@Override
    	public String namespace() {
    		return "my.clojure.namespace";
    	}
    }
    
### Testing all Clojure namespaces:    

    public class ClojureTests extends ClojureTest {
    	// automatically test all Clojure namespaces in classpath
    }