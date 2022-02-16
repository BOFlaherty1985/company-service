package com.investment.companyservice.web;

import com.investment.alphavantageapi.api.company.CompanyOverviewApi;
import com.investment.alphavantageapi.model.company.CompanyOverviewData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import problemdetail.ProblemDetail;
import server.companydetails.CompanyDetailsServerResponse;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CompanyOverviewControllerTest {

    @Mock
    private CompanyOverviewApi companyOverviewApi;

    @InjectMocks
    private CompanyOverviewController companyOverviewController;

    @Test
    public void shouldProductProblemDetailIfTickerIsNotProvided() {
        // given
        Optional<String> ticker = Optional.empty();

        String title = "title";
        String detail = "detail";
        String instance = "instance";
        String type = "type";
        String status = "status";

        // when
        ResponseEntity<CompanyDetailsServerResponse> result = companyOverviewController.getCompanyOverview(ticker);

        // then
        assertNotNull(result);
        CompanyDetailsServerResponse response = result.getBody();
        ProblemDetail problemDetail = response.getProblemDetail().get();
        assertNotNull(problemDetail);
        assertEquals(title, problemDetail.getTitle());
        assertEquals(detail, problemDetail.getDetail());
        assertEquals(instance, problemDetail.getInstance());
        assertEquals(type, problemDetail.getType());
        assertEquals(status, problemDetail.getStatus());
    }

    @Test
    public void shouldReturnResponseBodyContainingCompanyOverviewData() {
        // given
        Optional<String> ticker = Optional.of("IBM");
        Optional<String> name = Optional.of("International Business Systems");
        Optional<String> description = Optional.of("description");
        Optional<String> sector = Optional.of("sector");
        Optional<String> exchange = Optional.of("exchange");
        Optional<String> peRatio = Optional.of("peRatio");

        CompanyOverviewData companyOverviewData = CompanyOverviewData.builder()
                .symbol(ticker.get())
                .name(name.get())
                .description(description.get())
                .sector(sector.get())
                .exchange(exchange.get())
                .peRatio(peRatio.get())
                .build();

        given(companyOverviewApi.getCompanyOverviewData(ticker.get())).willReturn(companyOverviewData);

        // when
        ResponseEntity<CompanyDetailsServerResponse> result = companyOverviewController.getCompanyOverview(ticker);

        // then
        CompanyDetailsServerResponse response = result.getBody();
        assertNotNull(response);
        assertEquals(ticker, response.getSymbol());
        assertEquals(name, response.getName());
        assertEquals(description, response.getDescription());
        assertEquals(sector, response.getSector());
        assertEquals(exchange, response.getExchange());
        assertEquals(peRatio, response.getPeRatio());
    }
}