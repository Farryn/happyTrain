/**
 * 
 */
package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import services.RunService;
import servlets.ShowFoundTrainsServlet;
import valueobjects.RunVO;

/**
 * @author Damir Tuktamyshev
 *
 */
@Controller
public class ShowRunController {

	/**
	 * 
	 */
	public ShowRunController() {
	}

	/**
	 * Logger instance.
	 */
	private static final Logger LOG = Logger.getLogger(ShowFoundTrainsServlet.class);
       
	
	
	/** Process data from request.
     * @param req HttpServletRequest Object
     * @return page 
     */
	@RequestMapping(value = "/run", method = RequestMethod.GET)
	public String processGet(HttpServletRequest req) {
		LOG.info("Getting parameters from GET");
    	int trainId = Integer.parseInt(req.getParameter("train"));
    	
    	LOG.info("Getting Runs list from RunService");
		List<RunVO> runList = new ArrayList<RunVO>();
		try {
			runList = new RunService().getRunByTrainId(trainId);
		} catch (Exception e) {
			LOG.warn("Exception: " + e);
			LOG.info("No result was found");
			req.setAttribute("emptyList", 1);
		}
		req.setAttribute("runList", runList);
		return "protected/ShowRun";
	}
}