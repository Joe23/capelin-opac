package sample.capelin.core;

import org.capelin.core.models.CapelinRecord;
import org.capelin.core.utils.AbstractMarcImporter;
import org.marc4j.marc.Record;

/**
 * 
 * <a href="https://github.com/Joe23/capelin-opac/">Capelin-opac</a>
 * License: GNU AGPL v3 | http://www.gnu.org/licenses/agpl.html
 *
 * This is an example that only read the MARC file and out put total distinct MARC tags.
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 *
 */
public class SampleImporter extends AbstractMarcImporter {
	@Override
	protected void setUp() {
		input = SampleImporter.class.getResourceAsStream("msplit0.mrc");
	}
	/**
	 * An example of writing a customized mapToRecord.
	 */
	@Override
	protected CapelinRecord buildRecord(Record r){
		SampleCoreRecord sample = new SampleCoreRecord();
		sample.setDocumentType(util.getField(r, "012")); // DOCUMENT_TYPE
		sample.setUniqueId(util.getField(r, "014")); // UNIQUE_ID
		sample.setIsbn(util.getField(r, "020")); // ISBN
		sample.setIssn(util.getField(r, "022")); // ISSN
		sample.setAuthor(util.getField(r, "100")); // AUTHOR
		sample.setJournalAbbrev(util.getField(r, "210")); // JOURNAL_ABBREV
		sample.setJournalTitle(util.getField(r, "222")); // JOURNAL_TITLE		
		sample.setTitle(util.getField(r, "245")); // TITLE
		sample.setEdition(util.getField(r, "245")); // EDITION		
		sample.setVolume(util.getField(r, "512")); // VOLUME
		sample.setNumber(util.getField(r, "517")); // NUMBER
		sample.setPage(util.getField(r, "519")); // PAGE
		sample.setPublicationDate(util.getField(r, "528")); // PUBLICATION_DATE
		//sample.setSubject(util.getField(r, "650")); // SUBJECTS
		//sample.setSubject(util.getField(r, "695")); // GENERAL_SUBJECT
		
		String [] subjectTags = {"695","650"};		// SUBJECTS GENERAL_SUBJECT
		sample.setSubject(util.getField(r, subjectTags));
		
		sample.setHostItem(util.getField(r, "773")); // HOST_ITEM
		sample.setArticleId(util.getField(r, "851")); // ARTICLE_ID
		sample.setAvailability(util.getField(r, "856")); // AVAILABILITY
		sample.setCopyNote(util.getField(r, "890")); // COPY_NOTES
		sample.setOriginalNumber(util.getField(r, "891")); // ORIGINAL_NO
		return sample;
	}

	public static void main(String arg[]) throws Exception{
		SampleImporter importer = new SampleImporter();
		importer.run(false);
	}
}
