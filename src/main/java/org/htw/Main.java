package org.htw;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.log4j.BasicConfigurator;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class Main {
    public static void main(String[] args) {
        BasicConfigurator.configure();

        // Open the Black Eyed Peas RDF graph from the filesystem
        try {
            InputStream in = new FileInputStream(new File("./rdf/BlackEyedPeas-v1.rdf"));

            // Create an empty in‑memory model and populate it from the graph
            Model model = ModelFactory.createMemModelMaker().createDefaultModel();
            model.read(in, null); // null base URI, since model URIs are absolute
            in.close();
//            System.out.println(model);

            // Create a new query
            String queryString =
                    "PREFIX base: <http://www.semanticweb.org/aminesellami/ontologies/2023/4/blackEyedPeas/> " +
                            "PREFIX bep: <http://www.semanticweb.org/aminesellami/ontologies/2023/4/blackEyedPeas#> " +
                            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
                            "PREFIX owl: <http://www.w3.org/2002/07/owl#>" +

                            "SELECT * " +
                            "WHERE {" +
                            "?artist base:name ?x . " +
                            "?artist bep:isMemberOf ?band ." +
                            "?band base:name ?y ." +
                            "FILTER(?y = \"Black Eyed Peas\")" +
                            "}";

//            select active member
//                    "SELECT * " +
//                    "WHERE {" +
//                    "?artist base:name ?x . " +
//                    "?artist bep:isMemberOf ?band ." +
//                    "?band base:name ?y ." +
//                    "FILTER(?y = \"Black Eyed Peas\")" +
//                    "}";

//            select former member
//                    "SELECT * " +
//                    "WHERE {" +
//                    "?artist base:name ?x . " +
//                    "?artist bep:wasMemberOf ?band ." +
//                    "?band base:name ?y ." +
//                    "FILTER(?y = \"Black Eyed Peas\")" +
//                    "}";

//            select individual with name "Kim Hill"
//                    "SELECT * " +
//                    "WHERE {" +
//                    "?name base:name ?x . " +
//                    "FILTER(?x = \"Kim Hill\")" +
//                    "}";

//            select all individuals with gender "Female"
//                    "SELECT * " +
//                    "WHERE {" +
//                    "?female base:gender \"Female\" . " +
//                    "}";

//            select all individuals that have the name property
//                    "SELECT * " +
//                    "WHERE {" +
//                    "?name base:name ?x . " +
//                    "}";

            Query query = QueryFactory.create(queryString);

            // Execute the query and obtain results
            QueryExecution qe = QueryExecutionFactory.create(query, model);
            ResultSet results = qe.execSelect();

            // Output query results
            ResultSetFormatter.out(System.out, results, query);

            // Important ‑ free up resources used running the query
            qe.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}