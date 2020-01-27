package samples;

import org.eclipse.rdf4j.model.vocabulary.SKOS;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;

import com.zazuko.rdfstructure.RdfStructureBuilder;

public class SampleSectorShape {

	public static void main(String[] args) {
		final RdfStructureBuilder builder = new RdfStructureBuilder();

		builder.getModelBuilder()
				.setNamespace(SKOS.NS)
				.setNamespace("ex", "http://example.org/");

		builder.nodeShape("ex:SectorShape")
				.targetClass("ex:Sector")
				.property("skos:notation", prop -> {
					prop.count(1);
				});

		System.setProperty("org.eclipse.rdf4j.rio.inline_blank_nodes", Boolean.TRUE.toString());
		Rio.write(builder.getModelBuilder().build(), System.out, RDFFormat.TURTLE);
	}

}
