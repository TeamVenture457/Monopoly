package venture.cs414.android.monopoly;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class PlayerSelect extends AppCompatActivity {

    private Spinner numPlayersDropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_select);

        numPlayersDropdown = (Spinner)findViewById(R.id.numPlayers);
        Integer dropDownOptions[] = new Integer[]{2, 3, 4};
        ArrayAdapter<Integer> numPlayersAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, dropDownOptions);
        numPlayersDropdown.setAdapter(numPlayersAdapter);
    }

    public void clickStartGame(View view){
        int numPlayers = (int)numPlayersDropdown.getSelectedItem();
        Intent intent = new Intent(this, GameplayScreen.class);
        intent.putExtra("numPlayers", numPlayers);
        startActivity(intent);
        finish();
    }
}
