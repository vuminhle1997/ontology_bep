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

            // Create new queries
            String queryDescription1 = "select active members";
            String queryString1 =
                    "PREFIX base: <http://www.semanticweb.org/aminesellami/ontologies/2023/4/blackEyedPeas/> " +
                            "PREFIX bep: <http://www.semanticweb.org/aminesellami/ontologies/2023/4/blackEyedPeas#> " +
                            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
                            "PREFIX owl: <http://www.w3.org/2002/07/owl#>" +

                            "SELECT * " +
                            "WHERE {" +
                            "?artistURI base:name ?artistName . " +
                            "?artistURI bep:isMemberOf ?bandURI ." +
                            "?bandURI base:name ?bandName ." +
                            "FILTER(?bandName = \"Black Eyed Peas\")" +
                            "}";

            String queryDescription2 = "select former members";
            String queryString2 =
                    "PREFIX base: <http://www.semanticweb.org/aminesellami/ontologies/2023/4/blackEyedPeas/> " +
                            "PREFIX bep: <http://www.semanticweb.org/aminesellami/ontologies/2023/4/blackEyedPeas#> " +
                            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
                            "PREFIX owl: <http://www.w3.org/2002/07/owl#>" +

                            "SELECT * " +
                            "WHERE {" +
                            "?artistURI base:name ?artistName . " +
                            "?artistURI bep:wasMemberOf ?bandURI ." +
                            "?bandURI base:name ?bandName ." +
                            "FILTER(?bandName = \"Black Eyed Peas\")" +
                            "}";

            String queryDescription3 = "select individual with name \"Kim Hill\"";
            String queryString3 =
                    "PREFIX base: <http://www.semanticweb.org/aminesellami/ontologies/2023/4/blackEyedPeas/> " +
                            "PREFIX bep: <http://www.semanticweb.org/aminesellami/ontologies/2023/4/blackEyedPeas#> " +
                            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
                            "PREFIX owl: <http://www.w3.org/2002/07/owl#>" +

                            "SELECT ?name ?activeSince ?activeUntil ?role ?isFounder ?gender ?birthdate " +
                            "WHERE {" +
                            "?artistURI base:name ?artistName . " +
                            "?artistURI base:name ?name ." +
                            "?artistURI base:activeSince ?activeSince ." +
                            "?artistURI base:activeUntil ?activeUntil ." +
                            "?artistURI base:role ?role ." +
                            "?artistURI base:isFounder ?isFounder ." +
                            "?artistURI base:gender ?gender ." +
                            "?artistURI base:birthdate ?birthdate ." +
                            "FILTER(?artistName = \"Kim Hill\")" +
                            "}";

            String queryDescription4 = "select all individuals with gender \"Female\" that are still active in the band";
            String queryString4 =
                    "PREFIX base: <http://www.semanticweb.org/aminesellami/ontologies/2023/4/blackEyedPeas/> " +
                            "PREFIX bep: <http://www.semanticweb.org/aminesellami/ontologies/2023/4/blackEyedPeas#> " +
                            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
                            "PREFIX owl: <http://www.w3.org/2002/07/owl#>" +

                            "SELECT ?artistURI ?artistName ?gender " +
                            "WHERE {" +
                            "?artistURI base:name ?artistName . " +
                            "?artistURI bep:isMemberOf ?bandURI ." +
                            "?artistURI base:gender ?gender . " +
                            "?bandURI base:name ?bandName ." +
                            "FILTER(?bandName = \"Black Eyed Peas\")" +
                            "FILTER(?gender = \"Female\")" +
                            "}";

            String queryDescription5 = "select all individuals with gender \"Female\" that are not active in the band anymore";
            String queryString5 =
                    "PREFIX base: <http://www.semanticweb.org/aminesellami/ontologies/2023/4/blackEyedPeas/> " +
                            "PREFIX bep: <http://www.semanticweb.org/aminesellami/ontologies/2023/4/blackEyedPeas#> " +
                            "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>" +
                            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
                            "PREFIX owl: <http://www.w3.org/2002/07/owl#>" +

                            "SELECT ?artistURI ?artistName ?gender " +
                            "WHERE {" +
                            "?artistURI base:name ?artistName . " +
                            "?artistURI bep:wasMemberOf ?bandURI ." +
                            "?artistURI base:gender ?gender . " +
                            "?bandURI base:name ?bandName ." +
                            "FILTER(?bandName = \"Black Eyed Peas\")" +
                            "FILTER(?gender = \"Female\")" +
                            "}";

//            select all individuals that have the name property
//                    "SELECT * " +
//                    "WHERE {" +
//                    "?name base:name ?x . " +
//                    "}";

            Query[] queries = {
                    QueryFactory.create(queryString1),
                    QueryFactory.create(queryString2),
                    QueryFactory.create(queryString3),
                    QueryFactory.create(queryString4),
                    QueryFactory.create(queryString5),
            };
            String[] queryDescriptions = {
                    queryDescription1, queryDescription2,
                    queryDescription3, queryDescription4,
                    queryDescription5,
            };

            runQueries(queries, queryDescriptions, model);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public static void runQueries(Query[] queries, String[] queryDescriptions, Model model) {
        for (int i = 0; i < queries.length; i++) {
            Query query = queries[i];

            // Execute the query and obtain results
            QueryExecution qe = QueryExecutionFactory.create(query, model);
            ResultSet results = qe.execSelect();

            // Output query description
            String queryDescription = queryDescriptions[i];
            System.out.println(queryDescription);

            // Output query results
            ResultSetFormatter.out(System.out, results, query);

            // Important ‑ free up resources used running the query
            qe.close();
        }
    }
}