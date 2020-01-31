package samples;

import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.model.vocabulary.SKOS;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;

import com.zazuko.rdfstructure.RdfProperty;
import com.zazuko.rdfstructure.RdfStructureBuilder;
import com.zazuko.rdfstructure.RdfsClass;

public class SampleFooSimple {

	public static void main(String[] args) {
		final RdfStructureBuilder builder = new RdfStructureBuilder();

		builder.getModelBuilder()
				.setNamespace(EX.NS)
				.setNamespace(RDFS.NS)
				.setNamespace(SKOS.NS)
				.setNamespace("schema", "http://schema.org/");

		final RdfsClass foo = builder.rdfsClass("ex:Foo")
				.subClassOf("schema:Thing")
				.label("Foo");

		final RdfProperty property1 = builder.rdfProperty("ex:property1")
				.subPropertyOf("schema:interactionType")
				.label("property1");

		builder.nodeShape("ex:FooShape")
				.targetClass(foo)
				.property(property1, prop -> {
					prop.count(1)
							.clazz("skos:Concept");
				});

		System.setProperty("org.eclipse.rdf4j.rio.inline_blank_nodes", Boolean.TRUE.toString());
		Rio.write(builder.getModelBuilder().build(), System.out, RDFFormat.TURTLE);
	}

}
