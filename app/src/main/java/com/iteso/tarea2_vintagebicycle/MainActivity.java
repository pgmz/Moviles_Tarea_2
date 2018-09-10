package com.iteso.tarea2_vintagebicycle;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Button element, used to listen to clicks, and change the text on it
    Button button_addToCart;

    //Control toggle variable, to indicate if element is in cart or not
    Boolean itemAddedToCart = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get element of button, and add listener
        button_addToCart = findViewById(R.id.activity_main_button_add_to_cart);
        button_addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //If it's already on cart, notify user
                if (itemAddedToCart){
                    Toast.makeText(getApplicationContext(), getString(R.string.activity_main_button_add_to_cart_msg),
                            Toast.LENGTH_SHORT).show();
                } else {
                    //Else, give the user the option to undo the action, and change the button text
                    button_addToCart.setText(R.string.activity_main_button_added_to_cart);
                    showSnackBar();
                    itemAddedToCart = true;
                }
            }
        });

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //restore fields

        //get control variable, to now if element was added to cart previously or not
        Boolean addedToCart = Boolean.parseBoolean(savedInstanceState.getString("ADDED_TO_CART"));

        //set text depending on control variable, and if element is added, show snackbar
        if(addedToCart){
            button_addToCart.setText(getString(R.string.activity_main_button_added_to_cart));
            showSnackBar();
        } else {
            button_addToCart.setText(getString(R.string.activity_main_button_add_to_cart));
        }

        //set color selected
        ((RadioButton)findViewById(Integer.valueOf(savedInstanceState.getString("COLOR")))).setChecked(true);

        //store control variable
        itemAddedToCart = addedToCart;
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);

        int selectedId = ((RadioGroup)findViewById(R.id.activity_main_radio_group_color_selection)).getCheckedRadioButtonId();

        //save in bunlde
        String test = itemAddedToCart.toString();
        outState.putString("COLOR", String.valueOf(selectedId));
        outState.putString("ADDED_TO_CART", itemAddedToCart.toString());
    }

    public void activityMainImageButtonLikeAction(View view){

        //when like button is pressed, show toast of "+1 to the bicycle..."
        //TODO: Is this option intended to show only once?
        Toast.makeText(getApplicationContext(), getString(R.string.activity_main_image_button_like_msg),
                Toast.LENGTH_SHORT).show();

    }

    public void showSnackBar(){

        //create a snackbar, in our scroll layout (no coordinate layout is being used...)
        Snackbar.make(findViewById(R.id.activity_main_scroll_layout),
                //get string from resources and show it indefinite
                (String )getString(R.string.activity_main_button_added_to_cart_msg),
                Snackbar.LENGTH_INDEFINITE)

                //Show the option to undo it
                .setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //revert text, and set control variable to false
                        button_addToCart.setText(R.string.activity_main_button_add_to_cart);
                        itemAddedToCart = false;
                    }
                })
                .show();
    }

}
