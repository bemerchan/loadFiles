package com.upload.domain.core.upload;

import com.upload.application.api.model.CategoryResponse;
import com.upload.application.api.model.CurrencyResponse;
import com.upload.application.api.model.SitesResponse;
import com.upload.domain.core.external.MercadoLibreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class LoadDataServices {


    @Autowired
    MercadoLibreService mercadoLibreService;

    Map<String, String> mapCurrencies = new HashMap<>();
    Map<String, Map<String, String>> mapCategories = new HashMap<>();

    public LoadDataServices(MercadoLibreService mercadoLibreService) {
        this.mercadoLibreService = mercadoLibreService;
        this.loadCategories();
        this.loadCurrencies();
    }

    private void loadCurrencies() {
        List<CurrencyResponse> currencyResponses = mercadoLibreService.getCurrencies();
        if(currencyResponses != null) {
            mapCurrencies = currencyResponses.stream().collect(
                    Collectors.toMap(CurrencyResponse::getId, CurrencyResponse::getDescription));
        }
    }

    private void loadCategories() {
        List<SitesResponse> sites = mercadoLibreService.getSites();
        sites.forEach(site -> {
            List<CategoryResponse> categories = mercadoLibreService.getCategoriesBySite(site.getId());
            if(categories != null) {
                mapCategories.put(site.getId(), categories.stream().collect(
                        Collectors.toMap(CategoryResponse::getId, CategoryResponse::getName)));
            }
        });
    }

    public String getCategoryBySiteAndId(String site, String categoryId) {
        Map<String, String> categories = mapCategories.get(site);
        if(categories != null) {
            return categories.get(categoryId);
        }
        return null;
    }

    public String getCurrency(String currencyId) {
        return mapCurrencies.get(currencyId);
    }
}
