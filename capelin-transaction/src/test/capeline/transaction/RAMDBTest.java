package test.capeline.transaction;

import java.util.Arrays;
import java.util.List;
import junit.framework.TestCase;
import org.capelin.transaction.db.RAMDB;

public class RAMDBTest extends TestCase {
	public void test1() throws Exception {
		RAMDB db = new RAMDB();
		String term = "title";
		String[] v = { "book for arts", "book for science", "book for math","book for run","book for swim" };
		db.addDoc(term, Arrays.asList(v));
		List<String> list = db.search(term, TestUtils.buildQuery(term, "book"),	50);
		assertEquals(5,list.size());
		list = db.search(term, TestUtils.buildQuery(term, "swim"),	50);
		assertEquals(1,list.size());
	}
	public void test2() throws Exception {
		RAMDB db = new RAMDB();
		String term = "accessionNumber";
		String[] v = { "2008-001", "2008-002", "2008-003","2008-004","2008-011 ACT","2008" };
		//String[] v = { "book for arts", "book for science", "book for math","book for run","book for swim" };
		db.addDoc(term, Arrays.asList(v));
		List<String> list = db.search(term, TestUtils.paseQuery(term, "2008-00*"), 50);
		assertEquals(4,list.size());
		list = db.search(term, TestUtils.paseQuery(term, "2008-00*"), 2);
		assertEquals(2,list.size());
	}	
}
