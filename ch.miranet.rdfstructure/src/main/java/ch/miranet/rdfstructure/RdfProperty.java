package ch.miranet.rdfstructure;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.vocabulary.RDFS;

public class RdfProperty extends StructuralElement<IRI> {

	public RdfProperty(RdfStructureBuilder structBuilder, IRI iri) {
		super(structBuilder, iri);
	}

	public RdfProperty subPropertyOf(RdfProperty property) {
		return subPropertyOf(property.resource);
	}

	public RdfProperty subPropertyOf(String propertyPrefixedNameOrIri) {
		return subPropertyOf(this.b.mapToIRI(propertyPrefixedNameOrIri));
	}

	public RdfProperty subPropertyOf(IRI property) {
		this.b.modelBuilder.subject(this.resource)
				.add(RDFS.SUBPROPERTYOF, property);

		return this;
	}

	public RdfProperty label(String label) {
		this.b.modelBuilder.subject(this.resource)
				.add(RDFS.LABEL, label);

		return this;
	}

}
