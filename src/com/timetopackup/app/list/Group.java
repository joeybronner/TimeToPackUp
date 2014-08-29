package com.timetopackup.app.list;

import java.util.ArrayList;
import java.util.List;

public class Group
{

  public String _string;
  public String _color;
  public String _ico;
  public final List<String> children = new ArrayList<String>();

  public Group(String string, String color, String ico)
  {
	  this._string = string;
	  this._color = color;
	  this._ico = ico;
  }

} 
