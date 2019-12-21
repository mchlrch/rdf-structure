package ch.miranet.rdfstructure;

import org.eclipse.rdf4j.model.BNode;
import org.eclipse.rdf4j.model.vocabulary.SHACL;

public class NodeShape {

	protected final RdfStructureBuilder b;
	protected final String prefixedNameOrIri;

	public NodeShape(RdfStructureBuilder structBuilder, String prefixedNameOrIri) {
		this.b = structBuilder;
		this.prefixedNameOrIri = prefixedNameOrIri;
	}

	public NodeShape targetClass(RdfsClass targetClass) {
		return targetClass(targetClass.prefixedNameOrIri);
	}

	public NodeShape targetClass(String targetClassPrefixedNameOrIri) {
		this.b.modelBuilder.subject(this.prefixedNameOrIri)
				.add(SHACL.TARGET_CLASS, targetClassPrefixedNameOrIri);

		return this;
	}

	public PropertyShape property(String propertyPrefixedNameOrIri) {
		final BNode propertyShapeBNode = this.b.valueFactory.createBNode();

		this.b.modelBuilder.subject(this.prefixedNameOrIri)
				.add(SHACL.PROPERTY, propertyShapeBNode);

		this.b.modelBuilder.subject(propertyShapeBNode)
				.add(SHACL.PATH, propertyPrefixedNameOrIri);

		return new PropertyShape(this.b, propertyShapeBNode);
	}

	public PropertyShape property(RdfProperty property) {
		throw new UnimplementedFeatureException();
	}

	public NodeShape deactivated(boolean b) {
		throw new UnimplementedFeatureException();
	}

	public NodeShape closed(boolean b) {
		throw new UnimplementedFeatureException();
	}

	public NodeShape ignoredProperties(String string) {
		throw new UnimplementedFeatureException();
	}

}
