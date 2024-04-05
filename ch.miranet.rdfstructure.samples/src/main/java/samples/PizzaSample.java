package samples;

import org.eclipse.rdf4j.model.vocabulary.SKOS;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;

import ch.miranet.rdfstructure.NodeShape;
import ch.miranet.rdfstructure.RdfProperty;
import ch.miranet.rdfstructure.RdfStructureBuilder;
import ch.miranet.rdfstructure.RdfsClass;

public class PizzaSample {

	public static void main(String[] args) {
		RdfStructureBuilder builder = new RdfStructureBuilder();

		builder.getModelBuilder()
				.setNamespace("ex", "http://schema.example.org/");

		RdfsClass pizza = builder.rdfsClass("ex:Pizza").aRdfsClass()
				.subClassOf("ex:Flatbread")
				.label("Pizza")
				.comment("Pizza is a savory dish of Italian origin");

		RdfProperty dough = builder.rdfProperty("ex:dough").aRdfProperty()
				.label("Dough");

		RdfProperty sauce = builder.rdfProperty("ex:sauce").aRdfProperty()
				.label("Sauce");

		RdfProperty cheese = builder.rdfProperty("ex:cheese").aRdfProperty()
				.label("Cheese");

		RdfProperty topping = builder.rdfProperty("ex:topping").aRdfProperty()
				.label("Topping");

		NodeShape pizzaShape = builder.nodeShape("ex:PizzaShape").aNodeShape()
				.targetClass(pizza)
				.property(dough, x -> x
							.count(1)
							.clazz("ex:Dough"))
				.property(sauce, x -> x
							.comment("usually tomato sauce"))
				.property(cheese)
				.property(topping);

		
		// one of several ways of breaking out from RdfStructureBuilder and using the
		// rdf4j ModelBuilder directly
		pizzaShape.any( (modelBuilder, element) -> modelBuilder
				.subject(element)
				.add(SKOS.HIDDEN_LABEL, "Hidden label on my pizza shape"));

		System.setProperty("org.eclipse.rdf4j.rio.inline_blank_nodes", Boolean.TRUE.toString());
		Rio.write(builder.getModelBuilder().build(), System.out, RDFFormat.TURTLE);
	}

}
