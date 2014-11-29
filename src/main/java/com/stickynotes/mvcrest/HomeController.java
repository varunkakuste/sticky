package com.stickynotes.mvcrest;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxSessionStore;
import com.dropbox.core.DbxStandardSessionStore;
import com.dropbox.core.DbxWebAuth;
import com.dropbox.core.DbxWriteMode;
import com.stickynotes.forms.FilesForm;
import com.stickynotes.forms.LoginForm;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	DbxAppInfo appInfo;
	DbxRequestConfig config;
	DbxWebAuth webAuth;
    DbxAuthFinish authFinish;
    DbxClient client;
    
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate );
		return "home";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginPage() {
		logger.info("HomeController class --->>> loginPage() Method Start");
		LoginForm loginForm = new LoginForm();
		logger.info("HomeController class --->>> loginPage() Method End");
        return new ModelAndView("login" , "loginForm", loginForm);
    }
	
	@RequestMapping(value = "/home", method = RequestMethod.POST)
    public String login(@ModelAttribute("loginForm") LoginForm loginForm, HttpServletRequest request, HttpServletResponse response) throws DbxException, IOException {
		logger.info("HomeController class --->>> login() Method Start");
		String appKey = loginForm.getAppKey(); //z7fbx59ihvmdpgo
		String appSecret = loginForm.getAppSecret(); //f6b7wd2f1m5hdbg
		
		appInfo = new DbxAppInfo(appKey, appSecret);
		config = new DbxRequestConfig("JavaTutorial/1.0", Locale.getDefault().toString());
	    String redirectUri = "http://localhost:8080/mvcrest/callback";
	    HttpSession session = request.getSession(true);
	    String sessionKey = "dropbox-auth-csrf-token";
	    DbxSessionStore csrfTokenStore = new DbxStandardSessionStore(session, sessionKey);
	    webAuth = new DbxWebAuth(config, appInfo, redirectUri, csrfTokenStore);
	    
	    //Have the user sign in and authorize your app.
	    String authorizeUrl = webAuth.start();
	    System.out.println("Authorization Url " + authorizeUrl);
	    logger.info("HomeController class --->>> login() Method End");
		return "redirect:" +authorizeUrl;
    }
	
	@RequestMapping(value = "/callback", params = "code", method = RequestMethod.GET)
	@ResponseBody
    public ModelAndView accessCode(@RequestParam("code") String code, HttpServletRequest request, HttpServletResponse response) {
		logger.info("HomeController class --->>> accessCode() Method Start");

		ModelAndView modelView = null;
		DbxEntry.WithChildren listing = null;
		FilesForm filesForm = null;
		List<String> filesList = new ArrayList<String>();
		try {
			authFinish = webAuth.finish(request.getParameterMap());
			String accessToken = authFinish.accessToken;
			
			client = new DbxClient(config, accessToken);
			System.out.println("Linked account: " + client.getAccountInfo().displayName);
			
			//get the files from the Dropbox account
			listing = client.getMetadataWithChildren("/");
			System.out.println("Files in the root path:");
			for (DbxEntry child : listing.children) {
			    System.out.println("Name -> " + child.name + ":\n Description:\n" + child.toString());
			    filesList.add(child.name);
			}
			
			//get the values & set in the form to display it on stickynotes.jsp
			filesForm = new FilesForm();
			filesForm.setLoggedInUser(client.getAccountInfo().displayName);
			filesForm.setFilesToList(filesList);
			modelView = new ModelAndView("stickynotes" , "filesForm", filesForm);
			
		} catch (Exception e) {
			e.printStackTrace();
			modelView = loginPage();
		}
		
		logger.info("HomeController class --->>> accessCode() Method End");
		return modelView;
    }
	
	@RequestMapping(value = "/callback", params = "error_reason", method = RequestMethod.GET)
	@ResponseBody
    public ModelAndView error(@RequestParam("error_reason") String errorReason, @RequestParam("error") String error, @RequestParam("error_description") String description) {
		try {
			System.out.println("\nIn callback Error\n\n");
			System.out.println(errorReason);
			System.out.println(error);
			System.out.println(description);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return loginPage();
    }
	
	@RequestMapping(value = "/upload", method = RequestMethod.GET)
    public ModelAndView upload() {
		logger.info("HomeController class --->>> upload() Method");
        return new ModelAndView("uploadFile");
    }
	
	@RequestMapping(value = "/savefile", method = RequestMethod.POST)
    public ModelAndView save(@RequestParam("file") MultipartFile file) throws IllegalStateException, IOException, DbxException {
		logger.info("HomeController class --->>> save() Method Start");
		
		ModelAndView modelView = null;
		DbxEntry.WithChildren listing = null;
		List<String> filesList = new ArrayList<String>();
		FilesForm filesForm = null;
        
        String name = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();
        try {
            DbxEntry.File uploadedFile = client.uploadFile("/" + name, DbxWriteMode.add(), file.getSize(), inputStream);
            System.out.println("Uploaded: " + uploadedFile.toString());
        } finally {
            inputStream.close();
        }
        
        listing = client.getMetadataWithChildren("/");
		for (DbxEntry child : listing.children) {
		    filesList.add(child.name);
		}
		filesForm = new FilesForm();
		filesForm.setLoggedInUser(client.getAccountInfo().displayName);
		filesForm.setFilesToList(filesList);
		modelView = new ModelAndView("stickynotes" , "filesForm", filesForm);
		
		logger.info("HomeController class --->>> save() Method End");
		return modelView;
    }
	
}
