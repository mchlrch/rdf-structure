# Changelog of rdf-structure

## Version 2.0.0 (2024-05-29)

* Remove implicit rdf:type statements. This change requires rdf:type statements to be made explicitly if needed. For example:
  * b.rdfsClass("ex:Pizza").aRdfsClass()
  * b.rdfsClass("ex:Pizza").aOwlClass()
  * b.rdfsClass("ex:Pizza").a("ex:Whatever")
  * b.nodeShape("ex:MyShape").aNodeShape()


## Version 1.1.0 (2024-05-28)

* SHACL NodeShape: bnode NodeShape (see RdfStructureBuilderTest.nodeShape_bnode() )
* SHACL PropertyShape: named PropertyShape (see NodeShapeTest.simple_named_propertyShape() )
* SHACL PropertyShape: add nodeKind and datatype (see PropertyShapeTest)
* A bit of SKOS (see ConceptSchemeTest, ConceptTest)
* A tiny bit of OWL (see OwlOntologyTest)
* Java 11


## Version 1.0.0 (2021-03-05)

* The first public version
* Java 8