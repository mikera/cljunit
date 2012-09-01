package mikera.cljunit;

import org.junit.Test;


public class ClojureTest extends junit.framework.TestSuite {
	static {
		mikera.cljunit.TestRunner.staticInit();
	}
	
	@Test
	public void testRunner() {
		// TODO
	}
}
