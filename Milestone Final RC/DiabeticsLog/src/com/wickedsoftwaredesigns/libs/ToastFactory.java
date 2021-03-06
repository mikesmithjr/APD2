
package com.wickedsoftwaredesigns.libs;

import android.content.Context;
import android.widget.Toast;


/**
 * A factory for creating Toast objects.
 */
public class ToastFactory {
	
	/**
	 * Short toast.
	 *
	 * @param context the context
	 * @param message the message
	 */
	public static void shortToast(Context context, String message){
		
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
		toast.show();
	}

/**
 * Long toast.
 *
 * @param context the context
 * @param message the message
 */
public static void longToast(Context context, String message){
		
		Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
		toast.show();
	}
}
