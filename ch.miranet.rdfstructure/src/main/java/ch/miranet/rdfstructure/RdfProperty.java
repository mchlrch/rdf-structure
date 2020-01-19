package ch.miranet.rdfstructure;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.vocabulary.RDFS;

public class RdfProperty extends StructuralElement<IRI> {

	public RdfProperty(RdfStructureBuilder structBuilder, IRI iri) {
		super(structBuilder, iri);
	}

	public RdfProperty label(String label) {
		this.b.modelBuilder.subject(this.resource)
				.add(RDFS.LABEL, label);

		return this;
	}

	public RdfProperty subPropertyOf(RdfProperty property) {
		this.b.modelBuilder.subject(this.resource)
				.add(RDFS.SUBPROPERTYOF, property.resource);

		return this;
	}

}
