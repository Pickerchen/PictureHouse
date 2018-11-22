package tvcontroller.sei.com.lib;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;

import javax.swing.text.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import sun.rmi.runtime.Log;

public class myClass {


    private static String path_test = "G:\\ToSmartPig\\test2.txt";
    private static String path_train = "G:\\ToSmartPig\\train2.txt";
    private static String path_test_files = "G:\\ToSmartPig\\test";
    private static String path_train_files = "G:\\ToSmartPig\\train";
    private static   CharArrayWriter caw;
    public static void main(String[] args){
        caw = new CharArrayWriter();
        // 以文件夹作为遍历条件，以list文件中的顺序作为遍历条件
//        traverseByFile();
//         以list文件内容作为遍历条件进行读取
        traverseByListFile();
    }


    public static void parseXML(String path){
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        String string_buffer = "";
        String document_id = "";
        String document_name = "";
        String document_cat = "";
        String image_id = "";
        String image_name = "";
        String image_sectnum = "";
        try {
            DocumentBuilder db = documentBuilderFactory.newDocumentBuilder();
            org.w3c.dom.Document document = db.parse(path);
            NodeList testList = document.getChildNodes();
            NodeList testList2 = document.getElementsByTagName("document");

            NamedNodeMap namedNodeMap_document = testList2.item(0).getAttributes();
            for (int j=0; j<namedNodeMap_document.getLength(); j++){
                String nodeName = namedNodeMap_document.item(j).getNodeName();
                String value = namedNodeMap_document.item(j).getNodeValue();
                if (nodeName.equals("id")){
                    document_id = value;
                }
                else if (nodeName.equals("name")){
                    document_name = value;
                }
                else if (nodeName.equals("cat")){
                    document_cat = value;
                }
//                System.out.println("属性名："+namedNodeMap_document.item(j).getNodeName());
//                System.out.println("属性值："+namedNodeMap_document.item(j).getNodeValue());
//                namedNodeMap_document.item(j).getNodeName();
//                namedNodeMap_document.item(j).getNodeValue();
            }

            NodeList list_text = document.getElementsByTagName("text");
            NodeList list_image_set = document.getElementsByTagName("imageset");
            NodeList list_image = document.getElementsByTagName("image");
            NamedNodeMap namedNodeMap = list_image.item(0).getAttributes();
            System.out.println("namedNodeMap is " + namedNodeMap.getLength());
            for(int i=0; i<namedNodeMap.getLength(); i++){
                String nodeName = namedNodeMap.item(i).getNodeName();
                String value = namedNodeMap.item(i).getNodeValue();
                if (nodeName.equals("id")){
                    image_id = value;
                }
                else if (nodeName.equals("name")){
                    image_name = value;
                }
                else if (nodeName.equals("sectnum")){
                    image_sectnum = value;
                }
            }
            String text_Content = list_text.item(0).getTextContent();
            text_Content = text_Content.replaceAll("\t","");
            text_Content = text_Content.replaceAll("\n","");
            System.out.println("currentFileName is " + path + "textContent is " + text_Content);

            string_buffer += text_Content + "\t" + document_id + "\t" + document_name + "\t" + document_cat + "\t" + image_id + "\t" + image_name + "\t" + image_sectnum + "\n";
            writeStringToFile(string_buffer);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 替换html中不规则的字符串
     */
    public static void repalceAllTheWrongString(String path){
        //待替换字符
        String aStr="&ndash;";
        //替换字符
        String bStr="-";

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

        //替换
        String line=null;

        //以行为单位进行遍历
        try {
            while((line=br.readLine())!=null){
                //替换每一行中符合被替换字符条件的字符串
                line=line.replaceAll(aStr, bStr);
                //将该行写入内存
                caw.write(line);
                //添加换行符，并进入下次循环
                caw.append(System.getProperty("line.separator"));
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

    /**
     * 将字符串写进指定文件
     */

    public static void writeStringToFile(String stringBuffer){
        byte[] bytes = stringBuffer.getBytes();

//        try {
//            caw.write(stringBuffer);
//            caw.append(System.getProperty("line.separator"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            FileOutputStream fileOutputStream = new FileOutputStream(path_train);
//            fileOutputStream.write(stringBuffer.getBytes());
//            fileOutputStream.flush();
//            fileOutputStream.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        RandomAccessFile randomAccessFile;

        //随机访问文件流
        try {
            randomAccessFile = new RandomAccessFile(path_train,"rw");
            //文件长度
            long fileLength = randomAccessFile.length();
            randomAccessFile.seek(fileLength);
            randomAccessFile.write(bytes);
            randomAccessFile.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 以文件夹形式进行遍历
     */
    public static void traverseByFile(){
        File file = new File(path_train_files);
        File[] files = file.listFiles();
        for (int i=0; i<files.length; i++){
            parseXML(files[i].getAbsolutePath());
//           System.out.println("file.path is " + files[i].getAbsolutePath());
        }
    }

    /**
     * 以.list文件
     */
    private static FileInputStream fileInputStream = null;
    private static BufferedReader reader = null;
    private static String xmlPath = "G:\\ToSmartPig\\train\\";
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
                String fileName = line+".xml";
                String filaPath = xmlPath + fileName;
                parseXML(filaPath);
            }
                    }
                    catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}