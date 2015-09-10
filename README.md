# cljunit

JUnit test integration for Clojure.

The purpose of cljunit is to provide a convenient way to write JUnit tests that test Clojure namespaces. This is
very useful if, for example, you want to test Clojure code within a Java IDE like Eclipse that has integrated
JUnit testing facilities.

[![Build Status](https://secure.travis-ci.org/mikera/cljunit.png?branch=master)](https://travis-ci.org/mikera/cljunit)

## Usage

Include cljunit as a dependency from clojars:

[![Clojars Project](http://clojars.org/net.mikera/cljunit/latest-version.svg)](http://clojars.org/net.mikera/cljunit)

Then you should extend one of the cljunit classes to create a JUnit test suite to test your Clojure files.

Examples below:

### Testing all Clojure namespaces:    

This is the simplest solution that works for most projects. Just be warned: it will run every Clojure test on the classpath. Which might be a lot if your imported libraries have a lot of tests in them.

    import mikera.cljunit.ClojureTest;
    
    public class ClojureTests extends ClojureTest {
    	// automatically test all Clojure namespaces in classpath
    }
    
### Testing all Clojure namespaces with a given prefix:    

    import mikera.cljunit.ClojureTest;
    
    public class ClojureTests extends ClojureTest {
    	// filter namespaces with the given prefix
    	@Override public String filter() {
    	    return "com.mycompany";
    	}
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
