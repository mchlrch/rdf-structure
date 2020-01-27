package samples;

import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;

import com.zazuko.rdfstructure.RdfProperty;
import com.zazuko.rdfstructure.RdfStructureBuilder;
import com.zazuko.rdfstructure.RdfsClass;

public class SampleFoo {

	public static void main(String[] args) {
		final RdfStructureBuilder builder = new RdfStructureBuilder();

		builder.getModelBuilder()
				.setNamespace(EX.NS)
				.setNamespace(RDFS.NS)
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
							.clazz("ex:NewFoo")
							.any((modelBuilder, element) -> {
								modelBuilder.subject(element).add(RDFS.COMMENT,
										"Some comment about FooShape#property1");
							});
				})
				.any((modelBuilder, element) -> {
					modelBuilder.subject(element).add(RDFS.COMMENT, "Some comment about FooShape");
				});

		System.setProperty("org.eclipse.rdf4j.rio.inline_blank_nodes", Boolean.TRUE.toString());
		Rio.write(builder.getModelBuilder().build(), System.out, RDFFormat.TURTLE);
	}

}
