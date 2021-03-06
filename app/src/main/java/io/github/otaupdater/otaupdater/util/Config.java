package io.github.otaupdater.otaupdater.util;

import android.app.DownloadManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import io.github.otaupdater.otalibary.util.ShellExecuter;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * Created by sumit on 20/12/16.
 */

public class Config {
    public static boolean ShowLog;
    public static Uri uri;
    public static String DownloadFileName;
    public static String Tag="RomUpdaterConfig";
    public static DownloadManager downloadManager;
    public static String URL_OLD_RELEASES(){
        ShellExecuter.command="getprop ro.updater.oldrelease.url";
        String output=ShellExecuter.runAsRoot();
        return output;
    }
    public static String Showlog(){
        ShellExecuter.command="getprop ro.otaupdate.enable_log";
        String output=ShellExecuter.runAsRoot();
        if(output.equals(false))
        {
            ShowLog=false;
        }else {
            ShowLog=true;
        }
        if(output==null)
        {
            ShowLog=true;
        }
        return output;
    }
    public static boolean ShowToast;
    public static String ShowToast(){
        String output;
        ShellExecuter.command="getprop ro.otaupdate.enable_toast";
        output=ShellExecuter.runAsRoot();
        if(output.equals(false))
        {
            ShowToast=false;
        }else {
            ShowToast=true;
        }
        if(output==null)
        {
            ShowToast=true;
        }
        return output;
    }
    public static String UpdaterUri(){
        ShellExecuter.command="getprop ro.updater.uri";
        String Output=ShellExecuter.runAsRoot();
        if(Output==null)
        {
            Log.d("RomUpdater","Try to add Custom Url in Config.java");
            Output="";
        }
        return Output;
    }
    public static boolean isOnline(Context c) {
        ConnectivityManager conMgr = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if(netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()){
            Log.i(Tag,"No Internet connection");
            return false;
        }
        return true;
    }

    public static void Downloader(Context c){
        downloadManager = (DownloadManager) c.getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, DownloadFileName);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        Long reference = downloadManager.enqueue(request);

    }

}
