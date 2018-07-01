package edLineEditor;

import java.io.*;
import java.util.*;

public class Files {
    // 文件读取并转为 arraylist

    //单例模式
    //创建对象
    public static Files instance = new Files();

    private Files(){}

    public static Files getInstance(){
        return instance;
    }

    //读文件并存入arraylist
    public ArrayList<String> fileRead(){

        ArrayList<String> arr = new ArrayList<String>();
        try{
            File file = new File(EDLineEditor.fileName);

            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);

            String line = null;

            while((line = reader.readLine()) != null){
                arr.add(line + '\n');
            }
            reader.close();}
         catch (IOException e) {
            e.printStackTrace();
        }
        return arr;
    }

    //文件保存
    public void fileSave(ArrayList<String> arr,String fileName, boolean if_covered){
        try{
            FileWriter writer = new FileWriter(fileName,if_covered);

            for (String a : arr){
                writer.write(a);
            }
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //在文件中寻找上一个匹配行
    public int findLastMatchLine(ArrayList<String> arr,String word) {

        int line = Command.getCurrentLine();

        for(int i = line-1; i > 0; i--){
            if(arr.get(i-1).contains(word))
            return i-1;
        }
        for(int i = 1; i<=arr.size(); i++){
            if(arr.get(i-1).contains(word))
                return i-1;
        }
        return -1;
    }

    //在文件中寻找下一个匹配行
    public int findNextMatchLine(ArrayList<String> arr,String word) {

        int line = Command.getCurrentLine();

        for(int i = line; i <= arr.size(); i++){
            if(arr.get(i-1).contains(word)&& i!=line) return i-1;
        }
        for(int i = 1; i<=arr.size(); i++){
            if(arr.get(i-1).contains(word))
                return i-1;
        }
        return -1;
    }


    public void createFile(String filename) throws IOException{
        File file=new File(filename);
        if(!file.exists())
            file.createNewFile();
    }

    public void renameFile(String fileName, String newFileName){
        File file = new File(fileName);
        file.renameTo(new File(newFileName));
    }
}
