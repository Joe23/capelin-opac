package org.capelin.transaction.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.similar.MoreLikeThis;
import org.apache.lucene.search.spell.LuceneDictionary;
import org.apache.lucene.search.spell.SpellChecker;
import org.capelin.core.models.CapelinRecord;
import org.capelin.core.utils.StaticStrings;
import org.capelin.core.utils.UniqueList;
import org.capelin.transaction.db.RAMDB;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.SearchFactory;
import org.hibernate.search.reader.ReaderProvider;
import org.hibernate.search.store.DirectoryProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * 
 * <a href="https://github.com/Joe23/capelin-opac/">Capelin-opac</a>
 * License: GNU AGPL v3 | http://www.gnu.org/licenses/agpl.html
 * 
 * Core and the only class that access the database. No need to extend to
 * extends this class, but just set a few parameters.
 * 
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 * 
 */
public class RecordDao extends HibernateDaoSupport {

	protected int MAX_RESULT = 20;
	private static final Log log = LogFactory.getLog(RecordDao.class);

	protected Class<? extends CapelinRecord> recordClass;

	public void saveRecord(Object data) {
		getHibernateTemplate().saveOrUpdate(recordClass.cast(data));
	}

	/**
	 * Retrieve record form database.
	 * 
	 * @param id
	 * @return
	 */
	public CapelinRecord getRecord(int id) {
		return (CapelinRecord) getHibernateTemplate().load(recordClass, id);
	}

	public PagedRecord getRecordWithOthers(int id, Query luceneQuery,
			Filter filter, Sort sort, int pageNumber) {
		List<Integer> ids = getRecordIds(luceneQuery, filter, sort, pageNumber);
		int position = -1;
		Integer iterator;
		for (int i = 0; i < ids.size(); i++) {
			iterator = ids.get(i);
			if (id == iterator) {
				position = i;
			}
		}
		return new PagedRecord(getRecord(id), ids, position);
	}

	public PagedRecord getRecordByPage(Query luceneQuery, Filter filter,
			Sort sort, int pageNumber, boolean first) {
		List<Integer> ids = getRecordIds(luceneQuery, filter, sort, pageNumber);
		int position = (first) ? 0 : ids.size() - 1;
		return new PagedRecord(getRecord(ids.get(position)), ids, position);
	}

	private List<Integer> getRecordIds(Query luceneQuery, Filter filter,
			Sort sort, int pageNumber) {
		FullTextSession fullTextSession = Search
				.getFullTextSession(getSession());

		FullTextQuery fullTextQuery = getFullTextQuery(fullTextSession,
				luceneQuery, recordClass);
		fullTextQuery.setFilter(filter).setProjection(onlyIds).setSort(sort);

		List<?> searchResult = fullTextQuery.setMaxResults(getPageSize())
				.setFirstResult(getFirstResultNumber(pageNumber)).list();
		List<Integer> ids = new ArrayList<Integer>(searchResult.size());
		Integer iditerator;
		for (int i = 0; i < searchResult.size(); i++) {
			iditerator = (Integer) ((Object[]) searchResult.get(i))[0];
			ids.add(iditerator);
		}
		return ids;
	}

	public List<String> getResultTerms(String term, Query luceneQuery) {
		// TODO
		FullTextSession fullTextSession = Search
				.getFullTextSession(getSession());

		FullTextQuery fullTextQuery = getFullTextQuery(fullTextSession,
				luceneQuery, recordClass);
		int fetchSize = getPageSize() * 3;
		List<?> searchResult = fullTextQuery.setProjection(term)
				.setMaxResults(fetchSize).list();

		// Use a memory index to re-analyst it.
		String sIterator;
		String[] sTmp;
		
		List<String> list = new UniqueList<String>();

		for (int i = 0; i < searchResult.size(); i++) {
			sIterator = (String) ((Object[]) searchResult.get(i))[0];
			if(null == sIterator) continue;
			sTmp = sIterator.split(StaticStrings.DB_SPLIT_STRING);
			for(int j=0;j<sTmp.length;j++){
				list.add(sTmp[j]);
			}			
		}
		log.debug("First List: " + list.size());
		RAMDB db = new RAMDB();
		try {
			db.addDoc(term, list);
			//log.debug("term: " + term + " query: " + luceneQuery);
			list = db.search(term, luceneQuery, getPageSize());
		} catch (CorruptIndexException e) {			
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		}
		return list;

	}

