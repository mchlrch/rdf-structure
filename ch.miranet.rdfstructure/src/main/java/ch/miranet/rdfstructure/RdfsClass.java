package ch.miranet.rdfstructure;

import java.util.function.BiConsumer;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.vocabulary.OWL;
import org.eclipse.rdf4j.model.vocabulary.RDFS;

public class RdfsClass extends StructuralElement<IRI> {

	public RdfsClass(RdfStructureBuilder structBuilder, IRI iri) {
		super(structBuilder, iri);
	}

	/** rdf:type rdfs:Class */
	public RdfsClass aRdfsClass() {
		super.a(RDFS.CLASS);

		return this;
	}

	/** rdf:type owl:Class */
	public RdfsClass aOwlClass() {
		super.a(OWL.CLASS);

		return this;
	}

	/** rdfs:subClassOf */
	public RdfsClass subClassOf(RdfsClass superclass) {
		return subClassOf(superclass.resource);
	}

	/** rdfs:subClassOf */
	public RdfsClass subClassOf(String superclassPrefixedNameOrIri) {
		return subClassOf(this.b.mapToIRI(superclassPrefixedNameOrIri));
	}

	/** rdfs:subClassOf */
	public RdfsClass subClassOf(IRI superclass) {
		this.b.modelBuilder.subject(this.resource)
				.add(RDFS.SUBCLASSOF, superclass);

		return this;
	}

	/** rdfs:label */
	public RdfsClass label(String label) {
		this.b.modelBuilder.subject(this.resource)
				.add(RDFS.LABEL, label);

		return this;
	}

	/** rdfs:comment */
	public RdfsClass comment(String comment) {
		this.b.modelBuilder.subject(this.resource)
				.add(RDFS.COMMENT, comment);

		return this;
	}

	public RdfsClass any(BiConsumer<ModelBuilder, IRI> consumer) {
		super.any(consumer);
		return this;
	}
}
