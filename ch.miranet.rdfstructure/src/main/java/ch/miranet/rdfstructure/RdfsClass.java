package ch.miranet.rdfstructure;

public class RdfsClass {

	protected final String prefixedNameOrIri;

	public RdfsClass(String prefixedNameOrIri) {
		this.prefixedNameOrIri = prefixedNameOrIri;
	}

	public void subClassOf(RdfsClass cFoo) {
		throw new UnimplementedFeatureException();
	}

	public void label(String string) {
		throw new UnimplementedFeatureException();
	}

}
