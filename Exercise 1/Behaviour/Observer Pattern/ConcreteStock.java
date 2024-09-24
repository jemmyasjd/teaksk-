// Concrete Subject: Stock
import java.util.ArrayList;
import java.util.List;

public class ConcreteStock implements Stock {
    private List<Observer> observers;
    private String stockSymbol;
    private double price;

    public ConcreteStock(String stockSymbol, double price) {
        this.stockSymbol = stockSymbol;
        this.price = price;
        this.observers = new ArrayList<>();
    }

    public void setPrice(double price) {
        this.price = price;
        notifyObservers();
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(stockSymbol, price);
        }
    }
}
