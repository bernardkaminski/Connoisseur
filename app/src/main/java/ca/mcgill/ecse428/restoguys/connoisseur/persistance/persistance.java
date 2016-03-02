package ca.mcgill.ecse428.restoguys.connoisseur.persistance;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import com.thoughtworks.xstream.XStream;

/**
 * This static class provides the application-wide tools for writing and reading current/previous
 * GameManager states with XStream.
 */
public class Persistance {

	private static String fileName = "saveState.xml";

	/**
	 * Public method call for saveStateProtected.
	 */
	public static boolean saveState (Context contextFrom) {
		return saveStateProtected(ApplicationData.getInstance(), contextFrom);
	}


	/**
	 * Saves the object given to memory.
	 * @param objectToSave The object that we want to serialize and save.
	 * @param contextFrom The context from which the request is sent (so that the code knows which
	 *                    package is being used to store the saved file).
	 * @return 0 for successful operation / else for unsuccessful operation
	 */
	private static boolean saveStateProtected (Object objectToSave, Context contextFrom) {

		try {

			// Serialize the given object
			XStream xStream = new XStream();
			xStream.setMode(XStream.ID_REFERENCES);
			String objectToSaveString = xStream.toXML(objectToSave);

			// Generate reference to file using the given context.
			File fileToWrite = new File(contextFrom.getFilesDir(), fileName);

			// Write serialized object to the file.
			FileWriter toWrite = new FileWriter(fileToWrite, false);
			toWrite.write(objectToSaveString);

			// Close reader.
			toWrite.close();

		}
		catch (java.io.IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * Public call to loadStateProtected
	 * @param contextFrom
	 * @return True if saved data was loaded correctly. False if not.
	 */
	public static boolean loadState (Context contextFrom) {
		ApplicationData.getInstance().setApplicationData((ApplicationData) loadStateProtected(contextFrom));
		return !(ApplicationData.getInstance() == null);
	}

	/**
	 * Loads the saved object from disk, given a context.
	 * @param contextFrom The context from which the request is sent (so that the code knows which
	 *                    package is being used to find/load the saved file).
	 * @return The object, if found, or null if no object is found.
	 */
	private static Object loadStateProtected (Context contextFrom) {

		try {

			// Get XStream reference
			XStream xStream = new XStream();
			xStream.setMode(XStream.ID_REFERENCES);

			// Generate reference to file using the context provided.
			File fileToRead = new File(contextFrom.getFilesDir(), fileName);

			// Generate file reader using file
			FileReader toRead = new FileReader(fileToRead);

			// Use XStream to load the XML into an object
			Object objectToLoad = xStream.fromXML(toRead);
			toRead.close();

			return objectToLoad;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}


}

