package ch.miranet.rdfstructure;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.vocabulary.SKOS;

public class Concept extends StructuralElement<IRI> {

	public Concept(RdfStructureBuilder structBuilder, IRI iri) {
		super(structBuilder, iri);
	}

	/** skos:prefLabel */
	public Concept prefLabel(String label) {
		this.b.modelBuilder.subject(this.resource)
				.add(SKOS.PREF_LABEL, label);

		return this;
	}

	/** skos:broader */
	public Concept broader(Concept broaderConcept) {
		return broader(broaderConcept.resource);
	}
	
	/** skos:broader */
	public Concept broader(String broaderConceptPrefixedNameOrIri) {
		return broader(this.b.mapToIRI(broaderConceptPrefixedNameOrIri));
	}
	
	/** skos:broader */
	public Concept broader(IRI broaderConceptIri) {
		this.b.modelBuilder.subject(this.resource)
			.add(SKOS.BROADER, broaderConceptIri);

		return this;
	}
}
