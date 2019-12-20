package jhh.reportCenter.cfg;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@Slf4j
public class ElasticConfiguration {

    @Autowired
    protected ElasticProperties elasticProperties;

    @Bean
    //@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Client client() throws UnknownHostException {
        Settings settings = Settings.builder().put("cluster.name", elasticProperties.getClusterName())
                .build();
        List<TransportAddress> transportAddresses = new ArrayList<>();
        for (HttpHost host : hosts()) {
            transportAddresses.add(new InetSocketTransportAddress(InetAddress.getByName(host.getHostName()), host.getPort()));
        }
        TransportClient client = new PreBuiltTransportClient(settings);
        client.addTransportAddresses(transportAddresses.toArray(new TransportAddress[]{}));
        return client;
    }

    @Bean
    //@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public IndicesAdminClient adminClient() throws UnknownHostException {
        Client client = client();
        return client.admin().indices();
    }

    private List<HttpHost> hosts() {
        String[] hosts = elasticProperties.getClusterNodes().split(",");
        List<HttpHost> httpHosts = new ArrayList<>();
        for (String host : hosts) {
            HttpHost hh = HttpHost.create(host);
            httpHosts.add(hh);
        }
        return httpHosts;
    }
}
