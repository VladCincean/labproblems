package labproblems.domain;

import ro.droptable.labproblems.domain.Problem;

import static org.junit.Assert.*;

/**
 * Created by stefana on 3/5/2017.
 */
public class ProblemTest {
    @org.junit.Test
    public void equals() throws Exception {
        Problem a = new Problem((long)1, "woot");
        Problem b = new Problem((long)1, "woot");
        Problem c = new Problem((long)2, "woot");
        Problem d = new Problem((long)1, "wootwoot");

        assertTrue(a.equals(b));
        assertFalse(a.equals(c));
        assertFalse(a.equals(d));
    }

}