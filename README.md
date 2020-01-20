# rdf-structure

A Java DSL for SHACL shapes.

```
builder.nodeShape("ex:SectorShape")
	.targetClass("ex:Sector")
	.property("skos:notation", prop -> {
		prop.count(1);
	});
```
