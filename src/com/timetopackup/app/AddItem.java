package com.timetopackup.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddItem extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_item);
		
		/* Button 'add element' effect */
        final Button button = (Button) findViewById(R.id.btAjouter);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
			public void onClick(View v)
            {
            	setResult(RESULT_OK, null);
            	finish();
            }
        });
	}
}
