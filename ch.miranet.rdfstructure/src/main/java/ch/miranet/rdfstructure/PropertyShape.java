package ch.miranet.rdfstructure;

import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.vocabulary.SHACL;

public class PropertyShape {

	protected final RdfStructureBuilder b;
	protected final Resource propertyShapeResource;

	public PropertyShape(RdfStructureBuilder structBuilder, Resource propertyShapeResource) {
		this.b = structBuilder;
		this.propertyShapeResource = propertyShapeResource;
	}

	public PropertyShape minCount(int count) {
		this.b.modelBuilder.subject(this.propertyShapeResource)
				.add(SHACL.MIN_COUNT, count);

		return this;
	}

	public PropertyShape maxCount(int count) {
		this.b.modelBuilder.subject(this.propertyShapeResource)
				.add(SHACL.MAX_COUNT, count);

		return this;
	}

	public PropertyShape count(int count) {
		return minCount(count).maxCount(count);
	}

	public PropertyShape shClass(String prefixedNameOrIri) {
		throw new UnimplementedFeatureException();
	}

}
