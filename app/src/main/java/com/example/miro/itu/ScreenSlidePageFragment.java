package com.example.miro.itu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.support.v4.graphics.TypefaceCompatUtil.getTempFile;

public class  ScreenSlidePageFragment extends Fragment {

    View newTrip;
    private LinearLayout parentLinearLayout;
    ArrayList<String> tripNames;
    LayoutInflater inflater;

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        this.inflater = inflater;
        tripNames = new ArrayList<>();
        View view = inflater.inflate(R.layout.fragment_screen_slide_page, container, false);

        parentLinearLayout = view.findViewById(R.id.trip_list);

        ImageButton addNewTrip = view.findViewById(R.id.addButton);
        addNewTrip.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Title your track, traveler");
                View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.new_track_name, (ViewGroup) getView(), false);

                final EditText input = viewInflated.findViewById(R.id.input);
                builder.setView(viewInflated);

                builder.setPositiveButton("Choose image", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newTrackName = input.getText().toString();

                        newTrip = inflater.inflate(R.layout.trip_name_view, parentLinearLayout, false);

                        TextView trip_title = newTrip.findViewById(R.id.trip_text);
                        trip_title.setText(newTrackName);

                        tripNames.add(getString(R.string.testTitle));

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

        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            tripNames = savedInstanceState.getStringArrayList("tripNames");

            assert tripNames != null;
            for (String title: tripNames) {
                View rowView = inflater.inflate(R.layout.trip_name_view, parentLinearLayout, false);

                TextView trip_title = rowView.findViewById(R.id.trip_text);
                trip_title.setText(title);

                parentLinearLayout.addView(rowView);
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    ImageView image = newTrip.findViewById(R.id.trip_image);
                    image.setImageURI(selectedImage);
                }
                parentLinearLayout.addView(newTrip);
                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    ImageView image = newTrip.findViewById(R.id.trip_image);
                    image.setImageURI(selectedImage);
                }
                parentLinearLayout.addView(newTrip);
                break;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList("tripNames", tripNames);
    }
}
