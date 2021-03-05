package com.zazuko.rdfstructure;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.eclipse.rdf4j.model.BNode;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Namespace;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.eclipse.rdf4j.model.impl.SimpleNamespace;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.util.Models;
import org.eclipse.rdf4j.model.util.RDFCollections;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.model.vocabulary.SHACL;
import org.eclipse.rdf4j.model.vocabulary.SKOS;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NodeShapeTest {

	private Model refModel;
	private ModelBuilder refModelBuilder;
	private RdfStructureBuilder builder;
	private Namespace nsEx;

	@BeforeEach
	public void setUp() {
		nsEx = new SimpleNamespace("ex", "http://schema.example.org/");

		refModel = new LinkedHashModel();
		refModelBuilder = new ModelBuilder(refModel);
		refModelBuilder.setNamespace(nsEx);

		builder = new RdfStructureBuilder();
		builder.getModelBuilder().setNamespace(nsEx);
	}

	@Test
	public void nodeShape() {
		builder.nodeShape("ex:MyShape")
				.label("My Shape")
				.comment("Comment on my shape")
				.any((modelBuilder, element) -> modelBuilder.subject(element)
						.add(SKOS.HIDDEN_LABEL, "Hidden label on my shape"))
				.targetClass("ex:MyTargetClass")
				.deactivated(true)
				.closed(true)
				.ignoredProperties("rdf:type", "ex:foo");

		final IRI propertyFoo = SimpleValueFactory.getInstance().createIRI(nsEx.getName(), "foo");
		final List<IRI> ignoredProperties = Arrays.asList(RDF.TYPE, propertyFoo);

		final BNode ignoredPropertiesListHead = builder.valueFactory.createBNode();
		RDFCollections.asRDF(ignoredProperties, ignoredPropertiesListHead, refModel);

		refModelBuilder.subject("ex:MyShape")
				.add(RDF.TYPE, SHACL.NODE_SHAPE)
				.add(RDFS.LABEL, "My Shape")
				.add(RDFS.COMMENT, "Comment on my shape")
				.add(SKOS.HIDDEN_LABEL, "Hidden label on my shape")
				.add(SHACL.TARGET_CLASS, "ex:MyTargetClass")
				.add(SHACL.DEACTIVATED, true)
				.add(SHACL.CLOSED, true)
				.add(SHACL.IGNORED_PROPERTIES, ignoredPropertiesListHead);

		final Model refModel = refModelBuilder.build();

		assertTrue(Models.isomorphic(refModel, builder.modelBuilder.build()), "models are not isomorphic");
	}

}
