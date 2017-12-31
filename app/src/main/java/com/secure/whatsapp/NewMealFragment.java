package com.secure.whatsapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import android.widget.ArrayAdapter;
/*
 * This fragment manages the data entry for a
 * new Meal object. It lets the user input a 
 * meal name, give it a rating, and take a 
 * photo. If there is already a photo associated
 * with this meal, it will be displayed in the 
 * preview at the bottom, which is a standalone
 * ParseImageView.
 */
public class NewMealFragment extends Fragment {

	private ImageButton photoButton;
	private Button saveButton;
	private Button cancelButton;
	private Spinner mealRating;
	private ParseImageView mealPreview;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle SavedInstanceState) {
		final View v = inflater.inflate(R.layout.fragment_new_sell_car, parent, false);

		mealRating = ((Spinner) v.findViewById(R.id.rating_spinner));
		ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter
				.createFromResource(getActivity(), R.array.ratings_array,
						android.R.layout.simple_spinner_dropdown_item);
		mealRating.setAdapter(spinnerAdapter);

		photoButton = ((ImageButton) v.findViewById(R.id.photo_button));
		photoButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				InputMethodManager imm = (InputMethodManager) getActivity()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				startCamera();
			}
		});

		saveButton = ((Button) v.findViewById(R.id.save_button));
		saveButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v1) {
                String Name = ((EditText) v.findViewById(R.id.SellCarName)).getText().toString();
                String Contact = ((EditText) v.findViewById(R.id.SellCarContact)).getText().toString();
                String Desccription = ((EditText) v.findViewById(R.id.SellCarDescription)).getText().toString();
                String Address = ((EditText) v.findViewById(R.id.SellCarAddress)).getText().toString();
                if (Name.equals("") || Contact.equals("") || Desccription.equals("") || Address.equals(""))
                {       Toast.makeText(getActivity(), "Fill Everything", Toast.LENGTH_LONG).show();
                        return;
                }
                SellCarObject sellCarObject = ((SellCar) getActivity()).getCurrentMeal();
                sellCarObject.setName(Name);
                sellCarObject.setContact(Contact);
                sellCarObject.setDescription(Desccription);
                sellCarObject.setAddress(Address);
				sellCarObject.setAuthor(ParseUser.getCurrentUser());
				sellCarObject.setrating(mealRating.getSelectedItem().toString());
				sellCarObject.saveInBackground(new SaveCallback() {

					@Override
					public void done(ParseException e) {
						if (e == null) {
                            Toast.makeText(getActivity(),"Mail Sent",Toast.LENGTH_LONG).show();
							getActivity().setResult(Activity.RESULT_OK);
							getActivity().finish();
						} else {
							Toast.makeText(
									getActivity().getApplicationContext(),
									"Error saving: " + e.getMessage(),
									Toast.LENGTH_SHORT).show();
						}
					}

				});
			}
		});

		cancelButton = ((Button) v.findViewById(R.id.cancel_button));
		cancelButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().setResult(Activity.RESULT_CANCELED);
				getActivity().finish();
			}
		});

		mealPreview = (ParseImageView) v.findViewById(R.id.meal_preview_image);
		mealPreview.setVisibility(View.INVISIBLE);

		return v;
	}

	public void startCamera() {
		Fragment cameraFragment = new CameraFragment();
		FragmentTransaction transaction = getActivity().getFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.fragmentContainer, cameraFragment);
		transaction.addToBackStack("NewMealFragment");
		transaction.commit();
	}

	@Override
	public void onResume() {
		super.onResume();
		ParseFile photoFile = ((SellCar) getActivity())
				.getCurrentMeal().getPhotoFile();
		if (photoFile != null) {
			mealPreview.setParseFile(photoFile);
			mealPreview.loadInBackground(new GetDataCallback() {
				@Override
				public void done(byte[] data, ParseException e) {
					mealPreview.setVisibility(View.VISIBLE);
				}
			});
		}
	}

}
