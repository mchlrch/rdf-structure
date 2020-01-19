package ch.miranet.rdfstructure;

import org.eclipse.rdf4j.model.Resource;
import org.eclipse.rdf4j.model.vocabulary.SHACL;

public class PropertyShape extends StructuralElement<Resource> {

	public PropertyShape(RdfStructureBuilder structBuilder, Resource propertyShapeResource) {
		super(structBuilder, propertyShapeResource);
	}

	public PropertyShape minCount(int count) {
		this.b.modelBuilder.subject(this.resource)
				.add(SHACL.MIN_COUNT, count);

		return this;
	}

	public PropertyShape maxCount(int count) {
		this.b.modelBuilder.subject(this.resource)
				.add(SHACL.MAX_COUNT, count);

		return this;
	}

	public PropertyShape count(int count) {
		return minCount(count).maxCount(count);
	}

//	public PropertyShape shClass(IRI iri) {
	
//	public PropertyShape shClass(RdfsClass cls) {
	
	
	public PropertyShape shClass(String prefixedNameOrIri) {
		this.b.modelBuilder.subject(this.resource)
				.add(SHACL.CLASS, prefixedNameOrIri);

		return this;
	}

}
