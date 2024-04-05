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

		refModel = new TreeModel();
		refModelBuilder = new ModelBuilder(refModel);
		refModelBuilder.setNamespace(nsEx);

		builder = new RdfStructureBuilder(new TreeModel());
		builder.getModelBuilder().setNamespace(nsEx);
	}

	@Test
	public void conceptScheme() {
		builder.conceptScheme(schemeIri).aConceptScheme()
				.prefLabel("My Scheme")
				.any((modelBuilder, element) -> modelBuilder.subject(element)
						.add(SKOS.HIDDEN_LABEL, "Hidden label on my scheme"));

		final Model actualModel = builder.modelBuilder.build();

		refModelBuilder.subject(schemeIri)
				.add(RDF.TYPE, SKOS.CONCEPT_SCHEME)
				.add(SKOS.PREF_LABEL, "My Scheme")
				.add(SKOS.HIDDEN_LABEL, "Hidden label on my scheme");

		final Model refModel = refModelBuilder.build();

		assertIterableEquals(refModel, actualModel);
	}

	@Test
	public void topConcept_simple() {
		builder.conceptScheme(schemeIri).aConceptScheme()
				.hasTopConcept("ex:MyConcept");

		final Model actualModel = builder.modelBuilder.build();

		refModelBuilder.subject(schemeIri)
				.add(RDF.TYPE, SKOS.CONCEPT_SCHEME)
				.add(SKOS.HAS_TOP_CONCEPT, "ex:MyConcept");

		final Model refModel = refModelBuilder.build();

		assertIterableEquals(refModel, actualModel);
	}

	@Test
	public void topConcept_usingConsumer() {
		builder.conceptScheme(schemeIri).aConceptScheme()
				.hasTopConcept("ex:MyConcept", concept -> {
					concept
							.aConcept()
							.prefLabel("MyConcept")
							.any((modelBuilder, element) -> modelBuilder.subject(element)
									.add(SKOS.HIDDEN_LABEL, "Hidden label on my concept"));
				});

		final Model actualModel = builder.modelBuilder.build();

		refModelBuilder.subject(schemeIri)
				.add(RDF.TYPE, SKOS.CONCEPT_SCHEME)
				.add(SKOS.HAS_TOP_CONCEPT, "ex:MyConcept");

		refModelBuilder.subject("ex:MyConcept")
				.add(RDF.TYPE, SKOS.CONCEPT)
				.add(SKOS.PREF_LABEL, "MyConcept")
				.add(SKOS.HIDDEN_LABEL, "Hidden label on my concept");

		final Model refModel = refModelBuilder.build();

		assertIterableEquals(refModel, actualModel);
	}

	@Test
	public void conceptInScheme_simple() {
		builder.conceptScheme(schemeIri).aConceptScheme()
				.conceptInScheme("ex:MyConcept");

		final Model actualModel = builder.modelBuilder.build();

		refModelBuilder.subject(schemeIri)
				.add(RDF.TYPE, SKOS.CONCEPT_SCHEME);

		final IRI schemeIriAsIri = Values.iri(schemeIri);

		refModelBuilder.subject("ex:MyConcept")
				.add(SKOS.IN_SCHEME, schemeIriAsIri);

		final Model refModel = refModelBuilder.build();

		assertIterableEquals(refModel, actualModel);
	}

	@Test
	public void conceptInScheme_usingConsumer() {
		builder.conceptScheme(schemeIri).aConceptScheme()
				.conceptInScheme("ex:MyConcept", concept -> {
					concept
							.aConcept()
							.prefLabel("MyConcept")
							.any((modelBuilder, element) -> modelBuilder.subject(element)
									.add(SKOS.HIDDEN_LABEL, "Hidden label on my concept"));
				});

		final Model actualModel = builder.modelBuilder.build();

		refModelBuilder.subject(schemeIri)
				.add(RDF.TYPE, SKOS.CONCEPT_SCHEME);

		final IRI schemeIriAsIri = Values.iri(schemeIri);

		refModelBuilder.subject("ex:MyConcept")
				.add(SKOS.IN_SCHEME, schemeIriAsIri)
				.add(RDF.TYPE, SKOS.CONCEPT)
				.add(SKOS.PREF_LABEL, "MyConcept")
				.add(SKOS.HIDDEN_LABEL, "Hidden label on my concept");

		final Model refModel = refModelBuilder.build();

//		System.out.println(String.format("expected: %s", refModel));
//		System.out.println(String.format("actual:   %s", actualModel));

		assertIterableEquals(refModel, actualModel);
	}

}
