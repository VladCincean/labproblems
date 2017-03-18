package ro.droptable.labproblems;

import ro.droptable.labproblems.domain.Assignment;
import ro.droptable.labproblems.domain.Problem;
import ro.droptable.labproblems.domain.Student;
import ro.droptable.labproblems.domain.validators.AssignmentValidator;
import ro.droptable.labproblems.domain.validators.ProblemValidator;
import ro.droptable.labproblems.domain.validators.StudentValidator;
import ro.droptable.labproblems.repository.*;
import ro.droptable.labproblems.service.*;
import ro.droptable.labproblems.ui.Console;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Scanner;

/**
 * Created by vlad on 04.03.2017.
 */
public class Main {
    private static Repository<Long, Student> studentRepository;
    private static Repository<Long, Problem> problemRepository;
    private static Repository<Long, Assignment> assignmentRepository;

    private static StudentService studentService;
    private static ProblemService problemService;
    private static AssignmentService assignmentService;
    private static GeneralService generalService;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        printOptions();
        while (true) {
            System.out.print("Your option: ");
            while(!scanner.hasNextInt()) {
                System.out.println("Wrong command!");
                System.out.print("Your option: ");
            }
            int opt = scanner.nextInt();
            switch (opt) {
                case 1:
                    initInMemory();
                    break;
                case 2:
                    initFile();
                    break;
                case 3:
                    initXml();
                    break;
                case 4:
//                    initDb();
                    break;
                case 0:
                    System.exit(0);
                default:
                    System.out.println("Wrong command!");
                    break;
            }
            if (1 <= opt && opt <= 2) { // TODO: change to 'opt <= 4' once the db is implemented
                break;
            }
        }

        Console console = new Console(generalService);
        console.run();
    }

    private static void printOptions() {
        System.out.println(
                "1. in-memory persistence\n" +
                "2. file persistence\n" +
                "3. XML persistence\n" +
                "4. DB persistence\n" +
                "0. EXIT\n"
        );
    }

    private static void initInMemory() {
        studentRepository = new InMemoryRepository<>(new StudentValidator());
        problemRepository = new InMemoryRepository<>(new ProblemValidator());
        assignmentRepository = new InMemoryRepository<>(new AssignmentValidator());

        initServices();
    }

    private static void initFile() {
        Scanner scanner = new Scanner(System.in);

        final String path = "src/main/resources/";

        System.out.print("Students file [default: students.txt]: ");
        String studentsFile = scanner.nextLine().trim();
        studentsFile = studentsFile.equals("") ? "students.txt" : studentsFile;

        System.out.print("Problems file [default: problems.txt]: ");
        String problemsFile = scanner.nextLine().trim();
        problemsFile = problemsFile.equals("") ? "problems.txt" : problemsFile;

        System.out.print("Assignments file [default: assignments.txt]: ");
        String assignmentsFile = scanner.nextLine().trim();
        assignmentsFile = assignmentsFile.equals("") ? "assignments.txt" : assignmentsFile;

        studentRepository = new StudentFileRepository(new StudentValidator(), path + studentsFile);
        problemRepository = new ProblemFileRepository(new ProblemValidator(), path + problemsFile);
        assignmentRepository = new AssignmentFileRepository(new AssignmentValidator(), path + assignmentsFile);

        initServices();
    }

    private static void initXml() {
        Scanner scanner = new Scanner(System.in);

        final String path = "src/main/resources/";

        System.out.print("Students file [default: students.xml]: ");
        String studentsFile = scanner.nextLine().trim();
        studentsFile = studentsFile.equals("") ? "students.xml" : studentsFile;

        System.out.print("Problems file [default: problems.xml]: ");
        String problemsFile = scanner.nextLine().trim();
        problemsFile = problemsFile.equals("") ? "problems.xml" : problemsFile;

        System.out.print("Assignments file [default: assignments.xml]: ");
        String assignmentsFile = scanner.nextLine().trim();
        assignmentsFile = assignmentsFile.equals("") ? "assignments.xml" : assignmentsFile;

        studentRepository = new StudentFileRepository(new StudentValidator(), path + studentsFile);
        problemRepository = new ProblemFileRepository(new ProblemValidator(), path + problemsFile);
        assignmentRepository = new AssignmentFileRepository(new AssignmentValidator(), path + assignmentsFile);

        initServices();
    }

    private static void initDb(String url, String username, String password) {
        throw new NotImplementedException();

//        studentRepository = new StudentDbRepository(new StudentValidator(), url, username, password);
//        problemRepository = new ProblemDbRepository(new ProblemValidator(), url, username, password);
//        assignmentRepository = new AssignmentDbRepository(new AssignmentValidator(), url, username, password);
//
//        initServices();
    }

    private static void initServices() {
        studentService = new StudentService(studentRepository);
        problemService = new ProblemService(problemRepository);
        assignmentService = new AssignmentService(assignmentRepository);
        generalService = new GeneralService(studentService, problemService, assignmentService);
    }
}
