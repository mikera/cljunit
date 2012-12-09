package mikera.cljunit;

import junit.framework.AssertionFailedError;

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
			testVar.invoke();
		} catch (AssertionFailedError t) {
			n.fireTestAssumptionFailed(new Failure(getDescription(), t));
		} catch (Throwable t) {
			n.fireTestFailure(new Failure(getDescription(), t));
		}
		n.fireTestFinished(getDescription());
	}

	public Description getDescription() {
		return desc;
	}
}
