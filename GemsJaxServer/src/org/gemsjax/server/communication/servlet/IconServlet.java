package org.gemsjax.server.communication.servlet;

import gwtupload.server.UploadAction;
import gwtupload.server.exceptions.UploadActionException;
import gwtupload.shared.UConsts;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;
import java.util.Hashtable;
import java.util.List;
import java.util.Iterator;
import java.util.Random;



public class IconServlet extends UploadAction {

	  private static final long serialVersionUID = 1L;
	  
	  Hashtable<String, String> receivedContentTypes = new Hashtable<String, String>();
	  /**
	   * Maintain a list with received files and their content types. 
	   */
	  Hashtable<String, File> receivedFiles = new Hashtable<String, File>();
	  
	  
	  private String uploadFolder;
	  
	  public IconServlet(String uploadFolder){
		  
		  this.uploadFolder = uploadFolder;
		  
	  }
	  

	  /**
	   * Override executeAction to save the received files in a custom place
	   * and delete this items from session.  
	   */
	  @Override
	  public String executeAction(HttpServletRequest request, List<FileItem> sessionFiles) throws UploadActionException {
	    String response = "";
	    int cont = 0;
	    for (FileItem item : sessionFiles) {
	      if (false == item.isFormField()) {
	        cont ++;
	        try {
	      
	        	
	        	String fileName = item.getName();

                //  String root = getServletContext().getRealPath("/");
                  File path = new File(uploadFolder);
                  if (!path.exists()) {
                      boolean status = path.mkdirs();
                  }

                  Random random = new Random();
                  String randomisedfileName = random.nextInt()+ fileName;
                  File uploadedFile = new File(path + "/" +randomisedfileName);
                  while (uploadedFile.exists()){
                	randomisedfileName = random.nextInt()+ fileName;
                  	uploadedFile = new File(path + "/" + randomisedfileName);
                  }
                  
	          
	          item.write(uploadedFile);
	          
	          /// Save a list with the received files
	          receivedFiles.put(item.getFieldName(), uploadedFile);
	          receivedContentTypes.put(item.getFieldName(), item.getContentType());
	          
	          /// Send a customized message to the client.
	          response += "/icons/"+randomisedfileName;

	        } catch (Exception e) {
	          throw new UploadActionException(e);
	        }
	      }
	    }
	    
	    /// Remove files from session because we have a copy of them
	    removeSessionFileItems(request);
	    
	    /// Send your customized message to the client.
	    return response;
	  }
	  
	  /**
	   * Get the content of an uploaded file.
	   */
	  @Override
	  public void getUploadedFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    String fieldName = request.getParameter(UConsts.PARAM_SHOW);
	    File f = receivedFiles.get(fieldName);
	    if (f != null) {
	      response.setContentType(receivedContentTypes.get(fieldName));
	      FileInputStream is = new FileInputStream(f);
	      copyFromInputStreamToOutputStream(is, response.getOutputStream());
	    } else {
	      renderXmlResponse(request, response, XML_ERROR_ITEM_NOT_FOUND);
	   }
	  }
	  
	  /**
	   * Remove a file when the user sends a delete request.
	   */
	  @Override
	  public void removeItem(HttpServletRequest request, String fieldName)  throws UploadActionException {
	    File file = receivedFiles.get(fieldName);
	    receivedFiles.remove(fieldName);
	    receivedContentTypes.remove(fieldName);
	    if (file != null) {
	      file.delete();
	    }
	  }
	}






/*
public class IconServlet extends HttpServlet{
    private static final long serialVersionUID = -3208409086358916855L;

    private String uploadFolder;
    
    public IconServlet(String uploadFolder){
    	this.uploadFolder = uploadFolder;
    }
    
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        if (isMultipart) {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);

            try {
                List items = upload.parseRequest(request);
                Iterator iterator = items.iterator();
                while (iterator.hasNext()) {
                    FileItem item = (FileItem) iterator.next();

                    if (!item.isFormField()) {
                        String fileName = item.getName();

                      //  String root = getServletContext().getRealPath("/");
                        File path = new File(uploadFolder);
                        if (!path.exists()) {
                            boolean status = path.mkdirs();
                        }

                        Random random = new Random();
                        File uploadedFile = new File(path + "/" +random.nextInt()+ fileName);
                        while (uploadedFile.exists())
                        	uploadedFile = new File(path + "/" +random.nextInt()+ fileName);
                        
                        System.out.println(uploadedFile.getAbsolutePath());
                        item.write(uploadedFile);
                        response.getWriter().print(uploadedFile.getAbsolutePath()+"\n");
                    }
                }
                
            } catch (FileUploadException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
*/