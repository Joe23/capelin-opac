package org.capelin.core.utils;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.Directory;
import org.capelin.core.models.CapelinRecord;


/**
 * 
 * Convert from Lucene Index file as Capelin Record.
 * @author jing.xiao.ca <at> gmail.com
 * @date Feb 2012
 *
 */
public abstract class AbstractLuceneIndexImporter {
	protected Directory dir;

	
	public void run(boolean flag) throws Exception {
		setUp();
		long start = System.currentTimeMillis();
		int total = 0;
		if(flag){
			total = importData();
		}else{
			System.out.println(getIndexHeaders().toString());
		}
		dir.close();
		System.out.println("Total Records: " + total);
		System.out.println("Time: " + (System.currentTimeMillis() - start));
		System.exit(0);
	}
	
	protected int importData() throws IOException {
		IndexReader reader = IndexReader.open(dir);
		int totalDoc = reader.numDocs();
		// Read documents
		for(int i=0;i<totalDoc;i++){
			buildRecord(reader.document(i));
		}		
		reader.close();
		return totalDoc;
	}	
	
	protected List<String> getIndexHeaders() throws IOException {
		setUp();
		IndexReader reader = IndexReader.open(dir);		
		Collection<String> c = reader.getFieldNames(IndexReader.FieldOption.ALL);
		List<String> headers = new UniqueList<String>();
		Iterator<String> its = c.iterator();
		String tmp;
		while (its.hasNext()) {
			tmp = its.next();			
			if (tmp != "") {
				headers.add(tmp);
			}
		}
		reader.close();
		return headers;
	}	
	@Override
	protected void finalize() throws Throwable{
		super.finalize();
		dir.close();
	}	
	protected abstract void setUp() throws  IOException;
	protected abstract CapelinRecord buildRecord(Document doc);
}
