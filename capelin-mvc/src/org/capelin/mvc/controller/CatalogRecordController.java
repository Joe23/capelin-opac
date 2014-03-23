package org.capelin.mvc.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.capelin.core.models.CapelinRecord;
import org.capelin.core.utils.StaticStrings;
import org.capelin.mvc.mail.SMTPMailSender;
import org.capelin.mvc.utils.LuceneBuilder;
import org.capelin.mvc.utils.SessionCacheUtil;
import org.capelin.mvc.utils.WebViewUtils;
import org.capelin.mvc.utils.sorter.RecordSorter;
import org.capelin.mvc.web.form.AdvancedSearchObject;
import org.capelin.mvc.web.form.BasicSearchObject;
import org.capelin.mvc.web.form.FolderForm;
import org.capelin.mvc.web.form.ListForm;
import org.capelin.mvc.web.form.LoginForm;
import org.capelin.mvc.web.form.MailForm;
import org.capelin.mvc.web.form.SearchFormObject;
import org.capelin.mvc.web.form.SimilarForm;
import org.capelin.transaction.dao.PagedRecord;
import org.capelin.transaction.dao.PagedResults;
import org.capelin.transaction.dao.RecordDao;
import org.springframework.orm.hibernate3.HibernateObjectRetrievalFailureException;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;
import org.springframework.web.util.WebUtils;

/**
 * 
 * <a href="https://github.com/Joe23/capelin-opac/">Capelin-opac</a> 
 * 
 * License: GNU AGPL v3 | http://www.gnu.org/licenses/agpl.html
 * 
 * 
 * Core class for View controller. Only sets a few parameters to create a new
 * catalog.
 * 
 * There are three method that subclass is need to implement <code> 
 * public ModelAndView saveData(HttpServletRequest request, HttpServletResponse response, [Your Record Class] recordClass) <br>
 * public ModelAndView searchBasic(HttpServletRequest request, HttpServletResponse response, HttpSession session, [Your Basic Search Object Command] command) <br>
 * public ModelAndView searchAdvanced(HttpServletRequest request, HttpServletResponse response, HttpSession session,[Your Advanced Search Object] command) <br>
 * </code>
 * 
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 * @see sample.capelin.mvc.samplecatalog.SampleRecordController
 * 
 */
public abstract class CatalogRecordController extends MultiActionController {

	private static final Log log = LogFactory
			.getLog(CatalogRecordController.class);
	/*
	 * public static final String SESSION_SEARCH_KEY = "search_form"; public
	 * static final String SESSION_LIST_KEY = "list_form"; public static final
	 * String SESSION_Folder = "folder"; public static final String
	 * SESSION_PAGE_NUMBER = "pageNumber";
	 */
	public static final String REQUEST_TRAVELABLE_POSITION = "position";
	public static final String REQUEST_TRAVELABLE_PRE = "position_pre";
	public static final String REQUEST_TRAVELABLE_NEXT = "position_next";
	public static final String REQUEST_SEARCH_KEY = "search_form";
	public static final String REQUEST_LIST_KEY = "list_form";
	public static final String REQUEST_RESULT_KEY = "search_result";
	public static final String REQUEST_RESULT_TOTAL = "total";
	public static final String REQUEST_RESULT_TOTAL_PAGE = "total_page";
	public static final String REQUEST_URL_KEY = "url";
	public static final String REQUEST_DATA_ID = "id";
	public static final String REQUEST_PAGE = "page";
	public static final String REQUEST_DATA_KEY = "data";
	public static final String REQUEST_MSG = "message";
	public static final String REQUEST_SORT = "sort_by";

	public static final String REQUEST_LINK_TERM = "term";
	public static final String REQUEST_LINK_VALUE = "value";

	public static final String REQUEST_FOLDER_LIST = "folder_list";
	public static final String REQUEST_FOLDER_FORM = "folder_form";
	public static final String REQUEST_MAIL_FROM = "mail_form";
	public static final String REQUEST_MAIL_EMAIL = "mail_email";
	public static final String REQUEST_MAIL_BODY = "mail_body";
	public static final String REQUEST_MAIL_RESULT = "mail_result";
	public static final String REQUEST_ERROR = "error_message";
	public static final String REQUEST_EXCEPTION = "error_exception";
	public static final String REQUEST_RESULT_SIZE = "search_result_size";
	public static final String REQUEST_EXT_MATCH_ALL = "match_all";
	public static final String REQUEST_EXT_DOC_TYPE = "documentType";
	public static final String REQUEST_KEPT = "kept";
	

