package com.zazuko.rdfstructure;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.eclipse.rdf4j.model.BNode;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.model.vocabulary.SHACL;

public class NodeShape extends StructuralElement<IRI> {

	public NodeShape(RdfStructureBuilder structBuilder, IRI iri) {
		super(structBuilder, iri);
	}
	
	/** rdfs:label */
	public NodeShape label(String label) {
		this.b.modelBuilder.subject(this.resource)
				.add(RDFS.LABEL, label);

		return this;
	}
	
	/** rdfs:comment */
	public NodeShape comment(String comment) {
		this.b.modelBuilder.subject(this.resource)
				.add(RDFS.COMMENT, comment);

		return this;
	}

	/** sh:targetClass */
	public NodeShape targetClass(RdfsClass targetClass) {
		return targetClass(targetClass.resource);
	}

	/** sh:targetClass */
	public NodeShape targetClass(String targetClassPrefixedNameOrIri) {
		return targetClass(this.b.mapToIRI(targetClassPrefixedNameOrIri));
	}

	/** sh:targetClass */
	public NodeShape targetClass(IRI targetClassIri) {
		this.b.modelBuilder.subject(this.resource)
				.add(SHACL.TARGET_CLASS, targetClassIri);

		return this;
	}

	/** sh:property */
	public NodeShape property(RdfProperty property, Consumer<PropertyShape> propertyShapeConsumer) {
		return property(property.resource, propertyShapeConsumer);
	}

	/** sh:property */
	public NodeShape property(String propertyPrefixedNameOrIri, Consumer<PropertyShape> propertyShapeConsumer) {
		return property(this.b.mapToIRI(propertyPrefixedNameOrIri), propertyShapeConsumer);
	}

	/** sh:property */
	public NodeShape property(IRI propertyIri, Consumer<PropertyShape> propertyShapeConsumer) {
		final PropertyShape propertyShape = property0(propertyIri);
		propertyShapeConsumer.accept(propertyShape);
		return this;
	}

	/** sh:property */
	public NodeShape property(RdfProperty property) {
		property0(property.resource);
		return this;
	}

	/** sh:property */
	public NodeShape property(String propertyPrefixedNameOrIri) {
		property0(this.b.mapToIRI(propertyPrefixedNameOrIri));
		return this;
	}

	/** sh:property */
	public NodeShape property(IRI propertyIri) {
		property0(propertyIri);
		return this;
	}

	protected PropertyShape property0(IRI propertyIri) {
		final BNode propertyShapeBNode = this.b.valueFactory.createBNode();

		this.b.modelBuilder.subject(this.resource)
				.add(SHACL.PROPERTY, propertyShapeBNode);

		this.b.modelBuilder.subject(propertyShapeBNode)
				.add(SHACL.PATH, propertyIri);

		return new PropertyShape(this.b, propertyShapeBNode);
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

	public NodeShape any(BiConsumer<ModelBuilder, IRI> consumer) {
		super.any(consumer);
		return this;
	}
}
