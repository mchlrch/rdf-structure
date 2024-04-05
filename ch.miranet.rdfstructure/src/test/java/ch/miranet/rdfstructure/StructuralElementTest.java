package ch.miranet.rdfstructure;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Namespace;
import org.eclipse.rdf4j.model.impl.SimpleNamespace;
import org.eclipse.rdf4j.model.impl.TreeModel;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.util.Values;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.SKOS;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StructuralElementTest {

	private Namespace nsEx;
	private ModelBuilder refModelBuilder;
	private RdfStructureBuilder builder;

	@BeforeEach
	public void setUp() {
		nsEx = new SimpleNamespace("ex", "http://schema.example.org/");

		refModelBuilder = new ModelBuilder(new TreeModel());
		refModelBuilder.setNamespace(nsEx);

		builder = new RdfStructureBuilder(new TreeModel());
		builder.getModelBuilder().setNamespace(nsEx);
	}

	@Test
	public void myStructuralElement() {
		final IRI elemIri = Values.iri(nsEx, "myElement");
		final IRI myClass2Iri = Values.iri(nsEx, "MyClass2");

		final MyStructuralElement elem = new MyStructuralElement(builder, elemIri);
		elem.a("ex:MyClass1").a(myClass2Iri)
				.any((modelBuilder, element) -> modelBuilder.subject(element)
						.add(SKOS.HIDDEN_LABEL, "Hidden label on myElement"));

		final Model actualModel = builder.modelBuilder.build();

		refModelBuilder.subject(elemIri)
				.add(RDF.TYPE, "ex:MyClass1")
				.add(RDF.TYPE, myClass2Iri)
				.add(SKOS.HIDDEN_LABEL, "Hidden label on myElement");

		final Model refModel = refModelBuilder.build();

//		System.out.println(String.format("expected: %s", refModel));
//		System.out.println(String.format("actual:   %s", actualModel));

		assertIterableEquals(refModel, actualModel);
	}

	private static class MyStructuralElement extends StructuralElement<IRI> {
		public MyStructuralElement(RdfStructureBuilder structBuilder, IRI iri) {
			super(structBuilder, iri);
		}
	}

}
