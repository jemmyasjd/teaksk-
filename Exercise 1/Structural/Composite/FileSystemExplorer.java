import java.util.*;
import java.util.logging.*;

// Component
abstract class FileSystemComponent {
    protected String name;
    protected FileSystemComponent parent;

    public FileSystemComponent(String name) {
        this.name = name;
    }

    public abstract void display(int depth);
    public abstract long getSize();

    public void setParent(FileSystemComponent parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    protected String getIndentation(int depth) {
        return "  ".repeat(depth);
    }
}

// Leaf
class File extends FileSystemComponent {
    private long size;
    private static final Logger logger = Logger.getLogger(File.class.getName());

    public File(String name, long size) {
        super(name);
        this.size = size;
    }

    @Override
    public void display(int depth) {
        System.out.println(getIndentation(depth) + "📄 " + name + " (" + size + " bytes)");
    }

    @Override
    public long getSize() {
        return size;
    }
}

// Composite
class Directory extends FileSystemComponent {
    private List<FileSystemComponent> children = new ArrayList<>();
    private static final Logger logger = Logger.getLogger(Directory.class.getName());

    public Directory(String name) {
        super(name);
    }

    public void addComponent(FileSystemComponent component) {
        children.add(component);
        component.setParent(this);
        logger.info("Added " + component.getName() + " to " + name);
    }

    public void removeComponent(FileSystemComponent component) {
        children.remove(component);
        logger.info("Removed " + component.getName() + " from " + name);
    }

    @Override
    public void display(int depth) {
        System.out.println(getIndentation(depth) + "📁 " + name + " (" + getSize() + " bytes)");
        for (FileSystemComponent component : children) {
            component.display(depth + 1);
        }
    }

    @Override
    public long getSize() {
        return children.stream().mapToLong(FileSystemComponent::getSize).sum();
    }

    public List<FileSystemComponent> getChildren() {
        return children;
    }
}

// File System Ex1plorer
public class FileSystemExplorer {
    private Directory root;
    private Directory currentDirectory;
    private static final Logger logger = Logger.getLogger(FileSystemExplorer.class.getName());

    public FileSystemExplorer() {
        root = new Directory("root");
        currentDirectory = root;
    }

    public void explore() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nCurrent Directory: " + getCurrentPath());
            System.out.println("1. List contents");
            System.out.println("2. Add file");
            System.out.println("3. Add directory");
            System.out.println("4. Delete item");
            System.out.println("5. Navigate to subdirectory");
            System.out.println("6. Navigate up");
            System.out.println("7. Exit");

            System.out.print("Enter your choice (1-7): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    currentDirectory.display(0);
                    break;
                case 2:
                    addFile(scanner);
                    break;
                case 3:
                    addDirectory(scanner);
                    break;
                case 4:
                    deleteItem(scanner);
                    break;
                case 5:
                    navigateToSubdirectory(scanner);
                    break;
                case 6:
                    navigateUp();
                    break;
                case 7:
                    System.out.println("Exiting File System Explorer. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addFile(Scanner scanner) {
        System.out.print("Enter file name: ");
        String fileName = scanner.nextLine();
        System.out.print("Enter file size (bytes): ");
        long fileSize = scanner.nextLong();
        currentDirectory.addComponent(new File(fileName, fileSize));
    }

    private void addDirectory(Scanner scanner) {
        System.out.print("Enter directory name: ");
        String dirName = scanner.nextLine();
        currentDirectory.addComponent(new Directory(dirName));
    }

    private void deleteItem(Scanner scanner) {
        System.out.print("Enter item name to delete: ");
        String itemName = scanner.nextLine();
        currentDirectory.getChildren().removeIf(component -> component.getName().equals(itemName));
    }

    private void navigateToSubdirectory(Scanner scanner) {
        System.out.print("Enter subdirectory name: ");
        String subDirName = scanner.nextLine();
        for (FileSystemComponent component : currentDirectory.getChildren()) {
            if (component instanceof Directory && component.getName().equals(subDirName)) {
                currentDirectory = (Directory) component;
                logger.info("Navigated to " + subDirName);
                return;
            }
        }
        System.out.println("Subdirectory not found.");
    }

    private void navigateUp() {
        if (currentDirectory.parent != null) {
            currentDirectory = (Directory) currentDirectory.parent;
            logger.info("Navigated up to " + currentDirectory.getName());
        } else {
            System.out.println("Already at root directory.");
        }
    }

    private String getCurrentPath() {
        List<String> path = new ArrayList<>();
        FileSystemComponent current = currentDirectory;
        while (current != null) {
            path.add(0, current.getName());
            current = current.parent;
        }
        return "/" + String.join("/", path);
    }

    public static void main(String[] args) {
        System.out.println("Welcome to the Interactive File System Explorer!");
        new FileSystemExplorer().explore();
    }
}