package test.capeline.transaction;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.capelin.transaction.TxSpringContext;
import org.capelin.transaction.dao.RecordDao;

import sample.capelin.transaction.SampleTxRecord;
/**
 * 
 * <a href="https://github.com/Joe23/capelin-opac/">Capelin-opac</a>
 * License: GNU AGPL v3 | http://www.gnu.org/licenses/agpl.html 
 * 
 * 
 * @author Jing Xiao <jing.xiao.ca at gmail dot com
 *
 */
public class LikeThisDbAllTest extends TestCase {

	private RecordDao dao;

	private static final Log log = LogFactory.getLog(LikeThisDbAllTest.class);

	private List<Integer> ids = new ArrayList<Integer>();
	private List<String> titles = new ArrayList<String>();

	@Override
	protected void setUp() {
		dao = (RecordDao) TxSpringContext.getBeanFactory().getBean("sampleDao");
		setUpData();
	}

	private void setUpData() {
		SampleTxRecord record1 = new SampleTxRecord();
		record1.setTitle("A far Book");
		record1.setAuthor("Amanda A\nLarsen B");
		record1.setSubject("CONCEPTION BAY");
		dao.saveRecord(record1);
		ids.add(record1.getId());
		titles.add(record1.getTitle());
		record1.setId(0);

		record1.setTitle("A not far Book");
		record1.setAuthor("Bob A\nLarsen B");
		record1.setSubject("NORTHERN BAY");
		dao.saveRecord(record1);
		ids.add(record1.getId());
		titles.add(record1.getTitle());
		record1.setId(0);

		record1.setTitle("A close Book");
		record1.setAuthor("Arnason A\nLarsen B");
		record1.setSubject("SOCIAL LIFE and CUSTOMS");
		dao.saveRecord(record1);
		ids.add(record1.getId());
		titles.add(record1.getTitle());
		record1.setId(0);

		record1.setTitle("Boys never stop");
		record1.setAuthor("Boy A");
		record1.setSubject("CHURCH HISTORY-Roman Catholic");
		dao.saveRecord(record1);
		ids.add(record1.getId());
		titles.add(record1.getTitle());
		record1.setId(0);

		record1.setTitle("Boys may stop");
		record1.setAuthor("Boy B");
		record1.setSubject("CHURCH HISTORY-Protestant\nabcd");
		dao.saveRecord(record1);
		ids.add(record1.getId());
		titles.add(record1.getTitle());
		record1.setId(0);

		record1.setTitle("Twilight X");
		record1.setSubject("Cardiovascular System\nAlleles\nMyocardial Infarction\nCHURCH HISTORY-Protestant\n");
		dao.saveRecord(record1);
		ids.add(record1.getId());
		titles.add(record1.getTitle());
	}

	@Override
	protected void tearDown() {
		for (int id : ids) {
			dao.deleteRecord(id);
		}
	}

	public void testSpellcheck(){
		String term [] = dao.spellcheck("subject","churc");
		System.out.println("YOO: " + term.length);
		for(String t : term){
			System.out.println("DEbug: " + t);
			System.out.println();
		}		
	}
	
	public void _testSimilar(){
		String term [] = dao.moreLikeThis("subject","northen");
		System.out.println("YOO: " + term.length);
		for(String t : term){
			System.out.println("DEbug: " + t);
			System.out.println();
		}		
	}	
}
