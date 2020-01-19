package ch.miranet.rdfstructure;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Namespace;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.impl.LinkedHashModel;
import org.eclipse.rdf4j.model.impl.SimpleValueFactory;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.util.ModelException;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.model.vocabulary.SHACL;

public class RdfStructureBuilder {

	protected final Model model;
	protected final ModelBuilder modelBuilder;
	protected final ValueFactory valueFactory;

	public RdfStructureBuilder() {
		this(RDF.NS, RDFS.NS, SHACL.NS);
	}

	public RdfStructureBuilder(Namespace... namespaces) {
		this(null, namespaces);
	}

	protected RdfStructureBuilder(Model model, Namespace... namespaces) {
		this.model = (model == null) ? new LinkedHashModel() : model;
		this.modelBuilder = new ModelBuilder(this.model);
		this.valueFactory = SimpleValueFactory.getInstance();

		for (Namespace ns : namespaces) {
			this.model.setNamespace(ns);
		}
	}

	public ModelBuilder getModelBuilder() {
		return modelBuilder;
	}

	public ValueFactory getValueFactory() {
		return valueFactory;
	}

	public RdfsClass rdfsClass(String prefixedNameOrIri) {
		return rdfsClass(mapToIRI(prefixedNameOrIri));
	}

	public RdfsClass rdfsClass(IRI iri) {
		modelBuilder.subject(iri)
				.add(RDF.TYPE, RDFS.CLASS);

		return new RdfsClass(this, iri);
	}

	public RdfProperty rdfProperty(String prefixedNameOrIri) {
		return rdfProperty(mapToIRI(prefixedNameOrIri));
	}

	public RdfProperty rdfProperty(IRI iri) {
		modelBuilder.subject(iri)
				.add(RDF.TYPE, RDF.PROPERTY);

		return new RdfProperty(this, iri);
	}

	public NodeShape nodeShape(String prefixedNameOrIri) {
		return nodeShape(mapToIRI(prefixedNameOrIri));
	}

	public NodeShape nodeShape(IRI iri) {
		modelBuilder.subject(iri)
				.add(RDF.TYPE, SHACL.NODE_SHAPE);

		return new NodeShape(this, iri);
	}

	// ---------------------------

	protected IRI convertPrefixedName(String prefixedName) {
		if (prefixedName.indexOf(':') < 0) {
			return null;
		}

		final String prefix = prefixedName.substring(0, prefixedName.indexOf(':'));

		final ValueFactory vf = SimpleValueFactory.getInstance();

		for (Namespace ns : this.model.getNamespaces()) {
			if (prefix.equals(ns.getPrefix())) {
				return vf.createIRI(ns.getName(), prefixedName.substring(prefixedName.indexOf(':') + 1));
			}
		}

		return null;
	}

	protected IRI mapToIRI(String prefixedNameOrIRI) {
		if (prefixedNameOrIRI.indexOf(':') < 0) {
			throw new ModelException("not a valid prefixed name or IRI: " + prefixedNameOrIRI);
		}

		IRI iri = convertPrefixedName(prefixedNameOrIRI);
		if (iri == null) {
			iri = SimpleValueFactory.getInstance().createIRI(prefixedNameOrIRI);
		}
		return iri;
	}

}
