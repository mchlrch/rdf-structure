package ch.miranet.rdfstructure;

import org.eclipse.rdf4j.model.Namespace;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.model.vocabulary.SHACL;

public class RdfStructureBuilder {

	protected final ModelBuilder modelBuilder;
	protected final ValueFactory valueFactory;

	public RdfStructureBuilder() {
		this(RDF.NS, RDFS.NS, SHACL.NS);
	}

	public RdfStructureBuilder(Namespace... namespaces) {
		this.modelBuilder = new ModelBuilder();
		this.valueFactory = SimpleValueFactory.getInstance();

		for (Namespace ns : namespaces) {
			this.modelBuilder.setNamespace(ns);
		}
	}

	public ModelBuilder getModelBuilder() {
		return modelBuilder;
	}

	public ValueFactory getValueFactory() {
		return valueFactory;
	}

	public RdfsClass rdfsClass(String prefixedNameOrIri) {
		modelBuilder.subject(prefixedNameOrIri)
				.add(RDF.TYPE, RDFS.CLASS);

		return new RdfsClass(prefixedNameOrIri);
	}

	public RdfProperty rdfProperty(String prefixedNameOrIri) {
		modelBuilder.subject(prefixedNameOrIri)
				.add(RDF.TYPE, RDF.PROPERTY);

		return new RdfProperty(prefixedNameOrIri);
	}

	public NodeShape nodeShape(String prefixedNameOrIri) {
		modelBuilder.subject(prefixedNameOrIri)
				.add(RDF.TYPE, SHACL.NODE_SHAPE);

		return new NodeShape(this, prefixedNameOrIri);
	}

}
