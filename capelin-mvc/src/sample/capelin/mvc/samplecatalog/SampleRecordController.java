package sample.capelin.mvc.samplecatalog;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.capelin.mvc.controller.CatalogRecordController;
import org.capelin.mvc.utils.WebViewUtils;
import org.capelin.mvc.web.form.ARangedSearchObject;
import org.capelin.mvc.web.form.BasicSearchObject;
import org.capelin.mvc.web.form.SearchFormObject;
import org.springframework.web.servlet.ModelAndView;

public class SampleRecordController extends CatalogRecordController {

	public ModelAndView saveData(HttpServletRequest request,
			HttpServletResponse response, SampleMVCRecord record) {
		return super.saveData(request, response, record);
	}
	
	public ModelAndView searchBasic(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			BasicSearchObject command) {
		return search(session, command,1);
	}	

	public ModelAndView searchAdvanced(HttpServletRequest request,
			HttpServletResponse response, HttpSession session,
			ARangedSearchObject command) {
		return search(session, command,1);
	}
	
	@Override
	public ModelAndView toAdvancedSearch(HttpServletRequest request,
			HttpServletResponse response) {
		SearchFormObject form = new ARangedSearchObject();
		return getModelAndView(form.getResultView()).addObject(
				REQUEST_SEARCH_KEY, form);
	}
}
