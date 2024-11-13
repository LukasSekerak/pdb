package sk.upjs.gursky.pdb;

import static org.junit.Assert.fail;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ClusteredBPTreeTest {

	private static final File INDEX_FILE = new File("person.kl");
//	private ClusteredBPTree bptree;
	
	@Before
	public void setUp() throws Exception {
//		bptree = new ClusteredBPTree(Generator.GENERATED_FILE, INDEX_FILE);
	}

	@After
	public void tearDown() throws Exception {
//		bptree.close();
//      INDEX_FILE.delete();
	}

	@Test
	public void test() throws Exception {	
//		long time = System.currentTimeMillis();
//		ArrayList<PersonEntry> result = bptree.intervalQuery(new PersonStringKey("a"), new PersonStringKey("b999999999"));
//		time = System.currentTimeMillis() - time;
		
//		System.out.println(time);

//		assertTrue(result.size() > 0);
		fail("required method 'intervalQuery' not implemented");
	}
}
