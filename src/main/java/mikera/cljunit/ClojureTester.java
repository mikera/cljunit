package mikera.cljunit;

import java.util.ArrayList;
import java.util.List;

import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;

class ClojureTester {
	private Description desc;
	public List<String> namespaces;
	
	@SuppressWarnings("unused")
	public ArrayList<NamespaceTester> children=new ArrayList<NamespaceTester>();
	
	public ClojureTester(List<String> ns) {
		this.namespaces=ns;
		desc= Description.createSuiteDescription("Clojure Tests");
	
		for (String v: namespaces) {
			NamespaceTester vt=new NamespaceTester(v);
			desc.addChild(vt.getDescription());
			children.add(vt);
		}
	}
	
	public Description getDescription() {
		return desc;
	}
	
	public void runTest(RunNotifier n) {
		for (NamespaceTester nt:children) {
			nt.runTest(n);
		}
	}

}