	protected String jspViewPath;
	protected String catalogViewURI;
	protected RecordDao dao;
	protected LuceneBuilder luceneBuilder;
	protected Map<String, String> catalogAdmin;
	protected SMTPMailSender mailSender;
	protected SessionCacheUtil cacheUtil;
	protected String[] projections = new String[] { "id", "title", "author",
			"year", "documentType" };

	/**
	 * For Spring configuration Setup the mailSender to send record by email.
	 * 
	 * @param mailSender
	 */
	public void setMailSender(SMTPMailSender mailSender) {
		this.mailSender = mailSender;
	}

	/**
	 * For Spring configuration Setup the WEB-INF/JSP page path.
	 * 
	 * @param catalogViewPath
	 */
	public void setJspViewPath(String catalogViewPath) {
		jspViewPath = catalogViewPath;
	}

	protected ModelAndView getModelAndView(String path) {
		StringBuffer sb = new StringBuffer(jspViewPath).append(
				StaticStrings.SLASH).append(path);
		return new ModelAndView(sb.toString()).addObject(REQUEST_URL_KEY,
				catalogViewURI).addObject(WebViewUtils.TILE_DEF, jspViewPath);
	}

	protected ModelAndView getPrintView(String path) {
		String tiles = new StringBuffer(jspViewPath).append(".")
				.append("printFolder").toString();
		StringBuffer sb = new StringBuffer(jspViewPath).append(
				StaticStrings.SLASH).append(path);
		return new ModelAndView(sb.toString()).addObject(REQUEST_URL_KEY,
				catalogViewURI).addObject(WebViewUtils.TILE_DEF, tiles);
	}

	/**
	 * This is the method that your basicSearch and advanceSearch should be
	 * call.
	 * 
	 * @see sample.capelin.mvc.samplecatalog.SampleRecordController
	 * 
	 * @param session
	 * @param searchObject
	 * @param page
	 * @return
	 */
	protected ModelAndView search(HttpSession session,
			SearchFormObject searchObject, int page) {
		if (1 >= page) {
			log.debug("search object attribute added: " + page);
			cacheUtil.setSearchObject(session, searchObject, jspViewPath);
		}
		if (searchObject == null)
			return defaultView();
		log.debug("document type is: " + searchObject.getDocumentType());
		Query q = searchObject.getLuceneQuery();
		Filter f = searchObject.getLuceneFilter();
		Sort s = searchObject.getLuceneSort();
		if (null == q) {
			q = luceneBuilder.buildQuery(searchObject);
			searchObject.setLuceneQuery(q);
			f = luceneBuilder.buildFilter(searchObject);
			searchObject.setLuceneFilter(f);
			s = luceneBuilder.buildSort(searchObject);
			searchObject.setLuceneSort(s);
		}

		PagedResults result = null;
		if (null != q) {
			result = dao.searchPaged(q, f, s, projections, page);
		}
		log.debug("Viewing page: " + page);
		cacheUtil.setSearchCurrentPageNumber(session, page, jspViewPath);
		ModelAndView mav = getModelAndView(searchObject.getResultView());
		mav.addObject(REQUEST_SEARCH_KEY, searchObject);
		if (null != result) {
			cacheUtil.setSearchTotalPageNumber(session,
					result.getTotalPageNumber(), jspViewPath);
			mav.addObject(REQUEST_RESULT_KEY, result.getResultList());
			mav.addObject(REQUEST_RESULT_TOTAL, result.getTotalResultNumber());
			mav.addObject(REQUEST_RESULT_TOTAL_PAGE,
					result.getTotalPageNumber());
			mav.addObject(REQUEST_PAGE, page);
		}
		mav.addObject(REQUEST_FOLDER_FORM, new FolderForm());
		
		boolean [] kept = new boolean [result.getResultList().size()];
		for(int i=0;i<kept.length;i++){			
			Object[] t = (Object[])result.getResultList().get(i);
			kept[i] = cacheUtil.isInKeptFolder((Integer)t[0], session, jspViewPath); 
		}
		return mav.addObject(REQUEST_KEPT, kept).addObject(REQUEST_RESULT_SIZE,dao.getPageSize());
	}

