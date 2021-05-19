/*
package com.learn.learningproject.config;


import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.connection.ConnectionPoolSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.concurrent.TimeUnit;

*/
/*
created by Rahul sawaria on 17/05/21
*//*

@Configuration
@EnableMongoRepositories
public class MongoDbConfig {

    @Autowired
    private Environment env;

    @Bean
    public MongoClient mongo() {


        MongoClientOptions mongoClientOptions=MongoClientOptions.builder().connectTimeout(Integer.parseInt(env.getRequiredProperty("mongo.connectTimeout")))
                .maxWaitTime(Integer.parseInt(env.getRequiredProperty("mongo.maxWaitTime")))
                .connectionsPerHost(Integer.parseInt(env.getRequiredProperty("mongo.connectionsPerHost")))
                .socketTimeout(Integer.parseInt(env.getRequiredProperty("mongo.socketTimeout")))
                .build();


        MongoCredential credential=MongoCredential.createCredential(env.getRequiredProperty("mongo.userName"),
                env.getRequiredProperty("mongo.databaseName"), env.getRequiredProperty("mongo.password").toCharArray());

        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017/test");
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString).credential(credential)
                .applyToConnectionPoolSettings((ConnectionPoolSettings.Builder builder) -> {
                    builder.maxSize(Integer.parseInt(env.getRequiredProperty("mongo.connectionsPerHost"))) //connections count
                            .maxWaitTime(Integer.parseInt(env.getRequiredProperty("mongo.maxWaitTime")), TimeUnit.MILLISECONDS);
                }).applyToSocketSettings(builder -> {
                    builder.connectTimeout(Integer.parseInt(env.getRequiredProperty("mongo.socketTimeout")), TimeUnit.MILLISECONDS);
                }).build();

        return MongoClients.create(mongoClientSettings);

    }

    @Bean
    public MongoTemplate mongoTemplate()
    {
        return new MongoTemplate(mongo(),env.getRequiredProperty("mongo.databaseName"));
    }

}
*/
