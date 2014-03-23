package org.capelin.transaction.db;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.Sort;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

public class RAMDB {
	private RAMDirectory idx;

	public RAMDB() {
		idx = new RAMDirectory();
	}

	public void addDoc(String term, List<String> value)
			throws CorruptIndexException, IOException {
		IndexWriter writer = new IndexWriter(idx,  new StandardAnalyzer(Version.LUCENE_30), true,
				IndexWriter.MaxFieldLength.LIMITED);

		Document doc;
		for (String v : value) {
			doc = new Document();
			doc.add(new Field(term, v, Field.Store.YES, Field.Index.ANALYZED));
			writer.addDocument(doc);
		}
		writer.optimize();
		writer.close();
	}
	
	public List<String> search(String term, Query query, int max) throws IOException{
        Searcher searcher = new IndexSearcher(idx,true);
        ScoreDoc[] sds = searcher.search(query,null,max).scoreDocs;
        Document doc;
        List<String> result = new LinkedList<String>();
        for(int i=0;i<sds.length;i++){        	
        	doc = searcher.doc(sds[i].doc);        	
        	result.add(doc.get(term));
        }
        searcher.close();
		
		return result;
	}
	
	public List<String> search(String term, Query query, int max, Sort sort) throws IOException{
        Searcher searcher = new IndexSearcher(idx,true);
        ScoreDoc[] sds = searcher.search(query, null, max, sort).scoreDocs;        
        Document doc;
        List<String> result = new LinkedList<String>();
        for(int i=0;i<sds.length;i++){        	
        	doc = searcher.doc(sds[i].doc);        	
        	result.add(doc.get(term));
        }
        searcher.close();
		
		return result;
	}
	
	public Directory getDB(){
		return idx;
	}
}
