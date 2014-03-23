package sample.capelin.mvc.samplecatalog;

import java.io.IOException;

import org.capelin.core.models.CapelinMARCRecord;
import org.capelin.core.models.CapelinRecord;
import org.capelin.core.utils.AbstractMarcImporter;
import org.marc4j.marc.Record;

/**
 * 
 * <a href="https://github.com/Joe23/capelin-opac/">Capelin-opac</a>
 * License: GNU GPL v3 | http://www.gnu.org/licenses/gpl.html 
 *
 * Sample class for data import
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 *
 */
public class SampleMVCImporterDemo extends AbstractMarcImporter {

	@Override
	protected void setUp() throws IOException {
		input = SampleMVCImporterDemo.class.getResourceAsStream("msplit0.mrc");
	}
	
	@Override
	protected CapelinRecord buildRecord(Record r){
		CapelinMARCRecord record = new CapelinMARCRecord();
		record.setAuthor(util.getField(r, "100"));
		record.setDocumentType(util.getField(r, "012"));
		record.setNotes(util.getField(r, "500"));
		record.setOriginalNumber(util.getOriginalNumber(r));
		record.setTitle(util.getField(r, "245"));
		record.setYear(util.getControlField(r, "008"));
		/*
		return new MARC21Data(
				util.getField(r, "012"), // DOCUMENT_TYPE
				util.getField(r, "014"), // UNIQUE_ID
				util.getField(r, "020"), // ISBN
				util.getField(r, "022"), // ISSN
				util.getField(r, "100"), // AUTHOR
				util.getField(r, "210"), // JOURNAL_ABBREV
				util.getField(r, "222"), // JOURNAL_TITLE
				util.getField(r, "242"), // TRANSATION_TITLE
				util.getField(r, "245"), // TITLE
				util.getField(r, "250"), // EDITION
				util.getField(r, "260"), // PUBLISHER
				util.getField(r, "270"), // PLACE
				util.getField(r, "440"), // SERIES
				util.getField(r, "500"), // GENERAL_NOTE
				util.getField(r, "502"), // DISSERTATION_NOTE
				util.getField(r, "512"), // VOLUME
				util.getField(r, "517"), // NUMBER
				util.getField(r, "514"), // DATA_QUALITY
				util.getField(r, "519"), // PAGE
				util.getField(r, "520"), // SUMMARY
				util.getField(r, "528"), // PUBLICATION_DATE
				util.getField(r, "546"), // LANGUAGE
				util.getField(r, "650"), // SUBJECTS
				util.getField(r, "695"), // GENERAL_SUBJECT
				util.getField(r, "700"), // PERSONAL_NAME
				util.getField(r, "704"), // MAIN_AUTHOR
				util.getField(r, "730"), // MAIN_TITLE
				util.getField(r, "773"), // HOST_ITEM
				util.getField(r, "851"), // ARTICLE_ID
				util.getField(r, "856"), // AVAILABILITY
				util.getField(r, "890"), // COPY_NOTES
				util.getField(r, "891")); // ORIGINAL_NO
		*/
		return record;
	}

	public static void main(String[] arg) throws Exception {
	}

}
