package ro.droptable.labproblems.ui;

import ro.droptable.labproblems.domain.validators.ValidatorException;
import ro.droptable.labproblems.service.AssignmentService;
import ro.droptable.labproblems.service.ProblemService;
import ro.droptable.labproblems.service.StudentService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Created by vlad on 04.03.2017.
 */
public class Console {
    private StudentService studentService;
    private ProblemService problemService;
    private AssignmentService assignmentService;

    public Console(StudentService studentService,
                   ProblemService problemService,
                   AssignmentService assignmentService
    ) {
        this.studentService = studentService;
        this.problemService = problemService;
        this.assignmentService = assignmentService;
    }

    // TODO: implement the rest

    public void run() {
        while (true) {
            mainMenu();
        }
    }

    private void mainMenu() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nAvailable commands:\n" +
                "1 - READ a Student\n" +
                "2 - PRINT all Students\n" +
//                "3 - READ a Problem\n" +
//                "4 - PRINT all Problems\n" +
//                "5 - ASSIGN a Problem to a Student\n" +
//                "6 - PRINT all Assignments\n" +
                "0 - EXIT\n");

        System.out.print("Your option: ");
        int option = scanner.nextInt();
        switch (option) {
            case 1:
                readStudent();
                break;
            case 2:
                printAllStudents();
                break;
//            case 3:
//                readProblem();
//                break;
//            case 4:
//                printAllProblems();
//                break;
//            case 5:
//                assignProblemToStudent();
//                break;
//            case 6:
//                printAllAssignments();
//                break;
            case 0:
                System.out.println("Bye!");
                System.exit(0);
                break;
            default:
                System.out.println("Wrong command!");
                break;
        }
    }

    private void readStudent() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.print("serial number = ");
            String serialNumber = bufferedReader.readLine().trim();

            System.out.print("name = ");
            String name = bufferedReader.readLine().trim();

            System.out.print("group = ");
            int group;
            try {
                group = Integer.parseInt(bufferedReader.readLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. No valid integer given.");
                return;
            }

            try {
                studentService.add(serialNumber, name, group);
            } catch (ValidatorException e) {
                System.out.println("Input Student is invalid");
                System.err.println(e.toString());
            }

//            // DEBUG; TODO: remove this
//            System.out.println("serialNumber: " + serialNumber);
//            System.out.println("name: " + name);
//            System.out.println("group: " + group);
        } catch (IOException e) {
            e.printStackTrace(); // TODO: do something else
        }
    }

    private void readProblem() {
        // TODO: implement this
    }

    private void assignProblemToStudent() {
        // TODO: implement this
    }

    private void printAllStudents() {
        studentService.getAll().forEach(System.out::println);
    }

    private void printAllProblems() {
        problemService.getAll().forEach(System.out::println);
    }

    private void printAllAssignments() {
        assignmentService.getAll().forEach(System.out::println);
    }
}
