package org.osbook.jobstore.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

@Path("/upload")
public class FileUpload {

	private static final String UPLOAD_PATH = System.getenv("OPENSHIFT_DATA_DIR") == null ? "/tmp/" : System.getenv("OPENSHIFT_DATA_DIR");
	
	@POST
	@Consumes("multipart/form-data")
	public Response uploadFile(MultipartFormDataInput input) throws Exception {
		System.out.println("In uploadFile()..");
		Map<String, List<InputPart>> formParts = input.getFormDataMap();
		List<InputPart> inPart = formParts.get("file");
		for (InputPart inputPart : inPart) {
			MultivaluedMap<String, String> headers = inputPart.getHeaders();
			String fileName = parseFileName(headers);
			InputStream istream = inputPart.getBody(InputStream.class, null);
			fileName = UPLOAD_PATH + fileName;
			saveFile(istream, fileName);
		}
		return Response.status(Status.CREATED).entity("File saved").build();
	}

	private String parseFileName(MultivaluedMap<String, String> headers) {
		String[] contentDispositionHeader = headers.getFirst("Content-Disposition").split(";");
		for (String name : contentDispositionHeader) {
			if ((name.trim().startsWith("filename"))) {
				String[] tmp = name.split("=");
				String fileName = tmp[1].trim().replaceAll("\"", "");
				return fileName;
			}
		}
		return UUID.randomUUID().toString();
	}

	private void saveFile(InputStream uploadedInputStream, String serverLocation) throws IOException{
			OutputStream outpuStream = new FileOutputStream(new File(serverLocation));
			int read = 0;
			byte[] bytes = new byte[1024];
			outpuStream = new FileOutputStream(new File(serverLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				outpuStream.write(bytes, 0, read);
			}
			outpuStream.flush();
			outpuStream.close();
	}
}
