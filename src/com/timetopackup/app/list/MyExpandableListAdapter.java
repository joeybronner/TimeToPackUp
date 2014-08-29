package com.timetopackup.app.list;

import java.util.Iterator;
import java.util.List;
import com.timetopackup.app.R;
import com.timetopackup.app.db.MySQLiteHelper;
import com.timetopackup.app.obj.Element;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("InflateParams")
public class MyExpandableListAdapter extends BaseExpandableListAdapter
{
	/* global vars */
	String 	_eleid;
	String 	_ele;
	String 	_cat;
	String	_sta;
	String 	_msgerreur;
	private final SparseArray<Group> groups;
	public LayoutInflater inflater;
	public Activity activity;

	public MyExpandableListAdapter(Activity act, SparseArray<Group> groups)
	{
		activity = act;
		this.groups = groups;
		inflater = act.getLayoutInflater();
	}

	@Override
	public Object getChild(int groupPosition, int childPosition)
	{
		return groups.get(groupPosition).children.get(childPosition);
	}

	public String getChildStatus(String cat, String ele)
	{
		final MySQLiteHelper db = new MySQLiteHelper(activity.getBaseContext());
		return db.getElementStatus(cat, ele);
	}

	public String getChildID(String cat, String ele)
	{
		final MySQLiteHelper db = new MySQLiteHelper(activity.getBaseContext());
		return db.getElementID(cat, ele);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition)
	{
		return 0;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, final ViewGroup parent)
	{
		final String children = (String) getChild(groupPosition, childPosition);

		if (convertView == null)
		{
			convertView = inflater.inflate(R.layout.listrow_details, null);
		}

		_cat = getGroupName(groupPosition).toString();
		_sta = getChildStatus(_cat, children);

		final TextView text = (TextView) convertView.findViewById(R.id.textView1);

		text.setText(children);
		if (_sta.equals("0"))
		{
			text.setPaintFlags(text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
			text.setTextColor(Color.GRAY);
			text.setTypeface(null, Typeface.ITALIC);
		}
		else
		{
			text.setPaintFlags(text.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
			text.setTextColor(Color.BLACK);
			text.setTypeface(null, Typeface.NORMAL);
		}

		this.notifyDataSetChanged();

		/* child long click listener */
		convertView.setOnLongClickListener(new OnLongClickListener()
		{

			@Override
			public boolean onLongClick(View v)
			{ 
				_cat = getGroupName(groupPosition).toString();
				_ele = children.toString();

				MySQLiteHelper db = new MySQLiteHelper(activity.getBaseContext());
				db.deleteElement(_cat, _ele);
				notifyDataSetChanged();
				/* open and close to refresh */
				onGroupCollapsed(groupPosition);
				onGroupExpanded(groupPosition);
				return true;
			}
		});

		/* child simple click listener */
		convertView.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				notifyDataSetChanged();

				final MySQLiteHelper db = new MySQLiteHelper(activity.getBaseContext());

				final Dialog dialog = new Dialog(text.getContext());
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); 
				dialog.setContentView(R.layout.activity_add_item);

				Button dialogButton = (Button) dialog.findViewById(R.id.btAjouter);
				final EditText etElement = (EditText) dialog.findViewById(R.id.etElement);

				/* if content of child is 'Ajouter un element...' then call AddItem activity */
				if (children.toString().equals("Ajouter un élément..."))
				{  
					dialogButton.setOnClickListener(new OnClickListener()
					{
						@Override
						public void onClick(View v)
						{
							_ele = etElement.getText().toString();

							if (!validateFields())
							{
								Toast.makeText(dialog.getContext(),"Erreur: " + _msgerreur, Toast.LENGTH_SHORT).show();
							}
							else
							{
								_cat = getGroupName(groupPosition).toString();
								db.addElement(new Element(_ele, _cat, "im1", "1"));
								dialog.dismiss();
								notifyDataSetChanged();
								onGroupCollapsed(groupPosition);
								onGroupExpanded(groupPosition);
							}
						}
					});

					dialog.show();
				}
				else
				{
					_ele = text.getText().toString();
					_cat = getGroupName(groupPosition).toString();
					_sta = getChildStatus(_cat, _ele);
					
					if (_sta.equals("0"))
					{
						text.setPaintFlags(text.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
						text.setTextColor(Color.BLACK);
						text.setTypeface(null, Typeface.NORMAL);

						_sta = getChildStatus(_cat, _ele);
						_eleid = getChildID(_cat, _ele);
						db.updateElementStatus(_eleid, "1");
					}
					else if (_sta.equals("1"))
					{ 
						text.setPaintFlags(text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
						text.setTextColor(Color.GRAY);
						text.setTypeface(null, Typeface.ITALIC);

						_sta = getChildStatus(_cat, _ele);
						_eleid = getChildID(_cat, _ele);
						
						db.updateElementStatus(_eleid, "0");
					}
				}
			}
		});
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition)
	{
		return groups.get(groupPosition).children.size();
	}

