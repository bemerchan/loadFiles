package com.upload.domain.core.external;

import com.upload.application.api.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MercadoLibreService extends ResTemplateAbstrad {

    @Value("${client.mercadolibre.api.url}")
    private String apiUrl;

    public List<ItemResponse> findItems(List<String> ids) {
        try {
            ResponseEntity<List<ItemResponse>> response = findByParameter(apiUrl + "/items?ids=" +
                            String.join(",", ids),
                    new ParameterizedTypeReference<List<ItemResponse>>() {});
            return response.getBody();
        } catch (Exception e) {
            log.error("Error consultando los iems" + e.getMessage());
        }
        return Collections.emptyList();
    }

    public List<CurrencyResponse> getCurrencies() {
        try {
            ResponseEntity<List<CurrencyResponse>> response = findByParameter(apiUrl + "/currencies",
                    new ParameterizedTypeReference<List<CurrencyResponse>>() {});
            return response.getBody();
        } catch (Exception e) {
            log.error("Error consultando las monedas" + e.getMessage());
        }
        return Collections.emptyList();
    }

    public List<SitesResponse> getSites() {
        try {
            ResponseEntity<List<SitesResponse>> response = findByParameter(apiUrl + "/sites",
                    new ParameterizedTypeReference<List<SitesResponse>>() {});
            return response.getBody();
        } catch (Exception e) {
            log.error("Error consultando las ubicaciones" + e.getMessage());
        }
        return Collections.emptyList();
    }

    public List<CategoryResponse> getCategoriesBySite(String site) {
        try {
            ResponseEntity<List<CategoryResponse>> response = findByParameter(apiUrl + "/sites/" + site + "/categories",
                    new ParameterizedTypeReference<List<CategoryResponse>>() {});
            return response.getBody();
        } catch (Exception e) {
            log.error("Error consultando las categorias" + e.getMessage());
        }
        return Collections.emptyList();
    }

    public List<UserResponse> getUsersByIds(List<Long> ids) {
        try {
            ResponseEntity<List<UserResponse>> response = findByParameter(apiUrl + "/users?ids=" +
                            ids.stream().map(String::valueOf).collect(Collectors.joining(",")),
                    new ParameterizedTypeReference<List<UserResponse>>() {});
            return response.getBody();
        } catch (Exception e) {
            log.error("Error consultando los usuarios" + e.getMessage());
        }
        return Collections.emptyList();
    }
}
