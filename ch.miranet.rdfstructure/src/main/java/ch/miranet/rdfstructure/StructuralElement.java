package ch.miranet.rdfstructure;

import org.eclipse.rdf4j.model.Resource;

public abstract class StructuralElement<T extends Resource> {

	protected final RdfStructureBuilder b;
	protected final T resource;

	public StructuralElement(RdfStructureBuilder structBuilder, T resource) {
		this.b = structBuilder;
		this.resource = resource;
	}

}
