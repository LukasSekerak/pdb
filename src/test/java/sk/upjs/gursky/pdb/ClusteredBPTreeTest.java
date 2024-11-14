package sk.upjs.gursky.pdb;

import static org.junit.Assert.assertTrue;
import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sk.upjs.gursky.bplustree.entries.BPObjectIntDouble;

public class ClusteredBPTreeTest {

	private static final File INDEX_FILE = new File("person.kl");
	private ClusteredBPTree bptree;

	@Before
	public void setUp() throws Exception {
		//bptree = ClusteredBPTree.createOneByOne();
		bptree = ClusteredBPTree.createByBulkLoading();
	}

	@After
	public void tearDown() throws Exception {
		bptree.close();
		ClusteredBPTree.INDEX_FILE.delete();
	}

	@Test
	public void test() throws Exception {
		long time = System.currentTimeMillis();

		PersonEntry prev = null;
		int toPrint = 50;
		for (PersonEntry entry : bptree) {
			System.out.println(entry);
			if (prev != null) {
				assertTrue(prev.compareTo(entry) <= 0);
				assertTrue(prev.getKey().compareTo(entry.getKey()) <= 0);
			}
			prev = entry;
			if (toPrint == 0) break;
			toPrint--;
		}

		System.out.println(time);

	}
}
