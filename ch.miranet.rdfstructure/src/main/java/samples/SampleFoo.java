package samples;

import org.eclipse.rdf4j.model.vocabulary.SKOS;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;

import ch.miranet.rdfstructure.RdfProperty;
import ch.miranet.rdfstructure.RdfStructureBuilder;
import ch.miranet.rdfstructure.RdfsClass;

public class SampleFoo {

	public static void main(String[] args) {
		final RdfStructureBuilder builder = new RdfStructureBuilder();

		builder.getModelBuilder()
				.setNamespace(EX.NS)
				.setNamespace("schema", "http://schema.org/");

		final RdfsClass foo = builder.rdfsClass(EX.Foo)
//				.subClassOf("schema:Thing")
				.label("Foo");

		final RdfsClass specialFoo = builder.rdfsClass(EX.SpecialFoo)
				.subClassOf(foo)
				.label("SpecialFoo");
//		.comment("The special Foo");

		builder.rdfsClass("ex:NewFoo");
//				.subClassOf(EX.SpecialFoo);

		final RdfProperty property1 = builder.rdfProperty(EX.property1)
				.label("property1");

		final RdfProperty fizfaz = builder.rdfProperty(EX.specialProperty1)
				.subPropertyOf(property1)
//				.subPropertyOf(EX.property1)
//				.subPropertyOf("ex:property1")
				
				.label("specialProperty1");

//		NodeShape fooShape = builder.nodeShape("ex:FooShape")
//				.targetClass(foo)
//				.property("p1", prop -> {
//					prop.count(1)
//							.shClass("myType1")
//							.count(42);
//				})
//		;
		
//		.property(property1, prop -> {
		
//		.property("ex:property1", prop -> {
		
		Rio.write(builder.getModelBuilder().build(), System.out, RDFFormat.TURTLE);

//		String ttlOut = builder.buildDataset().asTurtle(); 
	}

}
