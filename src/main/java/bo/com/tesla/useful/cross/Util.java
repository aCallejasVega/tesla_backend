package bo.com.tesla.useful.cross;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.opencsv.CSVReader;

import bo.com.tesla.useful.config.BusinesException;
import bo.com.tesla.useful.config.Technicalexception;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import net.sf.jasperreports.export.WriterExporterOutput;

public class Util {
	public static String mensajeRow(String mensaje) {
		int input = mensaje.indexOf("input");
		int resource = mensaje.indexOf("resource");

		String newMenasje = "";

		if (resource > 0) {
			newMenasje = mensaje.substring(0, resource);
		}

		if (input > 0) {
			newMenasje = newMenasje + mensaje.substring(input, mensaje.length());
			if (newMenasje.contains("input=[]")) {
				newMenasje = newMenasje + ". El archivo contiene registros en blanco, estos no son permitidos. ";
			}
			return newMenasje.replace("Parsing error at line", "Se encontró un error en el archivo la línea es ")
					.replace("in input=", ". el registro es el siguiente:\n ");
		}
		return newMenasje;
	}

	public static String causeRow(String mensaje) {

		if (mensaje.contains("nroRegistro")) {
			return "Verifique el campo “Nro de Registro” no se encuentra en el formato adecuado o no contiene ningún valor.";
		}
		if (mensaje.contains("codigoCliente")) {
			return "Verifique el campo “Nro de Registro” no se encuentra en el formato adecuado o no contiene ningún valor.";
		}
		if (mensaje.contains("servicio")) {
			return "Verifique el campo “Servicio” no se encuentra en el formato adecuado o no contiene ningún valor.";
		}
		if (mensaje.contains("tipoServicio")) {
			return "Verifique el campo “Tipo de Servicio” no se encuentra en el formato adecuado o no contiene ningún valor.";
		}
		if (mensaje.contains("periodo")) {
			return "Verifique el campo “Periodo” no se encuentra en el formato adecuado o no contiene ningún valor.";
		}
		if (mensaje.contains("tipo")) {
			return "Verifique el campo “Tipo de Sección” no se encuentra en el formato adecuado o no contiene ningún valor.";
		}
		if (mensaje.contains("concepto")) {
			return "Verifique el campo “Concepto” no se encuentra en el formato adecuado o no contiene ningún valor.";
		}
		if (mensaje.contains("cantidad")) {
			return "Verifique el campo “Cantidad” no se encuentra en el formato adecuado o no contiene ningún valor.";
		}
		if (mensaje.contains("montoUnitario")) {
			return "Verifique el campo “Monto Unitario” no se encuentra en el formato adecuado o no contiene ningún valor.";
		}
		if (mensaje.contains("subTotal")) {
			return "Verifique el campo “Sub-Total” no se encuentra en el formato adecuado o no contiene ningún valor.";
		}
		if (mensaje.contains("tipoComprobante")) {
			return "Verifique el campo “Tipo Comprobante” no se encuentra en el formato adecuado o no contiene ningún valor.";
		}

		return "Ocurrió un error en el servidor, por favor intente la operación más tarde o consulte con su administrador.";
	}

	public static Long fileDataValidate(String path) throws BusinesException {
		Long rowInt = 0L;
		try {
			if (!path.contains(".csv")) {
				throw new BusinesException(
						"El archivo debe tener la extensión ‘csv’, por favor verifique la extensión del archivo y vuelva a cargarlo.");
			}
			CSVReader reader = new CSVReader(new FileReader(path), '|');
			List<String[]> allRows = reader.readAll();

			for (String[] rowString : allRows) {
				rowInt++;

				if (rowString.length <= 1) {
					throw new BusinesException("Se encontró un registro en blanco en la línea " + rowInt
							+ ", por favor verifique el archivo y vulva a cargarlo.");

				}
				if (rowString.length != 19) {

					throw new BusinesException(
							"Falta columna(s) en la línea " + rowInt + " verifique el archivo y vuelva a cargarlo.");
				}
			}
			if (rowInt == 0) {

				throw new BusinesException(
						"No se encontraron registros en el archivo, por favor verifique el contenido del archivo y vuelva a cargarlo.");
			}

		} catch (FileNotFoundException e) {
			throw new Technicalexception(e.getMessage(), e.getCause());
		} catch (IOException e) {
			throw new Technicalexception(e.getMessage(), e.getCause());
		}
		return rowInt;

	}

	public static Date stringToDate(String fecha) {
		try {
			return new SimpleDateFormat("dd/MM/yyyy").parse(fecha);
		} catch (Exception e) {
			new Technicalexception(e.getMessage(), e.getCause());
			return null;
		}
	}

	public static Date formatDate(Date date) {
		try {
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date todayWithZeroTime = formatter.parse(formatter.format(date));
			return todayWithZeroTime;
		} catch (Exception e) {
			new Technicalexception(e.getMessage(), e.getCause());
			return null;
		}
	}

	public static String dateToStringFormat(Date date) {
		try {
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			return formatter.format(date);
		} catch (Exception e) {
			new Technicalexception(e.getMessage(), e.getCause());
			return null;
		}
	}

	public static byte[] jasperExportFormat(JasperPrint jasperPrint, String format, String filesReport) {

		String filePath = "";
		try {
			switch (format) {
			case "pdf": //formato en pdf
				byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);
				return pdf;

			case "msword":

				filePath = filesReport +"/temp/"+ UUID.randomUUID().toString() + ".docx";
				JRDocxExporter exporterdoc = new JRDocxExporter();
				exporterdoc.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
				exporterdoc.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, filePath);
				exporterdoc.exportReport();

				byte[] docx = Files.readAllBytes(Paths.get(filePath));
				return docx;

			case "msexcel": //formato en excel
				filePath = filesReport +"/temp/"+ UUID.randomUUID().toString() + ".xlsx";
				JRXlsxExporter exporterXls = new JRXlsxExporter();
				exporterXls.setExporterInput(new SimpleExporterInput(jasperPrint));
				File outputFileXls = new File(filePath);
				exporterXls.setExporterOutput(new SimpleOutputStreamExporterOutput(outputFileXls));
				SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
				configuration.setDetectCellType(true);
				configuration.setCollapseRowSpan(false);
				configuration.setIgnoreGraphics(false);
				exporterXls.setConfiguration(configuration);
				exporterXls.exportReport();
				byte[] xls = Files.readAllBytes(Paths.get(filePath));
				return xls;

			
			case "octet-stream": // formato en rtf
				
				filePath = filesReport +"/temp/"+ UUID.randomUUID().toString() + ".rtf";
				JRRtfExporter rtfExporter = new JRRtfExporter();
				rtfExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
				rtfExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, filePath);		
				rtfExporter.exportReport();
				byte[] rtf = Files.readAllBytes(Paths.get(filePath));
				return rtf;

			}
		} catch (JRException | IOException e) {
			new Technicalexception(e.getMessage(), e.getCause());
		}
		return null;

	}

}
