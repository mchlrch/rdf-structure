package ch.miranet.rdfstructure;

public class RdfProperty {

	protected final String prefixedNameOrIri;

	public RdfProperty(String prefixedNameOrIri) {
		this.prefixedNameOrIri = prefixedNameOrIri;
	}

	public void label(String label) {
		throw new UnimplementedFeatureException();
	}

	public void subPropertyOf(RdfProperty property) {
		throw new UnimplementedFeatureException();
	}

}
