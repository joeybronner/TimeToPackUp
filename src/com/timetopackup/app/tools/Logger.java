package com.timetopackup.app.tools;

import android.content.Context;
import android.widget.Toast;

public class Logger
{
	
	/* global variables */
	private Context _context;
	private String _msg;
	private int _duration;
	
	public Logger() {} // not used
	
	public Logger(Context c, String msg, int duration)
	{
		this._context = c;
		this._msg = msg;
		this._duration = duration;
		
		/* call toast */
		writeToast();
	}
	
	private void writeToast()
	{
		Toast toast = Toast.makeText(this._context, this._msg, this._duration);
		toast.show();
	}
	
}
