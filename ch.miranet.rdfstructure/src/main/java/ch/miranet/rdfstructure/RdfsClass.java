package ch.miranet.rdfstructure;

import org.eclipse.rdf4j.model.IRI;

public class RdfsClass {

	protected final IRI iri;

	public RdfsClass(IRI iri) {
		this.iri = iri;
	}

	public RdfsClass subClassOf(RdfsClass superclass) {
		throw new UnimplementedFeatureException();
	}

	public RdfsClass label(String string) {
		throw new UnimplementedFeatureException();
	}

}
