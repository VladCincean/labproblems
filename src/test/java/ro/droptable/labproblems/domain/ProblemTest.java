package ro.droptable.labproblems.domain;

import ro.droptable.labproblems.domain.Problem;

import static org.junit.Assert.*;

/**
 * Created by stefana on 3/5/2017.
 */
public class ProblemTest {
    @org.junit.Test
    public void equals() throws Exception {
        Problem a = new Problem("a", "woot");
        Problem b = new Problem("a", "woot");
        Problem c = new Problem("b", "woot");
        Problem d = new Problem("a", "wootwoot");

        assertTrue(a.equals(b));
        assertFalse(a.equals(c));
        assertFalse(a.equals(d));
    }

}