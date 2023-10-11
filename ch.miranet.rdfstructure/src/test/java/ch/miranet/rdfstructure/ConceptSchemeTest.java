package ch.miranet.rdfstructure;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Namespace;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.eclipse.rdf4j.model.impl.SimpleNamespace;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.util.Models;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.SKOS;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConceptSchemeTest {

	private Model refModel;
	private ModelBuilder refModelBuilder;
	private RdfStructureBuilder builder;
	private String schemeIri;
	private Namespace nsEx;

	@BeforeEach
	public void setUp() {
		schemeIri = "https://data.example.org/vocabulary/my-scheme";
		nsEx = new SimpleNamespace("ex", schemeIri + "/");

		refModel = new LinkedHashModel();
		refModelBuilder = new ModelBuilder(refModel);
		refModelBuilder.setNamespace(nsEx);

		builder = new RdfStructureBuilder();
		builder.getModelBuilder().setNamespace(nsEx);
	}

	@Test
	public void conceptScheme() {
		builder.conceptScheme(schemeIri)
				.prefLabel("My Scheme")
				.any((modelBuilder, element) -> modelBuilder.subject(element)
						.add(SKOS.HIDDEN_LABEL, "Hidden label on my scheme"));

		refModelBuilder.subject(schemeIri)
				.add(RDF.TYPE, SKOS.CONCEPT_SCHEME)
				.add(SKOS.PREF_LABEL, "My Scheme")
				.add(SKOS.HIDDEN_LABEL, "Hidden label on my scheme");

		final Model refModel = refModelBuilder.build();

		assertTrue(Models.isomorphic(refModel, builder.modelBuilder.build()), "models are not isomorphic");
	}

	@Test
	public void topConcept_simple() {
		builder.conceptScheme(schemeIri)
				.hasTopConcept("ex:MyConcept");

		refModelBuilder.subject(schemeIri)
				.add(RDF.TYPE, SKOS.CONCEPT_SCHEME)
				.add(SKOS.HAS_TOP_CONCEPT, "ex:MyConcept");

		refModelBuilder.subject("ex:MyConcept")
				.add(RDF.TYPE, SKOS.CONCEPT);

		final Model refModel = refModelBuilder.build();

		assertTrue(Models.isomorphic(refModel, builder.modelBuilder.build()), "models are not isomorphic");
	}

	@Test
	public void topConcept_usingConsumer() {
		builder.conceptScheme(schemeIri)
				.hasTopConcept("ex:MyConcept", concept -> {
					concept
							.prefLabel("MyConcept")
							.any((modelBuilder, element) -> modelBuilder.subject(element)
									.add(SKOS.HIDDEN_LABEL, "Hidden label on my concept"));
				});

		refModelBuilder.subject(schemeIri)
				.add(RDF.TYPE, SKOS.CONCEPT_SCHEME)
				.add(SKOS.HAS_TOP_CONCEPT, "ex:MyConcept");

		refModelBuilder.subject("ex:MyConcept")
				.add(RDF.TYPE, SKOS.CONCEPT)
				.add(SKOS.PREF_LABEL, "MyConcept")
				.add(SKOS.HIDDEN_LABEL, "Hidden label on my concept");

		final Model refModel = refModelBuilder.build();

		assertTrue(Models.isomorphic(refModel, builder.modelBuilder.build()), "models are not isomorphic");
	}

}
