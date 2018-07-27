package com.graphql.tutorial;

import com.coxautodev.graphql.tools.SchemaParser;
import com.graphql.tutorial.dto.User;
import com.graphql.tutorial.repo.VoteRepository;
import com.graphql.tutorial.resolver.*;
import com.graphql.tutorial.repo.LinkRepository;
import com.graphql.tutorial.repo.UserRepository;
import com.graphql.tutorial.util.Scalars;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import graphql.schema.GraphQLSchema;
import graphql.servlet.GraphQLContext;
import graphql.servlet.SimpleGraphQLServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndpoint extends SimpleGraphQLServlet {
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
        super(buildSchema());
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
    protected GraphQLContext createContext(Optional<HttpServletRequest> request, Optional<HttpServletResponse> response) {
        User user = request
                .map(req -> req.getHeader("Authorization"))
                .filter(id -> !id.isEmpty())
                .map(id -> id.replace("Bearer ",""))
                .map(userRepository::findById)
                .orElse(null);

        return new AuthContext(user, request, response);
    }
}
