public class OhioCalculator implements TaxProcessor {
    @Override
    public double CalculateTax(TaxData data) {
        if (data.getFilingStatus().equals("M")) {
            return data.getEarnings() * 0.025;
        } else {
            return data.getEarnings() * 0.035;
        }
    }
}
