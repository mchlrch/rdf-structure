package ch.miranet.rdfstructure;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.eclipse.rdf4j.model.BNode;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.util.RDFCollections;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.model.vocabulary.SHACL;

public class NodeShape extends StructuralElement<Resource> {

	public NodeShape(RdfStructureBuilder structBuilder, Resource nodeShapeResource) {
		super(structBuilder, nodeShapeResource);
	}

	/** rdfs:label */
	public NodeShape label(String label) {
		this.b.modelBuilder.subject(this.resource)
				.add(RDFS.LABEL, label);

		return this;
	}

	/** rdfs:comment */
	public NodeShape comment(String comment) {
		this.b.modelBuilder.subject(this.resource)
				.add(RDFS.COMMENT, comment);

		return this;
	}

	/** sh:targetClass */
	public NodeShape targetClass(RdfsClass targetClass) {
		return targetClass(targetClass.resource);
	}

	/** sh:targetClass */
	public NodeShape targetClass(String targetClassPrefixedNameOrIri) {
		return targetClass(this.b.mapToIRI(targetClassPrefixedNameOrIri));
	}

	/** sh:targetClass */
	public NodeShape targetClass(IRI targetClassIri) {
		this.b.modelBuilder.subject(this.resource)
				.add(SHACL.TARGET_CLASS, targetClassIri);

		return this;
	}

	/** sh:property */
	public NodeShape property(RdfProperty property, Consumer<PropertyShape> propertyShapeConsumer) {
		return property(property.resource, propertyShapeConsumer);
	}

	/** sh:property */
	public NodeShape property(String propertyPrefixedNameOrIri, Consumer<PropertyShape> propertyShapeConsumer) {
		return property(this.b.mapToIRI(propertyPrefixedNameOrIri), propertyShapeConsumer);
	}

	/** sh:property */
	public NodeShape property(IRI propertyIri, Consumer<PropertyShape> propertyShapeConsumer) {
		final PropertyShape propertyShape = property0(propertyIri);
		propertyShapeConsumer.accept(propertyShape);
		return this;
	}

	/** sh:property */
	public NodeShape property(RdfProperty property) {
		property0(property.resource);
		return this;
	}

	/** sh:property */
	public NodeShape property(String propertyPrefixedNameOrIri) {
		property0(this.b.mapToIRI(propertyPrefixedNameOrIri));
		return this;
	}

	/** sh:property */
	public NodeShape property(IRI propertyIri) {
		property0(propertyIri);
		return this;
	}

	/** sh:property */
	protected NodeShape property(String propertyShapePrefixedNameOrIri, RdfProperty property, Consumer<PropertyShape> propertyShapeConsumer) {
		return property(this.b.mapToIRI(propertyShapePrefixedNameOrIri), property.resource, propertyShapeConsumer);
	}
	
	/** sh:property */
	protected NodeShape property(String propertyShapePrefixedNameOrIri, String propertyPrefixedNameOrIri, Consumer<PropertyShape> propertyShapeConsumer) {
		return property(this.b.mapToIRI(propertyShapePrefixedNameOrIri), this.b.mapToIRI(propertyPrefixedNameOrIri), propertyShapeConsumer);
	}
	
	/** sh:property */
	protected NodeShape property(String propertyShapePrefixedNameOrIri, IRI propertyIri, Consumer<PropertyShape> propertyShapeConsumer) {
		property0(this.b.mapToIRI(propertyShapePrefixedNameOrIri), propertyIri);
		return this;
	}
	
	/** sh:property */
	protected NodeShape property(String propertyShapePrefixedNameOrIri, RdfProperty property) {
		return property(this.b.mapToIRI(propertyShapePrefixedNameOrIri), property.resource);
	}
	
	/** sh:property */
	protected NodeShape property(String propertyShapePrefixedNameOrIri, String propertyPrefixedNameOrIri) {
		return property(this.b.mapToIRI(propertyShapePrefixedNameOrIri), this.b.mapToIRI(propertyPrefixedNameOrIri));
	}
	
	/** sh:property */
	protected NodeShape property(String propertyShapePrefixedNameOrIri, IRI propertyIri) {
		property0(this.b.mapToIRI(propertyShapePrefixedNameOrIri), propertyIri);
		return this;
	}	
	
	/** sh:property */
	protected NodeShape property(IRI propertyShapeIri, RdfProperty property, Consumer<PropertyShape> propertyShapeConsumer) {
		return property(propertyShapeIri, property.resource, propertyShapeConsumer);
	}
	
