package tvcontroller.sei.com.lib;


import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 文件夹分类
 * Created by chenqianghua on 2018/11/8.
 */

public class FilterFolder {

    private static String[] classifies = new String[]{"art","biology","geography","history","literature","media","music","royalty","sport","warfare"};
    private static List<Map<String,String>> typs = new ArrayList<>();
    private static String sourcePath = "G:\\wikipedia_dataset\\wikipedia_dataset\\texts\\";
    private static String destPath = "G:\\ToSmartPig\\test\\";
    public static void main(String[] args){
        FileInputStream fileInputStream = null;
        BufferedReader reader = null;
//        for (int i=0; i<classifies.length; i++){
//            String fileName = classifies[i];
//            File file = new File("G:\\ToSmartPig\\train\\"+fileName);
//            if (!file.exists()){
//                try {
//                    file.mkdirs();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        try {
             fileInputStream = new FileInputStream("G:\\wikipedia_dataset\\wikipedia_dataset\\testset_txt_img_cat.list");
             reader = new BufferedReader(new InputStreamReader(fileInputStream));
             int num = 0;
             String line = "";
             while (line != null){
                 num++;
                  line = reader.readLine();
//                 if (line.equals("")){
//                     continue;
//                 }
                line = line.replaceAll("\t","!");
                String name = line.split("!")[0];
                String fileType = line.split("!")[1];
                String fileName = name+".xml";
                String file_source_name = sourcePath + fileName;
                String dest_name = "";
                System.out.println(" fileName is " + name + " num is " + num);

                 dest_name = destPath  + fileName;
                 System.out.println("dest_name is " + dest_name + " sourceName is " + file_source_name + " num is " + num);

                 repalceAllTheWrongString(file_source_name);

                 copyFile(file_source_name,dest_name);

//                switch (fileType){
//                    case "1":
//                        dest_name = destPath + classifies[0] + "\\" + fileName;
//                        System.out.println("dest_name is " + dest_name + " sourceName is " + file_source_name + " num is " + num);
//                        copyFile(file_source_name,dest_name);
//                        break;
//                    case "2":
//                        dest_name = destPath + classifies[1] + "\\" + fileName;
//                        System.out.printf("dest_name is " + dest_name + " sourceName is " + file_source_name+ " num is " + num);
//                        copyFile(file_source_name,dest_name);
//                        break;
//                    case "3":
//                        dest_name = destPath + classifies[2] + "\\" + fileName;
//                        System.out.printf("dest_name is " + dest_name + " sourceName is " + file_source_name+ " num is " + num);
//                        copyFile(file_source_name,dest_name);
//                        break;
//                    case "4":
//                        dest_name = destPath + classifies[3] + "\\" + fileName;
//                        System.out.printf("dest_name is " + dest_name + " sourceName is " + file_source_name+ " num is " + num);
//                        copyFile(file_source_name,dest_name);
//                        break;
//                    case "5":
//                        dest_name = destPath + classifies[4] + "\\" + fileName;
//                        System.out.printf("dest_name is " + dest_name + " sourceName is " + file_source_name+ " num is " + num);
//                        copyFile(file_source_name,dest_name);
//                        break;
//                    case "6":
//                        dest_name = destPath + classifies[5] + "\\" + fileName;
//                        System.out.printf("dest_name is " + dest_name + " sourceName is " + file_source_name+ " num is " + num);
//                        copyFile(file_source_name,dest_name);
//                        break;
//                    case "7":
//                        dest_name = destPath + classifies[6] + "\\" + fileName;
//                        System.out.printf("dest_name is " + dest_name + " sourceName is " + file_source_name+ " num is " + num);
//                        copyFile(file_source_name,dest_name);
//                        break;
//                    case "8":
//                        dest_name = destPath + classifies[7] + "\\" + fileName;
//                        System.out.printf("dest_name is " + dest_name + " sourceName is " + file_source_name+ " num is " + num);
//                        copyFile(file_source_name,dest_name);
//                        break;
//                    case "9":
//                        dest_name = destPath + classifies[8] + "\\" + fileName;
//                        System.out.printf("dest_name is " + dest_name + " sourceName is " + file_source_name+ " num is " + num);
//                        copyFile(file_source_name,dest_name);
//                        break;
//                    case "10":
//                        dest_name = destPath + classifies[9] + "\\" + fileName;
//                        System.out.printf("dest_name is " + dest_name + " sourceName is " + file_source_name+ " num is " + num);
//                        copyFile(file_source_name,dest_name);
//                        break;
//                }
             }
//

//             System.out.printf("fileSize is " + line + " line.size is " + line.length());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void copyFile(final String sourcePath, final String destPath){
        new Thread(new Runnable() {
            @Override
            public void run() {
                int bytesum = 0;
                int byteread = 0;
                File oldFile = new File(sourcePath);
                File newFile = new File(destPath);
                if (!newFile.exists()){
                    try {
                        newFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    InputStream inputStream = new FileInputStream(oldFile);
                    FileOutputStream fileOutputStream = new FileOutputStream(newFile);
                    byte[] buffer = new byte[17408];
                    while ((byteread = inputStream.read(buffer)) != -1){
                        bytesum+=byteread;
                        fileOutputStream.write(buffer,0,byteread);
                        fileOutputStream.flush();
                    }
                    inputStream.close();
                    fileOutputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 替换html中不规则的字符串
     */
    public static void repalceAllTheWrongString(String path){
        //待替换字符
        String aStr="&ndash;";
        String cStr = "&mdash;";
        String eStr = " & ";
        String gStr = "&prime;";
        String rStr = "&";
        //替换字符
        String bStr="-";
        String dStr= "—";
        String fStr = "&";
        String hStr = "′";
        String sStr = " ";
        //读取文件
        File file=new File(path);

        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //内存流
        CharArrayWriter caw=new CharArrayWriter();
        RandomAccessFile randomAccessFile;

        //随机访问文件流
//        try {
//            randomAccessFile = new RandomAccessFile(path,"rw");
//            //文件长度
//            long fileLength = randomAccessFile.length();
//            randomAccessFile.seek(fileLength);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //替换
        String line=null;

        //以行为单位进行遍历
        try {
            while((line=br.readLine())!=null){
                //替换每一行中符合被替换字符条件的字符串
                line=line.replaceAll(aStr, bStr);
                line=line.replaceAll(cStr,dStr);
                line=line.replaceAll(eStr,fStr);
                line=line.replaceAll(gStr,hStr);
                line = line.replaceAll(rStr,sStr);
                //将该行写入内存
                caw.write(line);
                //添加换行符，并进入下次循环
//                caw.append(System.getProperty("line.separator"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //关闭输入流
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //将内存中的流写入源文件
        FileWriter fw= null;
        try {
            fw = new FileWriter(file);
            caw.writeTo(fw);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
