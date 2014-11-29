/**
 * 
 */
package com.stickynotes.dropbox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;

/**
 * @author Varun
 *
 */
public class StickyNotesDbx {

	public static boolean userAuthentication(String appKey, String appSecret) {
		boolean result = true;
		try {
			DbxAppInfo appInfo = new DbxAppInfo(appKey, appSecret);
			DbxRequestConfig config = new DbxRequestConfig("JavaTutorial/1.0", Locale.getDefault().toString());
		    DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(config, appInfo);
		    
		    //Have the user sign in and authorize your app.
		    String authorizeUrl = webAuth.start();
		    System.out.println("1. Go to: " + authorizeUrl);
		    System.out.println("2. Click \"Allow\" (you might have to log in first)");
		    System.out.println("3. Copy the authorization code.");
		    
		    //catch IOException
			String code = new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
			
			//catch DbxException
			DbxAuthFinish authFinish = webAuth.finish(code);
			String accessToken = authFinish.accessToken;
			
			DbxClient client = new DbxClient(config, accessToken);
			System.out.println("Linked account: " + client.getAccountInfo().displayName);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			result = false;
		} catch (DbxException dbxe) {
			dbxe.printStackTrace();
			result = false;
		} catch (Exception expt) {
			expt.printStackTrace();
			result = false;
		}
		
		return result;
	}
}
