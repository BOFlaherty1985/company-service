package com.investment.companyservice.web;

import com.investment.alphavantageapi.api.company.CompanyOverviewApi;
import com.investment.alphavantageapi.model.company.CompanyOverviewData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import server.companydetails.CompanyDetailsServerResponse;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = { "eureka.client.enabled=false"})
public class CompanyOverviewControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private CompanyOverviewApi companyOverviewApi;

    @LocalServerPort
    private Integer port;

    @Test
    public void shouldVerifyAndAssertCompanyOverviewApiResponse() {
        // given
        String ticker = "IBM";
        String name = "name";
        String description = "description";
        String sector = "sector";
        String exchange = "exchange";
        String peRatio = "peRatio";

        CompanyOverviewData companyOverviewData
                = CompanyOverviewData.builder()
                .symbol(ticker)
                .name(name)
                .description(description)
                .sector(sector)
                .exchange(exchange)
                .peRatio(peRatio)
                .build();

        when(companyOverviewApi.getCompanyOverviewData(ticker)).thenReturn(companyOverviewData);

        // when
        ResponseEntity<CompanyDetailsServerResponse> result =
                restTemplate.getForEntity("http://localhost:" + port + "/companyOverview?ticker=" + ticker,
                CompanyDetailsServerResponse.class);

        CompanyDetailsServerResponse response = result.getBody();

        // then
        assertEquals(response.getSymbol(), Optional.of(ticker));
        assertEquals(response.getName(), Optional.of(name));
        assertEquals(response.getDescription(), Optional.of(description));
        assertEquals(response.getSector(), Optional.of(sector));
        assertEquals(response.getExchange(), Optional.of(exchange));
        assertEquals(response.getPeRatio(), Optional.of(peRatio));
    }
}
