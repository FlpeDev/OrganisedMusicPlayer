package com.android.flpe.organisedmusicplayer.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.flpe.organisedmusicplayer.R;
import com.android.flpe.organisedmusicplayer.models.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MusicsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MusicsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MusicsFragment extends Fragment {
    /*private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";*/

    /*private String mParam1;
    private String mParam2;*/

    private FragmentManager fm = null;
    private OnFragmentInteractionListener mListener;
    private View root;
    private List<SongFragment> songFragments;

    public MusicsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * //@param param1 Parameter 1.
     * //@param param2 Parameter 2.
     * @return A new instance of fragment MusicsFragment.
     */
    public static MusicsFragment newInstance(/*String param1, String param2*/) {
        MusicsFragment fragment = new MusicsFragment();
        Bundle args = new Bundle();
        /*args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);*/
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getActivity().getSupportFragmentManager();
        createSongList();
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    private void createSongList() {
        Song[] songs = Song.getSongList(getActivity());
        songFragments = new ArrayList<>();

        for(Song song : songs){
            SongFragment fragment = SongFragment.newInstance(song);
            songFragments.add(fragment);
            fm.beginTransaction().add(R.id.container, fragment).commit();
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        Thread iconThread = new Thread() {
            @Override
            public void run() {
                for(SongFragment fragment : songFragments){
                    fragment.putIconOnView();
                }
            }
        };
        iconThread.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_music, container, false);
        return root;
    }

    /*
    public void onButtonPressed(Uri uri) {
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
