package org.capelin.mvc.utils;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.util.Version;
import org.capelin.core.utils.StaticStrings;
import org.capelin.mvc.web.form.SearchFormObject;
import org.capelin.mvc.web.form.SearchFormUnit;
import org.capelin.mvc.web.form.SimilarForm;

/**
 * 
 * <a href="https://github.com/Joe23/capelin-opac/">Capelin-opac</a> 
 * 
 * License: GNU AGPL v3 | http://www.gnu.org/licenses/agpl.html
 * 
 * 
 * Utilities that generate the Luncene query and filter
 * 
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 * 
 * @see also MultiIndexLuceneBuilder
 * 
 */
public class LuceneBuilder {
	protected static final Log log = LogFactory.getLog(LuceneBuilder.class);

	public Query buildQuery(SearchFormObject form) {
		BooleanQuery luceneQuery = null;
		try {			
			Set<String> keys = form.getMap().keySet();
			if (null == keys)
				return null;
			
			luceneQuery = new BooleanQuery();
			log.debug("keys: " + keys.size() + keys);
			if (WebViewUtils.BASIC.equals(form.getResultView())) {
				buildBasicQureyTerms(form, luceneQuery);
			} else {
				buildAdvancedQureyTerms(form, luceneQuery);
			}
		} catch (ParseException e) {
			e.printStackTrace();
			log.info("Cannot pase query: " + e);
		}
		log.debug("luceneQuery: " + luceneQuery);
		return luceneQuery;
	}

	public Query buildSimilarQuery(SimilarForm form, boolean fuzzy) {
		BooleanQuery luceneQuery = new BooleanQuery();
		try {
			String key = form.getTerm();
			QueryParser qp = getQueryParser(key);
			String value;
			if (fuzzy) {
				value = WebViewUtils.trim(form.getWiderValue());
			} else {
				value = WebViewUtils.trim(form.getValue())+"*";
			}

			luceneQuery.add(qp.parse(value), BooleanClause.Occur.SHOULD);
		} catch (ParseException e) {
			e.printStackTrace();
			log.info("Cannot pase query: " + e);
		}
		log.debug("luceneQuery: " + luceneQuery);
		return luceneQuery;
	}

	public Filter buildFilter(SearchFormObject form) {
		Filter f = null;
		if (null != form.getDocumentType()
				&& !StaticStrings.EMPLTY.equals(form.getDocumentType())) {
			TermQuery tq = new TermQuery(new Term(WebViewUtils.TYPE,
					form.getDocumentType()));
			f = new QueryWrapperFilter(tq);
		}
		log.debug("Filter: " + f);
		return f;
	}

	protected void buildBasicQureyTerms(SearchFormObject form,
			BooleanQuery query) throws ParseException {
		Map<String, SearchFormUnit> map = form.getMap();
		String key = map.keySet().iterator().next();

		String value = WebViewUtils.trim(map.get(key).getValue());
		if (form.isMatchAll()) {
			buildMatchAllQuery(form, key, value, map.get(key).getOp(), query);
		} else {
			log.debug("query value: " + value);
			query.add(getQueryParser(form, key).parse(value),
					getOccur(map.get(key).getOp()));
		}
	}

	protected void buildAdvancedQureyTerms(SearchFormObject form,
			BooleanQuery query) throws ParseException {
		Map<String, SearchFormUnit> map = form.getMap();
		QueryParser qp;
		String value;
		for (String key : map.keySet()) {
			value = WebViewUtils.trim(map.get(key).getValue());
			if (null != value) {
				qp = getQueryParser(form, key);
				query.add(qp.parse(value), getOccur(map.get(key).getOp()));
			}
		}
		addRangedTerms(query, form);
	}

	protected void addRangedTerms(BooleanQuery query, SearchFormObject form) {
		String term = form.getRangedTermForQuery();
		if (null != term) {
			Query q = new TermRangeQuery(term, form.getRangedMinForQuery(),
					form.getRangedMaxForQuery(), true, true);
			query.add(q, BooleanClause.Occur.MUST);
			log.debug("ranged Term: " + q);
		}
	}

	public Sort buildSort(SearchFormObject form) {
		if (null == form.getSortTerm()
				|| StaticStrings.EMPLTY.equals(form.getSortTerm()))
			return null;
		SortField sf;
		if (WebViewUtils.YEAR.equals(form.getSortTerm())) {
			sf = new SortField(form.getSortTerm(), SortField.STRING, true);
		} else if (WebViewUtils.ID.equals(form.getSortTerm())) {
			sf = new SortField(form.getSortTerm(), SortField.INT, true);
		} else {
			sf = new SortField(form.getSortTerm(), SortField.STRING);
		}
		Sort sort = new Sort();
		sort.setSort(sf);
		return sort;
	}

	protected QueryParser getQueryParser(SearchFormObject form, String key) {
		// check if search all
		if (StaticStrings.EMPLTY.equals(key)) {
			return new MultiFieldQueryParser(Version.LUCENE_30,
					form.getSearchKeys(), getAnalyzer(form.isKeyword()));
		}
		return new QueryParser(Version.LUCENE_30, key, getAnalyzer(form.isKeyword()));
	}
	
	protected QueryParser getQueryParser(String key) {
		return new QueryParser(Version.LUCENE_30, key, getAnalyzer(true));
	}	

	protected Analyzer getAnalyzer(boolean isKeyword) {
		if (isKeyword) {
			return new StandardAnalyzer(Version.LUCENE_30, Collections.EMPTY_SET);
			//return new SimpleAnalyzer();
		}
		return new StandardAnalyzer(Version.LUCENE_30);
	}

	protected BooleanClause.Occur getOccur(String op) {
		if (SearchFormUnit.AND.equals(op)) {
			return BooleanClause.Occur.MUST;
		} else if (SearchFormUnit.NOT.equals(op)) {
			return BooleanClause.Occur.MUST_NOT;
		}
		return BooleanClause.Occur.SHOULD;
	}

	protected void buildMatchAllQuery(SearchFormObject form, String key,
			String value, String op, BooleanQuery query) throws ParseException {
		if(null == value || StaticStrings.EMPLTY.equals(value.trim())) return;
		QueryParser qp;
		for (String v : value.split(StaticStrings.SPACE)) {
			qp = getQueryParser(form, key);
			Query q = qp.parse(v);
			if (!emptyQuery.equals(q)) {
				query.add(q, getOccur(op));
			}
		}
	}

	protected Query emptyQuery = getEmptyQuery();

	protected Query getEmptyQuery() {
		QueryParser qp = new QueryParser(Version.LUCENE_30,
				StaticStrings.EMPLTY, getAnalyzer(false));
		try {
			return qp.parse("the");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
