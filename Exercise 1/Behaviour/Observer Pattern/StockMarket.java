import java.util.Scanner;

public class StockMarket {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Taking Stock Symbol and Initial Price as input
        System.out.print("Enter the stock symbol (e.g., GOOGL, AAPL): ");
        String stockSymbol = scanner.nextLine();

        System.out.print("Enter the initial price of the stock: ");
        double initialPrice = scanner.nextDouble();
        scanner.nextLine();  // consume the newline

        // Create a ConcreteStock with user input
        ConcreteStock stock = new ConcreteStock(stockSymbol, initialPrice);

        // Asking for the number of investors
        System.out.print("Enter the number of investors to register: ");
        int numberOfInvestors = scanner.nextInt();
        scanner.nextLine(); // consume the newline

        // Registering investors dynamically
        for (int i = 1; i <= numberOfInvestors; i++) {
            System.out.print("Enter investor " + i + " name: ");
            String investorName = scanner.nextLine();
            Investor investor = new Investor(investorName);
            stock.registerObserver(investor);
        }

        // Loop for price updates and notifying investors
        String continueUpdate;
        do {
            // Taking stock price update as input
            System.out.print("Enter the new stock price: ");
            double newPrice = scanner.nextDouble();
            scanner.nextLine();  // consume the newline

            // Set the new price, which will notify the investors
            stock.setPrice(newPrice);

            // Asking if the user wants to continue updating the price
            System.out.print("Do you want to update the stock price again? (yes/no): ");
            continueUpdate = scanner.nextLine();
        } while (continueUpdate.equalsIgnoreCase("yes"));

        scanner.close();
    }
}