	public boolean validateFields()
	{
		/* name of section */ 
		if (_ele == null || _ele.isEmpty() || _ele == "")
		{
			_msgerreur = "Veuillez renseigner le nom de votre élément.";
			return false;
		}

		MySQLiteHelper db = new MySQLiteHelper(activity.getBaseContext());
		/* unique name of section */
		if (!db.uniqueElement(_cat, _ele))
		{
			_msgerreur = "Cet élément existe déjà. Trouvez un autre nom s'il vous plait.";
			return false;
		}

		/* categorie */
		if (_cat == null || _cat.isEmpty() || _cat == "")
		{
			_msgerreur = "inconnu.";
			return false;
		}

		/* that's ok */
		return true;
	}

	@Override
	public Object getGroup(int groupPosition)
	{
		return groups.get(groupPosition);
	}

	public String getGroupName(int groupPosition)
	{
		return groups.get(groupPosition)._string;
	}

	@Override
	public int getGroupCount()
	{
		return groups.size();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void onGroupCollapsed(int groupPosition)
	{
		try
		{
			Iterator itr = groups.get(groupPosition).children.iterator();
			while(itr.hasNext())
			{
				groups.get(groupPosition).children.remove(0);
				this.notifyDataSetChanged();
			}
		}
		catch (Exception e) { } // error when deleting one element. 
		super.onGroupCollapsed(groupPosition);
	}

	@Override
	public void onGroupExpanded(int groupPosition)
	{
		final MySQLiteHelper db = new MySQLiteHelper(activity.getBaseContext());

		_cat = getGroupName(groupPosition);
		List<Element> eles = db.getAllElements(groups.get(groupPosition)._string);

		for (int j=0; j<eles.size() ; j++)
		{
			_ele = eles.get(j).getName();
			groups.get(groupPosition).children.add(_ele);
		}
		groups.get(groupPosition).children.add("Ajouter un élément...");
		this.notifyDataSetChanged();
		super.onGroupExpanded(groupPosition);
	}

	@Override
	public long getGroupId(int groupPosition)
	{
		return 0;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
	{
		if (convertView == null)
		{
			convertView = inflater.inflate(R.layout.listrow_group, null);
		}
		Group group = (Group) getGroup(groupPosition);
		Context context = convertView.getContext();
		((CheckedTextView) convertView).setText(group._string);
		((CheckedTextView) convertView).setBackgroundColor(Color.parseColor(group._color));   
		((CheckedTextView) convertView).setCheckMarkDrawable(context.getResources().getIdentifier(group._ico, "drawable", context.getPackageName()));
		((CheckedTextView) convertView).setChecked(isExpanded);
		((CheckedTextView) convertView).setTextSize(26);

		return convertView;
	}

	@Override
	public boolean hasStableIds()
	{
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition)
	{
		return false;
	}
} 