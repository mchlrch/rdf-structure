# rdf-structure

[![Maven Central](https://img.shields.io/maven-central/v/ch.miranet.rdfstructure/rdf-structure.svg?label=Maven%20Central)](https://search.maven.org/artifact/ch.miranet.rdfstructure/rdf-structure)

`rdf-structure` is a fluent builder API with stencils for using subsets of RDFS, OWL, SHACL and SKOS. It is built ontop of [RDF4J ModelBuilder](https://rdf4j.org/javadoc/latest/org/eclipse/rdf4j/model/util/ModelBuilder.html)


## Example

Definition (input): 

```java
RdfsClass pizza = builder.rdfsClass("ex:Pizza")
  .subClassOf("ex:Flatbread")
  .label("Pizza")
  .comment("Pizza is a savory dish of Italian origin");

RdfProperty dough = builder.rdfProperty("ex:dough")
  .label("Dough");

RdfProperty sauce = builder.rdfProperty("ex:sauce")
  .label("Sauce");

RdfProperty cheese = builder.rdfProperty("ex:cheese")
  .label("Cheese");

RdfProperty topping = builder.rdfProperty("ex:topping")
  .label("Topping");

NodeShape pizzaShape = builder.nodeShape("ex:PizzaShape")
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
```

Turtle (output):

```turtle
@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .
@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .
@prefix sh: <http://www.w3.org/ns/shacl#> .
@prefix ex: <http://schema.example.org/> .
@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .

ex:Pizza a rdfs:Class;
  rdfs:subClassOf ex:Flatbread;
  rdfs:label "Pizza";
  rdfs:comment "Pizza is a savory dish of Italian origin" .

ex:dough a rdf:Property;
  rdfs:label "Dough" .

ex:sauce a rdf:Property;
  rdfs:label "Sauce" .

ex:cheese a rdf:Property;
  rdfs:label "Cheese" .

ex:topping a rdf:Property;
  rdfs:label "Topping" .

ex:PizzaShape a sh:NodeShape;
  sh:targetClass ex:Pizza;
  sh:property [
      sh:path ex:dough;
      sh:minCount "1"^^xsd:int;
      sh:maxCount "1"^^xsd:int;
      sh:class ex:Dough
    ], [
      sh:path ex:sauce;
      rdfs:comment "usually tomato sauce"
    ], [
      sh:path ex:cheese
    ], [
      sh:path ex:topping
    ] .
```

See the complete example:
- [Version 1.x](https://github.com/mchlrch/rdf-structure/blob/663818699b3c20b09fff8a590e21d3165e25ad50/ch.miranet.rdfstructure.samples/src/main/java/samples/PizzaSample.java) 
- [Version 2.x](ch.miranet.rdfstructure.samples/src/main/java/samples/PizzaSample.java)

## Limitations and Breakouts

Only a subset of what can be expressed with RDF Schema and SHACL is made available directly in this builder.

To fill in the gaps and describe whatever you want, there are several ways of breaking out from the `rdf-structure` builder and using the underlying [RDF4J ModelBuilder](https://rdf4j.org/javadoc/latest/org/eclipse/rdf4j/model/util/ModelBuilder.html) directly.

## Usage

Maven dependency for using the latest version:

```xml
<dependency>
	<groupId>ch.miranet.rdfstructure</groupId>
	<artifactId>rdf-structure</artifactId>
	<version>2.0.0</version>
</dependency>
```

If you don't have them declared yet, you most likely also want to include the dependencies for RDF4J in your project:

```xml
<dependencies>
  <dependency>
    <groupId>ch.miranet.rdfstructure</groupId>
    <artifactId>rdf-structure</artifactId>
    <version>2.0.0</version>
  </dependency>
  <dependency>
    <groupId>org.eclipse.rdf4j</groupId>
    <artifactId>rdf4j-model</artifactId>
  </dependency>
  <dependency>
    <groupId>org.eclipse.rdf4j</groupId>
    <artifactId>rdf4j-rio-turtle</artifactId>
  </dependency>
</dependencies>

<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>org.eclipse.rdf4j</groupId>
      <artifactId>rdf4j-bom</artifactId>
      <version>3.11.0</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>
```
