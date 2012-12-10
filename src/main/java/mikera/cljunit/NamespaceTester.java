package mikera.cljunit;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;

class NamespaceTester {
	public Description d;
	public String namespace;
	
	@SuppressWarnings("unused")
	public ArrayList<VarTester> children=new ArrayList<VarTester>();
	
	public NamespaceTester(String ns) {
		try {
			this.namespace=ns;
			d= Description.createSuiteDescription(namespace);
			Collection<String> testVars=Clojure.getTestVars(namespace);
			
			for (String v:testVars) {
				VarTester vt=new VarTester(namespace,v);
				d.addChild(vt.getDescription());
				children.add(vt);
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
	public Description getDescription() {
		return d;
	}
	
	public void runTest(RunNotifier n) {
		n.fireTestStarted(d);
		for (VarTester vt:children) {
			vt.runTest(n);
		}	
		n.fireTestFinished(d);
	}

}
