package com.zazuko.rdfstructure;

import java.util.function.BiConsumer;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.model.vocabulary.SHACL;

public class PropertyShape extends StructuralElement<Resource> {

	public PropertyShape(RdfStructureBuilder structBuilder, Resource propertyShapeResource) {
		super(structBuilder, propertyShapeResource);
	}
	
	/** rdfs:label */
	public PropertyShape label(String label) {
		this.b.modelBuilder.subject(this.resource)
				.add(RDFS.LABEL, label);

		return this;
	}
	
	/** rdfs:comment */
	public PropertyShape comment(String comment) {
		this.b.modelBuilder.subject(this.resource)
				.add(RDFS.COMMENT, comment);

		return this;
	}

	/** sh:minCount */
	public PropertyShape minCount(int count) {
		this.b.modelBuilder.subject(this.resource)
				.add(SHACL.MIN_COUNT, count);

		return this;
	}

	/** sh:maxCount */
	public PropertyShape maxCount(int count) {
		this.b.modelBuilder.subject(this.resource)
				.add(SHACL.MAX_COUNT, count);

		return this;
	}

	/** sh:minCount <br/> sh:maxCount */
	public PropertyShape count(int count) {
		return minCount(count).maxCount(count);
	}

	/** sh:class */
	public PropertyShape clazz(RdfsClass cls) {
		return clazz(cls.resource);
	}

	/** sh:class */
	public PropertyShape clazz(String prefixedNameOrIri) {
		return clazz(this.b.mapToIRI(prefixedNameOrIri));
	}

	/** sh:class */
	public PropertyShape clazz(IRI iri) {
		this.b.modelBuilder.subject(this.resource)
				.add(SHACL.CLASS, iri);

		return this;
	}

	public PropertyShape any(BiConsumer<ModelBuilder, Resource> consumer) {
		super.any(consumer);
		return this;
	}
}
