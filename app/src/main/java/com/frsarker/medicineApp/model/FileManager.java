package com.frsarker.medicineApp.model;

import android.content.Context;

import com.frsarker.medicineApp.data.Medicine_object;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FileManager {
    public static final String medicine_image_folder = "medicine_image";
    public static final String medicine_info_folder = "medicine_info";
    public static final String data_folder = "data";
    private Context context;
    Medicine_object medicine_object;

    public  FileManager(Context context){
        this.context = context;
    }
    public ArrayList<Medicine_object> ReadMediacineInfoFromFile(){
        ArrayList<Medicine_object> medicine_objects = new  ArrayList<Medicine_object>();
        return medicine_objects;
    }
    public ArrayList<String> GetListInfoFile(){
        ArrayList<String>filesPathList = new ArrayList<>();

        return  filesPathList;
    }

    public boolean CheckFilePathExit(String directory){
        File file = new File(directory);
        if(file.exists() && file.isDirectory())
            return true;
            // Do something you have found your directory
        return false;
    }
//    public String GetAssetFolderPath(){
//        ArrayList<String> rootFolderPath = new ArrayList<>();
//        try {
//            String[] rootFolder = context.getAssets().list("");
//            rootFolderPath = new ArrayList<String>(Arrays.asList(rootFolder));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        for (String folderPath: rootFolderPath){
//            if(folderPath.contains(data_folder))
//                datafolderPath = rootFolderPath + data_folder;
//        }
//
//        boolean folderExits = CheckFilePathExit(datafolderPath);
//        return datafolderPath;
//    }

    public String GetSubFolderAssetPath(String subFoldername){
        String medicineImageFolder = subFoldername;
        ArrayList<String> rootFolderPath = new ArrayList<>();
        try {
            String[] rootFolder = context.getAssets().list("");
            rootFolderPath = new ArrayList<String>(Arrays.asList(rootFolder));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String subFoder: rootFolderPath){
            if (subFoder.contains(subFoldername) || subFoder == subFoldername)
                medicineImageFolder =  subFoder;
        }
        return medicineImageFolder;
    }
    public String GetMedicineImageFolderPath(){
        String medicineImageFolder = GetSubFolderAssetPath(medicine_image_folder);
        return medicineImageFolder;
    }


    public String GetMedicineInfoFolderPath(){
        String medicineInfoFolder =GetSubFolderAssetPath(medicine_info_folder); ;
        return medicineInfoFolder;
    }
    public ArrayList<String>GetListFileInSubFolderAssetPath(String subForderPath){


    }
}