	/**
	 * Response the result for different page, after use search the database,
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	public ModelAndView searchPageTravel(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		String page = WebUtils.findParameterValue(request, REQUEST_PAGE);
		int pageNumber;
		if (null == page) {
			pageNumber = cacheUtil.getSearchCurrentPageNumber(session,
					jspViewPath);
		} else {
			pageNumber = Integer.parseInt(page);
		}
		ModelAndView mav = search(session,
				cacheUtil.getSearchObject(session, jspViewPath), pageNumber);
		return mav;
	}

	/**
	 * Response the request from link page (from url parameters), which used for
	 * author/subject browsing. Auto create a BasicSearchObject and store into
	 * the session for later browse.
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView searchByLink(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO debug
		String term = WebUtils.findParameterValue(request, REQUEST_LINK_TERM);
		String value = WebUtils.findParameterValue(request, REQUEST_LINK_VALUE);
		if (null == term || null == value)
			return defaultError(null, null);
		HttpSession session = request.getSession();
		BasicSearchObject sfo = getBasicSearchObject();
		sfo.setTerm(term);
		sfo.setMatchAll(false);
		log.debug("link value: " + value);
		sfo.setValue(WebViewUtils.getAllRequredTermValue(value,
			WebViewUtils.PLUS));

		sfo.setLuceneQuery(null);
		ModelAndView mav = search(session, sfo, 1);
		return mav;
	}

	public ModelAndView viewData(HttpServletRequest request,
			HttpServletResponse response) {
		String sId = WebUtils.findParameterValue(request, REQUEST_DATA_ID);
		if (null == sId)
			return defaultView();
		return viewDataById(request.getSession(), Integer.parseInt(sId));
	}

	protected ModelAndView viewDataById(HttpSession session, int id) {
		CapelinRecord record = null;
		try {
			record = dao.getRecord(id);
			return view(session, record);
		} catch (HibernateObjectRetrievalFailureException e) {
			return defaultError("Record not found.", e);
		}
	}

	public ModelAndView viewDataWithTravel(HttpServletRequest request,
			HttpServletResponse response) {
		String sId = WebUtils.findParameterValue(request, REQUEST_DATA_ID);
		HttpSession session = request.getSession();
		SearchFormObject sfo = cacheUtil.getSearchObject(session, jspViewPath);
		int pageNumber = cacheUtil.getSearchCurrentPageNumber(session,
				jspViewPath);
		if (null == sId)
			return defaultView();
		int id = Integer.parseInt(sId);
		if (null == sfo || pageNumber < 1 || null == sfo.getLuceneQuery()) {
			return viewDataById(request.getSession(), id);
		}
		Query luceneQuery = sfo.getLuceneQuery();
		try {

			PagedRecord prd = dao.getRecordWithOthers(id, luceneQuery,
					sfo.getLuceneFilter(), sfo.getLuceneSort(), pageNumber);
			return getDataTravelView(session, prd, pageNumber);
		} catch (HibernateObjectRetrievalFailureException e) {
			return defaultError("Record not found.", e);
		}
	}

	public ModelAndView viewDataTravel(HttpServletRequest request,
			HttpServletResponse response) {
		String sPos = WebUtils.findParameterValue(request,
				REQUEST_TRAVELABLE_POSITION);

		HttpSession session = request.getSession();
		List<Integer> ids = cacheUtil.getSearchResultIds(session, jspViewPath);
		int pageNumber = cacheUtil.getSearchCurrentPageNumber(session,
				jspViewPath);
		log.debug("view data travel, id: " + ids + " sPos: " + sPos + " page: "
				+ pageNumber);
		if (null == ids || null == sPos || StaticStrings.EMPLTY.equals(sPos)
				|| StaticStrings.EMPLTY.equals(ids) || pageNumber < 1)
			return defaultView();
		int position = Integer.parseInt(sPos);
		if (position >= 0 && position < ids.size()) { // in the cache range
			cacheUtil.setSearchResultPosition(session, position, jspViewPath);
			int id = ids.get(position);
			ModelAndView mav = viewDataById(session, id);
			setUpDataTravelMenu(session, mav, position, ids.size(), pageNumber);
			return mav;
		} else {
			boolean first = false;
			if (position < 0 && pageNumber > 1) { // previous page
				pageNumber--;

			} else if (cacheUtil.getSearchTotalPageNumber(session, jspViewPath) > pageNumber) {
				pageNumber++;
				first = true;
			}
			cacheUtil.setSearchCurrentPageNumber(session, pageNumber,
					jspViewPath);
			SearchFormObject sfo = cacheUtil.getSearchObject(session,
					jspViewPath);
			PagedRecord prd = dao.getRecordByPage(sfo.getLuceneQuery(),
					sfo.getLuceneFilter(), sfo.getLuceneSort(), pageNumber,
					first);
			return getDataTravelView(session, prd, pageNumber);
		}
	}

	private ModelAndView getDataTravelView(HttpSession session,
			PagedRecord prd, int currentPage) {
		CapelinRecord record = prd.getCapelinRecord();
		cacheUtil.setSearchResultIds(session, prd.getResultIds(), jspViewPath);
		cacheUtil.setSearchResultPosition(session, prd.getPosition(),
				jspViewPath);
		ModelAndView mav = view(session, record).addObject(
				REQUEST_TRAVELABLE_POSITION, prd.getPosition());
		setUpDataTravelMenu(session, mav, prd.getPosition(), prd.getResultIds()
				.size(), currentPage);
		return mav;
	}

	private void setUpDataTravelMenu(HttpSession session, ModelAndView mav,
			int position, int pageSize, int currentPage) {
		int totalPages = cacheUtil.getSearchTotalPageNumber(session,
				jspViewPath);
		mav.addObject(REQUEST_TRAVELABLE_POSITION, position).addObject(REQUEST_PAGE, currentPage);
		if ((currentPage > 1) || position > 0) {
			mav.addObject(REQUEST_TRAVELABLE_PRE, position - 1);
		}
		if (currentPage < totalPages || position < pageSize - 1) {
			mav.addObject(REQUEST_TRAVELABLE_NEXT, position + 1);
		}
	}

	/**
	 * Initial page to edit data. If user is not login in, which should never
	 * the case, forward the user to login page.
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toEditData(HttpServletRequest request,
			HttpServletResponse response) {
		if (!isLogin(request.getSession())) {
			return toLogin(request, response);
		}
		String id = WebUtils.findParameterValue(request, REQUEST_DATA_ID);

		CapelinRecord record = null;
		try {
			record = dao.getRecord(Integer.parseInt(id));
			ModelAndView mav = getModelAndView("edit");
			mav.addObject(REQUEST_MSG, "Update");
			mav.addObject(REQUEST_DATA_KEY, record);
			return mav;
		} catch (HibernateObjectRetrievalFailureException e) {
			return defaultError("Record not found.", e);
		}
	}

	/**
	 * Initial page to add new data. If user is not login in, which should never
	 * the case, forward the user to login page.
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toNewData(HttpServletRequest request,
			HttpServletResponse response) {
		if (!isLogin(request.getSession())) {
			return toLogin(request, response);
		}
		ModelAndView mav = getModelAndView("edit");
		mav.addObject(REQUEST_DATA_KEY, dao.newRecord());
		mav.addObject(REQUEST_MSG, "Add");
		return mav;
	}

	/**
	 * Initial page to add new data. If user is not login in, which should never
	 * the case, forward the user to login page.
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toSimilar(HttpServletRequest request,
			HttpServletResponse response) {
		return getModelAndView("similar").addObject(REQUEST_SEARCH_KEY,
				new SimilarForm());
	}

	/**
	 * Initial page to add new data. If user is not login in, which should never
	 * the case, forward the user to login page.
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView searchSimilar(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, SimilarForm form) {
		// TODO
		Query luceneQuery = luceneBuilder.buildSimilarQuery(form, false);
		List<String> daoResult = dao
				.getResultTerms(form.getTerm(), luceneQuery);
		if (daoResult.size() <= dao.getPageSize() / 2) {
			daoResult = dao.getResultTerms(form.getTerm(),
					luceneBuilder.buildSimilarQuery(form, true));
		}
		return getModelAndView("similar").addObject(REQUEST_SEARCH_KEY, form)
				.addObject(REQUEST_MSG, REQUEST_MSG)
				.addObject(REQUEST_RESULT_KEY, daoResult);
	}

	/**
	 * Initial page to list data. If user is not login in, which should never
	 * the case, forward the user to login page.
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toList(HttpServletRequest request,
			HttpServletResponse response) {
		if (!isLogin(request.getSession())) {
			return toLogin(request, response);
		}
		ModelAndView mav = getModelAndView("list");
		mav.addObject(REQUEST_LIST_KEY, new ListForm());
		return mav;
	}

	/**
	 * 
	 * Response the list data request. Access the database and sent back the
	 * data.
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param listForm
	 * @return
	 */
	public ModelAndView listCatalog(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, ListForm listForm) {
		return list(session, listForm, 1);
	}

