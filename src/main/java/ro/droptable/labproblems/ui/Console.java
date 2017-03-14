package ro.droptable.labproblems.ui;

import ro.droptable.labproblems.domain.validators.ValidatorException;
import ro.droptable.labproblems.service.AssignmentService;
import ro.droptable.labproblems.service.GeneralService;
import ro.droptable.labproblems.service.ProblemService;
import ro.droptable.labproblems.service.StudentService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by vlad on 04.03.2017.
 *
 * Class for working with a {@code Console} user interface
 * It allows:
 *      - performing CRUD operations on {@code Student}s and {@code Problem}s
 *      - assigning {@code Problem}s to {@code Student}s; assigning grades
 *      - filtering data
 *      - generating reports
 */
public class Console {
    private GeneralService generalService;
    public Console(GeneralService generalService) {
        this.generalService = generalService;
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
                "3 - UPDATE Student\n" +
                "4 - DELETE Student\n" +
                "5 - READ a Problem\n" +
                "6 - PRINT all Problems\n" +
                "7 - UPDATE Problem\n" +
                "8 - DELETE Problem\n" +
                "9 - ASSIGN a Problem to a Student\n" +
                "10 - PRINT all Assignments\n" +
                "11 - UPDATE Assignment\n" +
                "12 - DELETE Assignment\n" +
                "13 - Filter Students by name\n" +
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
            case 3:
                updateStudent();
                break;
            case 4:
                deleteStudent();
                break;
            case 5:
                readProblem();
                break;
            case 6:
                printAllProblems();
                break;
            case 7:
                updateProblem();
                break;
            case 8:
                deleteProblem();
                break;
            case 9:
                assignProblemToStudent();
                break;
            case 10:
                printAllAssignments();
                break;
            case 11:
                updateAssignment();
                break;
            case 12:
                deleteAssignment();
                break;
            case 13:
                filterStudents();
                break;
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
                generalService.addStudent(serialNumber, name, group);
            } catch (ValidatorException e) {
                System.out.println("Input Student is invalid");
                System.err.println(e.toString());
            }
        } catch (IOException e) {
            e.printStackTrace(); // TODO: do something else
        }
    }

    private void updateStudent() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.println("id = ");
            long id = Long.parseLong(bufferedReader.readLine().trim());
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
                generalService.updateStudent(id, serialNumber, name, group);
            } catch (ValidatorException e) {
                System.out.println("Input Student is invalid");
                System.err.println(e.toString());
            }
        } catch (IOException e) {
            e.printStackTrace(); // TODO: do something else
        }
    }

    private void deleteStudent() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.println("id = ");
            long id = Long.parseLong(bufferedReader.readLine().trim());
            try {
                generalService.deleteStudent(id);
            } catch (ValidatorException e) {
                System.out.println("Input Student is invalid");
                System.err.println(e.toString());
            }
        } catch (IOException e) {
            e.printStackTrace(); // TODO: do something else
        }
    }

    private void readProblem() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.print("title = ");
            String title = bufferedReader.readLine().trim();

            System.out.print("description = ");
            String description = bufferedReader.readLine().trim();

            try {
                generalService.addProblem(title, description);
            } catch (ValidatorException e) {
                System.out.println("Input Problem is invalid");
                System.err.println(e.toString());
            }

        } catch (IOException e) {
            e.printStackTrace(); // TODO: do something else
        }
    }

    private void updateProblem() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.print("title = ");
            String title = bufferedReader.readLine().trim();

            System.out.print("description = ");
            String description = bufferedReader.readLine().trim();

            System.out.println("id = ");
            long id = Long.parseLong(bufferedReader.readLine().trim());

            try {
                generalService.updateProblem(id, title, description);
            } catch (ValidatorException e) {
                System.out.println("Input Problem is invalid");
                System.err.println(e.toString());
            }

        } catch (IOException e) {
            e.printStackTrace(); // TODO: do something else
        }
    }

    private void deleteProblem() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.println("id = ");
            long id = Long.parseLong(bufferedReader.readLine().trim());

            try {
                generalService.deleteProblem(id);
            } catch (ValidatorException e) {
                System.out.println("Input Problem is invalid");
                System.err.println(e.toString());
            }

        } catch (IOException e) {
            e.printStackTrace(); // TODO: do something else
        }
    }

    private void assignProblemToStudent() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        try {
            printAllStudents();
            System.out.print("studentId = ");
            long studentId = Long.parseLong(bufferedReader.readLine().trim());

            printAllProblems();
            System.out.print("problemId = ");
            long problemId = Long.parseLong(bufferedReader.readLine().trim());

            try {
                generalService.addAssignment(studentId, problemId);
            } catch (ValidatorException e) {
                System.out.println("Input Assignment is invalid");
                System.err.println(e.toString());
            } catch (NoSuchElementException e){
                System.out.println("Input Assignment is invalid");
                System.err.println(e.getMessage());
            }

        } catch (IOException e) {
            e.printStackTrace(); // TODO: do something else
        }
    }

    private void updateAssignment() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.print("id = ");
            long id = Long.parseLong(bufferedReader.readLine().trim());

            printAllStudents();
            System.out.print("studentId = ");
            long studentId = Long.parseLong(bufferedReader.readLine().trim());

            printAllProblems();
            System.out.print("problemId = ");
            long problemId = Long.parseLong(bufferedReader.readLine().trim());

            System.out.print("grade = ");
            int grade;
            try {
                grade = Integer.parseInt(bufferedReader.readLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. No valid integer given.");
                return;
            }

            try {
                generalService.updateAssignment(id, studentId, problemId, grade);
            } catch (ValidatorException | NoSuchElementException e) {
                System.out.println("Input Assignment is invalid");
                System.err.println(e.toString());
            }

        } catch (IOException e) {
            e.printStackTrace(); // TODO: do something else
        }
    }

    private void deleteAssignment() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.print("id = ");
            long id = Long.parseLong(bufferedReader.readLine().trim());
            try {
                generalService.deleteAssignment(id);
            } catch (ValidatorException e) {
                System.out.println("Input Assignment is invalid");
                System.err.println(e.toString());
            }

        } catch (IOException e) {
            e.printStackTrace(); // TODO: do something else
        }
    }

    private void filterStudents(){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.print("string = ");
            String cont = bufferedReader.readLine().trim();
            System.out.println(generalService.filterStudentsByName(cont));

        } catch (IOException e) {
            e.printStackTrace(); // TODO: do something else
        }
    }
    private void printAllStudents() {
        generalService.findAllStudents().forEach(System.out::println);
    }

    private void printAllProblems() {
        generalService.findAllProblems().forEach(System.out::println);
    }

    private void printAllAssignments() {
        generalService.findAllAssignments().forEach(System.out::println);
    }
}
