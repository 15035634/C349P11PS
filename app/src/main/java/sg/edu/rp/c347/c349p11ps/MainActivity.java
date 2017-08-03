package sg.edu.rp.c347.c349p11ps;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    AlertDialog dialog2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LayoutInflater inflater = (LayoutInflater)
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout passPhrase =
                (LinearLayout) inflater.inflate(R.layout.passphrase, null);
        final EditText etPassphrase = (EditText) passPhrase
                .findViewById(R.id.editTextPassPhrase);

        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        builder2.setTitle("Please Enter")
                .setView(passPhrase)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(etPassphrase.getText().toString().equals("738964")){
                            Toast.makeText(MainActivity.this, "Welcome! ",Toast.LENGTH_LONG).show();
                            SharedPreferences.Editor editor = getSharedPreferences("code", MODE_PRIVATE).edit();
                            editor.putString("access", etPassphrase.getText().toString());
                            editor.apply();
                        }else{
                            Toast.makeText(MainActivity.this, "Wrong Access code, Please call 123456 for code ",Toast.LENGTH_LONG).show();

                            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                            homeIntent.addCategory( Intent.CATEGORY_HOME );
                            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(homeIntent);
                            finish();
                        }
                    }
                })
                // Set text for the negative button and the corresponding
                //  OnClickListener when it is clicked
                .setNegativeButton("No Access Code", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(MainActivity.this, "Please call 123456 for Access code ",Toast.LENGTH_LONG).show();
                        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                        homeIntent.addCategory( Intent.CATEGORY_HOME );
                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(homeIntent);
                        finish();

                    }
                });


         dialog2 = builder2.create();
        dialog2.show();


        String[] as = {"Singapore National Day is on Aug 9", "Singapore is 52 years old", "Theme is '#OneNationTogether'"};

        lv=(ListView)findViewById(R.id.lv);
        lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 , as));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Tally against the respective action item clicked
        //  and implement the appropriate action
        if (item.getItemId() == R.id.action_quit) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Quit?")
                    // Set text for the positive button and the corresponding
                    //  OnClickListener when it is clicked
                    .setPositiveButton("Quit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {			  Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                            homeIntent.addCategory( Intent.CATEGORY_HOME );
                            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(homeIntent);
                            finish();
                        }
                    })
                    // Set text for the negative button and the corresponding
                    //  OnClickListener when it is clicked
                    .setNegativeButton("Not Really", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            // Create the AlertDialog object and return it
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        } else if (item.getItemId() == R.id.action_friend) {
            String [] list = new String[] { "Email", "SMS" };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Select the way to enrich your friend")
                    // Set the list of items easily by just supplying an
                    //  array of the items
                    .setItems(list, new DialogInterface.OnClickListener() {
                        // The parameter "which" is the item index
                        // clicked, starting from 0
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) {
                                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                                emailIntent.setType("text/plain");
                                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{  "serveroverloadofficial@gmail.com"});
                                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Hello There");
                                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Use this App about National Day! The access code is 738964");


                                emailIntent.setType("message/rfc822");

                                try {
                                    startActivity(Intent.createChooser(emailIntent,
                                            "Send email using..."));
                                } catch (android.content.ActivityNotFoundException ex) {
                                    Toast.makeText(MainActivity.this,
                                            "No email clients installed.",
                                            Toast.LENGTH_SHORT).show();
                                }




                            } else {
                                //Send sms code here
                                int srcNumber = 91687491;
                                String message = "Hi there! Use this app for National Day, The access code is 738964";
                                Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( "sms:" + srcNumber));
                                intent.putExtra( "sms_body", message );
                                startActivity(intent);

                            }
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();


        } else {

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume(){
        super.onResume();
        SharedPreferences prefs = getSharedPreferences("code", MODE_PRIVATE);
        String restoredText = prefs.getString("access", "");
        if (restoredText.equals("738964")) {
        dialog2.cancel();

        }

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);

        return true;
    }


}
