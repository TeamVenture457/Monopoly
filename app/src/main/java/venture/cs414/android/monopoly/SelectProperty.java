package venture.cs414.android.monopoly;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SelectProperty extends AppCompatActivity {

    Spinner optionsSpin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_property);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String message = getIntent().getStringExtra("message");
        String[] options = getIntent().getStringArrayExtra("options");

        optionsSpin = (Spinner)findViewById(R.id.optionsSpinner);
        ArrayAdapter<String> actionsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, options);
        optionsSpin.setAdapter(actionsAdapter);
    }

}
