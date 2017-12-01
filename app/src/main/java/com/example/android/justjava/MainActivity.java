/**
 * IMPORTANT: Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 *
 */
package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    int price = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCheckBox = (CheckBox) findViewById(R.id.whipped_cream);
        boolean creamState = whippedCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate);
        boolean chocoState = chocolateCheckBox.isChecked();
        EditText nameField = (EditText) findViewById(R.id.name);
        String name = nameField.getText().toString();
        int price = calculatePrice(creamState, chocoState);
        String priceMessage = createOrderSummary(price, name, creamState, chocoState);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.forWhom, name));
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Calculates the price of the order.
     * @return total price.
     */
    private int calculatePrice(boolean creamState, boolean chocoState) {
        int basePrice = 5;
        if (creamState == true) {
            basePrice = basePrice + 1;
        }
        if (chocoState == true) {
            basePrice = basePrice + 2;
        }
       return price = basePrice * quantity;
    }

    /**
     * Creates order summary.
     * @param price is the price calculated in calculatePrice().
     * @param name is the name of the client.
     * @param creamState shows whether the whippedCheckBox is checked or not.
     * @param chocoState shows whether the chocolateCheckBox is checked or not.
     */
    private String createOrderSummary (int price, String name, boolean creamState, boolean chocoState) {
        String priceMessage = getString(R.string.nameSummary, name);
        priceMessage += "\n" + getString(R.string.addWhippedCream, creamState);
        priceMessage += "\n" + getString(R.string.addChocolate, chocoState);
        priceMessage += "\n" + getString(R.string.quantity2, quantity);
        priceMessage += "\n" + getString(R.string.order_summary_price,NumberFormat.getCurrencyInstance().format(price));
        priceMessage += "\n" + getString(R.string.thanks);
        return priceMessage;
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            Toast toast = Toast.makeText(this, getString(R.string.toastMoreCoffee), Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity (quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            Toast toast = Toast.makeText(this, getString(R.string.toastLessCoffee), Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity (quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int num) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + num);
    }
}