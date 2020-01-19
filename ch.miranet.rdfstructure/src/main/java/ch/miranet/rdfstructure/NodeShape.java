package ch.miranet.rdfstructure;

import java.util.function.Consumer;

import org.eclipse.rdf4j.model.BNode;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.vocabulary.SHACL;

public class NodeShape extends StructuralElement<IRI> {

	public NodeShape(RdfStructureBuilder structBuilder, IRI iri) {
		super(structBuilder, iri);
	}

	public NodeShape targetClass(RdfsClass targetClass) {
		return targetClass(targetClass.resource);
	}

	public NodeShape targetClass(String targetClassPrefixedNameOrIri) {
		return targetClass(this.b.mapToIRI(targetClassPrefixedNameOrIri));
	}

	public NodeShape targetClass(IRI targetClassIri) {
		this.b.modelBuilder.subject(this.resource)
				.add(SHACL.TARGET_CLASS, targetClassIri);

		return this;
	}

//	public NodeShape property(IRI propertyIri, Consumer<PropertyShape> propertyShapeConsumer) {
	
	public NodeShape property(String propertyPrefixedNameOrIri, Consumer<PropertyShape> propertyShapeConsumer) {
		final PropertyShape propertyShape = property0(propertyPrefixedNameOrIri);

		propertyShapeConsumer.accept(propertyShape);
		return this;
	}

//	public NodeShape property(String propertyIri) {
	
	public NodeShape property(String propertyPrefixedNameOrIri) {
		property0(propertyPrefixedNameOrIri);
		return this;
	}

	protected PropertyShape property0(String propertyPrefixedNameOrIri) {
		final BNode propertyShapeBNode = this.b.valueFactory.createBNode();

		this.b.modelBuilder.subject(this.resource)
				.add(SHACL.PROPERTY, propertyShapeBNode);

		this.b.modelBuilder.subject(propertyShapeBNode)
				.add(SHACL.PATH, propertyPrefixedNameOrIri);

		return new PropertyShape(this.b, propertyShapeBNode);
	}

	public NodeShape property(RdfProperty property) {
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
