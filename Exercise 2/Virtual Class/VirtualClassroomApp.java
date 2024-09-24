
import java.util.*;


interface Command {
    void execute();
}


class Classroom {
    private String name;
    private List<Assignment> assignments;
    private List<Student> students;

    public Classroom(String name) {
        this.name = name;
        this.assignments = new ArrayList<>();
        this.students = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public List<Student> getStudents() {
        return students;
    }

    public void addAssignment(Assignment assignment) {
        assignments.add(assignment);
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void notifyAssignmentSubmitted(String message) {
        System.out.println("Classroom [" + name + "] notified: " + message);
    }

    @Override
    public String toString() {
        return "Classroom: " + name + ", Students: " + students + ", Assignments: " + assignments;
    }
}

class Student {
    private String id;
    private String className;

    public Student(String id, String className) {
        this.id = id;
        this.className = className;
    }

    public String getId() {
        return id;
    }

    public String getClassName() {
        return className;
    }

    @Override
    public String toString() {
        return id;
    }
}

class Assignment {
    private String details;
    private String className;

    public Assignment(String details, String className) {
        this.details = details;
        this.className = className;
    }

    public String getDetails() {
        return details;
    }

    public String getClassName() {
        return className;
    }

    @Override
    public String toString() {
        return details;
    }
}

class ClassroomFactory {
    public Classroom createClassroom(String name) {
        return new Classroom(name);
    }
}

class StudentFactory {
    public Student createStudent(String id, String className) {
        return new Student(id, className);
    }
}

class AssignmentFactory {
    public Assignment createAssignment(String details, String className) {
        return new Assignment(details, className);
    }
}

class VirtualClassroomManager {
    private static VirtualClassroomManager instance;
    private List<Classroom> classrooms;
    private Map<String, Student> students;

    private VirtualClassroomManager() {
        classrooms = new ArrayList<>();
        students = new HashMap<>();
    }

    public static VirtualClassroomManager getInstance() {
        if (instance == null) {
            instance = new VirtualClassroomManager();
        }
        return instance;
    }

    public void addClassroom(Classroom classroom) {
        classrooms.add(classroom);
    }

    public void addStudent(Student student) {
        students.put(student.getId(), student);
        // Find and enroll student in the appropriate classroom
        for (Classroom classroom : classrooms) {
            if (classroom.getName().equals(student.getClassName())) {
                classroom.addStudent(student);
                break;
            }
        }
    }

    public void addAssignment(Assignment assignment) {
        for (Classroom classroom : classrooms) {
            if (classroom.getName().equals(assignment.getClassName())) {
                classroom.addAssignment(assignment);
                break;
            }
        }
    }

    public void submitAssignment(String studentId, String className, String assignmentDetails) {
        for (Classroom classroom : classrooms) {
            if (classroom.getName().equals(className)) {
                classroom.notifyAssignmentSubmitted("Assignment [" + assignmentDetails + "] submitted by Student [" + studentId + "]");
                return;
            }
        }
        System.out.println("Classroom not found for assignment submission.");
    }

    public List<Classroom> getClassrooms() {
        return classrooms;
    }
}

class AddClassroomCommand implements Command {
    private ClassroomFacade facade;
    private String className;

    public AddClassroomCommand(ClassroomFacade facade, String className) {
        this.facade = facade;
        this.className = className;
    }

    @Override
    public void execute() {
        facade.addClassroom(className);
        System.out.println("Classroom [" + className + "] has been created.");
        System.out.println(facade.getManager().getClassrooms());
    }
}

class AddStudentCommand implements Command {
    private ClassroomFacade facade;
    private String studentId;
    private String className;

    public AddStudentCommand(ClassroomFacade facade, String studentId, String className) {
        this.facade = facade;
        this.studentId = studentId;
        this.className = className;
    }

    @Override
    public void execute() {
        facade.addStudent(studentId, className);
        System.out.println("Student [" + studentId + "] has been enrolled in [" + className + "].");
        System.out.println(facade.getManager().getClassrooms());
    }
}

class ScheduleAssignmentCommand implements Command {
    private ClassroomFacade facade;
    private String className;
    private String assignmentDetails;

    public ScheduleAssignmentCommand(ClassroomFacade facade, String className, String assignmentDetails) {
        this.facade = facade;
        this.className = className;
        this.assignmentDetails = assignmentDetails;
    }

    @Override
    public void execute() {
        facade.scheduleAssignment(className, assignmentDetails);
        System.out.println("Assignment for [" + className + "] has been scheduled.");
        System.out.println(facade.getManager().getClassrooms());
    }
}

class SubmitAssignmentCommand implements Command {
    private VirtualClassroomManager manager;
    private String studentId;
    private String className;
    private String assignmentDetails;

    public SubmitAssignmentCommand(VirtualClassroomManager manager, String studentId, String className, String assignmentDetails) {
        this.manager = manager;
        this.studentId = studentId;
        this.className = className;
        this.assignmentDetails = assignmentDetails;
    }

    @Override
    public void execute() {
        manager.submitAssignment(studentId, className, assignmentDetails);
    }
}

class ClassroomFacade {
    private ClassroomFactory classroomFactory;
    private StudentFactory studentFactory;
    private AssignmentFactory assignmentFactory;
    private VirtualClassroomManager manager;

    public ClassroomFacade() {
        classroomFactory = new ClassroomFactory();
        studentFactory = new StudentFactory();
        assignmentFactory = new AssignmentFactory();
        manager = VirtualClassroomManager.getInstance();
    }

    public void addClassroom(String name) {
        Classroom classroom = classroomFactory.createClassroom(name);
        manager.addClassroom(classroom);
    }

    public void addStudent(String studentId, String className) {
        Student student = studentFactory.createStudent(studentId, className);
        manager.addStudent(student);
    }

    public void scheduleAssignment(String className, String assignmentDetails) {
        Assignment assignment = assignmentFactory.createAssignment(assignmentDetails, className);
        manager.addAssignment(assignment);
    }

    public VirtualClassroomManager getManager() {
        return manager;
    }
}


public class VirtualClassroomApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ClassroomFacade facade = new ClassroomFacade();
        VirtualClassroomManager manager = VirtualClassroomManager.getInstance();

        while (true) {
            System.out.print("Enter command: ");
            String input = scanner.nextLine();
            String[] parts = input.split(" ");
            String commandType = parts[0];

            Command command = null;
            switch (commandType) {
                case "add_classroom":
                    command = new AddClassroomCommand(facade, parts[1]);
                    break;
                case "add_student":
                    command = new AddStudentCommand(facade, parts[1], parts[2]);
                    break;
                case "schedule_assignment":
                    command = new ScheduleAssignmentCommand(facade, parts[1], parts[2]);
                    break;
                case "submit_assignment":
                    command = new SubmitAssignmentCommand(manager, parts[1], parts[2], parts[3]);
                    break;
                case "exit":
                    System.out.println("Exiting the application.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid command.");
                    continue;
            }

            if (command != null) {
                command.execute();
            }
        }
    }
}


