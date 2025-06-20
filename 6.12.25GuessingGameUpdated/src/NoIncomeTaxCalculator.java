public class NoIncomeTaxCalculator implements TaxProcessor{

    @Override
    public double CalculateTax(TaxData data) {
        return 0;
    }
}
