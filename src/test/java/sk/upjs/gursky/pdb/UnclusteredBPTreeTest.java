package sk.upjs.gursky.pdb;

import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UnclusteredBPTreeTest {

	private UnclusteredBPTree bptree;

	@Before
	public void setUp() throws Exception {
		bptree = UnclusteredBPTree.createByBulkLoading();
	}

	@After
	public void tearDown() throws Exception {
		bptree.close();
		UnclusteredBPTree.INDEX_FILE.delete();
	}

	@Test
	public void test() throws Exception {
//		long time = System.currentTimeMillis();
//		List<SurnameAndOffsetEntry> result = bptree.intervalQuery(new PersonStringKey("a"), new PersonStringKey("b999999999"));
//		time = System.currentTimeMillis() - time;
//
//		System.out.println("Interval unclustered: " + time / 1_000_000 + " ms");
//
//		for (int i = 0; i < 20; i++) {
//			System.out.println(result.get(i));
//		}
//
//		assertTrue(result.size() > 0);

		List<PersonEntry> result = bptree.unclusteredIntervalQuery(new PersonStringKey("a"), new PersonStringKey("b999999999"));

		for (int i = 0; i < 20; i++) {
			System.out.println(result.get(i));
		}

	}
}
