package ch.miranet.rdfstructure;

import java.util.function.BiConsumer;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.vocabulary.OWL;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;

public class RdfProperty extends StructuralElement<IRI> {

	public RdfProperty(RdfStructureBuilder structBuilder, IRI iri) {
		super(structBuilder, iri);
	}

	/** rdf:type rdf:Property */
	public RdfProperty aRdfProperty() {
		super.a(RDF.PROPERTY);

		return this;
	}
	
	/** rdf:type owl:DatatypeProperty */
	public RdfProperty aOwlDatatypeProperty() {
		super.a(OWL.DATATYPEPROPERTY);

		return this;
	}
	
	/** rdf:type owl:ObjectProperty */
	public RdfProperty aOwlObjectProperty() {
		super.a(OWL.OBJECTPROPERTY);

		return this;
	}

	/** rdfs:subPropertyOf */
	public RdfProperty subPropertyOf(RdfProperty property) {
		return subPropertyOf(property.resource);
	}

	/** rdfs:subPropertyOf */
	public RdfProperty subPropertyOf(String propertyPrefixedNameOrIri) {
		return subPropertyOf(this.b.mapToIRI(propertyPrefixedNameOrIri));
	}

	/** rdfs:subPropertyOf */
	public RdfProperty subPropertyOf(IRI property) {
		this.b.modelBuilder.subject(this.resource)
				.add(RDFS.SUBPROPERTYOF, property);

		return this;
	}

	/** rdfs:label */
	public RdfProperty label(String label) {
		this.b.modelBuilder.subject(this.resource)
				.add(RDFS.LABEL, label);

		return this;
	}

	/** rdfs:comment */
	public RdfProperty comment(String comment) {
		this.b.modelBuilder.subject(this.resource)
				.add(RDFS.COMMENT, comment);

		return this;
	}

	public RdfProperty any(BiConsumer<ModelBuilder, IRI> consumer) {
		super.any(consumer);
		return this;
	}
}
