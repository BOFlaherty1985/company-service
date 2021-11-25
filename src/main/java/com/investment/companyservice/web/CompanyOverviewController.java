package com.investment.companyservice.web;

import com.investment.alphavantageapi.api.company.CompanyOverviewApi;
import com.investment.alphavantageapi.model.company.CompanyOverviewData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanyOverviewController {

    private CompanyOverviewApi alphaVantageCompanyOverviewApi;

    @Autowired
    public CompanyOverviewController(CompanyOverviewApi alphaVantageCompanyOverviewApi) {
        this.alphaVantageCompanyOverviewApi = alphaVantageCompanyOverviewApi;
    }

    @GetMapping(value = "/companyOverview")
    public CompanyOverviewData getCompanyOverview(String ticker) {
        return alphaVantageCompanyOverviewApi.getCompanyOverviewData(ticker);
    }
}
