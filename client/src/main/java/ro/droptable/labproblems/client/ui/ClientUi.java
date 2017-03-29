package ro.droptable.labproblems.client.ui;

import ro.droptable.labproblems.client.service.AssignmentServiceClient;
import ro.droptable.labproblems.client.service.ProblemServiceClient;
import ro.droptable.labproblems.client.service.StudentServiceClient;
import ro.droptable.labproblems.common.domain.validators.ValidatorException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

/**
 * Created by vlad on 27.03.2017.
 *
 * Class for working with a console user interface
 * It allows:
 *      - performing CRUD operations on {@code Student}s, {@code Problem}s and {@code Assignment}s
 *      - assigning {@code Problem}s to {@code Student}s; assigning grades
 *      - filtering data
 *      - generating reports
 */
public class ClientUi {
    private StudentServiceClient studentService;
    private ProblemServiceClient problemService;
    private AssignmentServiceClient assignmentService;
    private final String menu = "\nAvailable commands:\n" +
            "------ Student CRUD ------\n" +
            "1 - READ a Student\n" +
            "2 - PRINT ALL Students\n" +
            "3 - DELETE a Student\n" +
            "4 - UPDATE a Student\n" +
            "------ Problem CRUD ------\n" +
            "5 - READ a Problem\n" +
            "6 - PRINT ALL Problems\n" +
            "7 - DELETE a Problem\n" +
            "8 - UPDATE a Problem\n" +
            "----- Assignment CRUD ----\n" +
            "9 - Assign a Problem to a Student\n" +
            "10 - PRINT ALL Assignments\n" +
            "11 - DELETE an Assignment\n" +
            "12 - UPDATE an Assignment\n" +
            "---------- Filter --------\n" +
            "13 - Filter Students by name\n" +
            "14 - Filter Problems by title\n" +
            "15 - Filter Assignments by grade\n" +
            "16 - Filter largest group\n" +
            "--------- Reports --------\n" +
            "17 - Report Student average grade\n" +
            "--------------------------\n" +
            "0 - EXIT\n";

    public ClientUi(
            StudentServiceClient studentService,
            ProblemServiceClient problemService,
            AssignmentServiceClient assignmentService
    ) {
        this.studentService = studentService;
        this.problemService = problemService;
        this.assignmentService = assignmentService;
    }

    public void run() {
        while (true) {
            mainMenu();
        }
    }

