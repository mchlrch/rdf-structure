package samples;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Namespace;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleNamespace;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;

public class EX {

	public static final String NAMESPACE = "http://example.org/";
	public static final String PREFIX = "ex";
	public static final Namespace NS = new SimpleNamespace(PREFIX, NAMESPACE);

	public static final IRI Foo;
	public static final IRI SpecialFoo;

	public static final IRI property1;
	public static final IRI specialProperty1;

	static {
		final ValueFactory f = SimpleValueFactory.getInstance();

		Foo = f.createIRI(NAMESPACE, "Foo");
		SpecialFoo = f.createIRI(NAMESPACE, "SpecialFoo");

		property1 = f.createIRI(NAMESPACE, "property1");
		specialProperty1 = f.createIRI(NAMESPACE, "specialProperty1");
	}

}
