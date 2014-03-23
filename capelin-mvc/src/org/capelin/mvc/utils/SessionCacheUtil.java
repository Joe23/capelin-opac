package org.capelin.mvc.utils;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.capelin.core.models.CapelinRecord;
import org.capelin.core.utils.UniqueList;
import org.capelin.mvc.web.form.ListForm;
import org.capelin.mvc.web.form.SearchFormObject;

import com.google.code.kaptcha.Constants;

public class SessionCacheUtil {
	private static String LIST_FORM = "_list_form_";
	private static String SEARCH_OBJECT = "_search_object_";
	private static String KEPT_FOLDER = "_kept_folder_";
	private static String SEARCH_CURRENT_PAGE_NUMBER = "_search_current_page_number_";
	private static String SEARCH_TOTAL_PAGE_NUMBER = "_search_total_page_number_";
	private static String SEARCH_RESULT_IDS = "_search_result_ids_";
	private static String SEARCH_RESULT_POSITION = "_search_result_position_";
	private static String LIST_PAGE_NUMBER = "_list_page_number_";
	
	
	public void setSearchObject(HttpSession session, SearchFormObject searchObject, String controllerId){
		session.setAttribute(getAttributeName(controllerId,SEARCH_OBJECT), searchObject);
	}
	
	public void setSearchCurrentPageNumber(HttpSession session,int pageNumber, String controllerId){
		session.setAttribute(getAttributeName(controllerId,SEARCH_CURRENT_PAGE_NUMBER), pageNumber);
	}
	
	public int getSearchCurrentPageNumber(HttpSession session, String controllerId){
		Integer page = (Integer) session.getAttribute(getAttributeName(controllerId,SEARCH_CURRENT_PAGE_NUMBER));
		int intPage = 1;
		if (null == page) {
			setSearchCurrentPageNumber(session,intPage,controllerId);
		} else {
			intPage = page.intValue();
		}
		return intPage;
	}
	
	public void setListPageNumber(HttpSession session,int pageNumber, String controllerId){		
		session.setAttribute(getAttributeName(controllerId,LIST_PAGE_NUMBER), pageNumber);
	}
	
	public int getListPageNumber(HttpSession session, String controllerId){
		Integer page = (Integer) session.getAttribute(getAttributeName(controllerId,LIST_PAGE_NUMBER));
		int intPage = 1;
		if (null == page) {
			setListPageNumber(session,intPage,controllerId);
		} else {
			intPage = page.intValue();
		}
		return intPage;
	}	
	
	public SearchFormObject getSearchObject(HttpSession session, String controllerId){
		return (SearchFormObject)session.getAttribute(getAttributeName(controllerId,SEARCH_OBJECT));
	}
	
	public boolean doesContain(HttpSession session,String value){
		return (session.getAttribute(value) != null);
	}
	
	public void setValue(HttpSession session,String key, String value, String controllerId){
		session.setAttribute(getAttributeName(controllerId,key), value);		
	}

	public void setValue(HttpSession session,String key, String value){
		session.setAttribute(key, value);		
	}
	
	public ListForm getListForm(HttpSession session, String controllerId){
		return (ListForm) session.getAttribute(getAttributeName(controllerId,LIST_FORM));
	}
	
	public void setListForm(HttpSession session, ListForm listForm, String controllerId){
		session.setAttribute(getAttributeName(controllerId,LIST_FORM),listForm);
	}	
	
	public void deleteValue(HttpSession session,String term, String controllerId){
		session.removeAttribute(term);		
	}
	
	public List<CapelinRecord> getFolderRecords(HttpSession session, String controllerId){
		@SuppressWarnings("unchecked")
		List<CapelinRecord> recordList = (List<CapelinRecord>) session
				.getAttribute(getAttributeName(controllerId,KEPT_FOLDER));
		if (recordList == null) {
			recordList = new UniqueList<CapelinRecord>();
			session.setAttribute(getAttributeName(controllerId,KEPT_FOLDER), recordList);
		}
		return recordList;
	}
	
	public boolean isInKeptFolder(int toCheckId, HttpSession session, String controllerId){
		List<CapelinRecord> recordList = getFolderRecords(session,controllerId);
		for(CapelinRecord r: recordList){
			if(r.getId() == toCheckId) return true;
		}
		return false;		
	}
	
	public void setFolderRecords(HttpSession session, List<CapelinRecord> records, String controllerId){
		session.setAttribute(getAttributeName(controllerId,KEPT_FOLDER), records);
	}
	
	public void setSearchResultIds(HttpSession session, List<Integer> ids, String controllerId){
		session.setAttribute(getAttributeName(controllerId,SEARCH_RESULT_IDS), ids);
	}

	@SuppressWarnings("unchecked")
	public List<Integer> getSearchResultIds(HttpSession session, String controllerId){
		return (List<Integer>)session.getAttribute(getAttributeName(controllerId,SEARCH_RESULT_IDS));
	}	
	
	public void setSearchResultPosition(HttpSession session,int pos, String controllerId){
		session.setAttribute(getAttributeName(controllerId,SEARCH_RESULT_POSITION), pos);
	}
	
	public int getSearchResultPosition(HttpSession session, String controllerId){
		return (Integer)session.getAttribute(getAttributeName(controllerId,SEARCH_RESULT_POSITION));
	}
	public void setSearchTotalPageNumber(HttpSession session,int number, String controllerId){
		session.setAttribute(getAttributeName(controllerId,SEARCH_TOTAL_PAGE_NUMBER), number);
	}
	
	public int getSearchTotalPageNumber(HttpSession session, String controllerId){
		return (Integer)session.getAttribute(getAttributeName(controllerId,SEARCH_TOTAL_PAGE_NUMBER));
	}
	
	public String getCaptcha(HttpSession session){
		return (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
	}
	
	private String getAttributeName(String controllerId, String pro){
		return (new StringBuffer(controllerId)).append(pro).toString();
	}
}