    private void mainMenu() {
        Scanner scanner = new Scanner(System.in);

        System.out.println(menu);

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
                deleteStudent();
                break;
            case 4:
                updateStudent();
                break;
            case 5:
                readProblem();
                break;
            case 6:
                printAllProblems();
                break;
            case 7:
                deleteProblem();
                break;
            case 8:
                updateProblem();
                break;
            case 9:
                assignProblemToStudent();
                break;
            case 10:
                printAllAssignments();
                break;
            case 11:
                deleteAssignment();
                break;
            case 12:
                updateAssignment();
                break;
            case 13:
                filterStudents();
                break;
            case 14:
                filterProblems();
                break;
            case 15:
                filterAssignments();
                break;
            case 16:
                filterLargestGroup();
                break;
            case 17:
                reportStudentAverageGrade();
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
            System.out.print("id  = ");
            Long id = null;
            try {
                id = Long.valueOf(bufferedReader.readLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. No valid id (Long) given.");
                return;
            }

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
                studentService.addStudent(
                        id.toString() + "," +
                        serialNumber + "," +
                        name + "," +
                        Integer.toString(group));
            } catch (ValidatorException e) {
                System.out.println("Input Student is invalid");
                System.err.println(e.toString());
            }
        } catch (IOException e) {
            e.printStackTrace(); // TODO: do something else
        }
    }

    private void printAllStudents() {
        // maybe TODO: refa intr-un mod mai elegant
        CompletableFuture<String> students = studentService.findAllStudents("");

        students.thenAccept(System.out::println);

//        try {
//            System.out.println(students.get()); // TODO: non-blocking
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }
    }

    private void deleteStudent() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.print("id = ");
            Long id = null;
            try {
                id = Long.valueOf(bufferedReader.readLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. No valid id (Long) given.");
                return;
            }

            try {
                studentService.deleteStudent(id.toString());
            } catch (ValidatorException e) {
                System.out.println("Input ID is invalid");
                System.err.println(e.toString());
            }
        } catch (IOException e) {
            e.printStackTrace(); // TODO: do something else
        }
    }

    private void updateStudent() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.print("id = ");
            Long id = null;
            try {
                id = Long.valueOf(bufferedReader.readLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. No valid id (Long) given.");
                return;
            }

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
                studentService.updateStudent(
                        id.toString() + "," +
                        serialNumber + "," +
                        name + "," +
                        Integer.toString(group)
                );
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
            System.out.print("id = ");
            Long id = null;
            try {
                id = Long.valueOf(bufferedReader.readLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. No valid id (Long) given.");
                return;
            }
            
            System.out.print("title = ");
            String title = bufferedReader.readLine().trim();

            System.out.print("description = ");
            String description = bufferedReader.readLine().trim();

            try {
                problemService.addProblem(id.toString()+","+ title+","+description);
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

            System.out.print("id = ");
            Long id = Long.parseLong(bufferedReader.readLine().trim());

            try {
                problemService.updateProblem(title+","+description+","+id.toString());
            } catch (ValidatorException e) {
                System.out.println("Input Problem is invalid");
                System.err.println(e.toString());
            }

        } catch (IOException e) {
            e.printStackTrace(); // TODO: do something else
        }
    }
    
    private void printAllProblems() {
        CompletableFuture<String> problems = problemService.findAllProblems("");
        problems.thenAccept(System.out::println);

//        try {
//            System.out.println(problems.get()); // TODO: non-blocking
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }
    }
    private void deleteProblem() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.print("id = ");
            Long id = Long.parseLong(bufferedReader.readLine().trim());

            try {
                problemService.deleteProblem(id.toString());
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
            System.out.print("id = ");
            Long id = Long.parseLong(bufferedReader.readLine().trim());

            printAllStudents();
            System.out.print("studentId = ");
            Long studentId = Long.parseLong(bufferedReader.readLine().trim());

            printAllProblems();
            System.out.print("problemId = ");
            Long problemId = Long.parseLong(bufferedReader.readLine().trim());

            try {
                assignmentService.addAssignment(id.toString()+","+studentId.toString()+","+problemId.toString());
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
            long assignmentId = Long.parseLong(bufferedReader.readLine().trim());

            printAllProblems();
            System.out.print("problemId = ");
            long problemId = Long.parseLong(bufferedReader.readLine().trim());

            System.out.print("grade = ");
            Double grade;
            try {
                grade = Double.valueOf(bufferedReader.readLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. No valid integer given.");
                return;
            }

            try {
                assignmentService.updateAssignment(id +","+ assignmentId+","+problemId+","+grade);
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
            Long id = Long.parseLong(bufferedReader.readLine().trim());
            try {
                assignmentService.deleteAssignment(id.toString());
            } catch (ValidatorException e) {
                System.out.println("Input Assignment is invalid");
                System.err.println(e.toString());
            }

        } catch (IOException e) {
            e.printStackTrace(); // TODO: do something else
        }
    }

    private void printAllAssignments() {
        CompletableFuture<String> assignments = assignmentService.findAllAssignments("");
        assignments.thenAccept(System.out::println);

//        try {
//            System.out.println(assignments.get()); // TODO: non-blocking
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }
    }

    private void filterStudents(){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.print("string = ");
            String cont = bufferedReader.readLine().trim();
            studentService.filterStudentsByName(cont)
                    .thenAccept(System.out::println);

//            System.out.println(studentService.filterStudentsByName(cont).get());

        } catch (IOException e) {
            e.printStackTrace(); // TODO: do something else
        }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
    }

    private void filterProblems(){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.print("string = ");
            String cont = bufferedReader.readLine().trim();
            problemService.filterProblemsByTitle(cont)
                    .thenAccept(System.out::println);

//            System.out.println(problemService.filterProblemsByTitle(cont).get());

        } catch (IOException e) {
            e.printStackTrace(); // TODO: do something else
        }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
    }

    private void filterAssignments(){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("grade = ");
        Double grade;
        try {
            grade = Double.valueOf(bufferedReader.readLine().trim());
        } catch (NumberFormatException | IOException e) {
            System.out.println("Invalid input. No valid integer given.");
            return;
        }
        assignmentService.filterAssignmentsByGrade(grade.toString())
                .thenAccept(System.out::println);
//        System.out.println(assignmentService.filterAssignmentsByGrade(grade.toString()));
    }

    private void filterLargestGroup(){
        studentService.filterLargestGroup()
                .thenAccept(System.out::println);

//        try {
//            System.out.println(studentService.filterLargestGroup().get());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
    }

    private void reportStudentAverageGrade(){
        studentService.reportStudentAverage()
                .thenAccept(System.out::println);

//        try {
//            System.out.println(studentService.reportStudentAverage().get());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
    }
}
