package com.samsamohoh.webtoonsearch.adapter.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestClientBuilder;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.data.client.orhlc.AbstractOpenSearchConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories
public class SearchEngineConfig extends AbstractOpenSearchConfiguration {

    @Value("${spring.opensearch.uris}")
    private String opensearchUri;

    @Value("${spring.opensearch.connection-timeout}")
    private int connectionTimeout;

    @Value("${spring.opensearch.socket-timeout}")
    private int socketTimeout;

    @Value("${spring.opensearch.id}")
    private String id;

    @Value("${spring.opensearch.pwd}")
    private String pwd;

    /*
    * Local 환경에서 사용한 client
    * */
//    @Bean
//    public OpenSearchClient openSearchClient() {
//        RestClient restClient = RestClient.builder(HttpHost.create(opensearchUri))
//                .setRequestConfigCallback(requestConfigBuilder -> requestConfigBuilder
//                        .setConnectTimeout(connectionTimeout)
//                        .setSocketTimeout(socketTimeout)
//                )
//                .build();
//
//        OpenSearchTransport transport = new RestClientTransport(
//                restClient,
//                new JacksonJsonpMapper()
//        );
//
//        return new OpenSearchClient(transport);
//    }

    @Override
    @Bean
    public RestHighLevelClient opensearchClient() {

        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(id, pwd));

        RestClientBuilder builder = RestClient.builder(
                        new HttpHost(opensearchUri, 443, "https"))
                .setHttpClientConfigCallback(httpAsyncClientBuilder -> httpAsyncClientBuilder
                        .setDefaultCredentialsProvider(credentialsProvider));

        return new RestHighLevelClient(builder);
    }
}
