package bo.com.tesla.useful.cross;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public class HandlingFiles {

	public static String saveFileToDisc(MultipartFile file, String entidadName, String pathOrigin) throws Exception {

		String fileName = "(" + UUID.randomUUID().toString() + ")_" + file.getOriginalFilename();
		FileOutputStream os = null;
		String path = null;
		try {
			path = getDirectory(entidadName, pathOrigin) + File.separator + fileName;
			os = new FileOutputStream(path);
			os.write(file.getBytes());
			return path;

		} catch (Exception e) {
			// todo manejo de excepciones
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public static String getDirectory(String entidadName, String pathOrigin) throws Exception {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		String path = pathOrigin + File.separator + entidadName + File.separator + year;
		File directory = new File(path);
		if (!directory.exists()) {
			if (directory.mkdirs()) {
				return path;
			} else {
				throw new Exception("No se pudo crear el directorio");
			}
		}
		return path;

	}

}