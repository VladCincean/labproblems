package ro.droptable.labproblems.ui;

import ro.droptable.labproblems.domain.Assignment;
import ro.droptable.labproblems.domain.Problem;
import ro.droptable.labproblems.domain.Student;
import ro.droptable.labproblems.service.AssignmentService;
import ro.droptable.labproblems.service.ProblemService;
import ro.droptable.labproblems.service.Service;
import ro.droptable.labproblems.service.StudentService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

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
        readStudent();
        printAllStudents();
    }

    private void readStudent() {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.print("serial number = ");
            String serialNumber = bufferedReader.readLine().trim();

            System.out.print("name = ");
            String name = bufferedReader.readLine().trim();

            System.out.print("group = ");
            int group = Integer.parseInt(bufferedReader.readLine().trim());

            studentService.add(serialNumber, name, group);

            // DEBUG; TODO: remove this
            System.out.println("serialNumber: " + serialNumber);
            System.out.println("name: " + name);
            System.out.println("group: " + group);
        } catch (IOException e) {
            e.printStackTrace(); // TODO: do something else
        }
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
