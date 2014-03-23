package test.capeline.transaction;

import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.capelin.transaction.TxSpringContext;
import org.capelin.transaction.dao.RecordDao;
import org.springframework.orm.ObjectRetrievalFailureException;

import sample.capelin.transaction.SampleTxRecord;
/**
 * 
 * <a href="https://github.com/Joe23/capelin-opac/">Capelin-opac</a>
 * License: GNU AGPL v3 | http://www.gnu.org/licenses/agpl.html 
 * 
 * A unit test to make sure hibernate, hibernate search and lucene are work well together.
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 *
 */
public class DbTest extends TestCase {

	private RecordDao dao;
	
	private static final Log log = LogFactory.getLog(DbTest.class);

	protected void setUp() {
		// SessionFactory sf =
		// SpringContext.getBeanFactory().getBean(HibernateTransactionManager.class).getSessionFactory();
		dao = (RecordDao) TxSpringContext.getBeanFactory().getBean("sampleDao");
	}

	@SuppressWarnings("rawtypes")
	public void testAddSearch() throws InterruptedException {

		SampleTxRecord record1 = new SampleTxRecord();
		record1.setTitle("XYZ Google title1");
		record1.setAuthor("Test author1");
		record1.setSubject("Test subject1");
		dao.saveRecord(record1);
		
		//now database has one record.
		int id = record1.getId();
		assertTrue(0 != id);
		assertNotNull(dao.getRecord(id));

		// test if data is in DB and correct.
		SampleTxRecord record2 = (SampleTxRecord) dao.getRecord(id);
		assertTrue(record2.getTitle().equals(record1.getTitle()));
		assertTrue(record2.getAuthor().equals(record1.getAuthor()));
		assertTrue(record2.getSubject().equals(record1.getSubject()));

		// test searchPagedPagedpaged
		
		String [] results = {"id","title","author","subject" };
		Query q = buildQuery("title", "google");
		List list = dao.searchPaged(q, null, null,results,1).getResultList();
		assertEquals(1, list.size());
		
		q = buildQuery("title", "yahoo");
		log.info("New Query: " + q);
		list = dao.searchPaged(q, null, null,results,1).getResultList();
		

		assertEquals(0, list.size());

		// test update data.
		String newTitle = "XYZ Yahoo a title";
		record2.setTitle(newTitle);
		dao.saveRecord(record2);
		record1 = (SampleTxRecord) dao.getRecord(id);
		assertTrue(record1.getTitle().equals(newTitle));

		Thread.sleep(100);
		// test searchPagedPagedpaged

		q = buildQuery("title", "yahoo");
		list = dao.searchPaged(q, null, null,results,1).getResultList();
		assertEquals(1, list.size());

		q = buildQuery("title", "google");
		list = dao.searchPaged(q, null, null,results,1).getResultList();
		assertEquals(0, list.size());

		// add google
		record1 = new SampleTxRecord();
		record1.setTitle("XYZ Google title1");
		record1.setAuthor("Test author1");
		record1.setSubject("Test subject1");
		dao.saveRecord(record1);
		assertTrue(record1.getId() != record2.getId());
		assertTrue(record1.getId() != 0);
		assertEquals(record1.getSubject(), record2.getSubject());

		Thread.sleep(100);
		// searchPagedPagedpaged again
		q = buildQuery("title", "google");
		list = dao.searchPaged(q, null, null,results,1).getResultList();
		assertEquals(1, list.size());

		q = buildQuery("title", "xyz");
		list = dao.searchPaged(q, null, null,results,1).getResultList();
		assertEquals(2, list.size());

		Thread.sleep(100);
		// delete record
		dao.deleteRecord(id);

		// searchPagedPagedpaged again
		q = buildQuery("title", "yahoo");
		list = dao.searchPaged(q, null, null,results,1).getResultList();
		assertEquals(0, list.size());

		q = buildQuery("title", "xyz");
		list = dao.searchPaged(q, null, null,results,1).getResultList();
		assertEquals(1, list.size());
		
		try {
			dao.getRecord(id);
			fail("should failed as data is deleted");
		} catch (ObjectRetrievalFailureException o) {
			log.info(id);
		}
		// delete record
		dao.deleteRecord(record1.getId());

		// searchPagedPagedpaged again.
		list = dao.searchPaged(q, null, null,results,1).getResultList();
		assertEquals(0, list.size());

	}

	private Query buildQuery(String term, String value) {
		TermQuery tq = new TermQuery(new Term(term, value));
		return tq;
	}
}
