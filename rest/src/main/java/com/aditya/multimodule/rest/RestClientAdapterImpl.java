package com.aditya.multimodule.rest;

import com.aditya.multimodule.model.Client;
import com.aditya.multimodule.model.commons.ErrorCode;
import com.aditya.multimodule.model.commons.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class RestClientAdapterImpl implements RestClientAdapter {
    private final String baseUrl;
    private final RestTemplate template;

    public RestClientAdapterImpl(@Value("${spring.account.service}") String baseUrl, RestTemplate template) {
        this.baseUrl = baseUrl;
        this.template = template;
    }

    @Override
    public Result<Client> getClient(String nit) {
        try {
            String url = baseUrl.concat("/").concat(nit);

            ResponseEntity<Result<Client>> responseEntity = template
                    .exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<Result<Client>>() {
                    });

            return validateSapResponse(responseEntity);
        } catch (Exception exception) {
            return Result.errorResult(ErrorCode.CLIENT_ERROR,
                    "An error prevented the client from being retrieved. NIT: " + nit);
        }
    }

    private <T> Result<T> validateSapResponse(ResponseEntity<Result<T>> responseEntity) {
        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) getError(responseEntity.getStatusCodeValue());

        return Optional.of(responseEntity)
                .filter(response -> response.getBody() != null)
                .map(ResponseEntity::getBody)
                .filter(Result::isSuccess)
                .orElse(Result.errorResult(ErrorCode.SERVER_ERROR,
                        "Error trying to find the client. Status code: " + responseEntity.getStatusCodeValue()));

    }

    private static Result<Client> getError(int responseEntity) {
        return Result.errorResult(ErrorCode.SERVER_ERROR,
                "Error trying to find the client. Status code: " + responseEntity);
    }
}
