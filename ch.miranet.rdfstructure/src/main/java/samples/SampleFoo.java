package samples;

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
				.subClassOf("schema:Thing")
				.label("Foo");

		builder.rdfsClass(EX.SpecialFoo)
				.subClassOf(foo)
				.label("SpecialFoo");
//		.comment("The special Foo");

		builder.rdfsClass("ex:NewFoo")
				.subClassOf(EX.SpecialFoo);

		final RdfProperty property1 = builder.rdfProperty(EX.property1)
				.label("property1");

		builder.rdfProperty(EX.specialProperty1)
				.subPropertyOf(property1)
				.label("specialProperty1");

		builder.nodeShape("ex:FooShape")
				.targetClass(foo)
				.property(EX.property1, prop -> {
					prop.count(1)
							.shClass("ex:NewFoo");
				});

		Rio.write(builder.getModelBuilder().build(), System.out, RDFFormat.TURTLE);
	}

}
