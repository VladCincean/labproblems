package ro.droptable.labproblems.domain;

import ro.droptable.labproblems.domain.Problem;
import ro.droptable.labproblems.domain.Student;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by vlad on 04.03.2017.
 */
public class StudentTest {
    @org.junit.Test
    public void equals() throws Exception {
        Student a = new Student("1","alice", 222);
        Student b = new Student("1","alice", 222);
        Student c = new Student("2","alice", 222);
        Student d = new Student("1","alic", 222);
        assertTrue(a.equals(b));
        assertFalse(a.equals(c));
        assertFalse(a.equals(d));
    }
}
