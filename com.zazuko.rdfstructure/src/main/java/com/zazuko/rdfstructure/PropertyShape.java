package com.zazuko.rdfstructure;

import java.util.function.BiConsumer;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.vocabulary.SHACL;

public class PropertyShape extends StructuralElement<Resource> {

	public PropertyShape(RdfStructureBuilder structBuilder, Resource propertyShapeResource) {
		super(structBuilder, propertyShapeResource);
	}

	public PropertyShape minCount(int count) {
		this.b.modelBuilder.subject(this.resource)
				.add(SHACL.MIN_COUNT, count);

		return this;
	}

	public PropertyShape maxCount(int count) {
		this.b.modelBuilder.subject(this.resource)
				.add(SHACL.MAX_COUNT, count);

		return this;
	}

	public PropertyShape count(int count) {
		return minCount(count).maxCount(count);
	}

	public PropertyShape clazz(RdfsClass cls) {
		return clazz(cls.resource);
	}

	public PropertyShape clazz(String prefixedNameOrIri) {
		return clazz(this.b.mapToIRI(prefixedNameOrIri));
	}

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
