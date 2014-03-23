package test.capeline.transaction;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.util.Version;

public class TestUtils {
	public static Query buildQuery(String term, String value) {
		TermQuery tq = new TermQuery(new Term(term, value));
		return tq;
	}

	public static Query paseQuery(String term, String value)
			throws ParseException {
		QueryParser qp = new QueryParser(Version.LUCENE_30, term,
				new StandardAnalyzer(Version.LUCENE_30));
		return qp.parse(value);
	}
}
