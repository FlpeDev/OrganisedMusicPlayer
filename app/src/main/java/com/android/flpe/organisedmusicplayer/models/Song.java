package com.android.flpe.organisedmusicplayer.models;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Flo on 23/07/2017.
 */

public class Song implements Parcelable{

    private String path;

    private String name;
    private String[] artists;
    private String[] featuredArtists;
    private String[] remixArtists;
    private String duration;


    private static final String SONGNAME_REGEX = "^(.*) \\((.*) (?:Remix|remix)\\)$";
    private static final String ARTIST_REGEX = "^(.*) (?:featuring|Featuring|feat|feat.|Feat|Feat.|ft|ft.|Ft|Ft.|f.|F.) (.*)$";

    private Song(String dataString, String titleString, String artistString, String durationString){
        path = dataString;
        getDuration(durationString);
        getNameAndRemix(titleString);
        getArtists(artistString);
    }

    protected Song(Parcel in) {
        path = in.readString();
        name = in.readString();
        artists = in.createStringArray();
        featuredArtists = in.createStringArray();
        remixArtists = in.createStringArray();
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    private void getArtists(String artistString) {
        Pattern p = Pattern.compile(ARTIST_REGEX);
        Matcher m = p.matcher(artistString);

        if(m.find()) {
            artists = m.group(1).split("&");
            featuredArtists = m.group(2).split("&");
        }
        else{
            artists = artistString.split("&");
            featuredArtists = null;
        }
    }

    private void getDuration(String durationString) {
        int durationInSeconds = Integer.parseInt(durationString)/1000;
        int nbOfMinutes = durationInSeconds/60;
        int nbOfSeconds = durationInSeconds - 60*nbOfMinutes;
        duration = nbOfMinutes + ":" + String.format("%02d", nbOfSeconds);
    }

    private void getNameAndRemix(String titleString){
        Pattern p = Pattern.compile(SONGNAME_REGEX);
        Matcher m = p.matcher(titleString);

        if(m.find()) {
            name = m.group(1);
            remixArtists = m.group(2).split("&");
        }
        else{
            name = titleString;
            remixArtists = null;
        }
    }






    public static Song[] getSongList(Activity activity){
        ContentResolver cr = activity.getContentResolver();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        Cursor cur = cr.query(uri, null, selection, null, sortOrder);


        if(cur != null)
        {
            int count = cur.getCount();
            Song[] songs = new Song[count];

            if(count > 0)
            {
                while(cur.moveToNext())
                {
                    songs[cur.getPosition()] = new Song(cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.DATA)),
                            cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.TITLE)),
                            cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.ARTIST)),
                            cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.DURATION)));
                }
            }
            cur.close();
            return songs;
        }
        return new Song[0];
    }


    public Bitmap getImage(){
        Bitmap image;

        MediaMetadataRetriever mData=new MediaMetadataRetriever();
        mData.setDataSource(path);

        try{
            byte art[]=mData.getEmbeddedPicture();
            image= BitmapFactory.decodeByteArray(art, 0, art.length);
        }
        catch(Exception e){
           image=null;
        }

        return image;
    }


    //<editor-fold description="getters">
    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public String[] getArtists() {
        return  artists;
    }

    public String[] getFeaturedArtists() {
        return featuredArtists;
    }

    public String[] getRemixArtists() {
        return remixArtists;
    }

    public String getDuration() {
        return duration;
    }
    //</editor-fold>

    //<editor-fold description="Parcelable auto-generated methods">
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(path);
        parcel.writeString(name);
        parcel.writeStringArray(artists);
        parcel.writeStringArray(featuredArtists);
        parcel.writeStringArray(remixArtists);
    }
    //</editor-fold>
}
