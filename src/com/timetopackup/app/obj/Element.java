package com.timetopackup.app.obj;

public class Element
{

	/* Variables */
	private String 	_name;
	private String 	_catname;
	private String	_icon;
	private String 	_status; 
	
	public Element () { /* all categories informations are set */ }
	
	public Element(String n, String c, String i, String s)
	{
		this._name 			= n;
		this._catname 		= c;
		this._icon 			= i;
		this._status 		= s;
	}
	
	/* -------------------- GETTERS -------------------- */
	
	public String getName() { return this._name; }
	
	public String getCatN() { return this._catname;	}
	
	public String getIcon() { return this._icon; }
	
	public String getStat() { return this._status;	}
	
	/* -------------------- SETTERS -------------------- */
	public void setName(String n) {	this._name 		= n; }
	
	public void setCatN(String c) {	this._catname 	= c; }
	
	public void setIcon(String i) {	this._icon 		= i; }
	
	public void setStat(String s) {	this._status 	= s; }
	
}
