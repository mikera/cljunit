package mikera.cljunit;

import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

import clojure.lang.RT;
import clojure.lang.Var;

public class VarTester {
	Var testVar;
	Description desc;
	
	public VarTester(String ns,String name) {
		Clojure.require(ns);
		testVar=RT.var(ns, name);
		
		desc=Description.createSuiteDescription(ns+"/"+name);		
	}
	
	public void runTest(RunNotifier n) {
		n.fireTestStarted(getDescription());
		
		try {
			Clojure.invokeTest(testVar);
			n.fireTestFinished(getDescription());
		} catch (Throwable t) {
			n.fireTestFailure(new Failure(getDescription(), t));
		}
	}

	public Description getDescription() {
		return desc;
	}
}
