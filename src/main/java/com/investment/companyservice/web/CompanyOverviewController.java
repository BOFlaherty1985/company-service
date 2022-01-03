package com.investment.companyservice.web;

import com.investment.alphavantageapi.api.company.CompanyOverviewApi;
import com.investment.alphavantageapi.model.company.CompanyOverviewData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import problemdetail.ProblemDetail;
import problemdetail.ProblemDetailBuilder;
import server.companydetails.CompanyDetailsServerResponse;
import server.companydetails.CompanyDetailsServerResponseBuilder;

import java.util.Optional;

@RestController
public class CompanyOverviewController {

    private CompanyOverviewApi alphaVantageCompanyOverviewApi;

    @Autowired
    public CompanyOverviewController(CompanyOverviewApi alphaVantageCompanyOverviewApi) {
        this.alphaVantageCompanyOverviewApi = alphaVantageCompanyOverviewApi;
    }

    @GetMapping(value = "/companyOverview")
    public ResponseEntity<CompanyDetailsServerResponse> getCompanyOverview(Optional<String> ticker) {
        if (ticker.isEmpty()) {
            ProblemDetail problemDetail = ProblemDetailBuilder.problemDetailBuilder()
                    .title("title")
                    .detail("detail")
                    .instance("instance")
                    .type("type")
                    .status("status")
                    .build();
            return ResponseEntity.ok(new CompanyDetailsServerResponseBuilder()
                    .problemDetail(Optional.of(problemDetail))
                    .build());
        }
        CompanyOverviewData companyOverviewData = alphaVantageCompanyOverviewApi.getCompanyOverviewData(ticker.get());
        return ResponseEntity.ok(CompanyDetailsServerResponseBuilder.builder
                .symbol(Optional.of(companyOverviewData.getSymbol()))
                .name(Optional.of(companyOverviewData.getName()))
                .description(Optional.of(companyOverviewData.getDescription()))
                .sector(Optional.of(companyOverviewData.getSector()))
                .exchange(Optional.of(companyOverviewData.getExchange()))
                .peRatio(Optional.of(companyOverviewData.getPeRatio()))
                .build());
    }
}
