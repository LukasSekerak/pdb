package sk.upjs.gursky.pdb;

import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class UnclusteredBPTreeSalaryTest {
    private UnclusteredBPTreeSalary bptree;

    @Before
    public void setUp() throws Exception {
        bptree = UnclusteredBPTreeSalary.createByBulkLoading();
    }

    @After
    public void tearDown() throws Exception {
        bptree.close();
        UnclusteredBPTreeSalary.INDEX_FILE.delete();
    }

    @Test
    public void test() throws Exception {
        List<PersonEntry> result = bptree.unclusteredIntervalQuery(new SalaryKey(400), new SalaryKey(1000));

        for (int i = 0; i < 100; i++) {
            System.out.println(result.get(i));
        }

    }
}