	/**
	 * 
	 * Response the list data with different page. Access the database and sent
	 * back the data.
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	public ModelAndView listCatalogByPage(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		String page = WebUtils.findParameterValue(request, REQUEST_PAGE);
		int pageNumber;
		if (null == page) {
			return toList(request, response);
		} else {
			pageNumber = Integer.parseInt(page);
		}
		ListForm listForm = cacheUtil.getListForm(session, jspViewPath);
		ModelAndView mav = list(session, listForm, pageNumber);

		return mav;
	}

	/**
	 * 
	 * Initial the mail data request.
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toMailFolder(HttpServletRequest request,
			HttpServletResponse response) {
		return getModelAndView("sendMail").addObject(REQUEST_MAIL_FROM,
				new MailForm());
	}

	/**
	 * 
	 * Response the mail data request. call mail sender and send the email.
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 * @return
	 */
	public ModelAndView mailFolder(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, MailForm form) {
		String captcha = cacheUtil.getCaptcha(session);
		if (!form.getCaptcha().equals(captcha)) {
			return toMailFolder(request, response).addObject(REQUEST_ERROR,
					"Captcha not match.");
		}

		List<CapelinRecord> recordList = cacheUtil.getFolderRecords(session,
				jspViewPath);
		if (null == recordList || recordList.size() <= 0)
			return defaultView();
		StringBuffer sb = new StringBuffer(getEmailPreText());
		for (CapelinRecord r : recordList) {
			sb.append("\n-------------- Capelin OPAC Record --------------\n")
					.append(r);
		}
		String body = sb.toString();
		boolean sent = false;
		try {
			sent = mailSender.sendText(form.getEmail(), body);
		} catch (Exception e) {
			log.warn("Unexpected error during email sending: " + e);
		}
		if (!sent) {
			body = "Email sending failed to " + form.getEmail();
		}
		return getModelAndView("mailResult")
				.addObject(REQUEST_MAIL_EMAIL, form.getEmail())
				.addObject(REQUEST_MAIL_BODY, body)
				.addObject(REQUEST_MAIL_RESULT, sent);
	}

