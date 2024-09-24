import java.util.*;
import java.util.logging.*;

// Abstract Product: Button
interface Button {
    void render();
    void onClick();
}

// Abstract Product: Checkbox
interface Checkbox {
    void render();
    boolean isChecked();
    void setChecked(boolean checked);
}

// Abstract Product: TextField
interface TextField {
    void render();
    String getText();
    void setText(String text);
}

// Abstract Factory
interface UIComponentFactory {
    Button createButton();
    Checkbox createCheckbox();
    TextField createTextField();
}

// Concrete Products for Windows
class WindowsButton implements Button {
    private static final Logger logger = Logger.getLogger(WindowsButton.class.getName());

    @Override
    public void render() {
        logger.info("Rendering Windows-style button");
    }

    @Override
    public void onClick() {
        logger.info("Windows button clicked");
    }
}

class WindowsCheckbox implements Checkbox {
    private static final Logger logger = Logger.getLogger(WindowsCheckbox.class.getName());
    private boolean checked = false;

    @Override
    public void render() {
        logger.info("Rendering Windows-style checkbox");
    }

    @Override
    public boolean isChecked() {
        return checked;
    }

    @Override
    public void setChecked(boolean checked) {
        this.checked = checked;
        logger.info("Windows checkbox state changed to: " + checked);
    }
}

class WindowsTextField implements TextField {
    private static final Logger logger = Logger.getLogger(WindowsTextField.class.getName());
    private String text = "";

    @Override
    public void render() {
        logger.info("Rendering Windows-style text field");
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
        logger.info("Windows text field updated: " + text);
    }
}

// Concrete Products for MacOS
class MacOSButton implements Button {
    private static final Logger logger = Logger.getLogger(MacOSButton.class.getName());

    @Override
    public void render() {
        logger.info("Rendering MacOS-style button");
    }

    @Override
    public void onClick() {
        logger.info("MacOS button clicked");
    }
}

class MacOSCheckbox implements Checkbox {
    private static final Logger logger = Logger.getLogger(MacOSCheckbox.class.getName());
    private boolean checked = false;

    @Override
    public void render() {
        logger.info("Rendering MacOS-style checkbox");
    }

    @Override
    public boolean isChecked() {
        return checked;
    }

    @Override
    public void setChecked(boolean checked) {
        this.checked = checked;
        logger.info("MacOS checkbox state changed to: " + checked);
    }
}

class MacOSTextField implements TextField {
    private static final Logger logger = Logger.getLogger(MacOSTextField.class.getName());
    private String text = "";

    @Override
    public void render() {
        logger.info("Rendering MacOS-style text field");
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
        logger.info("MacOS text field updated: " + text);
    }
}

// Concrete Factories
class WindowsUIComponentFactory implements UIComponentFactory {
    @Override
    public Button createButton() {
        return new WindowsButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new WindowsCheckbox();
    }

    @Override
    public TextField createTextField() {
        return new WindowsTextField();
    }
}

class MacOSUIComponentFactory implements UIComponentFactory {
    @Override
    public Button createButton() {
        return new MacOSButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new MacOSCheckbox();
    }

    @Override
    public TextField createTextField() {
        return new MacOSTextField();
    }
}

// Application using the Abstract Factory
class CrossPlatformApplication {
    private final UIComponentFactory factory;
    private final List<Button> buttons = new ArrayList<>();
    private final List<Checkbox> checkboxes = new ArrayList<>();
    private final List<TextField> textFields = new ArrayList<>();
    private static final Logger logger = Logger.getLogger(CrossPlatformApplication.class.getName());

    public CrossPlatformApplication(UIComponentFactory factory) {
        this.factory = factory;
    }

    public void createUI() {
        buttons.add(factory.createButton());
        checkboxes.add(factory.createCheckbox());
        textFields.add(factory.createTextField());
        logger.info("UI components created");
    }

    public void renderUI() {
        buttons.forEach(Button::render);
        checkboxes.forEach(Checkbox::render);
        textFields.forEach(TextField::render);
        logger.info("UI rendered");
    }

    public void simulateUserInteraction() {
        buttons.forEach(Button::onClick);
        checkboxes.forEach(checkbox -> checkbox.setChecked(true));
        textFields.forEach(textField -> textField.setText("Hello, Abstract Factory!"));
        logger.info("User interaction simulated");
    }
}

// Main class to demonstrate the Abstract Factory pattern
public class CrossPlatformUIDemo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose your platform: Windows or MacOS?");
        String platformChoice = scanner.nextLine().toLowerCase();

        UIComponentFactory factory;

        if (platformChoice.equals("windows")) {
            factory = new WindowsUIComponentFactory();
        } else if (platformChoice.equals("macos")) {
            factory = new MacOSUIComponentFactory();
        } else {
            throw new UnsupportedOperationException("Unsupported platform choice");
        }

        CrossPlatformApplication app = new CrossPlatformApplication(factory);
        app.createUI();
        app.renderUI();
        app.simulateUserInteraction();
    }
}
