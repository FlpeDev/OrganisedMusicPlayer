package com.android.flpe.organisedmusicplayer.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.flpe.organisedmusicplayer.R;
import com.android.flpe.organisedmusicplayer.models.Song;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SongFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SongFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SongFragment extends Fragment {
    private static final String ARG_SONG = "SONG";
    private Song song;
    private View root;

    /*private String mParam1;
    private String mParam2;*/

    private OnFragmentInteractionListener mListener;

    public SongFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     //* @param param1 Parameter 1.
     //* @param param2 Parameter 2.
     * @return A new instance of fragment SongFragment.
     */
    public static SongFragment newInstance(Song song) {
        SongFragment fragment = new SongFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_SONG, song);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            song = getArguments().getParcelable(ARG_SONG);
            putInformationOnView();
        }
    }

    private void putInformationOnView() {
        ImageView songIcon = (ImageView) root.findViewById(R.id.casque);
        TextView nameText = (TextView) root.findViewById(R.id.title);
        TextView artistText = (TextView) root.findViewById(R.id.artist);
        TextView remixText = (TextView) root.findViewById(R.id.remix);
        TextView hourText = (TextView) root.findViewById(R.id.hour);

        nameText.setText(song.getName());
        if(song.getArtists() != null) {
            artistText.setText(TextUtils.join("", song.getArtists()));
        }
        if(song.getRemixArtists() != null) {
            remixText.setText(TextUtils.join("", song.getRemixArtists()));
        }
        hourText.setText(song.getDuration());

        Bitmap image = song.getImage();
        if(image != null){
            songIcon.setImageBitmap(image);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_song, container, false);
        return root;
    }

    /*public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        @SuppressWarnings("EmptyMethod")
        void onFragmentInteraction(Uri uri);
    }
}
