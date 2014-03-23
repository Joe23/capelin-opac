package org.capelin.mvc.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.capelin.core.models.CapelinRecord;
import org.capelin.mvc.utils.SessionCacheUtil;
import org.capelin.transaction.dao.RecordDao;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.spring.DwrSpringServlet;

public class CapelinAjax extends DwrSpringServlet {
	private static final long serialVersionUID = 1857513078976276871L;
	private SessionCacheUtil cacheUtil;
	private Map<String, RecordDao> daoMap;

	public void addOneToFolder(String jspViewPath, String sId) {
		HttpSession session = WebContextFactory.get().getSession();
		int id = Integer.parseInt(sId);
		List<CapelinRecord> recordList = cacheUtil.getFolderRecords(session,
				jspViewPath);
		recordList.add(daoMap.get(jspViewPath).getRecord(id));
	}
	
	public void deleteOneFromFolder(String jspViewPath, String sId) {
		int id = Integer.parseInt(sId);
		List<CapelinRecord> recordList = cacheUtil.getFolderRecords(WebContextFactory.get().getSession(),
				jspViewPath);		
		for (int i = 0; i < recordList.size(); i++) {
			CapelinRecord r = recordList.get(i);
			if (r.getId() == id) {
				recordList.remove(i);
				break;
			}
		}
	}	
	

	public void setSessionCacheUtil(SessionCacheUtil cacheUtil) {
		this.cacheUtil = cacheUtil;
	}
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setDaoMap(Map map) {
		daoMap = map;
	}
}