	/** sh:property */
	protected NodeShape property(IRI propertyShapeIri, String propertyPrefixedNameOrIri, Consumer<PropertyShape> propertyShapeConsumer) {
		return property(propertyShapeIri, this.b.mapToIRI(propertyPrefixedNameOrIri), propertyShapeConsumer);
	}
	
	/** sh:property */
	protected NodeShape property(IRI propertyShapeIri, IRI propertyIri, Consumer<PropertyShape> propertyShapeConsumer) {
		property0(propertyShapeIri, propertyIri);
		return this;
	}
	
	/** sh:property */
	protected NodeShape property(IRI propertyShapeIri, RdfProperty property) {
		return property(propertyShapeIri, property.resource);
	}
	
	/** sh:property */
	protected NodeShape property(IRI propertyShapeIri, String propertyPrefixedNameOrIri) {
		return property(propertyShapeIri, this.b.mapToIRI(propertyPrefixedNameOrIri));
	}

	/** sh:property */
	protected NodeShape property(IRI propertyShapeIri, IRI propertyIri) {
		property0(propertyShapeIri, propertyIri);
		return this;
	}

	protected PropertyShape property0(IRI propertyIri) {
		final BNode propertyShapeBNode = this.b.valueFactory.createBNode();

		return property0(propertyShapeBNode, propertyIri);
	}

	protected PropertyShape property0(Resource propertyShapeResource, IRI propertyIri) {
		this.b.modelBuilder.subject(this.resource)
				.add(SHACL.PROPERTY, propertyShapeResource);

		this.b.modelBuilder.subject(propertyShapeResource)
				.add(SHACL.PATH, propertyIri);

		return new PropertyShape(this.b, propertyShapeResource);
	}

	/** sh:deactivated */
	public NodeShape deactivated(boolean yesOrNo) {
		this.b.modelBuilder.subject(this.resource)
				.add(SHACL.DEACTIVATED, yesOrNo);

		return this;
	}

	/** sh:closed */
	public NodeShape closed(boolean yesOrNo) {
		this.b.modelBuilder.subject(this.resource)
				.add(SHACL.CLOSED, yesOrNo);

		return this;
	}

	/** sh:ignoredProperties */
	public NodeShape ignoredProperties(RdfProperty property, RdfProperty... moreProperties) {
		final RdfProperty[] in = new RdfProperty[moreProperties.length + 1];
		in[0] = property;
		System.arraycopy(moreProperties, 0, in, 1, moreProperties.length);

		final List<IRI> ignoredProperties = Arrays.asList(in).stream()
				.map(RdfProperty::getResource)
				.collect(Collectors.toList());

		return ignoredProperties(ignoredProperties);
	}

	/** sh:ignoredProperties */
	public NodeShape ignoredProperties(String propertyPrefixedNameOrIri, String... morePropertyPrefixedNamesOrIris) {
		final String[] in = new String[morePropertyPrefixedNamesOrIris.length + 1];
		in[0] = propertyPrefixedNameOrIri;
		System.arraycopy(morePropertyPrefixedNamesOrIris, 0, in, 1, morePropertyPrefixedNamesOrIris.length);

		final List<IRI> ignoredProperties = Arrays.asList(in).stream()
				.map(this.b::mapToIRI)
				.collect(Collectors.toList());

		return ignoredProperties(ignoredProperties);
	}

	/** sh:ignoredProperties */
	public NodeShape ignoredProperties(IRI propertyIri, IRI... morePropertyIris) {
		final IRI[] in = new IRI[morePropertyIris.length + 1];
		in[0] = propertyIri;
		System.arraycopy(morePropertyIris, 0, in, 1, morePropertyIris.length);

		return ignoredProperties(Arrays.asList(in));
	}

	/** sh:ignoredProperties */
	public NodeShape ignoredProperties(Iterable<IRI> propertyIris) {
		final BNode listHead = this.b.valueFactory.createBNode();
		RDFCollections.asRDF(propertyIris, listHead, this.b.model);

		this.b.modelBuilder.subject(this.resource)
				.add(SHACL.IGNORED_PROPERTIES, listHead);

		return this;
	}

	public NodeShape any(BiConsumer<ModelBuilder, Resource> consumer) {
		super.any(consumer);
		return this;
	}
}
