package ch.miranet.rdfstructure;

import org.eclipse.rdf4j.model.IRI;

public class RdfProperty {

	protected final IRI iri;

	public RdfProperty(IRI iri) {
		this.iri = iri;
	}

	public RdfProperty label(String label) {
		throw new UnimplementedFeatureException();
	}

	public RdfProperty subPropertyOf(RdfProperty property) {
		throw new UnimplementedFeatureException();
	}

}
