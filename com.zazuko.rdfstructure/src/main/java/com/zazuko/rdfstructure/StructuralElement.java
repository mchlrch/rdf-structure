package com.zazuko.rdfstructure;

import java.util.function.BiConsumer;

import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.util.ModelBuilder;

public abstract class StructuralElement<T extends Resource> {

	protected final RdfStructureBuilder b;
	protected final T resource;

	public StructuralElement(RdfStructureBuilder structBuilder, T resource) {
		this.b = structBuilder;
		this.resource = resource;
	}

	public T getResource() {
		return resource;
	}

	protected StructuralElement<T> any(BiConsumer<ModelBuilder, T> consumer) {
		consumer.accept(this.b.modelBuilder, this.resource);
		return this;
	}
}
