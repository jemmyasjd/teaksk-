import java.util.*;
import java.util.logging.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Product: Document
class Document {
    private final String title;
    private final String header;
    private final String footer;
    private final List<String> sections;
    private final Map<String, String> metadata;
    private final List<String> images;
    private final String format;

    private Document(DocumentBuilder builder) {
        this.title = builder.title;
        this.header = builder.header;
        this.footer = builder.footer;
        this.sections = builder.sections;
        this.metadata = builder.metadata;
        this.images = builder.images;
        this.format = builder.format;
    }

    public void printDocument() {
        System.out.println("Title: " + title);
        System.out.println("Header: " + header);
        System.out.println("Footer: " + footer);
        System.out.println("Sections:");
        for (String section : sections) {
            System.out.println("- " + section);
        }
        System.out.println("Metadata: " + metadata);
        System.out.println("Images: " + images);
        System.out.println("Format: " + format);
    }

    @Override
    public String toString() {
        return "Document{" +
                "title='" + title + '\'' +
                ", header='" + header + '\'' +
                ", footer='" + footer + '\'' +
                ", sections=" + sections +
                ", metadata=" + metadata +
                ", images=" + images +
                ", format='" + format + '\'' +
                '}';
    }

    // Builder
    static class DocumentBuilder {
        private String title;
        private String header;
        private String footer;
        private List<String> sections = new ArrayList<>();
        private Map<String, String> metadata = new HashMap<>();
        private List<String> images = new ArrayList<>();
        private String format;
        private static final Logger logger = Logger.getLogger(DocumentBuilder.class.getName());

        public DocumentBuilder(String title) {
            this.title = title;
        }

        public DocumentBuilder addHeader(String header) {
            this.header = header;
            return this;
        }

        public DocumentBuilder addFooter(String footer) {
            this.footer = footer;
            return this;
        }

        public DocumentBuilder addSection(String section) {
            this.sections.add(section);
            return this;
        }

        public DocumentBuilder addMetadata(String key, String value) {
            this.metadata.put(key, value);
            return this;
        }

        public DocumentBuilder addImage(String imagePath) {
            this.images.add(imagePath);
            return this;
        }

        public DocumentBuilder setFormat(String format) {
            this.format = format;
            return this;
        }

        public Document build() {
            logger.info("Building document: " + title);
            validateDocument();
            return new Document(this);
        }

        private void validateDocument() {
            List<String> errors = new ArrayList<>();

            if (title == null || title.isEmpty()) {
                errors.add("Title is required");
            }
            if (sections.isEmpty()) {
                errors.add("Document must have at least one section");
            }
            if (format == null || format.isEmpty()) {
                errors.add("Document format must be specified");
            }

            if (!errors.isEmpty()) {
                throw new IllegalStateException("Invalid document: " + String.join(", ", errors));
            }
        }
    }
}

// Director: DocumentGenerator
class DocumentGenerator {
    private static final Logger logger = Logger.getLogger(DocumentGenerator.class.getName());

    public Document createBasicDocument(String title, String content) {
        logger.info("Creating basic document: " + title);
        return new Document.DocumentBuilder(title)
                .addSection(content)
                .setFormat("txt")
                .build();
    }

    public Document createDetailedReport(String title, List<String> sections, Map<String, String> metadata) {
        logger.info("Creating detailed report: " + title);
        Document.DocumentBuilder builder = new Document.DocumentBuilder(title)
                .addHeader("Confidential Report")
                .addFooter("Page {page} of {total}")
                .setFormat("pdf");

        for (String section : sections) {
            builder.addSection(section);
        }

        for (Map.Entry<String, String> entry : metadata.entrySet()) {
            builder.addMetadata(entry.getKey(), entry.getValue());
        }

        builder.addMetadata("Generated", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        return builder.build();
    }

    public Document createRichMediaPresentation(String title, List<String> sections, List<String> imagePaths) {
        logger.info("Creating rich media presentation: " + title);
        Document.DocumentBuilder builder = new Document.DocumentBuilder(title)
                .addHeader("Interactive Presentation")
                .addFooter("© " + LocalDateTime.now().getYear() + " Our Company")
                .setFormat("html");

        for (String section : sections) {
            builder.addSection(section);
        }

        for (String imagePath : imagePaths) {
            builder.addImage(imagePath);
        }

        builder.addMetadata("Author", System.getProperty("user.name"));
        builder.addMetadata("Created", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        return builder.build();
    }
}

// Main class to demonstrate dynamic input and document printing
public class DocumentGeneratorDemo {
    private static final Logger logger = Logger.getLogger(DocumentGeneratorDemo.class.getName());
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        DocumentGenerator generator = new DocumentGenerator();

        System.out.println("Choose document type (1: Basic, 2: Detailed, 3: Rich Media): ");
        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        switch (choice) {
            case 1:
                createBasicDocument(generator);
                break;
            case 2:
                createDetailedReport(generator);
                break;
            case 3:
                createRichMediaPresentation(generator);
                break;
            default:
                System.out.println("Invalid choice!");
                break;
        }
    }

    private static void createBasicDocument(DocumentGenerator generator) {
        System.out.println("Enter document title: ");
        String title = scanner.nextLine();

        System.out.println("Enter document content: ");
        String content = scanner.nextLine();

        Document basicDoc = generator.createBasicDocument(title, content);
        System.out.println("Document created successfully!");
        basicDoc.printDocument();
    }

    private static void createDetailedReport(DocumentGenerator generator) {
        System.out.println("Enter document title: ");
        String title = scanner.nextLine();

        System.out.println("Enter number of sections: ");
        int sectionCount = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        List<String> sections = new ArrayList<>();
        for (int i = 0; i < sectionCount; i++) {
            System.out.println("Enter section " + (i + 1) + ": ");
            sections.add(scanner.nextLine());
        }

        System.out.println("Enter number of metadata entries: ");
        int metadataCount = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        Map<String, String> metadata = new HashMap<>();
        for (int i = 0; i < metadataCount; i++) {
            System.out.println("Enter metadata key: ");
            String key = scanner.nextLine();
            System.out.println("Enter metadata value: ");
            String value = scanner.nextLine();
            metadata.put(key, value);
        }

        Document detailedReport = generator.createDetailedReport(title, sections, metadata);
        System.out.println("Detailed Report created successfully!");
        detailedReport.printDocument();
    }

    private static void createRichMediaPresentation(DocumentGenerator generator) {
        System.out.println("Enter document title: ");
        String title = scanner.nextLine();

        System.out.println("Enter number of sections: ");
        int sectionCount = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        List<String> sections = new ArrayList<>();
        for (int i = 0; i < sectionCount; i++) {
            System.out.println("Enter section " + (i + 1) + ": ");
            sections.add(scanner.nextLine());
        }

        System.out.println("Enter number of images: ");
        int imageCount = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        List<String> images = new ArrayList<>();
        for (int i = 0; i < imageCount; i++) {
            System.out.println("Enter image path " + (i + 1) + ": ");
            images.add(scanner.nextLine());
        }

        Document richMediaPresentation = generator.createRichMediaPresentation(title, sections, images);
        System.out.println("Rich Media Presentation created successfully!");
        richMediaPresentation.printDocument();
    }
}
