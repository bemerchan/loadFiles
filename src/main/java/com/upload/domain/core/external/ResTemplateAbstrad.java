package com.upload.domain.core.external;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ResTemplateAbstrad {

    RestTemplate restTemplate = new RestTemplate();

    public <T> ResponseEntity<T> findByParameter(String url, ParameterizedTypeReference<T> parameterizedTypeReference) {
        return this.processRequest(this.restTemplate.exchange(url, HttpMethod.GET, this.getHttpEntity(), parameterizedTypeReference));
    }

    private HttpEntity<HttpHeaders> getHttpEntity() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
        return new HttpEntity(httpHeaders);
    }

    private <T> ResponseEntity<T> processRequest(final ResponseEntity<T> response) throws RuntimeException {
        try {
            if (HttpStatus.OK.equals(response.getStatusCode())) {
                return response;
            } else {
                throw new RuntimeException("Http Error Code: {}" + response.getStatusCode().name());
            }
        } catch (HttpServerErrorException var3) {
            throw new RuntimeException(var3.getMessage(), var3);
        }
    }
}
