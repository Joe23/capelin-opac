package org.capelin.transaction.utils;

import org.apache.commons.logging.Log;
import org.capelin.core.models.CapelinRecord;
import org.hibernate.FlushMode;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;

class ImportHelper {
	static int buildIndexManually(SessionFactory sf,Class<? extends CapelinRecord> clazz, int BATCH_SIZE, Log log) throws Exception {
		Session session = sf.getCurrentSession();
		FullTextSession fullTextSession = Search.getFullTextSession(session);
		fullTextSession.setFlushMode(FlushMode.MANUAL);
		Transaction transaction = fullTextSession.beginTransaction();
		// Scrollable results will avoid loading too many objects in memory
		ScrollableResults results = fullTextSession
				.createCriteria(clazz).setFetchSize(BATCH_SIZE)
				.scroll(ScrollMode.FORWARD_ONLY);
		int index = 0;
		while (results.next()) {
			index++;
			fullTextSession.index(results.get(0)); // index each element
			if (index % BATCH_SIZE == 0) {
				fullTextSession.flushToIndexes(); // apply changes to indexes
				fullTextSession.clear(); // free memory since the queue is
											// processed
				log.info(index);
			}
		}
		log.info("Flushing ...");
		transaction.commit();
		return index;
	}
	
}
