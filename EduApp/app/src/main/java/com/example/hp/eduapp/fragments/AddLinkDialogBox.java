package com.example.hp.eduapp.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.hp.eduapp.R;

/**
 * Created by radman on 10/22/2016.
 */
public class AddLinkDialogBox extends DialogFragment {

    EditText urlText;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.save_link_dialog_layout, null);
        urlText = (EditText) rootView.findViewById(R.id.link_edittext);

        builder.setTitle(R.string.link_dialog_title_message)
                .setView(rootView)
                .setPositiveButton(R.string.save_link_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String url = Uri.parse(urlText.getText().toString()).buildUpon().build().toString();
                        LinksFragment.links.add(url);
                        LinksFragment.linksAdapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton(R.string.cancel_save_link, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //cancel the operation
                        AddLinkDialogBox.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }
}
