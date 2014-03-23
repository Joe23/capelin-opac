package sample.capelin.mvc.samplecatalog;

import org.capelin.core.models.CapelinMARCRecord;
import org.capelin.core.models.CapelinRecord;
import org.hibernate.annotations.Entity;
import org.hibernate.search.annotations.Boost;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

/**
 * 
 * <a href="https://github.com/Joe23/capelin-opac/">Capelin-opac</a>
 * License: GNU GPL v3 | http://www.gnu.org/licenses/gpl.html 
 *
 * Same as transaction Record. The class may omit when Hibernate Search support
 * xml configuration.
 * 
 * Repackage to keep the Web Sample together.
 * 
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 */
@Entity
@Indexed
public class SampleMVCRecord extends CapelinMARCRecord {
	@DocumentId
	private int id;

	@Field(index = Index.TOKENIZED, store = Store.YES)
	private String author;

	@Field(index = Index.TOKENIZED, store = Store.YES)
	private String journalTitle;
	
	@Field(index = Index.TOKENIZED, store = Store.YES)
	private String subject;

	@Field(index = Index.TOKENIZED, store = Store.YES)
	private String title;
	
	@Field(index = Index.TOKENIZED, store = Store.YES)
	@Boost(1.5f)
	private String documentType;	
	
	@Field(index = Index.TOKENIZED, store = Store.YES)
	@Boost(0.8f)
	private String notes;	
	
	@Field(index = Index.TOKENIZED, store = Store.YES)
	private String year;
	
	private String authorAdd;
	private String authorMain;
	private String edition;
	private String isbn;
	private String issn;
	private String journalAbbrev;
	
	private String language;
	private String number;
	private String originalNumber;
	private String pages;
	private String place;
	private String publicationDate;
	private String publicationInfo;
	private String publisher;
	private String series;
	private String titleMain;
	private String titleOther;
	private String volume;
	
	
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}	

	public String getJournalAbbrev() {
		return journalAbbrev;
	}

	public String getJournalTitle() {
		return journalTitle;
	}

	public String getEdition() {
		return edition;
	}

	public String getNumber() {
		return number;
	}

	public String getLanguage() {
		return language;
	}

	public void setJournalAbbrev(String journalAbbrev) {
		this.journalAbbrev = journalAbbrev;
	}

	public void setJournalTitle(String journalTitle) {
		this.journalTitle = journalTitle;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public int getId() {
		return id;
	}

	public String getNotes() {
		return notes;
	}

	public String getYear() {
		return year;
	}

	public String getAuthorAdd() {
		return authorAdd;
	}

	public String getAuthorMain() {
		return authorMain;
	}

	public String getIsbn() {
		return isbn;
	}

	public String getIssn() {
		return issn;
	}

	public String getOriginalNumber() {
		return originalNumber;
	}

	public String getPages() {
		return pages;
	}

	public String getPlace() {
		return place;
	}

	public String getPublicationDate() {
		return publicationDate;
	}

	public String getPublicationInfo() {
		return publicationInfo;
	}

	public String getPublisher() {
		return publisher;
	}

	public String getSeries() {
		return series;
	}

	public String getTitleMain() {
		return titleMain;
	}

	public String getTitleOther() {
		return titleOther;
	}

	public String getVolume() {
		return volume;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public void setAuthorAdd(String authorAdd) {
		this.authorAdd = authorAdd;
	}

	public void setAuthorMain(String authorMain) {
		this.authorMain = authorMain;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public void setIssn(String issn) {
		this.issn = issn;
	}

	public void setOriginalNumber(String originalNumber) {
		this.originalNumber = originalNumber;
	}

	public void setPages(String pages) {
		this.pages = pages;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public void setPublicationDate(String publicationDate) {
		this.publicationDate = publicationDate;
	}

	public void setPublicationInfo(String publicationInfo) {
		this.publicationInfo = publicationInfo;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public void setTitleMain(String titleMain) {
		this.titleMain = titleMain;
	}

	public void setTitleOther(String titleOther) {
		this.titleOther = titleOther;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}
	
	protected String munHolding;
	protected String url;	

	
	public String getMunHolding() {
		return munHolding;
	}
	public String getUrl() {
		return url;
	}
	public void setMunHolding(String munHolding) {
		this.munHolding = munHolding;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof CapelinRecord){
			return getId() == ((CapelinRecord) o).getId();
		}
		return false;
	}
}
