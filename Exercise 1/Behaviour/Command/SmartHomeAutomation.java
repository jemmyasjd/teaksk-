import java.util.Scanner;

public class SmartHomeAutomation {
    public static void main(String[] args) {
        RemoteController remote = new RemoteController();
        Light livingRoomLight = new Light();

        Command lightOn = new LightOnCommand(livingRoomLight);
        Command lightOff = new LightOffCommand(livingRoomLight);

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nEnter a command: ");
            System.out.println("1. Turn on the light");
            System.out.println("2. Turn off the light");
            System.out.println("3. Undo last action");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    remote.setCommand(lightOn);
                    remote.pressButton();
                    break;

                case 2:
                    remote.setCommand(lightOff);
                    remote.pressButton();
                    break;

                case 3:
                    remote.pressUndo();
                    break;

                case 4:
                    exit = true;
                    System.out.println("Exiting Smart Home Automation System.");
                    break;

                default:
                    System.out.println("Invalid choice! Please enter a valid option.");
            }
        }

        scanner.close();
    }
}