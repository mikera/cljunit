package mikera.cljunit;

import java.lang.annotation.Annotation;

import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

import clojure.lang.RT;

@RunWith(TestRunner.class)
public class TestRunner extends Runner {
	public static final Description DESCRIPTION = Description.createSuiteDescription("cljunit TestRunner", new Annotation[0]);
	
	public TestRunner(Class<?> c) throws Exception {
		RT.loadResourceScript("mikera/cljunit/core.clj");
	}
	 
	@Override
	public Description getDescription() {
		return DESCRIPTION;
	}

	@Override
	public void run(RunNotifier notifier) {
		Description desc=getDescription();
		
		RT.var("mikera.cljunit.core", "run-junit-tests").invoke(notifier);
		notifier.fireTestStarted(desc);
		notifier.fireTestFinished(desc);
	}

	public static void staticInit() {
		// TODO: initialization
	}

}
