package com.timetopackup.app;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;
import com.timetopackup.app.db.MySQLiteHelper;
import com.timetopackup.app.obj.Categorie;
import com.timetopackup.app.tools.ImageAdapter;
import com.timetopackup.app.tools.color.ColorPickerDialogFragment;
import com.timetopackup.app.tools.color.ColorPickerListener;

public class AddSection extends ActionBarActivity
{
	/* global variables */
	String 	_nom;
	int 	_col = 0;
	String  _hexcol="#FAAC58";
	String 	_logo;
	String 	_msgerreur;
	MySQLiteHelper db = new MySQLiteHelper(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_section);
		
		/* action bar color */
        android.app.ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#666666")));
       
        /* textWatcher (listener) to get the name of the section */
        EditText etSection = (EditText) findViewById(R.id.etSection);
        etSection.addTextChangedListener(new TextWatcher()
        {    
        	@Override
			@SuppressLint("DefaultLocale")
        	public void afterTextChanged(Editable s)
        	{
        		_nom = s.toString().toUpperCase();
        	}

        	@Override
			@SuppressLint("DefaultLocale")
        	public void beforeTextChanged(CharSequence s, int start, int count, int after)
        	{
        		_nom = s.toString().toUpperCase();
        	}

        	@Override
			@SuppressLint("DefaultLocale")
        	public void onTextChanged(CharSequence s, int start, int before, int count)
        	{
        		_nom = s.toString().toUpperCase();
        	}

        });

        /* logo gridview (selector) */
        GridView gridView = (GridView) findViewById(R.id.gvLogos);
        gridView.setAdapter(new ImageAdapter(this));
        gridView.setSelected(true);
        gridView.setOnItemClickListener(new OnItemClickListener() 
        {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id)
            {           
            	_logo = a.getItemAtPosition(position).toString();
            	buildLogoName();
            }
        });
        
        /* button color picker */
        final Button button = (Button) findViewById(R.id.btCouleur);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
			public void onClick(View v)
            {
            	colorPicker();
            }
        });
	}
	
	public void displayStatus()
	{
		Toast toast = Toast.makeText(this, 	"_hexcol: " + _hexcol 	+ "\n" + 
											"_name  : " + _nom		+ "\n" +
											"_logo  : " + _logo		
				, Toast.LENGTH_LONG);
		toast.show();
	}
	
	public void buildLogoName()
	{
		/* build the good logo name with "im" at the beginning */
		_logo = "im".concat(_logo);
	}
	
	public boolean validateFields()
	{
		/* name of section */ 
		if (_nom == null || _nom.isEmpty() || _nom == "")
		{
			_msgerreur = "Veuillez renseigner le nom de votre section.";
			return false;
		}
		
		/* name of section exist in database */
		{
        	if (!db.uniqueCategorie(_nom))
        	{
        		_msgerreur = "Le nom de cette section existe déjà.";
        		return false;
        	}
		}
		
		/* logo */
		if (_logo == null || _logo.isEmpty() || _logo == "")
		{
			_msgerreur = "Veuillez sélectionner un logo.";
			return false;
		}
		
		/* that's ok */
		return true;
	}

	public void colorPicker()
	{
        ColorPickerListener onAmbilWarnaListener = new ColorPickerListener()
        {
            @Override
            public void onCancel(ColorPickerDialogFragment dialogFragment)
            {
            	/* nothing */
            }

            @Override
            public void onOk(ColorPickerDialogFragment dialogFragment, int color)
            {
            	_col = color;
            	_hexcol = String.format("#%06X", (0xFFFFFF & color));
            	
            	final Button button2 = (Button) findViewById(R.id.btCouleur);
            	GridView gridView = (GridView) findViewById(R.id.gvLogos);
            	
        		button2.setBackgroundColor(_col);
                gridView.setBackgroundColor(_col);
            }
        };

        ColorPickerDialogFragment fragment = ColorPickerDialogFragment.newInstance(99);
        fragment.setOnAmbilWarnaListener(onAmbilWarnaListener);
        fragment.show(this.getFragmentManager(), "color_picker_dialog");
	}
	
    @Override
	public boolean onCreateOptionsMenu(Menu menu)
    {
		getMenuInflater().inflate(R.menu.add_section, menu);
		return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem menu)
    {
        super.onOptionsItemSelected(menu);
        switch(menu.getItemId())
        {                
            case R.id.action_ok: 
                /* first, check if all values are filled */
                if (!validateFields())
                {
                	Toast.makeText(getBaseContext(), "Erreur: " + _msgerreur, Toast.LENGTH_SHORT).show();
                }
                else
                {
                	/* all is ok */
                	db.addCategorie(new Categorie(_nom, "description", _hexcol, _logo));
                	setResult(RESULT_OK, null);
                	finish();                	
                }
                break;
        }
        return super.onOptionsItemSelected(menu);
    }   
}
