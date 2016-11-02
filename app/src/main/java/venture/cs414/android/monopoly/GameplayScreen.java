package venture.cs414.android.monopoly;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import venture.cs414.android.monopoly.backEnd.GameController;

public class GameplayScreen extends AppCompatActivity {

    GameController gameController;
    Spinner buyHouseSpinner;
    Spinner sellHouseSpinner;
    Spinner buyHotelSpinner;
    Spinner sellHotelSpinner;
    Spinner mortgagePropertySpinner;
    Spinner sellPropertySpinner;

    RelativeLayout layout;
    RelativeLayout blocker;

    PopupWindow jailPopup;
    PopupWindow boardPopup;
    PopupWindow notificationPopup;
    PopupWindow blockerWindow;

    String description;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameplay_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int numPlayers = getIntent().getIntExtra("numPlayers", 2);

        gameController = new GameController(numPlayers, GameplayScreen.this);

        buyHouseSpinner = (Spinner)findViewById(R.id.buyHouseSpinner);
        sellHouseSpinner = (Spinner)findViewById(R.id.sellHouseSpinner);
        buyHotelSpinner = (Spinner)findViewById(R.id.buyHotelSpinner);
        sellHotelSpinner = (Spinner)findViewById(R.id.sellHotelSpinner);
        mortgagePropertySpinner = (Spinner)findViewById(R.id.mortgagePropertySpinner);
        sellPropertySpinner = (Spinner)findViewById(R.id.sellPropertySpinner);

        updateSpinners();

        description = gameController.getCurrentPlayerInfo();


        textView = new TextView(this);
        textView = (TextView)findViewById(R.id.playerInfoText);
        description = gameController.getCurrentPlayerInfo();
        textView.setText(description);
        textView.setTextSize(15);
        //textView.setTextColor(Color.WHITE);
        //textView.setShadowLayer(8, 0, 0, Color.BLACK);