	/**
	 * Retrieve the data from database.
	 * 
	 * @param session
	 * @param listForm
	 * @param page
	 * @return
	 */
	protected ModelAndView list(HttpSession session, ListForm listForm, int page) {
		PagedResults result = null;

		boolean ascending = !WebViewUtils.ZERO.equals(listForm.getOrderBy());
		result = dao.listPaged(listForm.getField(), listForm.getStartWith(),
				page, ascending);

		log.debug("Viewing page: " + page);
		cacheUtil.setListPageNumber(session, page, jspViewPath);
		cacheUtil.setListForm(session, listForm, jspViewPath);

		ModelAndView mav = getModelAndView("list");
		mav.addObject(REQUEST_LIST_KEY, listForm);
		if (null != result) {
			mav.addObject(REQUEST_RESULT_KEY, result.getResultList());
			mav.addObject(REQUEST_RESULT_TOTAL, result.getTotalResultNumber());
			mav.addObject(REQUEST_RESULT_TOTAL_PAGE,
					result.getTotalPageNumber());
			mav.addObject(REQUEST_PAGE, page);
		}
		return mav;
	}

	/**
	 * delete the data. If the user is not logged in, return to the login page.
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView deleteData(HttpServletRequest request,
			HttpServletResponse response) {
		if (!isLogin(request.getSession())) {
			return toLogin(request, response);
		}
		String sId = WebUtils.findParameterValue(request, REQUEST_DATA_ID);
		log.info("delete record id: " + sId);
		int id = Integer.parseInt(sId);
		dao.deleteRecord(id);
		HttpSession session = request.getSession();
		List<CapelinRecord> recordList = cacheUtil.getFolderRecords(session,
				jspViewPath);
		for (int i = 0; i < recordList.size(); i++) {
			CapelinRecord r = recordList.get(i);
			if (r.getId() == id) {
				recordList.remove(i);
				break;
			}
		}
		return searchPageTravel(request, response, session);
	}

	/**
	 * Actual saveData Use dao to save the data to database. If the user is not
	 * logged in, return to the login page.
	 * 
	 * @param session
	 * @param data
	 * @return ModelAndView
	 */
	protected ModelAndView saveData(HttpServletRequest request,
			HttpServletResponse response, Object data) {
		log.debug("save record: " + data);
		if (!isLogin(request.getSession())) {
			return toLogin(request, response);
		}
		dao.saveRecord(data);
		ModelAndView mav = getModelAndView("view");
		mav.addObject(REQUEST_DATA_KEY, data);
		return mav;
	}

