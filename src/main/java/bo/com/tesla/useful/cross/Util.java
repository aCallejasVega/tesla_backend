package bo.com.tesla.useful.cross;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

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
			return newMenasje;
		}
		return newMenasje;
	}

	public static String causeRow(String mensaje) {
		int input = mensaje.indexOf("default message [");
		if (input > 0) {
			return mensaje.substring(input, mensaje.length());
		}
		return mensaje;
	}

	public static Long fileDataValidate(String path) throws Exception {
		Long rowInt=0L; 
		try {
			if(!path.contains(".csv")) {
				System.out.println(path);
				throw new Exception("El archivo debe tener la extensión ‘csv’, por favor verifique la extensión del archivo y vuelva a cargarlo.");
			}
			CSVReader reader = new CSVReader(new FileReader(path),'|');
			List<String[]> allRows = reader.readAll();
			
			for (String[] rowString : allRows) {
				rowInt++;
				
				if(rowString.length<=1) {
					throw new Exception("Se encontró un registro en blanco en la línea "+rowInt+", por favor verifique el archivo y vulva a cargarlo.");
				}
				if(rowString.length!=18) {
					throw new Exception("Falta columna(s) en la línea "+rowInt +" verifique el archivo y vulva a cargarlo.");
				}				
			}
			if(rowInt==0) {
				throw new Exception("No se encontraron registros en el archivo, por favor verifique el contenido del archivo y vulva a cargarlo.");
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CsvException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rowInt;

	}
}
