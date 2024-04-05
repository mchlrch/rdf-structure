package ch.miranet.rdfstructure;

import java.util.function.BiConsumer;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.vocabulary.RDF;

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

	/** rdf:type */
	public StructuralElement<T> a(RdfsClass clazz) {
		return a(clazz.resource);
	}

	/** rdf:type */
	public StructuralElement<T> a(String classPrefixedNameOrIri) {
		return a(this.b.mapToIRI(classPrefixedNameOrIri));
	}

	/** rdf:type */
	public StructuralElement<T> a(IRI clazz) {
		this.b.modelBuilder.subject(this.resource)
				.add(RDF.TYPE, clazz);
		return this;
	}

}
