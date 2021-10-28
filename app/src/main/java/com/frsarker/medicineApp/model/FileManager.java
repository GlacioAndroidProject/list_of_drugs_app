package com.frsarker.medicineApp.model;

import android.content.Context;

import com.frsarker.medicineApp.data.Medicine_object;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

        ArrayList<String>mediacineInfoFileList = GetListMedicineInfoFile();
        ArrayList<String>medicineImageFiles = GetListMedicineImageFile();

        for (String filePath: mediacineInfoFileList){
            int count = 0;
            Medicine_object medicine_object = new Medicine_object();
            try {
                InputStream inputStream = context.getAssets().open(filePath);
                //Read text from file
                StringBuilder text = new StringBuilder();

                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while ((line = br.readLine())!= null) {
                        if(count == 0){
                            medicine_object = new Medicine_object();
                            medicine_object.setName(line);
                            for (String imagePath: medicineImageFiles){
                                if(imagePath.contains(medicine_object.getName()))
                                {
                                    medicine_object.setImageUrl(imagePath);
                                    break;
                                }
                            }
                            count++;
                        }
                        else if (count == 1){
                            medicine_object.setType(line);
                            count++;
                        }
                        else if (count == 2){
                            medicine_object.setContent(line);
                            count++;
                        }
                        else if (count == 3){
                            medicine_object.setUses(line);
                            count = 0;
                            medicine_objects.add(medicine_object);
                        }
                    }
                    br.close();
                }
                catch (IOException e) {
                    System.out.println("Error: "+ e.toString());
                    //You'll need to add proper error handling here
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
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
        String [] list;
        ArrayList<String> filesPath = new ArrayList<>();
        ArrayList<String> filesName = new ArrayList<>();

        try {
            list = context.getAssets().list(subForderPath);
            filesName = new ArrayList<String>(Arrays.asList(list));
            for (String fileName:filesName){
                filesPath.add(subForderPath+ "/" + fileName);
            }
        }
        catch (Exception e) {

        }
        return  filesPath;

    }

    public ArrayList<String>GetListMedicineImageFile(){
        ArrayList<String> filesPath = new ArrayList<>();
        filesPath = GetListFileInSubFolderAssetPath(GetMedicineImageFolderPath());
        return  filesPath;
    }
    public ArrayList<String> GetListMedicineInfoFile(){
        ArrayList<String> filesPath = new ArrayList<>();
        filesPath = GetListFileInSubFolderAssetPath(GetMedicineInfoFolderPath());
        return  filesPath;
    }

}
