package ch.miranet.rdfstructure;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Namespace;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.eclipse.rdf4j.model.impl.SimpleNamespace;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.util.Models;
import org.eclipse.rdf4j.model.vocabulary.OWL;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.model.vocabulary.SKOS;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OwlOntologyTest {

	private Model refModel;
	private ModelBuilder refModelBuilder;
	private RdfStructureBuilder builder;
	private String ontologyIri;
	private Namespace nsEx;

	@BeforeEach
	public void setUp() {
		ontologyIri = "https://schema.example.org/myontology";
		nsEx = new SimpleNamespace("ex", ontologyIri + "/");

		refModel = new LinkedHashModel();
		refModelBuilder = new ModelBuilder(refModel);
		refModelBuilder.setNamespace(nsEx);

		builder = new RdfStructureBuilder();
		builder.getModelBuilder().setNamespace(nsEx);
	}

	@Test
	public void owlOntology() {
		builder.owlOntology(ontologyIri)
				.label("My Ontology")
				.any((modelBuilder, element) -> modelBuilder.subject(element)
						.add(SKOS.HIDDEN_LABEL, "Hidden label on my ontology"));

		refModelBuilder.subject(ontologyIri)
				.add(RDF.TYPE, OWL.ONTOLOGY)
				.add(RDFS.LABEL, "My Ontology")
				.add(SKOS.HIDDEN_LABEL, "Hidden label on my ontology");

		final Model refModel = refModelBuilder.build();

		assertTrue(Models.isomorphic(refModel, builder.modelBuilder.build()), "models are not isomorphic");
	}

	@Test
	public void owlClass_simple() {
		builder.owlOntology(ontologyIri)
				.owlClass("ex:MyClass");

		refModelBuilder.subject(ontologyIri)
				.add(RDF.TYPE, OWL.ONTOLOGY);

		refModelBuilder.subject("ex:MyClass")
				.add(RDF.TYPE, OWL.CLASS)
				.add(RDFS.ISDEFINEDBY, builder.valueFactory.createIRI(ontologyIri));

		final Model refModel = refModelBuilder.build();

		assertTrue(Models.isomorphic(refModel, builder.modelBuilder.build()), "models are not isomorphic");
	}

	@Test
	public void owlClass_usingConsumer() {
		builder.owlOntology(ontologyIri)
				.owlClass("ex:SuperClass", cls -> {
					cls
							.label("SuperClass")
							.comment("Comment on my SuperClass")
							.any((modelBuilder, element) -> modelBuilder.subject(element)
									.add(OWL.EQUIVALENTCLASS, "ex:SuperClassEquivalent"));
				})
				.owlClass("ex:SubClass", cls -> {
					cls
							.label("SubClass")
							.comment("Comment on my SubClass")
							.subClassOf("ex:SuperClass");
				});

		refModelBuilder.subject(ontologyIri)
				.add(RDF.TYPE, OWL.ONTOLOGY);

		refModelBuilder.subject("ex:SuperClass")
				.add(RDF.TYPE, OWL.CLASS)
				.add(RDFS.ISDEFINEDBY, builder.valueFactory.createIRI(ontologyIri))
				.add(RDFS.LABEL, "SuperClass")
				.add(RDFS.COMMENT, "Comment on my SuperClass")
				.add(OWL.EQUIVALENTCLASS, "ex:SuperClassEquivalent");

		refModelBuilder.subject("ex:SubClass")
				.add(RDF.TYPE, OWL.CLASS)
				.add(RDFS.ISDEFINEDBY, builder.valueFactory.createIRI(ontologyIri))
				.add(RDFS.LABEL, "SubClass")
				.add(RDFS.COMMENT, "Comment on my SubClass")
				.add(RDFS.SUBCLASSOF, "ex:SuperClass");

		final Model refModel = refModelBuilder.build();

		assertTrue(Models.isomorphic(refModel, builder.modelBuilder.build()), "models are not isomorphic");
	}

	@Test
	public void datatypeProperty_simple() {
		builder.owlOntology(ontologyIri)
				.datatypeProperty("ex:myDatatypeProperty");

		refModelBuilder.subject(ontologyIri)
				.add(RDF.TYPE, OWL.ONTOLOGY);

		refModelBuilder.subject("ex:myDatatypeProperty")
				.add(RDF.TYPE, OWL.DATATYPEPROPERTY)
				.add(RDFS.ISDEFINEDBY, builder.valueFactory.createIRI(ontologyIri));

		final Model refModel = refModelBuilder.build();

		assertTrue(Models.isomorphic(refModel, builder.modelBuilder.build()), "models are not isomorphic");
	}

	@Test
	public void datatypeProperty_usingConsumer() {
		builder.owlOntology(ontologyIri)
				.datatypeProperty("ex:myDatatypeProperty", prop -> {
					prop
							.label("my property")
							.comment("Comment on my property")
							.subPropertyOf("ex:superproperty")
							.any((modelBuilder, element) -> modelBuilder.subject(element)
									.add(OWL.EQUIVALENTPROPERTY, "ex:otherproperty"));
				});

		refModelBuilder.subject(ontologyIri)
				.add(RDF.TYPE, OWL.ONTOLOGY);

		refModelBuilder.subject("ex:myDatatypeProperty")
				.add(RDF.TYPE, OWL.DATATYPEPROPERTY)
				.add(RDFS.ISDEFINEDBY, builder.valueFactory.createIRI(ontologyIri))
				.add(RDFS.LABEL, "my property")
				.add(RDFS.COMMENT, "Comment on my property")
				.add(RDFS.SUBPROPERTYOF, "ex:superproperty")
				.add(OWL.EQUIVALENTPROPERTY, "ex:otherproperty");

		final Model refModel = refModelBuilder.build();

		assertTrue(Models.isomorphic(refModel, builder.modelBuilder.build()), "models are not isomorphic");
	}

	@Test
	public void objectProperty_simple() {
		builder.owlOntology(ontologyIri)
				.objectProperty("ex:myObjectProperty");

		refModelBuilder.subject(ontologyIri)
				.add(RDF.TYPE, OWL.ONTOLOGY);

		refModelBuilder.subject("ex:myObjectProperty")
				.add(RDF.TYPE, OWL.OBJECTPROPERTY)
				.add(RDFS.ISDEFINEDBY, builder.valueFactory.createIRI(ontologyIri));

		final Model refModel = refModelBuilder.build();

		assertTrue(Models.isomorphic(refModel, builder.modelBuilder.build()), "models are not isomorphic");
	}

	@Test
	public void objectProperty_usingConsumer() {
		builder.owlOntology(ontologyIri)
				.objectProperty("ex:myObjectProperty", prop -> {
					prop
							.label("my property")
							.comment("Comment on my property")
							.subPropertyOf("ex:superproperty")
							.any((modelBuilder, element) -> modelBuilder.subject(element)
									.add(OWL.EQUIVALENTPROPERTY, "ex:otherproperty"));
				});

		refModelBuilder.subject(ontologyIri)
				.add(RDF.TYPE, OWL.ONTOLOGY);

		refModelBuilder.subject("ex:myObjectProperty")
				.add(RDF.TYPE, OWL.OBJECTPROPERTY)
				.add(RDFS.ISDEFINEDBY, builder.valueFactory.createIRI(ontologyIri))
				.add(RDFS.LABEL, "my property")
				.add(RDFS.COMMENT, "Comment on my property")
				.add(RDFS.SUBPROPERTYOF, "ex:superproperty")
				.add(OWL.EQUIVALENTPROPERTY, "ex:otherproperty");

		final Model refModel = refModelBuilder.build();

		assertTrue(Models.isomorphic(refModel, builder.modelBuilder.build()), "models are not isomorphic");
	}
}
