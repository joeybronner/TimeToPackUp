
package com.timetopackup.app;

import java.util.List;

import com.timetopackup.app.db.MySQLiteHelper;
import com.timetopackup.app.list.Group;
import com.timetopackup.app.list.MyExpandableListAdapter;
import com.timetopackup.app.obj.Categorie;
import com.timetopackup.app.obj.Element;
import com.timetopackup.app.tools.Logger;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

public class MainActivity extends ActionBarActivity
{
	/* global variables */
	Logger log;
	SparseArray<Group> groups = new SparseArray<Group>();
	MySQLiteHelper db = new MySQLiteHelper(this);
	private	String	sectionSelected;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/* action bar color */
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#666666")));

		/* expandable list */
		createGroups();
		ExpandableListView listView = (ExpandableListView) findViewById(R.id.listView);
		final MyExpandableListAdapter adapter = new MyExpandableListAdapter(this,groups);
		listView.setAdapter(adapter);  

		listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
		{
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View v,int index, long arg3)
			{
				AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.this);

				sectionSelected = adapter.getGroupName(index);
				ab.setMessage("Voulez-vous supprimer cette section et les éléments qu'elle contient ?")
				.setPositiveButton("Oui", dialogClickListener)
				.setNegativeButton("Non", dialogClickListener).show();
				return true;
			}
		});
	}


	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener()
	{
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			switch (which)
			{

			case DialogInterface.BUTTON_POSITIVE:
				db.deleteCategorie(sectionSelected);
				// deleting all items from this category
				List<Element> eles = db.getAllElements(sectionSelected);
				for (int j=0; j<eles.size() ; j++)
				{
					db.deleteElement(sectionSelected, eles.get(j).getName());
				}	
				// end of deleting items 
				finish();
				startActivity(getIntent());
				break;

			case DialogInterface.BUTTON_NEGATIVE:
				// nothing
				break;
			}
		}
	};

	DialogInterface.OnClickListener dialogDemoListener = new DialogInterface.OnClickListener()
	{
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			switch (which)
			{
			case DialogInterface.BUTTON_POSITIVE:
				/* creation of sample data in database */
				db.addCategorie(new Categorie ("PAPIERS", "Non", "#DF4949", "im2"));
				db.addElement(new Element ("Passeport", "PAPIERS", "", "1"));
				db.addElement(new Element ("Carte d'identité", "PAPIERS", "", "1"));
				db.addElement(new Element ("Carte bancaire", "PAPIERS", "", "0"));

				db.addCategorie(new Categorie ("MULTIMEDIA", "Non", "#45B29D", "im40"));
				db.addElement(new Element ("Chargeur téléphone", "MULTIMEDIA", "", "1"));
				db.addElement(new Element ("Appareil photo", "MULTIMEDIA", "", "0"));
				db.addElement(new Element ("Adaptateur de prise de courant", "MULTIMEDIA", "", "0"));

				db.addCategorie(new Categorie ("TROUSSE DE TOILETTE", "Non", "#309AC1", "im15"));
				db.addElement(new Element ("Mousse à raser", "TROUSSE DE TOILETTE", "", "1"));
				db.addElement(new Element ("Coupe ongles", "TROUSSE DE TOILETTE", "", "1"));
				db.addElement(new Element ("Cotons-tiges", "TROUSSE DE TOILETTE", "", "1"));
				db.addElement(new Element ("Déodorant", "TROUSSE DE TOILETTE", "", "0"));
				db.addElement(new Element ("Parfum", "TROUSSE DE TOILETTE", "", "0"));

				db.addCategorie(new Categorie ("VÊTEMENTS", "Non", "#E27A3F", "im71"));
				db.addElement(new Element ("T-shirt", "VÊTEMENTS", "", "1"));
				db.addElement(new Element ("Pull", "VÊTEMENTS", "", "0"));
				db.addElement(new Element ("Jeans", "VÊTEMENTS", "", "0"));

				db.addCategorie(new Categorie ("PLAGE", "Non", "#FF9B00", "im67"));
				db.addElement(new Element ("Maillot de bain", "PLAGE", "", "1"));
				db.addElement(new Element ("Crême solaire", "PLAGE", "", "1"));
				db.addElement(new Element ("Casquette/chapeau", "PLAGE", "", "1"));
				db.addElement(new Element ("Tongs/sandalles", "PLAGE", "", "1"));

				db.addCategorie(new Categorie ("AUTRES", "Non", "#ACBEAF", "im52"));
				db.addElement(new Element ("Sac plastique linge sale", "AUTRES", "", "0"));

				List<Categorie> cats = db.getAllCategories();

				for (int i = 0; i<cats.size();i++)
				{
					Group group = new Group(cats.get(i).getName(), cats.get(i).getColo(), cats.get(i).getIcon());
					groups.append(i, group);  
				}
				/* recreate app */
				recreate();
				break;

			case DialogInterface.BUTTON_NEGATIVE:
				// nothing
				break;
			}
		}
	};

	public void createGroups()
	{
		List<Categorie> hey = db.getAllCategories();

		if (hey.size() == 0)
		{
			if (db.getAppDemo() == 1)
			{
				db.setAppDemo();

				AlertDialog.Builder adDemo = new AlertDialog.Builder(MainActivity.this);
				adDemo
				.setMessage("Merci d'avoir installé l'application!\nVoulez-vous ajouter les données du mode démo pour découvrir l'application ?")
				.setPositiveButton("Oui", dialogDemoListener)
				.setNegativeButton("Non", dialogDemoListener).show();
			}
		}
		else
		{
			for (int i = 0; i<hey.size();i++)
			{
				Group group = new Group(hey.get(i).getName(), hey.get(i).getColo(), hey.get(i).getIcon());
				groups.append(i, group);  
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menu)
	{
		super.onOptionsItemSelected(menu);

		switch(menu.getItemId())
		{
		case R.id.action_newsection:
			Intent n = new Intent(this, AddSection.class);
			startActivityForResult(n, 1);
			break;

		case R.id.action_infos:
			Intent i = new Intent(this, Help.class);
			startActivityForResult(i, 1);
			break;
		}
		return true;
	}    

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==RESULT_OK)
		{
			Intent refresh = new Intent(this, MainActivity.class);
			startActivity(refresh);
			this.finish();
		}
	}
}
