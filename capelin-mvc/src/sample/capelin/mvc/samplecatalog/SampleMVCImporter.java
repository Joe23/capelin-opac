package sample.capelin.mvc.samplecatalog;

import static org.capelin.mvc.utils.CapelinSpringContext.getBeanFactory;

import java.io.IOException;

import org.capelin.core.models.CapelinRecord;
import org.capelin.transaction.utils.TXMarcRecordImporter;
import org.marc4j.marc.Record;
import org.springframework.orm.hibernate3.HibernateTransactionManager;

/**
 * 
 * <a href="https://github.com/Joe23/capelin-opac/">Capelin-opac</a>
 * License: GNU GPL v3 | http://www.gnu.org/licenses/gpl.html 
 *
 * Sample class for data import
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 *
 */
public class SampleMVCImporter extends TXMarcRecordImporter {

	@Override
	protected void setUp() throws IOException {
		input = SampleMVCImporterDemo.class.getResourceAsStream("msplit0.mrc");
		sf = ((HibernateTransactionManager) getBeanFactory().getBean("txManager")).getSessionFactory();
	}

	public static void main(String[] arg) throws Exception {
		(new SampleMVCImporter()).run(INDEX_ONLY);
		System.exit(0);
	}

	@Override
	protected Class<? extends CapelinRecord> getRecordClass() {
		return SampleMVCRecord.class;
	}

	@Override
	protected CapelinRecord buildRecord(Record marcRecord) {
		SampleMVCRecord r = new SampleMVCRecord();
		r.setAuthor(util.getField(marcRecord, "100"));
		r.setAuthorAdd(util.getField(marcRecord, "700"));
		r.setAuthorMain(util.getField(marcRecord, "704"));		
		r.setDocumentType(util.getField(marcRecord, "012"));
		r.setEdition(util.getField(marcRecord, "250"));
		r.setIsbn(util.getField(marcRecord, "020"));
		r.setIssn(util.getField(marcRecord, "022"));
		r.setJournalAbbrev(util.getField(marcRecord, "210"));
		r.setJournalTitle(util.getField(marcRecord, "222",'a'));
		r.setLanguage(util.getField(marcRecord, "546"));
	
		String[] tmp = { "500", "502" };
		r.setNotes(util.getField(marcRecord, tmp));
		
		r.setNumber(util.getField(marcRecord, "517"));
		r.setOriginalNumber(util.getField(marcRecord, "891"));
		r.setPages(util.getField(marcRecord, "519"));
		r.setPlace(util.getField(marcRecord, "270"));
		r.setPublicationDate(util.getField(marcRecord, "528"));
		r.setPublicationInfo(util.getField(marcRecord, "773"));
		r.setPublisher(util.getField(marcRecord, "260"));
		r.setSeries(util.getField(marcRecord, "440"));
		tmp = new String [] { "695", "650" };
		r.setSubject(util.getField(marcRecord, tmp));
		r.setTitle(util.getField(marcRecord, "245"));
		r.setTitleMain(util.getField(marcRecord, "730"));
		r.setTitleOther(util.getField(marcRecord, "242"));
		r.setUrl(util.getField(marcRecord, "856"));
		r.setVolume(util.getField(marcRecord, "512"));
		return r;
	}

}
