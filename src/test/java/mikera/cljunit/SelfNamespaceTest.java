package mikera.cljunit;

public class SelfNamespaceTest extends NamespaceTest {

	@Override
	public String namespace() {
		return "mikera.cljunit.core";
	}
	
}