	protected ModelAndView defaultView() {
		SearchFormObject form = getBasicSearchObject();
		return getModelAndView(form.getResultView()).addObject(
				REQUEST_SEARCH_KEY, form);
	}

	protected ModelAndView defaultError(String message, Exception e) {
		message = (null == message) ? "Requested item not found." : message;
		return getModelAndView("error").addObject(REQUEST_ERROR, message)
				.addObject(REQUEST_EXCEPTION, e);
	}

	/**
	 * Initial page for advance search
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toAdvancedSearch(HttpServletRequest request,
			HttpServletResponse response) {
		SearchFormObject form = getAdvancedSearchObject();
		return getModelAndView(form.getResultView()).addObject(
				REQUEST_SEARCH_KEY, form);
	}

	/**
	 * Initial page for basic page
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toBasicSearch(HttpServletRequest request,
			HttpServletResponse response) {
		return defaultView();
	}

	/**
	 * response the login request. Check the user name and password are
	 * identical to the catalogAdmin Map.
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param loginForm
	 * @return defaultView if successful or remain at the toLogin page
	 */
	public ModelAndView login(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			LoginForm loginForm) {
		String password = catalogAdmin.get(loginForm.getUsername());
		String captcha = cacheUtil.getCaptcha(session);
		if (!loginForm.getCaptcha().equals(captcha)) {
			return toLogin(request, response).addObject(REQUEST_ERROR,
					"Captcha not match.");
		}
		boolean login = (password != null && password.equals(loginForm
				.getPassword()));
		if (login) {
			cacheUtil.setValue(session, jspViewPath, loginForm.getUsername());
			return defaultView();
		}
		return toLogin(request, response).addObject(REQUEST_ERROR,
				"Username/Password not match.");
	}

	/**
	 * Initial the login page.
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toLogin(HttpServletRequest request,
			HttpServletResponse response) {
		return getModelAndView("login").addObject(new LoginForm());
	}

	/**
	 * clean the logged in information from cache.
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	public ModelAndView logout(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		cacheUtil.deleteValue(session, jspViewPath, jspViewPath);
		return defaultView();
	}

	/**
	 * remove all kept records.
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return last view of search result.
	 */
	public ModelAndView clearFolder(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		List<CapelinRecord> recordList = cacheUtil.getFolderRecords(session,
				jspViewPath);
		recordList.clear();
		return searchPageTravel(request, response, session);
	}

