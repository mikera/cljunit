package mikera.cljunit;

import java.util.ArrayList;
import java.util.List;

import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;

class ClojureTester {
	public Description d;
	public List<String> namespaces;
	
	@SuppressWarnings("unused")
	public ArrayList<NamespaceTester> children=new ArrayList<NamespaceTester>();
	
	public ClojureTester(List<String> ns) {
		this.namespaces=ns;
		d= Description.createSuiteDescription("Clojure Tests");
	
		for (String v: namespaces) {
			NamespaceTester vt=new NamespaceTester(v);
			d.addChild(vt.getDescription());
			children.add(vt);
		}
	}
	
	public Description getDescription() {
		return d;
	}
	
	public void runTest(RunNotifier n) {
		for (NamespaceTester vt:children) {
			vt.runTest(n);
		}
	}

}
