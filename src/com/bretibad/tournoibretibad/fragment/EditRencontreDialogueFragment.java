package com.bretibad.tournoibretibad.fragment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bretibad.tournoibretibad.R;
import com.bretibad.tournoibretibad.model.Joueur;
import com.bretibad.tournoibretibad.model.MatchCategory;
import com.bretibad.tournoibretibad.model.Rencontre;
import com.bretibad.tournoibretibad.service.RencontreService;
import com.bretibad.tournoibretibad.utils.RestResultReceiver;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class EditRencontreDialogueFragment extends DialogFragment {

	AlertDialog.Builder builder;
	private Rencontre rencontre;

	Spinner sh1List, sh2List, sh3List, sh4List, sd1List, sd2List, dh1J1List, dh1J2List, dh2J1List, dh2J2List, dd1J1List, dd1J2List, dx1J1List,
			dx1J2List, dx2J1List, dx2J2List;
	LinearLayout sh3Panel, sh4Panel, dh1Panel, dh2Panel, sd1Panel, sd2Panel, dd1Panel, dx1Panel, dx2Panel;
	Spinner scorep, scorec;

	CheckBox fin, live;
	TextView title;

	NoticeEditRencontreDialogListener mListener;

	public interface NoticeEditRencontreDialogListener {
		public void onEditRencontreDialogPositiveClick(DialogFragment dialog, int equipe, int journee);

	}

	public static EditRencontreDialogueFragment newInstance(Rencontre rencontre) {
		EditRencontreDialogueFragment f = new EditRencontreDialogueFragment();

		// Supply index input as an argument.
		Bundle args = new Bundle();
		args.putParcelable("rencontre", rencontre);
		f.setArguments(args);

		return f;
	}

	// Override the Fragment.onAttach() method to instantiate the
	// NoticeDialogListener
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// Verify that the host activity implements the callback interface
		try {
			// Instantiate the NoticeDialogListener so we can send events to the
			// host
			mListener = (NoticeEditRencontreDialogListener) activity;
		} catch (ClassCastException e) {
			// The activity doesn't implement the interface, throw exception
			throw new ClassCastException(activity.toString() + " must implement NoticeDialogListener");
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		rencontre = (Rencontre) getArguments().getParcelable("rencontre");
		// Use the Builder class for convenient dialog construction
		builder = new AlertDialog.Builder(getActivity());

		initMainView();

		getActivity().setProgressBarIndeterminateVisibility(true);
		Intent listJoueurIntent = RencontreService.getInstance(getActivity()).getListJoueurIntent(new RestResultReceiver() {

			@Override
			public void onRESTResult(int resultCode, String result) {
				if (resultCode == 200) {
					Type joueurType = new TypeToken<List<Joueur>>() {
					}.getType();
					Gson gson = new Gson();
					List<Joueur> joueurs = gson.fromJson(result, joueurType);
					fillEditRencontrePopup(builder, joueurs);
					getActivity().setProgressBarIndeterminateVisibility(false);

				} else {
					Toast.makeText(getActivity(), "Erreur: Impossible de r�cup�rer la liste de joueurs", Toast.LENGTH_SHORT).show();
				}
			}

		});
		getActivity().startService(listJoueurIntent);

		builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				saveRencontre();
			}
		}).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
			}
		});

		return builder.create();
	}

	private void initMainView() {
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.edit_rencontre_fragment, null);
		builder.setView(view);

		title = (TextView) view.findViewById(R.id.edit_rencontre_title_panel);
		fin = (CheckBox) view.findViewById(R.id.finMatchCheckBox);
		live = (CheckBox) view.findViewById(R.id.liveCheckBox);

		scorep = (Spinner) view.findViewById(R.id.scorep);
		scorec = (Spinner) view.findViewById(R.id.scorec);

		sh3Panel = (LinearLayout) view.findViewById(R.id.sh3Panel);
		sh4Panel = (LinearLayout) view.findViewById(R.id.sh4Panel);
		sd1Panel = (LinearLayout) view.findViewById(R.id.sd1Panel);
		sd2Panel = (LinearLayout) view.findViewById(R.id.sd2Panel);
		dh1Panel = (LinearLayout) view.findViewById(R.id.dh1Panel);
		dh2Panel = (LinearLayout) view.findViewById(R.id.dh2Panel);
		dd1Panel = (LinearLayout) view.findViewById(R.id.dd1Panel);
		dx1Panel = (LinearLayout) view.findViewById(R.id.dx1Panel);
		dx2Panel = (LinearLayout) view.findViewById(R.id.dx2Panel);

		sh1List = (Spinner) view.findViewById(R.id.sh1List);
		sh2List = (Spinner) view.findViewById(R.id.sh2List);
		sh3List = (Spinner) view.findViewById(R.id.sh3List);
		sh4List = (Spinner) view.findViewById(R.id.sh4List);

		sd1List = (Spinner) view.findViewById(R.id.sd1List);
		sd2List = (Spinner) view.findViewById(R.id.sd2List);

		dh1J1List = (Spinner) view.findViewById(R.id.dh1J1List);
		dh1J2List = (Spinner) view.findViewById(R.id.dh1J2List);
		dh2J1List = (Spinner) view.findViewById(R.id.dh2J1List);
		dh2J2List = (Spinner) view.findViewById(R.id.dh2J2List);

		dd1J1List = (Spinner) view.findViewById(R.id.dd1J1List);
		dd1J2List = (Spinner) view.findViewById(R.id.dd1J2List);

		dx1J1List = (Spinner) view.findViewById(R.id.dx1J1List);
		dx1J2List = (Spinner) view.findViewById(R.id.dx1J2List);

		dx2J1List = (Spinner) view.findViewById(R.id.dx2J1List);
		dx2J2List = (Spinner) view.findViewById(R.id.dx2J2List);
	}

	protected void saveRencontre() {
		rencontre.setLive(live.isChecked() ? 1 : 0);
		rencontre.setFinmatch(fin.isChecked() ? "OK" : "");

		String scorepValue = (String) scorep.getSelectedItem();
		String scorecValue = (String) scorec.getSelectedItem();
		rencontre.setMatchpour(scorepValue != null && !scorepValue.isEmpty() ? Integer.parseInt(scorepValue) : null);
		rencontre.setMatchcontre(scorecValue != null && !scorecValue.isEmpty() ? Integer.parseInt(scorecValue) : null);

		rencontre.setSh1((String) sh1List.getSelectedItem());
		rencontre.setSh2((String) sh2List.getSelectedItem());
		if (sh3List != null) {
			rencontre.setSh3((String) sh3List.getSelectedItem());
		}
		rencontre.setSd1((String) sd1List.getSelectedItem());
		if (sd2List != null) {
			rencontre.setSd2((String) sd2List.getSelectedItem());
		}
		String selectedDh1J1 = (String) dh1J1List.getSelectedItem();
		String selectedDh1J2 = (String) dh1J2List.getSelectedItem();
		if (selectedDh1J1.length() == 0 && selectedDh1J2.length() == 0) {
			rencontre.setDh1("");
		} else {
			rencontre.setDh1(selectedDh1J1 + " - " + selectedDh1J2);
		}
		String selectedDd1J1 = (String) dd1J1List.getSelectedItem();
		String selectedDd1J2 = (String) dd1J2List.getSelectedItem();
		if (selectedDd1J1.length() == 0 && selectedDd1J2.length() == 0) {
			rencontre.setDd1("");
		} else {
			rencontre.setDd1(selectedDd1J1 + " - " + selectedDd1J2);
		}
		String selectedDx1J1 = (String) dx1J1List.getSelectedItem();
		String selectedDx1J2 = (String) dx1J2List.getSelectedItem();
		if (selectedDx1J1.length() == 0 && selectedDx1J2.length() == 0) {
			rencontre.setDx1("");
		} else {
			rencontre.setDx1(selectedDx1J1 + " - " + selectedDx1J2);
		}
		if (dx2J1List != null && dx2J2List != null) {
			String selectedDx2J1 = (String) dx2J1List.getSelectedItem();
			String selectedDx2J2 = (String) dx2J2List.getSelectedItem();
			if (selectedDx2J1.length() == 0 && selectedDx2J2.length() == 0) {
				rencontre.setDx2("");
			} else {
				rencontre.setDx2(selectedDx2J1 + " - " + selectedDx2J2);
			}
		}

		Intent updateRencontreIntent = RencontreService.getInstance(getActivity()).getUpdateRencontreIntent(rencontre, new RestResultReceiver() {

			@Override
			public void onRESTResult(int resultCode, String result) {
				mListener.onEditRencontreDialogPositiveClick(EditRencontreDialogueFragment.this, rencontre.getNumequipe(), rencontre.getJournee());
			}
		});
		getActivity().startService(updateRencontreIntent);

	}

	protected void fillEditRencontrePopup(Builder alert, List<Joueur> joueursList) {
		List<String> allJoueur = new ArrayList<String>();
		List<String> hommeList = new ArrayList<String>();
		List<String> femmeList = new ArrayList<String>();
		allJoueur.add("");
		hommeList.add("");
		femmeList.add("");
		for (Joueur j : joueursList) {
			allJoueur.add(j.getFullName());
			if (j.getGenre().toLowerCase().equals("m")) {
				hommeList.add(j.getFullName());
			} else if (j.getGenre().toLowerCase().equals("f")) {
				femmeList.add(j.getFullName());
			}
		}

		title.setText("J" + rencontre.getJournee() + " - Equipe" + rencontre.getNumequipe() + " / " + rencontre.getAdversaire());

		initCheckBox();
		initScore();

		for (MatchCategory cat : MatchCategory.values()) {
			LinearLayout ll = new LinearLayout(getActivity());
			ll.setOrientation(LinearLayout.HORIZONTAL);

			switch (cat) {
			case SH1:
				initSpinner(sh1List, hommeList, cat.name(), ll, rencontre.getSh1());
				break;
			case SH2:
				initSpinner(sh2List, hommeList, cat.name(), ll, rencontre.getSh2());
				break;
			case SH3:
				if (rencontre.getSh() >= 3) {
					sh3Panel.setVisibility(View.VISIBLE);
					initSpinner(sh3List, hommeList, cat.name(), ll, rencontre.getSh3());
				} else {
					sh3Panel.setVisibility(View.GONE);
					sh3List = null;
				}

				break;
			case SH4:
				if (rencontre.getSh() >= 4) {
					sh4Panel.setVisibility(View.VISIBLE);
					initSpinner(sh4List, hommeList, cat.name(), ll, rencontre.getSh4());
				} else {
					sh4Panel.setVisibility(View.GONE);
					sh4List = null;
				}

				break;
			case SD1:
				if (rencontre.getSd() >= 1) {
					sd1Panel.setVisibility(View.VISIBLE);
					initSpinner(sd1List, femmeList, cat.name(), ll, rencontre.getSd1());
				} else {
					sd1Panel.setVisibility(View.GONE);
					sd1List = null;
				}
				break;
			case SD2:
				if (rencontre.getSd() >= 2) {
					sd2Panel.setVisibility(View.VISIBLE);
					initSpinner(sd2List, femmeList, cat.name(), ll, rencontre.getSd2());
				} else {
					sd2Panel.setVisibility(View.GONE);
					sd2List = null;
				}
				break;
			case DH1:
				if (rencontre.getDh() >= 1) {
					dh1Panel.setVisibility(View.VISIBLE);
					initDoubleSpinner(dh1J1List, dh1J2List, hommeList, hommeList, cat.name(), ll, rencontre.getDh1());
				} else {
					dh1Panel.setVisibility(View.GONE);
					dh1J1List = null;
					dh1J2List = null;
				}
				break;
			case DH2:
				if (rencontre.getDh() >= 2) {
					dh2Panel.setVisibility(View.VISIBLE);
					initDoubleSpinner(dh2J1List, dh2J2List, hommeList, hommeList, cat.name(), ll, rencontre.getDh2());
				} else {
					dh2Panel.setVisibility(View.GONE);
					dh2J1List = null;
					dh2J2List = null;
				}
				break;
			case DD1:
				if (rencontre.getDd() >= 1) {
					dd1Panel.setVisibility(View.VISIBLE);
					initDoubleSpinner(dd1J1List, dd1J2List, femmeList, femmeList, cat.name(), ll, rencontre.getDd1());
				} else {
					dd1Panel.setVisibility(View.GONE);
					dd1J1List = null;
					dd1J2List = null;
				}
				break;
			case DX1:
				if (rencontre.getDx() >= 1) {
					dx1Panel.setVisibility(View.VISIBLE);
					initDoubleSpinner(dx1J1List, dx1J2List, hommeList, femmeList, cat.name(), ll, rencontre.getDx1());
				} else {
					dx1Panel.setVisibility(View.GONE);
					dx1J1List = null;
					dx1J2List = null;
				}
				break;
			case DX2:
				if (rencontre.getDx() >= 2) {
					dx2Panel.setVisibility(View.VISIBLE);
					initDoubleSpinner(dx2J1List, dx2J2List, hommeList, femmeList, cat.name(), ll, rencontre.getDx2());
				} else {
					dx2Panel.setVisibility(View.GONE);
					dx2J1List = null;
					dx2J2List = null;
				}
				break;
			default:
				break;
			}

		}
	}

	private void initScore() {
		ArrayAdapter<String> scoreAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Arrays.asList("", "0", "1",
				"2", "3", "4", "5", "6", "7", "8"));
		scorep.setAdapter(scoreAdapter);
		scorec.setAdapter(scoreAdapter);

		scorep.setSelection(scoreAdapter.getPosition(rencontre.getMatchpour() != null ? (rencontre.getMatchpour() + "") : "0"));
		scorec.setSelection(scoreAdapter.getPosition(rencontre.getMatchcontre() != null ? (rencontre.getMatchcontre() + "") : "0"));

		// scorep.setText(rencontre.getMatchpour() != null ?
		// (rencontre.getMatchpour() + "") : "");
		// scorec.setText(rencontre.getMatchcontre() != null ?
		// (rencontre.getMatchcontre() + "") : "");
	}

	private void initCheckBox() {
		fin.setChecked(rencontre.getFinmatch() != null && rencontre.getFinmatch().equalsIgnoreCase("OK"));
		live.setChecked(rencontre.getLive() == 1);
	}

	private void initSpinner(Spinner spinner, List<String> joueurTxt, String cat, LinearLayout ll, String selectedValue) {
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, joueurTxt);
		spinner.setAdapter(adapter);
		if (selectedValue != null) {
			int position = adapter.getPosition(selectedValue);
			spinner.setSelection(position);
		}
	}

	private void initDoubleSpinner(Spinner spinner1, Spinner spinner2, List<String> joueurSpinner1Txt, List<String> joueurSpinner2Txt, String cat,
			LinearLayout ll, String selectedValueDouble) {

		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, joueurSpinner1Txt);
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, joueurSpinner2Txt);

		spinner1.setAdapter(adapter1);
		spinner2.setAdapter(adapter2);

		if (selectedValueDouble != null) {
			String[] split = selectedValueDouble.split(" - ");
			if (split.length == 2) {
				int positionJ1 = adapter1.getPosition(split[0]);
				int positionJ2 = adapter2.getPosition(split[1]);
				spinner1.setSelection(positionJ1);
				spinner2.setSelection(positionJ2);
			}
		}

	}
}
