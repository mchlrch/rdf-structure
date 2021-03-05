package samples;

import org.eclipse.rdf4j.model.vocabulary.SKOS;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;

import com.zazuko.rdfstructure.NodeShape;
import com.zazuko.rdfstructure.RdfProperty;
import com.zazuko.rdfstructure.RdfStructureBuilder;
import com.zazuko.rdfstructure.RdfsClass;

public class PizzaSample {

	public static void main(String[] args) {
		final RdfStructureBuilder builder = new RdfStructureBuilder();

		builder.getModelBuilder()
				.setNamespace("ex", "http://schema.example.org/");

		final RdfsClass pizza = builder.rdfsClass("ex:Pizza")
				.subClassOf("ex:Flatbread")
				.label("Pizza")
				.comment("Pizza is a savory dish of Italian origin");

		final RdfProperty dough = builder.rdfProperty("ex:dough")
				.label("Dough");

		final RdfProperty sauce = builder.rdfProperty("ex:sauce")
				.label("Sauce");

		final RdfProperty cheese = builder.rdfProperty("ex:cheese")
				.label("Cheese");

		final RdfProperty topping = builder.rdfProperty("ex:topping")
				.label("Topping");

		final NodeShape pizzaShape = builder.nodeShape("ex:PizzaShape")
				.targetClass(pizza)
				.property(dough, propertyShape -> {
					propertyShape
							.count(1)
							.clazz("ex:Dough");

				})
				.property(sauce, propertyShape -> {
					propertyShape.comment("usually tomato sauce");
				})
				.property(cheese)
				.property(topping);

		
		// one of several ways of breaking out from RdfStructureBuilder and using the
		// rdf4j ModelBuilder directly
		pizzaShape.any((modelBuilder, element) -> {
			modelBuilder.subject(element)
					.add(SKOS.HIDDEN_LABEL, "Hidden label on my pizza shape");
		});

		System.setProperty("org.eclipse.rdf4j.rio.inline_blank_nodes", Boolean.TRUE.toString());
		Rio.write(builder.getModelBuilder().build(), System.out, RDFFormat.TURTLE);
	}

}
