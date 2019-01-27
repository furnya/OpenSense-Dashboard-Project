package com.opensense.dashboard.server.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.*;

public class FileUpload extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			List<FileItem> items = upload.parseRequest(request);

			if (items == null || items.isEmpty()) {
				return;
			}
			FileItem item = items.get(0);
			
			if(item.getSize()>(1024*1024)*10) {
				return;
			}
			BufferedReader in = new BufferedReader(new InputStreamReader(item.getInputStream()));
			String inputLine;
			StringBuilder content = new StringBuilder();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine+"\n");
			}
			in.close();
			
			response.setContentType("text/html");
			
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
			out.write(content.toString());
			out.close();

		} catch (

		Exception e) {
			System.out.println("File Uploading Failed!" + e.getMessage());
		}

	}
}
