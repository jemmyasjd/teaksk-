// Concrete Observer: Investor
public class Investor implements Observer {
    private String investorName;

    public Investor(String investorName) {
        this.investorName = investorName;
    }

    @Override
    public void update(String stockSymbol, double price) {
        System.out.println("Notification to " + investorName + ": Stock " + stockSymbol + " is now $" + price);
    }
}
