package ch.miranet.rdfstructure;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.eclipse.rdf4j.model.BNode;
import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Namespace;
import org.eclipse.rdf4j.model.impl.SimpleNamespace;
import org.eclipse.rdf4j.model.util.ModelBuilder;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.model.vocabulary.SHACL;
import org.eclipse.rdf4j.model.vocabulary.SKOS;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PropertyShapeTest {

	private ModelBuilder refModelBuilder;
	private RdfStructureBuilder builder;

	@BeforeEach
	public void setUp() {
		final Namespace nsEx = new SimpleNamespace("ex", "http://schema.example.org/");

		refModelBuilder = new ModelBuilder();
		refModelBuilder.setNamespace(nsEx);

		builder = new RdfStructureBuilder();
		builder.getModelBuilder().setNamespace(nsEx);
	}

	@Test
	public void simple_propertyShape() {
		builder.nodeShape("ex:MyShape")
				.property("ex:myproperty");

		final BNode propertyShapeBNode = builder.valueFactory.createBNode();

		refModelBuilder.subject("ex:MyShape")
				.add(RDF.TYPE, SHACL.NODE_SHAPE)
				.add(SHACL.PROPERTY, propertyShapeBNode);

		refModelBuilder.subject(propertyShapeBNode)
				.add(SHACL.PATH, "ex:myproperty");

		final Model refModel = refModelBuilder.build();

		assertEquals(refModel, builder.modelBuilder.build());
	}

	@Test
	public void expressive_propertyShape() {
		builder.nodeShape("ex:MyShape")
				.property("ex:myproperty", propertyShape -> {
					propertyShape
							.label("my property shape")
							.comment("Comment on my property shape")
							.any((modelBuilder, element) -> modelBuilder.subject(element)
									.add(SKOS.HIDDEN_LABEL, "Hidden label on my property shape"))
							.count(42)
							.clazz("ex:MyClass");
				});

		final BNode propertyShapeBNode = builder.valueFactory.createBNode();

		refModelBuilder.subject("ex:MyShape")
				.add(RDF.TYPE, SHACL.NODE_SHAPE)
				.add(SHACL.PROPERTY, propertyShapeBNode);

		refModelBuilder.subject(propertyShapeBNode)
				.add(SHACL.PATH, "ex:myproperty")
				.add(RDFS.LABEL, "my property shape")
				.add(RDFS.COMMENT, "Comment on my property shape")
				.add(SKOS.HIDDEN_LABEL, "Hidden label on my property shape")
				.add(SHACL.MIN_COUNT, 42)
				.add(SHACL.MAX_COUNT, 42)
				.add(SHACL.CLASS, "ex:MyClass");

		final Model refModel = refModelBuilder.build();

		assertEquals(refModel, builder.modelBuilder.build());
	}

}
