package ch.miranet.rdfstructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.rdf4j.model.BNode;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Namespace;
import org.eclipse.rdf4j.model.impl.SimpleNamespace;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.util.Models;
import org.eclipse.rdf4j.model.vocabulary.OWL;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.model.vocabulary.SHACL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RdfStructureBuilderTest {

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
	public void empty() {
		final Model model = builder.modelBuilder.build();

		assertTrue(model.isEmpty());
	}

	@Test
	public void rdfsClass() {
		final RdfsClass rdfsClass = builder.rdfsClass("ex:MyClass");

		final Model refModel = refModelBuilder.subject("ex:MyClass")
				.add(RDF.TYPE, RDFS.CLASS)
				.build();

		assertNotNull(rdfsClass);

		assertEquals(refModel, builder.modelBuilder.build());
	}

	@Test
	public void rdfProperty() {
		final RdfProperty rdfProperty = builder.rdfProperty("ex:property1");

		final Model refModel = refModelBuilder.subject("ex:property1")
				.add(RDF.TYPE, RDF.PROPERTY)
				.build();

		assertNotNull(rdfProperty);

		assertEquals(refModel, builder.modelBuilder.build());
	}

	@Test
	public void nodeShape() {
		final NodeShape nodeShape = builder.nodeShape("ex:NodeShape1");

		final Model refModel = refModelBuilder.subject("ex:NodeShape1")
				.add(RDF.TYPE, SHACL.NODE_SHAPE)
				.build();

		assertNotNull(nodeShape);

		assertEquals(refModel, builder.modelBuilder.build());
	}
	
	@Test
	public void nodeShape_bnode() {
		final NodeShape nodeShape = builder.nodeShape();

		final BNode nodeShapeBNode = builder.valueFactory.createBNode();
		final Model refModel = refModelBuilder.subject(nodeShapeBNode)
				.add(RDF.TYPE, SHACL.NODE_SHAPE)
				.build();

		assertNotNull(nodeShape);

		assertTrue(Models.isomorphic(refModel, builder.modelBuilder.build()), "models are not isomorphic");
	}
	
	@Test
	public void owlOntology() {
		final String ontologyIri = "https://schema.example.org/myontology";
		
		final OwlOntology owlOntology = builder.owlOntology(ontologyIri);

		final Model refModel = refModelBuilder.subject(ontologyIri)
				.add(RDF.TYPE, OWL.ONTOLOGY)
				.build();

		assertNotNull(owlOntology);

		assertEquals(refModel, builder.modelBuilder.build());
	}

}
