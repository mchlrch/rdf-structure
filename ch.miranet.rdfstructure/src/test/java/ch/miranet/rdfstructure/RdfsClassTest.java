package ch.miranet.rdfstructure;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Namespace;
import org.eclipse.rdf4j.model.impl.SimpleNamespace;
import org.eclipse.rdf4j.model.impl.TreeModel;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.vocabulary.OWL;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.model.vocabulary.SKOS;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RdfsClassTest {

	private ModelBuilder refModelBuilder;
	private RdfStructureBuilder builder;

	@BeforeEach
	public void setUp() {
		final Namespace nsEx = new SimpleNamespace("ex", "http://schema.example.org/");

		refModelBuilder = new ModelBuilder(new TreeModel());
		refModelBuilder.setNamespace(nsEx);

		builder = new RdfStructureBuilder(new TreeModel());
		builder.getModelBuilder().setNamespace(nsEx);
	}

	@Test
	public void rdfsClass() {
		builder.rdfsClass("ex:SuperClass").aRdfsClass()
				.label("SuperClass")
				.comment("Comment on SuperClass")
				.any((modelBuilder, element) -> modelBuilder.subject(element)
						.add(SKOS.HIDDEN_LABEL, "Hidden label on SuperClass"));

		builder.rdfsClass("ex:SubClass").aRdfsClass()
				.subClassOf("ex:SuperClass");

		final Model actualModel = builder.modelBuilder.build();

		refModelBuilder.subject("ex:SuperClass")
				.add(RDF.TYPE, RDFS.CLASS)
				.add(RDFS.LABEL, "SuperClass")
				.add(RDFS.COMMENT, "Comment on SuperClass")
				.add(SKOS.HIDDEN_LABEL, "Hidden label on SuperClass");

		refModelBuilder.subject("ex:SubClass")
				.add(RDF.TYPE, RDFS.CLASS)
				.add(RDFS.SUBCLASSOF, "ex:SuperClass");

		final Model refModel = refModelBuilder.build();

//		System.out.println(String.format("expected: %s", refModel));
//		System.out.println(String.format("actual:   %s", actualModel));

		assertIterableEquals(refModel, actualModel);
	}

	@Test
	public void owlClass() {
		builder.rdfsClass("ex:SomeClass").aOwlClass();

		final Model actualModel = builder.modelBuilder.build();

		refModelBuilder.subject("ex:SomeClass")
				.add(RDF.TYPE, OWL.CLASS);

		final Model refModel = refModelBuilder.build();

		assertIterableEquals(refModel, actualModel);
	}

}
