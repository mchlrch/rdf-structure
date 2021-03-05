package ch.miranet.rdfstructure;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Namespace;
import org.eclipse.rdf4j.model.impl.SimpleNamespace;
import org.eclipse.rdf4j.model.util.ModelBuilder;
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

		refModelBuilder = new ModelBuilder();
		refModelBuilder.setNamespace(nsEx);

		builder = new RdfStructureBuilder();
		builder.getModelBuilder().setNamespace(nsEx);
	}

	@Test
	public void rdfProperty() {
		builder.rdfProperty("ex:superproperty")
				.label("super property")
				.comment("Comment on super property")
				.any((modelBuilder, element) -> modelBuilder.subject(element)
						.add(SKOS.HIDDEN_LABEL, "Hidden label on super property"));

		builder.rdfProperty("ex:subproperty")
				.subPropertyOf("ex:superproperty");

		refModelBuilder.subject("ex:superproperty")
				.add(RDF.TYPE, RDF.PROPERTY)
				.add(RDFS.LABEL, "super property")
				.add(RDFS.COMMENT, "Comment on super property")
				.add(SKOS.HIDDEN_LABEL, "Hidden label on super property");

		refModelBuilder.subject("ex:subproperty")
				.add(RDF.TYPE, RDF.PROPERTY)
				.add(RDFS.SUBPROPERTYOF, "ex:superproperty");

		final Model refModel = refModelBuilder.build();

		assertEquals(refModel, builder.modelBuilder.build());
	}


}
