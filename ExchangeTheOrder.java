package tvcontroller.sei.com.lib;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenqianghua on 2018/11/22.
 */

public class ExchangeTheOrder {



    private static String trainList = "G:\\ToSmartPig\\trainset_txt_img_cat.list";
    private static String testList = "G:\\ToSmartPig\\testset_txt_img_cat.list";

    private static String trainList_newSort = "G:\\ToSmartPig\\trainset_txt_img_cat.list";
    private static String testList_newSort = "G:\\ToSmartPig\\testset_txt_img_cat.list";

    private static String test_docuId = "G:\\ToSmartPig\\test.txt";
    private static String train_docuId = "G:\\ToSmartPig\\train.txt";
    private static List<String> list_train;
    private static List<String> list_test;//记录的新顺序
    private static List<String> list_train_new;
    private static List<String> list_test_new;

    public static void main(String[] args){
        list_train = new ArrayList<>();
        list_test = new ArrayList<>();
        list_test_new = new ArrayList<>();
        list_train_new = new ArrayList<>();

    }

    private static FileInputStream fileInputStream = null;
    private static BufferedReader reader = null;
    public static void traverseByListFile(){
        try {
            fileInputStream = new FileInputStream("G:\\ToSmartPig\\trainset_txt_img_cat.list");
            reader = new BufferedReader(new InputStreamReader(fileInputStream));
            int num = 0;
            String line = "";
            while (line != null){
                num++;
                line = reader.readLine();
                System.out.println("line is " + line);
                list_train.add(line);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
