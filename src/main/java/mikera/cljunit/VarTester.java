package mikera.cljunit;

import org.junit.runner.Description;
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
			testVar.invoke();
		} catch (Throwable t) {
			
		}
		n.fireTestFinished(getDescription());
	}

	public Description getDescription() {
		return desc;
	}
}
