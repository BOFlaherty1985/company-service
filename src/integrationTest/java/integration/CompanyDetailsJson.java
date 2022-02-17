package integration;

import problemdetail.ProblemDetail;

public class CompanyDetailsJson {

    private String symbol;
    private String name;
    private String description;
    private String sector;
    private String peRatio;
    private String exchange;
    private ProblemDetail problemDetail;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getPeRatio() {
        return peRatio;
    }

    public void setPeRatio(String peRatio) {
        this.peRatio = peRatio;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public ProblemDetail getProblemDetail() {
        return problemDetail;
    }

    public void setProblemDetail(ProblemDetail problemDetail) {
        this.problemDetail = problemDetail;
    }
}
