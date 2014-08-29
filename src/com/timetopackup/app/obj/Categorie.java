package com.timetopackup.app.obj;

public class Categorie
{
	/* Variables */
	private int _id;
	private String _name;
	private String _description;
	private String _color;
	private String _ico;
	
	public Categorie () { /* les infos de la categorie sont settees */}
	
	public Categorie(String n, String d, String c, String i)
	{
		super();
		this._name = n;
		this._description = d;
		this._color = c;
		this._ico = i;
	}
	
	@Override
	public String toString()
	{
		return "Categorie["+ this._name +"]";
	}
	
	/* -------------------- GETTERS -------------------- */
	public int getId()	{ return this._id; }
	
	public String getName() { return this._name; }
	
	public String getDesc() { return this._description;	}
	
	public String getColo() { return this._color; }
	
	public String getIcon() { return this._ico;	}
	
	/* -------------------- SETTERS -------------------- */
	public void setName(String n) {	this._name = n;	}
	
	public void setDesc(String d) {	this._description = d; }
	
	public void setColo(String c) {	this._color = c; }
	
	public void setIcon(String i) {	this._ico = i; }
	
	public void setId(int i) { this._id = i; }

}
