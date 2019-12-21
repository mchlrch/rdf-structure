# rdf-structure

A Java DSL for SHACL shapes.

```
final RdfStructureBuilder builder = new RdfStructureBuilder();

builder.nodeShape("ex:SectorShape")
	.targetClass("ex:Sector")
	.property("skos:notation").count(1);
```
