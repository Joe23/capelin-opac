package test.capeline.mvc;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import junit.framework.TestCase;

import org.capelin.mvc.controller.CatalogRecordController;
import org.capelin.mvc.utils.CapelinSpringContext;
import org.capelin.mvc.web.form.LoginForm;
import org.capelin.transaction.dao.RecordDao;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.kaptcha.Constants;

import sample.capelin.mvc.samplecatalog.SampleRecordController;

/**
 * 
 * <a href="https://github.com/Joe23/capelin-opac/">Capelin-opac</a>
 * License: GNU AGPL v3 | http://www.gnu.org/licenses/agpl.html 
 *
 * @author Jing Xiao <jing.xiao.ca at gmail dot com>
 *
 */
public class WebTest extends TestCase {
	//private static final Log log = LogFactory.getLog(WebTest.class);

	private SampleRecordController src;
	private RecordDao dao;
	//private LuceneBuilder luceneBuilder = new LuceneBuilder();
	private HttpServletRequest req = new MockHttpServletRequest();	

	
	protected void setUp() {
		// SessionFactory sf =
		// SpringContext.getBeanFactory().getBean(HibernateTransactionManager.class).getSessionFactory();
		src = (SampleRecordController) CapelinSpringContext.getBeanFactory().getBean("catalogController");
		dao = (RecordDao) CapelinSpringContext.getBeanFactory().getBean("sampleDao");
	}
	
	public void blogin(){
		ModelAndView mav = src.toLogin(new MockHttpServletRequest(), new MockHttpServletResponse());
		Map<String, Object> m = mav.getModel();
		assertTrue(m.get(CatalogRecordController.REQUEST_URL_KEY).equals("sample.cat?do="));
		assertNotNull(m.get("loginForm"));		
	}
	
	public void login(){
		LoginForm loginForm = new LoginForm();
		loginForm.setUsername("capelin");
		String captcha = "abcdefg";
		loginForm.setCaptcha(captcha);
		
		// no password, no captcha
		ModelAndView mav = src.login(req, new MockHttpServletResponse(), req.getSession(), loginForm);
		ModelAndView exp = src.toLogin(new MockHttpServletRequest(), new MockHttpServletResponse()); 
		assertEquals(exp.getViewName(),mav.getViewName());
		
		// no password
		req.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY,captcha);		
		mav = src.login(req, new MockHttpServletResponse(), req.getSession(), loginForm);
		assertEquals(exp.getViewName(),mav.getViewName());
		
		// password not match
		loginForm.setPassword("opac1");
		mav = src.login(req, new MockHttpServletResponse(), req.getSession(), loginForm);
		assertEquals(exp.getViewName(),mav.getViewName());
		
		// no captcha		
		req.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY,null);
		loginForm.setPassword("opac");
		mav = src.login(req, new MockHttpServletResponse(), req.getSession(), loginForm);
		assertEquals(exp.getViewName(),mav.getViewName());
		
		//success
		req.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY,captcha);
		mav = src.login(req, new MockHttpServletResponse(), req.getSession(), loginForm);
		System.out.println(mav.getViewName());
		exp = src.toBasicSearch(new MockHttpServletRequest(), new MockHttpServletResponse());
		assertEquals(exp.getViewName(),mav.getViewName());
	}
	
	public void testController(){
		blogin();
		login();
		//TODO: add/delete/search record.
	}
	/*
	public void testDao(){
		String [] result = dao.moreLikeThis("title", "care");
		System.out.println(result.length);
		for(String s : result){
			System.out.println(s);
		}
	}
	*/
	

}
