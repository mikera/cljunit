package mikera.cljunit;

import java.lang.annotation.Annotation;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

import clojure.lang.RT;


public class TestRunner extends Runner {
	private Runner internalRunner;
	
	public TestRunner() {
		internalRunner=null;
		//internalRunner = (Runner) RT.var("mikera.test.cljunit", "create-runner").invoke();
	}
	
	@Override
	public Description getDescription() {
		return Description.createSuiteDescription("cljunit TestRunner", new Annotation[0]);
	}

	@Override
	public void run(RunNotifier notifier) {
		if (internalRunner!=null) {
			internalRunner.run(notifier);
		}
	}

}