        layout = (RelativeLayout) findViewById(R.id.content);

    }

    public void updateInfo(){
        //Update player positions

        description = gameController.getCurrentPlayerInfo();
        textView.setText(description);

        if(gameController.currentPlayerInJail()){
            //Create layouts and views for popup window
            LinearLayout popLayout = new LinearLayout(this);
            TextView sandwichText = new TextView(this);
            ImageView sandwichImage = new ImageView(this);
            jailPopup = new PopupWindow(this);

            //Set layout orientation
            popLayout.setOrientation(LinearLayout.VERTICAL);

            //Set the text for the popup
            sandwichText.setText("You are in jail!  How would you like to get out?");

            //Create and set the image for the popup
            String picture = "sandwich";
            int resID = getResources().getIdentifier(picture, "drawable", this.getPackageName());
            Drawable drawPic = getResources().getDrawable(resID);
            sandwichImage.setBackground(drawPic);

            //Create Button to dismiss
            Button but = new Button(this);
            but.setText("Roll");
            but.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gameController.rollForJail();
                    blockerWindow.dismiss();
                    jailPopup.dismiss();
                    updateInfo();
                }
            });

            Button but2 = new Button(this);
            but2.setText("Pay $50");
            but2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateInfo();
                    gameController.payJail();
                    blockerWindow.dismiss();
                    jailPopup.dismiss();
                }
            });

            //Place layout in the popup
            popLayout.addView(sandwichImage);
            popLayout.addView(sandwichText);
            popLayout.setBackgroundColor(Color.WHITE);
            popLayout.addView(but);
            popLayout.addView(but2);
            jailPopup.setContentView(popLayout);

            placeBlocker();
            jailPopup.showAtLocation(layout, Gravity.CENTER, 10, 10);
        }
    }

    public void updateSpinners(){
        ArrayAdapter<String> buyHouseAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, gameController.getAvailableOptions("buy house"));
        buyHouseSpinner.setAdapter(buyHouseAdapter);

        ArrayAdapter<String> sellHouseAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, gameController.getAvailableOptions("sell house"));
        sellHouseSpinner.setAdapter(sellHouseAdapter);

        ArrayAdapter<String> buyHotelAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, gameController.getAvailableOptions("buy hotel"));
        buyHotelSpinner.setAdapter(buyHotelAdapter);

        ArrayAdapter<String> sellHotelAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, gameController.getAvailableOptions("sell hotel"));
        sellHotelSpinner.setAdapter(sellHotelAdapter);

        ArrayAdapter<String> mortgagePropertyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, gameController.getAvailableOptions("mortgage"));
        mortgagePropertySpinner.setAdapter(mortgagePropertyAdapter);

        ArrayAdapter<String> sellPropertyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, gameController.getAvailableOptions("sell property"));
        sellPropertySpinner.setAdapter(sellPropertyAdapter);
    }

    public void clickSellHouse(View view){
        String choice = sellHouseSpinner.getSelectedItem().toString();
        gameController.doAction("sell house", choice);
        updateSpinners();
    }

    public void clickBuyHouse(View view){
        String choice = buyHouseSpinner.getSelectedItem().toString();
        gameController.doAction("buy house", choice);
        updateSpinners();
    }

    public void clickSellHotel(View view){
        String choice = sellHotelSpinner.getSelectedItem().toString();
        gameController.doAction("sell hotel", choice);
        updateSpinners();
    }

    public void clickBuyHotel(View view){
        String choice = buyHotelSpinner.getSelectedItem().toString();
        gameController.doAction("buy hotel", choice);
        updateSpinners();
    }

    public void clickMortgageProperty(View view){
        String choice = mortgagePropertySpinner.getSelectedItem().toString();
        gameController.doAction("mortgage", choice);
        updateSpinners();
    }

    public void clickUnmortgageProperty(View view){

    }

    public void clickSellProperty(View view){

    }

    public void clickRollDice(View view){
        String dialogueText = gameController.rollCurrentPlayer();
        notify(dialogueText);

    }

    public void clickViewMap(View view){
        //Create layouts and views for popup window
        LinearLayout popLayout = new LinearLayout(this);
        ImageView boardImage = new ImageView(this);
        boardPopup = new PopupWindow(this);

        //Set layout orientation
        popLayout.setOrientation(LinearLayout.VERTICAL);

        //Create and set the image for the popup
        String picture = "monopolyboardnumbered";
        int resID = getResources().getIdentifier(picture, "drawable", this.getPackageName());
        Drawable drawPic = getResources().getDrawable(resID);
        boardImage.setBackground(drawPic);
        boardImage.setScaleType(ImageView.ScaleType.CENTER_CROP);

        //Create Button to dismiss
        Button but = new Button(this);
        but.setText("Dismiss");
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boardPopup.dismiss();
                blockerWindow.dismiss();
            }
        });

        //Place layout in the popup
        popLayout.addView(boardImage);
        popLayout.setBackgroundColor(Color.WHITE);
        popLayout.addView(but);
        boardPopup.setContentView(popLayout);

        placeBlocker();
        boardPopup.showAtLocation(layout, Gravity.CENTER, 10, 10);
    }

    public void placeBlocker(){
        blocker = new RelativeLayout(this);
        blocker.setVisibility(View.INVISIBLE);
        blockerWindow = new PopupWindow(blocker, ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.FILL_PARENT);
        blockerWindow.showAtLocation(layout, Gravity.CENTER, 10, 10);
    }

    public void notify(String message){
        //Create layouts and views for popup window
        LinearLayout popLayout = new LinearLayout(this);
        TextView notificationText = new TextView(this);
        ImageView lossImage = new ImageView(this);
        notificationPopup = new PopupWindow(this);

        //Set layout orientation
        popLayout.setOrientation(LinearLayout.VERTICAL);

        //Set the text for the popup
        notificationText.setText(message);

        //Create Button to dismiss
        Button but = new Button(this);
        but.setText("Dismiss");
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blockerWindow.dismiss();
                notificationPopup.dismiss();
                finish();
            }
        });

        //Place layout in the popup
        popLayout.addView(notificationText);
        popLayout.setBackgroundColor(Color.WHITE);
        popLayout.addView(but);
        notificationPopup.setContentView(popLayout);

        placeBlocker();
        notificationPopup.showAtLocation(layout, Gravity.CENTER, 10, 10);
    }


}
