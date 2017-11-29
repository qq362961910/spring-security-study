package com.jy.study.spring.security.wiremock;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.Charset;

public class MockServer {
    public static void main(String[] args) throws IOException {
        WireMock.configureFor(8062);
        WireMock.removeAllMappings();
        mock("/order/1", "01");
        mock("/order/2", "02");
    }

    private static void mock (String url, String fileName) throws IOException {
        ClassPathResource resource = new ClassPathResource("mock/response/" + fileName +  ".txt");
        String content = FileUtils.readFileToString(resource.getFile(), Charset.defaultCharset());
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo(url))
            .willReturn(WireMock.aResponse().withBody(content).withStatus(200)));
    }
}
