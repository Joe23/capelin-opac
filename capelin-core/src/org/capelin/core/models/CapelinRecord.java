package org.capelin.core.models;

/**
 * <a href="https://github.com/Joe23/capelin-opac/">Capelin-opac</a>
 * License: GNU AGPL v3 | http://www.gnu.org/licenses/agpl.html
 *  
 * Interface for all records. 
 * 
 * @see CapelinMARCRecord
 * 
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 * 
 */
public interface CapelinRecord {	
	public int getId() ;
	public String getDocumentType() ;
	public String getAuthor() ;
	public String getTitle() ;
	public String getYear();
	public String toString();
}
