package com.example.miro.itu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private View changedView;
    private LinearLayout parentLinearLayout;

    View newTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parentLinearLayout = findViewById(R.id.trip_list);
    }

    public void onToMap(View view) {
        Intent intent = new Intent(this, MapActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("title", ((TextView)((View) view.getParent().getParent()).findViewById(R.id.trip_text)).getText().toString()); //Your id
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onChangeImage(View view) {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , 2);//one can be replaced with any action code

        changedView = view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    ImageView image = newTrip.findViewById(R.id.trip_image);
                    image.setImageURI(selectedImage);

                    parentLinearLayout.addView(newTrip);
                }
                break;

            case 2:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    ImageView image = changedView.findViewById(R.id.trip_image);
                    image.setImageURI(selectedImage);
                }
                break;
        }
    }

    public void onDelete(final View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Are you sure you want to delete your memories from this trip ?");
                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ViewGroup parentView = (ViewGroup) view.getParent().getParent().getParent();
                        parentView.removeView((View) view.getParent().getParent());
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
    }

    public void onAdd(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title your track, traveler");
        View newTrackName = getLayoutInflater().inflate(R.layout.new_track_name, null);

        final EditText input = newTrackName.findViewById(R.id.input);
        builder.setView(newTrackName);

        builder.setPositiveButton("Choose image", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newTrackName = input.getText().toString();

                newTrip = getLayoutInflater().inflate(R.layout.trip_name_view, parentLinearLayout, false);

                TextView trip_title = newTrip.findViewById(R.id.trip_text);
                trip_title.setText(newTrackName);

//                tripNames.add(getString(R.string.testTitle));

                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 1);//one can be replaced with any action code

                dialog.dismiss();
            }
        });

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }
}
