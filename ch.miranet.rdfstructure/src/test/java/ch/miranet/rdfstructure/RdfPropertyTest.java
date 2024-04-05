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

public class RdfPropertyTest {

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
	public void rdfProperty() {
		builder.rdfProperty("ex:superproperty").aRdfProperty()
				.label("super property")
				.comment("Comment on super property")
				.any((modelBuilder, element) -> modelBuilder.subject(element)
						.add(SKOS.HIDDEN_LABEL, "Hidden label on super property"));

		builder.rdfProperty("ex:subproperty").aRdfProperty()
				.subPropertyOf("ex:superproperty");

		final Model actualModel = builder.modelBuilder.build();

		refModelBuilder.subject("ex:superproperty")
				.add(RDF.TYPE, RDF.PROPERTY)
				.add(RDFS.LABEL, "super property")
				.add(RDFS.COMMENT, "Comment on super property")
				.add(SKOS.HIDDEN_LABEL, "Hidden label on super property");

		refModelBuilder.subject("ex:subproperty")
				.add(RDF.TYPE, RDF.PROPERTY)
				.add(RDFS.SUBPROPERTYOF, "ex:superproperty");

		final Model refModel = refModelBuilder.build();

//		System.out.println(String.format("expected: %s", refModel));
//		System.out.println(String.format("actual:   %s", actualModel));

		assertIterableEquals(refModel, actualModel);
	}

	@Test
	public void owlDatatypeProperty() {
		builder.rdfProperty("ex:someproperty").aOwlDatatypeProperty();

		final Model actualModel = builder.modelBuilder.build();

		refModelBuilder.subject("ex:someproperty")
				.add(RDF.TYPE, OWL.DATATYPEPROPERTY);

		final Model refModel = refModelBuilder.build();

		assertIterableEquals(refModel, actualModel);
	}

	@Test
	public void owlObjectProperty() {
		builder.rdfProperty("ex:someproperty").aOwlObjectProperty();

		final Model actualModel = builder.modelBuilder.build();

		refModelBuilder.subject("ex:someproperty")
				.add(RDF.TYPE, OWL.OBJECTPROPERTY);

		final Model refModel = refModelBuilder.build();

		assertIterableEquals(refModel, actualModel);
	}

}
