package ro.droptable.labproblems.service;

import org.junit.Before;
import org.junit.Test;
import ro.droptable.labproblems.domain.Assignment;
import ro.droptable.labproblems.domain.Problem;
import ro.droptable.labproblems.domain.Student;
import ro.droptable.labproblems.domain.validators.AssignmentValidator;
import ro.droptable.labproblems.domain.validators.ProblemValidator;
import ro.droptable.labproblems.domain.validators.StudentValidator;
import ro.droptable.labproblems.domain.validators.ValidatorException;
import ro.droptable.labproblems.repository.InMemoryRepository;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * Created by stefana on 3/12/2017.
 */
public class GeneralServiceTest {

    GeneralService g;
    Student st;
    Problem pr;

    @Before
    public void setUp() throws Exception {
        g = new GeneralService(
                new StudentService(new InMemoryRepository<>(new StudentValidator())),
                new ProblemService(new InMemoryRepository<>(new ProblemValidator())),
                new AssignmentService(new InMemoryRepository<>(new AssignmentValidator())));

        g.addStudent("1","alice", 222);
        g.addStudent("2", "bob", 222);
        g.addProblem("bla", "blabla");
        g.addProblem("w", "hy");

        st = g.findOneStudentByAttributes("1", "alice", 222).get();
        pr = g.findOneProblemByAttributes("bla", "blabla").get();

        g.addAssignment(st.getId(), pr.getId());

    }

    @Test
    public void reportStudentAverage() throws Exception {
        Student s2 = g.findOneStudentByAttributes("2", "bob", 222).get();
        Problem p2 = g.findOneProblemByAttributes("w", "hy").get();
        g.addAssignment(st.getId(), p2.getId());
        g.updateAssignment(g.findOneAssignmentByAttributes(st.getId(), p2.getId()).get().getId(), st.getId(), p2.getId(), 10);

        g.addAssignment(s2.getId(), p2.getId());
        g.updateAssignment(g.findOneAssignmentByAttributes(s2.getId(), p2.getId()).get().getId(), s2.getId(), p2.getId(), 5);

        g.addAssignment(s2.getId(), pr.getId());
        g.updateAssignment(g.findOneAssignmentByAttributes(s2.getId(), pr.getId()).get().getId(), s2.getId(), pr.getId(), 7);

        assertTrue(g.reportStudentAverage().get(st) == 5);
        assertTrue(g.reportStudentAverage().get(s2) == 6);
    }

    @Test
    public void filterLargestGroup() throws Exception {
        g.addStudent("3", "abc", 223);
        g.addStudent("4", "a", 223);
        g.addStudent("5", "b", 222);
        assertTrue(g.filterLargestGroup() == 222);
    }

    @Test
    public void addStudent() throws Exception {
        g.addStudent("3","alice", 222);
        assertTrue(g.findAllStudents().size() == 3);
        try {
            g.addStudent("3","alice", 200);
            assertTrue(false);
        } catch (ValidatorException e) {
            assertFalse(false);
        }
    }

    @Test
    public void findOneStudentById() throws Exception {
        assertTrue(st.equals(g.findOneStudentById(st.getId()).get()));
    }

    @Test
    public void findOneStudentByAttributes() throws Exception {
        assertTrue(st.getId() == g.findOneStudentByAttributes(st.getSerialNumber(), st.getName(), st.getGroup()).get().getId());
    }

    @Test
    public void findAllStudents() throws Exception {
        assertTrue(g.findAllStudents().size() == 2);
    }

    @Test
    public void updateStudent() throws Exception {
        g.updateStudent(st.getId(), "1","alice", 223);
        assertTrue(g.findOneStudentById(st.getId()).get().getGroup() == 223);
        try {
            g.updateStudent(st.getId(), "1","alice", 0);
            assertTrue(false);
        }catch(ValidatorException e){}
        assertTrue(g.findOneStudentById(st.getId()).get().getGroup() == 223);
    }

    @Test
    public void deleteStudent() throws Exception {
        g.deleteStudent(st.getId());
        assertTrue(g.findAllAssignments().size() == 0);
    }

    @Test
    public void filterStudentsByName() throws Exception {
        assertTrue(g.filterStudentsByName("al").size() == 1);
        assertTrue(g.filterStudentsByName("x").size() == 0);
    }

