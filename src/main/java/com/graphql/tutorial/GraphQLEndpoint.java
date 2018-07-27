package com.graphql.tutorial;

import com.coxautodev.graphql.tools.SchemaParser;
import com.graphql.tutorial.repo.UserRepository;
import com.graphql.tutorial.repo.VoteRepository;
import com.graphql.tutorial.resolver.*;
import com.graphql.tutorial.repo.LinkRepository;
import com.graphql.tutorial.util.Scalars;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import graphql.schema.GraphQLSchema;
import graphql.servlet.SimpleGraphQLServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndpoint extends HttpServlet {
    private SimpleGraphQLServlet graph;
    private static LinkRepository linkRepository;
    private static UserRepository userRepository;
    private static VoteRepository voteRepository;

    static {
        MongoDatabase db = new MongoClient().getDatabase("graphql");
        linkRepository = new LinkRepository(db.getCollection("links"));
        userRepository = new UserRepository(db.getCollection("users"));
        voteRepository = new VoteRepository(db.getCollection("votes"));
    }

    public GraphQLEndpoint() {
        graph = SimpleGraphQLServlet
                .builder(buildSchema())
                .withGraphQLContextBuilder(new AuthContextBuilder(userRepository))
                .build();
    }

    private static GraphQLSchema buildSchema() {
        return SchemaParser.newParser()
                .file("schema.graphqls")
                .resolvers(
                        new Query(linkRepository),
                        new Mutation(linkRepository, userRepository, voteRepository),
                        new SigninResolver(),
                        new LinkResolver(userRepository),
                        new VoteResolver(linkRepository, userRepository)
                )
                .scalars(Scalars.dateTime)
                .build()
                .makeExecutableSchema();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        graph.service(req, resp);
    }
}
