package com.zazuko.rdfstructure;

import java.util.function.BiConsumer;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.vocabulary.RDFS;

public class RdfsClass extends StructuralElement<IRI> {

	public RdfsClass(RdfStructureBuilder structBuilder, IRI iri) {
		super(structBuilder, iri);
	}

	public RdfsClass subClassOf(RdfsClass superclass) {
		return subClassOf(superclass.resource);
	}

	public RdfsClass subClassOf(String superclassPrefixedNameOrIri) {
		return subClassOf(this.b.mapToIRI(superclassPrefixedNameOrIri));
	}

	public RdfsClass subClassOf(IRI superclass) {
		this.b.modelBuilder.subject(this.resource)
				.add(RDFS.SUBCLASSOF, superclass);

		return this;
	}

	public RdfsClass label(String label) {
		this.b.modelBuilder.subject(this.resource)
				.add(RDFS.LABEL, label);

		return this;
	}

	public RdfsClass any(BiConsumer<ModelBuilder, IRI> consumer) {
		super.any(consumer);
		return this;
	}
}
