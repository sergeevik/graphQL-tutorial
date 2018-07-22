package com.graphql.tutorial;

import com.coxautodev.graphql.tools.SchemaParser;
import com.graphql.tutorial.dto.Mutation;
import com.graphql.tutorial.dto.Query;
import com.graphql.tutorial.repo.LinkRepository;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import graphql.schema.GraphQLSchema;
import graphql.servlet.SimpleGraphQLServlet;

import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndpoint extends SimpleGraphQLServlet {
    private static LinkRepository linkRepository;

    static {
        MongoDatabase db = new MongoClient().getDatabase("graphql");
        linkRepository = new LinkRepository(db.getCollection("links"));
    }

    public GraphQLEndpoint() {
        super(buildSchema());
    }

    private static GraphQLSchema buildSchema() {
        return SchemaParser.newParser()
                .file("schema.graphqls")
                .resolvers(new Query(linkRepository), new Mutation(linkRepository))
                .build()
                .makeExecutableSchema();
    }
}
