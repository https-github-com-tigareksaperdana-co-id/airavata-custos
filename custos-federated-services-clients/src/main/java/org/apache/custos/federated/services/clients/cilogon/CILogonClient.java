/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package org.apache.custos.federated.services.clients.cilogon;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.util.Base64;

/**
 * This class is responsible for CILogon operations
 */
@Component
public class CILogonClient {
    private final static Logger LOGGER = LoggerFactory.getLogger(CILogonClient.class);

    @Value("${ciLogon.admin.client.id:cilogon:/adminClient/332b62ccdae98bad47bc4a823fafa369/1573169678768}")
    private String adminClientId;


    @Value("${ciLogon.admin.client.secret:lzoJ7F9JOq-q4qabXg8W9btHA1Nqwd0TFRfbCAiffL-QKlsuFtiRY5OaR1C8vGBy2glt9KEDPCBKproXFhQNyA}")
    private String adminClientSecret;


    @Value("${ciLogon.admin.auth.endpoint:https://test.cilogon.org/oauth2/oidc-cm}")
    private String ciLogonAuthEndpoint;

    private RestTemplate template = new RestTemplate();


    public CILogonResponse registerClient(@NotNull String clientName, @NotNull String[] redirectURIs,
                                          @NotNull String comment, String[] scopes, String homeURL,
                                          String contactEmail) throws JSONException {

        CILogonRequest req = new CILogonRequest();

        req.setClientName(clientName);
        req.setRedirectURIs(redirectURIs);
        req.setComment(comment);

        if (scopes != null && scopes.length > 0) {
            req.setScope(scopes);
        }

        if (homeURL != null) {
            req.setClientURI(homeURL);
        }

        if (contactEmail != null) {
            req.setContacts(new String[]{contactEmail});
        }
        HttpHeaders headers = new HttpHeaders();

        LOGGER.info("client name" + clientName);
        LOGGER.info("URIS "+redirectURIs.toString());
        LOGGER.info("Comment "+comment);
        LOGGER.info("Token " +getBearerToken());
        LOGGER.info("Scopes", scopes.toString());
        LOGGER.info("Scopes size", scopes.length);
        for(String scope: scopes) {
            LOGGER.info(scope);
        }

        headers.add("Authorization", getBearerToken());
        headers.setContentType(MediaType.APPLICATION_JSON);


        HttpEntity<CILogonRequest> entity = new HttpEntity<CILogonRequest>(req, headers);
        ResponseEntity<CILogonResponse> responseEntity = template.exchange
                (ciLogonAuthEndpoint, HttpMethod.POST, entity, CILogonResponse.class);
        return responseEntity.getBody();
    }

    public CILogonResponse getClient(@NotNull String clientId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", getBearerToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity(headers);

        String url = ciLogonAuthEndpoint + "?client_id=" + clientId;
        System.out.println(url);
        ResponseEntity<CILogonResponse> responseEntity = template.
                exchange(url, HttpMethod.GET, entity, CILogonResponse.class);

        return responseEntity.getBody();
    }

    public void updateClient(@NotNull String clientId, @NotNull String clientName,
                             @NotNull String[] redirectURIs, @NotNull String comment, String[] scopes,
                             String homeURL, String contactEmail) {
        CILogonRequest req = new CILogonRequest();

        req.setClientName(clientName);
        req.setRedirectURIs(redirectURIs);
        req.setComment(comment);

        if (scopes != null && scopes.length > 0) {
            req.setScope(scopes);
        }

        if (homeURL != null) {
            req.setClientURI(homeURL);
        }

        if (contactEmail != null) {
            req.setContacts(new String[]{contactEmail});
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", getBearerToken());
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<CILogonRequest> entity = new HttpEntity<CILogonRequest>(req, headers);
        String url = ciLogonAuthEndpoint + "?client_id=" + clientId;
        template.put(url, entity);
    }


    public void deleteClient(@NotNull String clientId) {

        String url = ciLogonAuthEndpoint + "?client_id=" + clientId;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", getBearerToken());
        HttpEntity<Object> entity = new HttpEntity<Object>(headers);
        template.exchange(url, HttpMethod.DELETE, entity, String.class);
    }


    private String getBearerToken() {
        String decoded = adminClientId + ":" + adminClientSecret;
        return "Bearer " + Base64.getEncoder().encodeToString(decoded.getBytes());
    }

}
