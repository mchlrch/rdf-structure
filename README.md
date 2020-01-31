# rdf-structure

A Java DSL for ontological and structural definitions. `rdf:Property`, `rdfs:Class`, `sh:NodeShape`, ...

Definition (input): 

```
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
```

Turtle (output):

```
@prefix ex: <http://example.org/> .
@prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .
@prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#> .
@prefix schema: <http://schema.org/> .
@prefix sh: <http://www.w3.org/ns/shacl#> .
@prefix skos: <http://www.w3.org/2004/02/skos/core#> .
@prefix xsd: <http://www.w3.org/2001/XMLSchema#> .

ex:Foo a rdfs:Class;
  rdfs:label "Foo";
  rdfs:subClassOf schema:Thing .

ex:property1 a rdf:Property;
  rdfs:label "property1";
  rdfs:subPropertyOf schema:interactionType .

ex:FooShape a sh:NodeShape;
  sh:property [
      sh:class skos:Concept;
      sh:maxCount "1"^^xsd:int;
      sh:minCount "1"^^xsd:int;
      sh:path ex:property1
    ];
  sh:targetClass ex:Foo .
```

See the [complete example](com.zazuko.rdfstructure.samples/src/main/java/samples/SampleFooSimple.java)