	/**
	 * Remove the kept the record from the kept folder.
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 * @return
	 */
	public ModelAndView deleteFromFolder(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, FolderForm form) {
		List<CapelinRecord> recordList = cacheUtil.getFolderRecords(session,
				jspViewPath);
		for (Integer id : form.getIds()) {
			for (int i = 0; i < recordList.size(); i++) {
				CapelinRecord r = recordList.get(i);
				if (r.getId() == id) {
					recordList.remove(i);
					break;
				}
			}
		}
		return viewFolder(recordList, form);
	}

	/**
	 * Response view data request first check if it in the kept folder,
	 * otherwise retrieve from database
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	public ModelAndView viewCacheData(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		String id = WebUtils.findParameterValue(request, REQUEST_DATA_ID);
		int intId = Integer.parseInt(id);
		CapelinRecord target = findDataFromSession(session,intId);
		if (null == target) {
			log.info("View cached data that not in cache. id: " + id);
			return viewDataById(session, intId);
		}
		return view(session, target);
	}
	
	protected CapelinRecord findDataFromSession(HttpSession session, int id){
		List<CapelinRecord> recordList = cacheUtil.getFolderRecords(session,
				jspViewPath);
		for (CapelinRecord record : recordList) {
			if (record.getId() == id) {
				return record;
			}
		}		
		return null;
	}

	protected ModelAndView view(HttpSession session, CapelinRecord record) {
		ModelAndView mav = getModelAndView("view");
		mav.addObject(REQUEST_DATA_KEY, record);
		if(cacheUtil.isInKeptFolder(record.getId(), session, jspViewPath)){
			mav.addObject(REQUEST_MSG, REQUEST_MSG);
		}
		return mav;
	}

	/**
	 * View the kept folder, if request sort, sort it.
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	public ModelAndView viewFolder(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		String sort = WebUtils.findParameterValue(request, REQUEST_SORT);
		List<CapelinRecord> recordList = cacheUtil.getFolderRecords(session,
				jspViewPath);
		RecordSorter.getInstance().sort(recordList, sort);
		return viewFolder(recordList, new FolderForm());
	}

	/**
	 * Response add record to kept folder. get all records from database and
	 * then store it to session.
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @param form
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView addToFolder(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, FolderForm form) {
		List<CapelinRecord> recordList = cacheUtil.getFolderRecords(session,
				jspViewPath);
		List<Integer> idList = Arrays.asList(form.getIds());
		if (idList.size() < 1)
			return searchPageTravel(request, response, session);
		recordList.addAll((List<CapelinRecord>) (dao.getRecords(idList)));
		return viewFolder(recordList, form);
	}

	/*
	 * Position not working yet.
	 */
	public ModelAndView addOneToFolder(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		String sId = WebUtils.findParameterValue(request, REQUEST_DATA_ID);
		String sPos = WebUtils.findParameterValue(request,
				REQUEST_TRAVELABLE_POSITION);
		if (null == sId)
			return defaultView();
		int id = Integer.parseInt(sId);
		List<CapelinRecord> recordList = cacheUtil.getFolderRecords(session,
				jspViewPath);
		recordList.add(dao.getRecord(id));
		if (null == sPos || StaticStrings.EMPLTY.equals(sPos))
			return viewFolder(request, response, request.getSession());
		return viewDataTravel(request, response);
	}
	
	public ModelAndView externalLinkSearch(HttpServletRequest request,
			HttpServletResponse response) {
		String docType = WebUtils.findParameterValue(request, REQUEST_EXT_DOC_TYPE);
		String term = WebUtils.findParameterValue(request, REQUEST_LINK_TERM);
		String value = WebUtils.findParameterValue(request, REQUEST_LINK_VALUE);
		String matchAll = WebUtils.findParameterValue(request, REQUEST_EXT_MATCH_ALL);
		if (null == value)
			return defaultError(null, null);
		term = (null == term)?"":term;
		boolean match = (null == matchAll)?false:true;
		HttpSession session = request.getSession();
		BasicSearchObject sfo = getBasicSearchObject();
		sfo.setTerm(term);
		sfo.setValue(value);
		sfo.setMatchAll(match);
		sfo.setDocumentType(docType);
		ModelAndView mav = search(session, sfo, 1);
		Integer i = (Integer) mav.getModel().get(REQUEST_RESULT_TOTAL);
		if (i <= 0) {
			sfo.setValue(WebViewUtils.getAllRequredTermValue(value,
					WebViewUtils.PLUS));
			sfo.setLuceneQuery(null);
			mav = search(session, sfo, 1);
		}
		return mav;
	}	