	/**
	 * Retrieve records form database.
	 * 
	 * @param ids
	 * @return
	 */
	public List<?> getRecords(Collection<?> ids) {
		DetachedCriteria criteria = DetachedCriteria.forClass(recordClass);
		criteria.add(Restrictions.in("id", ids));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	/**
	 * delete record from database and index.
	 * 
	 * @param id
	 */
	public void deleteRecord(int id) {
		getHibernateTemplate().delete(getRecord(id));
	}

	protected FullTextQuery getFullTextQuery(FullTextSession fullTextSession,
			Query luceneQuery, Class<?> clazz) {
		FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(
				luceneQuery, clazz);
		return fullTextQuery;
	}

	private int pageSize = MAX_RESULT;

	private int getFirstResultNumber(int pageNumber) {
		return (pageNumber - 1) * getPageSize();
	}

	private int getTotalPageNumber(int totalResultNumber) {
		return (int) Math.ceil((float) totalResultNumber / getPageSize());
	}

	/**
	 * Search the record from Lucene index.
	 * 
	 * @param luceneQuery
	 * @param filter
	 * @param resultKeys
	 * @param pageNumber
	 * @return
	 */
	public PagedResults searchPaged(Query luceneQuery, Filter filter,
			Sort sort, String[] projections, int pageNumber) {
		FullTextSession fullTextSession = Search
				.getFullTextSession(getSession());
		List<?> result = null;
		FullTextQuery fullTextQuery = getFullTextQuery(fullTextSession,
				luceneQuery, recordClass);

		int total = fullTextQuery.setFilter(filter).getResultSize();
		fullTextQuery.setFilter(filter).setProjection(projections)
				.setSort(sort);
		result = fullTextQuery.setMaxResults(getPageSize())
				.setFirstResult(getFirstResultNumber(pageNumber)).list();

		return new PagedResults(total, getTotalPageNumber(total), result);
	}

	/**
	 * List all relevant record.
	 * 
	 * @param field
	 * @param value
	 * @param pageNumber
	 * @param ascending
	 * @return PagedResults
	 */
	public PagedResults listPaged(String field, String value, int pageNumber,
			boolean ascending) {

		DetachedCriteria criteria = DetachedCriteria.forClass(recordClass);
		criteria.add(Restrictions.ilike(field, value, MatchMode.START));
		if (ascending) {
			criteria.addOrder(Order.asc(field));
		} else {
			criteria.addOrder(Order.desc(field));
		}
		List<?> result = getHibernateTemplate().findByCriteria(criteria,
				getFirstResultNumber(pageNumber), getPageSize());

		Criteria crit = getSession().createCriteria(recordClass);
		crit.add(Restrictions.ilike(field, value, MatchMode.START));
		Number total = (Number) crit.setProjection(Projections.rowCount())
				.uniqueResult();
		return new PagedResults(total.intValue(),
				getTotalPageNumber(total.intValue()), result);
	}

	/**
	 * Spell check the term from the field in index, return similar terms. If
	 * the keyword is not tokenized, this will give the full name and function
	 * like browse.
	 * 
	 * @param field
	 * @param term
	 * @return
	 */
	public String[] spellcheck(String field, String term) {
		SearchFactory searchFactory = Search.getFullTextSession(getSession())
				.getSearchFactory();
		DirectoryProvider<?> recordProvider = searchFactory
				.getDirectoryProviders(recordClass)[0];
		ReaderProvider readerProvider = searchFactory.getReaderProvider();
		IndexReader reader = readerProvider.openReader(recordProvider);
		String[] similars = null;
		try {
			SpellChecker spellchecker = new SpellChecker(
					recordProvider.getDirectory());
			spellchecker.indexDictionary(new LuceneDictionary(reader, field));
			spellchecker.setAccuracy(0.0001f);
			similars = spellchecker.suggestSimilar(term, getPageSize(), reader,
					field, true);
		} catch (IOException e) {
			log.error("Index not found: " + e);
		} finally {
			readerProvider.closeReader(reader);
		}
		return similars;
	}

	/**
	 * This one is not working. luncene more like this is compare document to
	 * document.
	 * 
	 * 
	 * @param field
	 * @param term
	 * @return
	 */
	public String[] moreLikeThis(String field, String term) {
		// TODO
		SearchFactory searchFactory = Search.getFullTextSession(getSession())
				.getSearchFactory();
		DirectoryProvider<?> recordProvider = searchFactory
				.getDirectoryProviders(recordClass)[0];
		ReaderProvider readerProvider = searchFactory.getReaderProvider();
		IndexReader reader = readerProvider.openReader(recordProvider);

		String[] similars = null;
		try {
			MoreLikeThis mlt = new MoreLikeThis(reader);
			similars = mlt.retrieveInterestingTerms(pageSize);
		} catch (Exception e) {
			log.error("Unexpected exception: " + e);
		} finally {
			readerProvider.closeReader(reader);
		}
		return similars;
	}

	/**
	 * 
	 * Return the number of record in the PagedResults
	 * 
	 * @return int
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * For spring configuration. the number of record in the PagedResults
	 * 
	 * @return
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * Spring configuration The data class that this dao should work on.
	 * 
	 * @param recordClass
	 */
	public void setRecordClass(Class<? extends CapelinRecord> recordClass) {
		this.recordClass = recordClass;
	}

	/**
	 * Create a new Record with is assigned by setRecordClass()
	 * 
	 * @return CapelinRecord
	 */
	public CapelinRecord newRecord() {
		return (CapelinRecord) BeanUtils.instantiateClass(recordClass);
	}

	private static final String[] onlyIds = new String[] { "id" };
}
