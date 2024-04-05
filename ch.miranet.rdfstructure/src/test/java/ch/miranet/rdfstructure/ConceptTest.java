package ch.miranet.rdfstructure;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Namespace;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.eclipse.rdf4j.model.impl.SimpleNamespace;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.util.Models;
import org.eclipse.rdf4j.model.util.Values;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.SKOS;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConceptTest {

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
	public void concept() {
		builder.conceptScheme(schemeIri).aConceptScheme()
				.conceptInScheme("ex:fruit", fruit -> fruit
						.aConcept())

				.conceptInScheme("ex:apple", apple -> apple
						.aConcept()
						.prefLabel("Apple")
						.broader("ex:fruit")
						.any((modelBuilder, element) -> modelBuilder.subject(element)
								.add(SKOS.HIDDEN_LABEL, "Hidden label on my apple")));

		refModelBuilder.subject(schemeIri)
				.add(RDF.TYPE, SKOS.CONCEPT_SCHEME);

		final IRI schemeIriAsIri = Values.iri(schemeIri);

		refModelBuilder.subject("ex:fruit")
				.add(RDF.TYPE, SKOS.CONCEPT)
				.add(SKOS.IN_SCHEME, schemeIriAsIri);

		refModelBuilder.subject("ex:apple")
				.add(RDF.TYPE, SKOS.CONCEPT)
				.add(SKOS.IN_SCHEME, schemeIriAsIri)
				.add(SKOS.PREF_LABEL, "Apple")
				.add(SKOS.BROADER, "ex:fruit")
				.add(SKOS.HIDDEN_LABEL, "Hidden label on my apple");

		final Model refModel = refModelBuilder.build();

		assertTrue(Models.isomorphic(refModel, builder.modelBuilder.build()), "models are not isomorphic");
	}

}