	public ModelAndView viewDataPlain(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		String id = WebUtils.findParameterValue(request, REQUEST_DATA_ID);
		int intId = Integer.parseInt(id);
		CapelinRecord target = findDataFromSession(session, intId);
		if (null == target) {
			target = dao.getRecord(intId);
		}
		return getAjaxView("ajax-view").addObject(REQUEST_DATA_KEY, target);
	}

	protected ModelAndView getAjaxView(String path) {
		String tiles = "ajax";
		StringBuffer sb = new StringBuffer(jspViewPath).append(
				StaticStrings.SLASH).append(path);
		return new ModelAndView(sb.toString()).addObject(REQUEST_URL_KEY,
				catalogViewURI).addObject(WebViewUtils.TILE_DEF, tiles);
	}	
	/**
	 * Get all kept record, and then posted to another page for print.
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	public ModelAndView printFolder(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		List<CapelinRecord> recordList = cacheUtil.getFolderRecords(session,
				jspViewPath);
		return getPrintView("printFolder").addObject(REQUEST_FOLDER_LIST,
				recordList);
	}

	private ModelAndView viewFolder(List<CapelinRecord> recordList,
			FolderForm form) {
		ModelAndView mav = getModelAndView("viewFolder");
		mav.addObject(REQUEST_FOLDER_LIST, recordList);
		form.setIds(null);
		mav.addObject(REQUEST_FOLDER_FORM, form);
		return mav;
	}

	/**
	 * Check if the user is logged in.
	 * 
	 * @param session
	 * @return
	 */
	protected boolean isLogin(HttpSession session) {
		return cacheUtil.doesContain(session, jspViewPath);
	}

	/**
	 * For Spring configuration Setup the Data Access Object
	 * 
	 * @param dao
	 */
	public void setDao(RecordDao dao) {
		this.dao = dao;
	}

	/**
	 * For Spring configuration Setup LuceneBuilder This can be the
	 * MultiIndexLuceneBuilder.
	 * 
	 * @param queryBuilder
	 */
	public void setLuceneBuilder(LuceneBuilder queryBuilder) {
		this.luceneBuilder = queryBuilder;
	}

	/**
	 * For Spring configuration Set the Web URI
	 * 
	 * @param catalogViewURI
	 */
	public void setCatalogViewURI(String catalogViewURI) {
		this.catalogViewURI = catalogViewURI;
	}

	/**
	 * For Spring configuration Set user name and password map.
	 * 
	 * @param catalogAdmin
	 */
	public void setCatalogAdmin(Map<String, String> catalogAdmin) {
		this.catalogAdmin = catalogAdmin;
	}

	@Override
	protected ModelAndView handleNoSuchRequestHandlingMethod(
			NoSuchRequestHandlingMethodException ex,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return defaultView();
	}

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav;
		try {
			mav = super.handleRequestInternal(request, response);
		} catch (HttpSessionRequiredException ex) {
			mav = defaultError("Session has expired!", ex);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			mav = defaultError("Runtime Error!", ex);
		}
		return mav;
	}

	protected BasicSearchObject getBasicSearchObject() {
		return new BasicSearchObject();
	}

	protected AdvancedSearchObject getAdvancedSearchObject() {
		return new AdvancedSearchObject();
	}

	public void setProjections(String projection) {
		projections = projection.trim().split(",");
	}

	public void setSessionCacheUtil(SessionCacheUtil cacheUtil) {
		this.cacheUtil = cacheUtil;
	}

	protected String getEmailPreText() {
		return "A few records from Capelin-OPAC\n\n";
	}
}