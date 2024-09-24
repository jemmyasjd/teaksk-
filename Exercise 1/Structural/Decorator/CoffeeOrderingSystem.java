import java.util.*;
import java.util.logging.*;
import java.text.DecimalFormat;

// Component interface
interface Coffee {
    String getDescription();
    double getCost();
}

// Concrete Component
class SimpleCoffee implements Coffee {
    @Override
    public String getDescription() {
        return "Simple Coffee";
    }

    @Override
    public double getCost() {
        return 1.0;
    }
}

// Decorator
abstract class CoffeeDecorator implements Coffee {
    protected Coffee decoratedCoffee;

    public CoffeeDecorator(Coffee coffee) {
        this.decoratedCoffee = coffee;
    }

    @Override
    public String getDescription() {
        return decoratedCoffee.getDescription();
    }

    @Override
    public double getCost() {
        return decoratedCoffee.getCost();
    }
}

// Concrete Decorators
class Milk extends CoffeeDecorator {
    public Milk(Coffee coffee) {
        super(coffee);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", Milk";
    }

    @Override
    public double getCost() {
        return super.getCost() + 0.5;
    }
}

class Sugar extends CoffeeDecorator {
    public Sugar(Coffee coffee) {
        super(coffee);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", Sugar";
    }

    @Override
    public double getCost() {
        return super.getCost() + 0.2;
    }
}

class Whip extends CoffeeDecorator {
    public Whip(Coffee coffee) {
        super(coffee);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", Whip";
    }

    @Override
    public double getCost() {
        return super.getCost() + 0.7;
    }
}

class Syrup extends CoffeeDecorator {
    private String flavor;

    public Syrup(Coffee coffee, String flavor) {
        super(coffee);
        this.flavor = flavor;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + ", " + flavor + " Syrup";
    }

    @Override
    public double getCost() {
        return super.getCost() + 0.6;
    }
}

// Coffee Ordering System
public class CoffeeOrderingSystem {
    private static final Logger logger = Logger.getLogger(CoffeeOrderingSystem.class.getName());
    private static final DecimalFormat df = new DecimalFormat("#.##");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Coffee coffee = new SimpleCoffee();

        System.out.println("Welcome to the Coffee Ordering System!");
        System.out.println("Start with a Simple Coffee ($1.0)");

        while (true) {
            System.out.println("\nCurrent Order: " + coffee.getDescription());
            System.out.println("Current Cost: $" + df.format(coffee.getCost()));
            System.out.println("\nAdd more ingredients?");
            System.out.println("1. Milk ($0.5)");
            System.out.println("2. Sugar ($0.2)");
            System.out.println("3. Whip ($0.7)");
            System.out.println("4. Syrup ($0.6)");
            System.out.println("5. Finish Order");

            System.out.print("Enter your choice (1-5): ");
            int choice = scanner.nextInt();

            if (choice == 5) {
                break;
            }

            switch (choice) {
                case 1:
                    coffee = new Milk(coffee);
                    logger.info("Added Milk to the order");
                    break;
                case 2:
                    coffee = new Sugar(coffee);
                    logger.info("Added Sugar to the order");
                    break;
                case 3:
                    coffee = new Whip(coffee);
                    logger.info("Added Whip to the order");
                    break;
                case 4:
                    System.out.print("Enter syrup flavor: ");
                    scanner.nextLine(); // Consume newline
                    String flavor = scanner.nextLine();
                    coffee = new Syrup(coffee, flavor);
                    logger.info("Added " + flavor + " Syrup to the order");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        System.out.println("\nFinal Order: " + coffee.getDescription());
        System.out.println("Total Cost: $" + df.format(coffee.getCost()));
        logger.info("Order completed: " + coffee.getDescription() + " - $" + df.format(coffee.getCost()));
    }
}