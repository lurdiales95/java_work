public class TaxData {
    private double earnings;
    private String filingStatus;
    private String state;

    public TaxData(double earnings, String filingStatus, String state) {
        this.earnings = earnings;
        this.filingStatus = filingStatus;
        this.state = state;

    }

    public String getState() {
        return state;

    }

    public double getEarnings() {
        return earnings;
    }

    public String getFilingStatus() {
        return filingStatus;
    }
}