    @Test
    public void filterProblemsByName() throws Exception {
        assertTrue(g.filterProblemsByName("b").size() == 1);
        assertTrue(g.filterProblemsByName("x").size() == 0);
    }

    @Test
    public void filterAssignmentsByGrade() throws Exception {
        assertTrue(g.filterAssignmentsByGrade(0).size() == 1);
        assertTrue(g.filterAssignmentsByGrade(2).size() == 0);
    }

    @Test
    public void addProblem() throws Exception {
        g.addProblem("a","b");
        assertTrue(g.findAllProblems().size() == 3);
        try {
            g.addProblem("","a");
            assertTrue(false);
        } catch (ValidatorException e) {
            assertFalse(false);
        }
    }

    @Test
    public void findOneProblemById() throws Exception {
        assertTrue(g.findOneProblemById(pr.getId()).get().equals(pr));
    }

    @Test
    public void findOneProblemByAttributes() throws Exception {
        assertTrue(g.findOneProblemByAttributes(pr.getTitle(), pr.getDescription()).get().equals(pr));
    }

    @Test
    public void findAllProblems() throws Exception {
        assertTrue(g.findAllProblems().size() == 2);
    }

    @Test
    public void updateProblem() throws Exception {
        g.updateProblem(pr.getId(), "b", "b");
        assertTrue(g.findOneProblemById(pr.getId()).get().getTitle().equals("b"));
        try {
            g.updateProblem(pr.getId(), "", "b");
            assertTrue(false);
        }catch(ValidatorException e){}
        assertTrue(g.findOneProblemById(pr.getId()).get().getTitle().equals("b"));
    }

    @Test
    public void deleteProblem() throws Exception {
        g.deleteProblem(pr.getId());
        assertTrue(g.findAllAssignments().size() == 0);
    }

    @Test
    public void addAssignment() throws Exception {
        Student ns = new Student("1", "2", 333);
        Problem np = new Problem("1", "1");
        try{
            g.addAssignment(ns.getId(), np.getId());
            assertTrue(false);
        }catch(ValidatorException e){}
        g.addStudent(ns.getSerialNumber(), ns.getName(), ns.getGroup());
        g.addProblem(np.getTitle(), np.getDescription());
        g.addAssignment(
                g.findOneStudentByAttributes(ns.getSerialNumber(), ns.getName(), ns.getGroup()).get().getId(),
                g.findOneProblemByAttributes(np.getTitle(), np.getDescription()).get().getId());
        assertTrue(g.findAllAssignments().size() == 2);
    }

    @Test
    public void findOneAssignmentById() throws Exception {
    }

    @Test
    public void findOneAssignmentByAttributes() throws Exception {
        assertTrue(g.findOneAssignmentByAttributes(st.getId(), pr.getId()).get().getProblemId() == pr.getId());
    }

    @Test
    public void findAllAssignments() throws Exception {
        assertTrue(g.findAllAssignments().size() == 1);
    }

    @Test
    public void updateAssignment() throws Exception {
        Student ns = new Student("1", "2", 333);
        Problem np = new Problem("1", "1");
        try{
            g.updateAssignment(g.findOneAssignmentByAttributes(st.getId(), pr.getId()).get().getId(), ns.getId(), np.getId(), 1);
            assertTrue(false);
        }catch(ValidatorException e){}
        g.addStudent(ns.getSerialNumber(), ns.getName(), ns.getGroup());
        g.addProblem(np.getTitle(), np.getDescription());
        g.updateAssignment(g.findOneAssignmentByAttributes(st.getId(), pr.getId()).get().getId(),
                g.findOneStudentByAttributes(ns.getSerialNumber(), ns.getName(), ns.getGroup()).get().getId(),
                g.findOneProblemByAttributes(np.getTitle(), np.getDescription()).get().getId(), 1);
        assertTrue(g.findAllAssignments().size() == 1);
    }

    @Test
    public void deleteAssignment() throws Exception {
        g.deleteAssignment(g.findOneAssignmentByAttributes(st.getId(), pr.getId()).get().getId());
        assertTrue(g.findAllAssignments().size() == 0);
    }

}