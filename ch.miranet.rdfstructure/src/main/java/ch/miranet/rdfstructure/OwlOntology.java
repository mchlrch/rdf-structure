package ch.miranet.rdfstructure;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.vocabulary.OWL;
import org.eclipse.rdf4j.model.vocabulary.RDFS;

public class OwlOntology extends StructuralElement<IRI> {

	public OwlOntology(RdfStructureBuilder structBuilder, IRI iri) {
		super(structBuilder, iri);
	}

	/** rdf:type owl:Ontology */
	public OwlOntology aOwlOntology() {
		super.a(OWL.ONTOLOGY);

		return this;
	}

	/** rdfs:label */
	public OwlOntology label(String label) {
		this.b.modelBuilder.subject(this.resource)
				.add(RDFS.LABEL, label);

		return this;
	}

	/** owl:Class */
	public OwlOntology owlClass(String prefixedNameOrIri, Consumer<RdfsClass> classConsumer) {
		return owlClass(this.b.mapToIRI(prefixedNameOrIri), classConsumer);
	}

	/** owl:Class */
	public OwlOntology owlClass(IRI classIri, Consumer<RdfsClass> classConsumer) {
		final RdfsClass cls = owlClass0(classIri);
		classConsumer.accept(cls);
		return this;
	}

	/** owl:Class */
	public OwlOntology owlClass(String prefixedNameOrIri) {
		return owlClass(this.b.mapToIRI(prefixedNameOrIri));
	}

	/** owl:Class */
	public OwlOntology owlClass(IRI iri) {
		owlClass0(iri);
		return this;
	}

	protected RdfsClass owlClass0(IRI classIri) {
		this.b.modelBuilder.subject(classIri)
				.add(RDFS.ISDEFINEDBY, this.resource);

		return new RdfsClass(this.b, classIri);
	}

	/** owl:DatatypeProperty */
	public OwlOntology datatypeProperty(String propertyPrefixedNameOrIri, Consumer<RdfProperty> propertyConsumer) {
		return datatypeProperty(this.b.mapToIRI(propertyPrefixedNameOrIri), propertyConsumer);
	}

	/** owl:DatatypeProperty */
	public OwlOntology datatypeProperty(IRI propertyIri, Consumer<RdfProperty> propertyConsumer) {
		final RdfProperty property = datatypeProperty0(propertyIri);
		propertyConsumer.accept(property);
		return this;
	}

	/** owl:DatatypeProperty */
	public OwlOntology datatypeProperty(String propertyPrefixedNameOrIri) {
		datatypeProperty0(this.b.mapToIRI(propertyPrefixedNameOrIri));
		return this;
	}

	/** owl:DatatypeProperty */
	public OwlOntology datatypeProperty(IRI propertyIri) {
		datatypeProperty0(propertyIri);
		return this;
	}

	protected RdfProperty datatypeProperty0(IRI propertyIri) {
		this.b.modelBuilder.subject(propertyIri)
				.add(RDFS.ISDEFINEDBY, this.resource);

		return new RdfProperty(this.b, propertyIri);
	}

	/** owl:ObjectProperty */
	public OwlOntology objectProperty(String propertyPrefixedNameOrIri, Consumer<RdfProperty> propertyConsumer) {
		return objectProperty(this.b.mapToIRI(propertyPrefixedNameOrIri), propertyConsumer);
	}

	/** owl:ObjectProperty */
	public OwlOntology objectProperty(IRI propertyIri, Consumer<RdfProperty> propertyConsumer) {
		final RdfProperty property = objectProperty0(propertyIri);
		propertyConsumer.accept(property);
		return this;
	}

	/** owl:ObjectProperty */
	public OwlOntology objectProperty(String propertyPrefixedNameOrIri) {
		objectProperty0(this.b.mapToIRI(propertyPrefixedNameOrIri));
		return this;
	}

	/** owl:ObjectProperty */
	public OwlOntology objectProperty(IRI propertyIri) {
		objectProperty0(propertyIri);
		return this;
	}

	protected RdfProperty objectProperty0(IRI propertyIri) {
		this.b.modelBuilder.subject(propertyIri)
				.add(RDFS.ISDEFINEDBY, this.resource);

		return new RdfProperty(this.b, propertyIri);
	}

	public OwlOntology any(BiConsumer<ModelBuilder, IRI> consumer) {
		super.any(consumer);
		return this;
	}

}
