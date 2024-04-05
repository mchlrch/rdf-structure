package ch.miranet.rdfstructure;

import java.util.function.Consumer;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.vocabulary.SKOS;

public class ConceptScheme extends StructuralElement<IRI> {

	public ConceptScheme(RdfStructureBuilder structBuilder, IRI iri) {
		super(structBuilder, iri);
	}

	/** rdf:type skos:ConceptScheme */
	public ConceptScheme aConceptScheme() {
		super.a(SKOS.CONCEPT_SCHEME);

		return this;
	}

	/** skos:prefLabel */
	public ConceptScheme prefLabel(String label) {
		this.b.modelBuilder.subject(this.resource)
				.add(SKOS.PREF_LABEL, label);

		return this;
	}

	/** skos:hasTopConcept */
	public ConceptScheme hasTopConcept(String prefixedNameOrIri, Consumer<Concept> conceptConsumer) {
		return hasTopConcept(this.b.mapToIRI(prefixedNameOrIri), conceptConsumer);
	}

	/** skos:hasTopConcept */
	public ConceptScheme hasTopConcept(IRI conceptIri, Consumer<Concept> conceptConsumer) {
		final Concept concept = hasTopConcept0(conceptIri);
		conceptConsumer.accept(concept);
		return this;
	}

	/** skos:hasTopConcept */
	public ConceptScheme hasTopConcept(String prefixedNameOrIri) {
		return hasTopConcept(this.b.mapToIRI(prefixedNameOrIri));
	}

	/** skos:hasTopConcept */
	public ConceptScheme hasTopConcept(IRI iri) {
		hasTopConcept0(iri);
		return this;
	}

	protected Concept hasTopConcept0(IRI conceptIri) {
		this.b.modelBuilder.subject(conceptIri);

		this.b.modelBuilder.subject(this.resource)
				.add(SKOS.HAS_TOP_CONCEPT, conceptIri);

		return new Concept(this.b, conceptIri);
	}

	/** ^skos:inScheme */
	public ConceptScheme conceptInScheme(String prefixedNameOrIri, Consumer<Concept> conceptConsumer) {
		return conceptInScheme(this.b.mapToIRI(prefixedNameOrIri), conceptConsumer);
	}

	/** ^skos:inScheme */
	public ConceptScheme conceptInScheme(IRI conceptIri, Consumer<Concept> conceptConsumer) {
		final Concept concept = conceptInScheme0(conceptIri);
		conceptConsumer.accept(concept);
		return this;
	}

	/** ^skos:inScheme */
	public ConceptScheme conceptInScheme(String prefixedNameOrIri) {
		return conceptInScheme(this.b.mapToIRI(prefixedNameOrIri));
	}

	/** ^skos:inScheme */
	public ConceptScheme conceptInScheme(IRI iri) {
		conceptInScheme0(iri);
		return this;
	}

	protected Concept conceptInScheme0(IRI conceptIri) {
		this.b.modelBuilder.subject(conceptIri)
				.add(SKOS.IN_SCHEME, this.resource);

		return new Concept(this.b, conceptIri);
	}

}
