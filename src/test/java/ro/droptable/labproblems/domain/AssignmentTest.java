package ro.droptable.labproblems.domain;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by stefana on 3/5/2017.
 */
public class AssignmentTest {
    @Test
    public void equals() throws Exception {
        Student a = new Student("1","alice", 222);
        Student b = new Student("1","alice", 222);
        Problem c = new Problem("woot", "woot");
        Problem d = new Problem("woot", "woot");
        Assignment a1 = new Assignment(a, c);
        Assignment a2  = new Assignment(b, d);
        assertFalse(a1.equals(a2));
        a2  = new Assignment(a, c);
        assertTrue(a1.equals(a2));
    }

}