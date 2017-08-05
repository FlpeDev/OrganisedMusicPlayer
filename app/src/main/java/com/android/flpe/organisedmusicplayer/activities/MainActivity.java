package com.android.flpe.organisedmusicplayer.activities;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.android.flpe.organisedmusicplayer.R;
import com.android.flpe.organisedmusicplayer.fragments.ArtistsFragment;
import com.android.flpe.organisedmusicplayer.fragments.ContactFragment;
import com.android.flpe.organisedmusicplayer.fragments.FolderFragment;
import com.android.flpe.organisedmusicplayer.fragments.MusicsFragment;
import com.android.flpe.organisedmusicplayer.fragments.PlaylistFragment;
import com.android.flpe.organisedmusicplayer.fragments.SearchFragment;
import com.android.flpe.organisedmusicplayer.fragments.SongFragment;
import com.android.flpe.organisedmusicplayer.models.Song;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MusicsFragment.OnFragmentInteractionListener,
        ArtistsFragment.OnFragmentInteractionListener, SearchFragment.OnFragmentInteractionListener,
        PlaylistFragment.OnFragmentInteractionListener, FolderFragment.OnFragmentInteractionListener,
        ContactFragment.OnFragmentInteractionListener, SongFragment.OnFragmentInteractionListener{

    private static final String FRAGMENT_KEY = "idFragment";

    private FragmentManager fm = null;
    private Fragment fragment = null;
    private int idFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        fm = getSupportFragmentManager();
        if(savedInstanceState == null){
            fragment = MusicsFragment.newInstance();
            idFragment = 0;
        }
        else{                                      //les fragments sont detruits (par defaut) lors de la rotation de l'ecran : tt est recréé ici
            idFragment = savedInstanceState.getInt(FRAGMENT_KEY);
            fragment = getGoodFragment(idFragment);
        }
        fm.beginTransaction().replace(R.id.content_to_replace,fragment).commit();
    }

    private Fragment getGoodFragment(int id){
        Fragment retour = null;
        switch(id){
            case 0:
                retour = MusicsFragment.newInstance();
                break;
            case 1:
                retour = ArtistsFragment.newInstance();
                break;
            case 2:
                retour = SearchFragment.newInstance();
                break;
            case 3:
                retour = PlaylistFragment.newInstance();
                break;
            case 4:
                retour = FolderFragment.newInstance();
                break;
            case 5:
                retour = ContactFragment.newInstance();
                break;
        }
        return retour;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.right_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_music) {
            fragment = MusicsFragment.newInstance();
            idFragment = 0;
        } else if (id == R.id.nav_artist) {
            fragment = ArtistsFragment.newInstance();
            idFragment = 1;
        } else if (id == R.id.nav_search) {
            fragment = SearchFragment.newInstance();
            idFragment = 2;
        } else if (id == R.id.nav_playlist) {
            fragment = PlaylistFragment.newInstance();
            idFragment = 3;
        } else if (id == R.id.nav_folders) {
            fragment = FolderFragment.newInstance();
            idFragment = 4;
        } else if (id == R.id.nav_bug) {
            fragment = ContactFragment.newInstance();
            idFragment = 5;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        fm.beginTransaction().replace(R.id.content_to_replace,fragment).commit();
        return true;
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(FRAGMENT_KEY, idFragment);     //ca qui fait la sauvegarde de l'id de fragment
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
