package ch.miranet.rdfstructure;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.vocabulary.RDFS;

public class RdfsClass extends StructuralElement<IRI> {

	public RdfsClass(RdfStructureBuilder structBuilder, IRI iri) {
		super(structBuilder, iri);
	}

	public RdfsClass subClassOf(RdfsClass superclass) {
		this.b.modelBuilder.subject(this.resource)
				.add(RDFS.SUBCLASSOF, superclass.resource);

		return this;
	}

	public RdfsClass label(String label) {
		this.b.modelBuilder.subject(this.resource)
				.add(RDFS.LABEL, label);

		return this;
	}

}
