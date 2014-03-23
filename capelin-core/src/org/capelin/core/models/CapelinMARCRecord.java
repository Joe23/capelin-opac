package org.capelin.core.models;
/**
 * 
 * <a href="https://github.com/Joe23/capelin-opac/">Capelin-opac</a>
 * License: GNU AGPL v3 | http://www.gnu.org/licenses/agpl.html 
 * 
 * Basic elements for MARC Record.
 * If your record do not follow this standard, try to implement the CapelinRecord interface
 *  
 * @see CapelinRecord
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 * 
 */
public class CapelinMARCRecord implements CapelinRecord{

	protected int id;
	protected String documentType;
	protected String author;
	protected String notes;
	protected String originalNumber;
	protected String title;
	protected String subject;
	protected String year;
	public int getId() {
		return id;
	}
	public String getDocumentType() {
		return documentType;
	}
	public String getAuthor() {
		return author;
	}
	public String getNotes() {
		return notes;
	}
	public String getOriginalNumber() {
		return originalNumber;
	}
	public String getTitle() {
		return title;
	}
	public String getSubject() {
		return subject;
	}
	public String getYear() {
		return year;
	}
	
	protected void setId(int id) {
		this.id = id;
	}
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public void setOriginalNumber(String originalNumber) {
		this.originalNumber = originalNumber;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public void setYear(String year) {
		this.year = year;
	}
}
