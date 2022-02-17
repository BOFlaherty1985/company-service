package integration;

import com.google.gson.Gson;
import com.investment.alphavantageapi.api.company.CompanyOverviewApi;
import com.investment.alphavantageapi.model.company.CompanyOverviewData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = CompanyServiceIntegerationTestConfiguration.class)
public class CompanyOverviewControllerIntegrationTest {

    @MockBean
    private CompanyOverviewApi companyOverviewApi;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldVerifyAndAssertCompanyOverviewApiResponse() throws Exception {
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

        //TODO - bearer token will not be valid for > 24 hours. Can we mock the response from JwtRequestFilter?
        // when
        MvcResult result = mockMvc.perform(get(String.format("/companyOverview?ticker=%s", ticker))
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0VXNlciIsImV4cCI6MTY0NTEzODIwOX0.dWwGak8Vpc1Iq-88i1fHU3KqdaDaiYjDLIDmUOIUAmY"))
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        CompanyDetailsJson companyOverviewResponse = new Gson().fromJson(jsonResponse, CompanyDetailsJson.class);

        // then
        assertEquals(companyOverviewResponse.getSymbol(), ticker);
        assertEquals(companyOverviewResponse.getName(), name);
        assertEquals(companyOverviewResponse.getDescription(), description);
        assertEquals(companyOverviewResponse.getSector(), sector);
        assertEquals(companyOverviewResponse.getExchange(), exchange);
        assertEquals(companyOverviewResponse.getPeRatio(), peRatio);
    }
}
