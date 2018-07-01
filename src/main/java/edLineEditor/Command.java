package edLineEditor;

import java.util.ArrayList;
import java.util.Collections;

@SuppressWarnings("unchecked")
public class Command {

    static int address_line = 0;       // 无逗号时的地址目标行
    static int address_first_line = 0; // 有逗号时的地址开始行
    static int address_second_line = 0;// 有逗号时的地址结束行

    static boolean have_saved = true;

    /**
     * original_file_array 是指令前的缓存区内容转为ArrayList
     * input_array 是输入内容转为ArrayList
     */

    public static ArrayList<String> original_file_array = new ArrayList<String>();
    public static ArrayList<String> input_array = new ArrayList<String>();


    /**
     * 每次类的初始化将original_file_array 初始化
     * 将操作前的内容存入 last_cache_array
     * 将result 初始化
     *
     * 用command类里面的方法进行地址解析后 每个类得到地址 再进行实际的操作
     * 在command类中进行了是否为错误地址的判断 在类的实际操作前先判断error_exist 若是 则直接输出？
     * 在command类结束后进行还原 如果修改了缓存区内容没有修改则将files_saved变量改为false
     *
     * @param commandstr
     * @return
     */

    //去除指令 得到地址(该遍历也进行一部分的错误判断）
    public static String getAddress(String commandstr){
        String address = "";
        for(int i = 0; i < commandstr.length(); i++){

            char singleCommand = commandstr.charAt(i);

            // 遇到/ 跳过里面的内容
            if(singleCommand == '/') {
                if(address.charAt(i-1)=='-'||address.charAt(i-1)=='+') EDLineEditor.error_exist = true;
                address+= '/';
                for(int j = i+1; j < commandstr.length(); j++){
                    address += commandstr.charAt(j);
                    if(commandstr.charAt(j) == '/') {
                        i = j;
                        break;
                    }
                }
            }

            // 遇到？ 跳过里面的内容
            else if(singleCommand == '?'){
                if(address.charAt(i-1)=='-'||address.charAt(i-1)=='+') EDLineEditor.error_exist = true;
                address += '?';
                for(int j = i+1; j < commandstr.length(); j++){
                    address += commandstr.charAt(j);
                    if(commandstr.charAt(j) == '?') {
                        i = j;
                        break;
                    }
                }
            }
           else if(singleCommand == '\''){
                address += "" + singleCommand + commandstr.charAt(i+1);
                i+=2;
            }
            else if(singleCommand == ','){
                address += ",";
                EDLineEditor.comma_location = i;
            }
            // ; 和, 等相连接 错误
            else if(singleCommand == ';'){
                if(commandstr.charAt(i+1) == '+' || commandstr.charAt(i+1) == '-'){
                    EDLineEditor.error_exist = true;
                }
            }

            else if((singleCommand>='A' && singleCommand<='Z'  ||  singleCommand>='a' && singleCommand<='z') || singleCommand == '='){
                if(i==0){
                    address = "" + EDLineEditor.currentLine;
                }
                else return address;
            }
            else address += singleCommand;
        }
        return address;
    }




    //得到指令 a i w....
    public static String getConcreteCommand(String commandstr){

        for(int i = 0; i < commandstr.length(); i++){

            char singleCommand = commandstr.charAt(i);

            if(singleCommand == '/') {
                for(int j = i+1; j < commandstr.length(); j++){
                    if(commandstr.charAt(j) == '/'){
                        i = j;
                        break;
                    }
                }
            }

            else if(singleCommand == '?'){
                for(int j = i+1; j < commandstr.length(); j++){
                    if(commandstr.charAt(j) == '?') {
                        i = j;
                        break;
                    }
                }
            }

            else if(singleCommand == '\''){
                i+=1;
            }

            else if((singleCommand>='A' && singleCommand<='Z'  ||  singleCommand>='a' && singleCommand<='z') || singleCommand == '='){
                if(singleCommand == 's') {EDLineEditor.s_location = i;}
                return "" + singleCommand;
            }
        }
        return "?";
    }


    // 判断有无逗号
    public static boolean common_is_exist(String commandstr){
        for(int i = 0; i < commandstr.length(); i++){
            char singleCommand = commandstr.charAt(i);

            if(singleCommand == '/') {
                for(int j = i+1; j < commandstr.length(); j++){
                    if(commandstr.charAt(j) == '/'){
                        i = j;
                        break;
                    }
                }
            }

            else if(singleCommand == '?'){
                for(int j = i+1; j < commandstr.length(); j++){
                    if(commandstr.charAt(j) == '?') {
                        i = j;
                        break;
                    }
                }
            }

            if(commandstr.charAt(i) == ',' && i !=0) {
                EDLineEditor.comma_location = i;
                return true;
            }
        }
        return false;
    }


    public static boolean mark_is_exist(String commandStr){
        for(int i = 0; i < commandStr.length(); i++){
            if(commandStr.charAt(i) == '\'' && i !=0) return true;
        }
        return false;
    }



    // 如果有逗号，输出两个逗号各自的地址数组
    public static int[] getRange(String commandStr){
        String address = "";
        address = getAddress(commandStr);
        String[] address_range = new String[2];

        address_range[0] = address.substring(0,EDLineEditor.comma_location);
        address_range[1]  = address.substring(EDLineEditor.comma_location+1);


        int[] address_range_line = new int[2];
        address_range_line[0] = getFinalLine(address_range[0]);
        address_range_line[1] = getFinalLine(address_range[1]);

        if(address_range_line[0] > address_range_line[1]){
            address_range_line[0] = -1;
            EDLineEditor.error_exist = false;
        }

        return address_range_line;
    }

    // 对最小单位的解析
    public static int getLine(String address){
        int line = 0;
        String word = "";
        for(int i = 0; i < address.length(); i++) {
            if (address.charAt(i) == '?') {
                for (int j = i + 1; j < address.length(); j++) {
                    if (address.charAt(j) == '?') {
                        line = Files.getInstance().findLastMatchLine(EDLineEditor.cache_arr,word);
                        if(line < 0) EDLineEditor.error_exist = true;
                        word = "";
                        break;
                    }
                    word += address.charAt(j);
                }
            }
            if (address.charAt(i) == '/') {
                if (address.charAt(i) == '/') {
                    for (int j = i + 1; j < address.length(); j++) {
                        if (address.charAt(j) == '/') {
                            line = Files.getInstance().findNextMatchLine(EDLineEditor.cache_arr,word);
                            if(line < 0) EDLineEditor.error_exist = true;
                            word = "";
                            break;
                        }
                        word += address.charAt(j);
                    }

                }

            }

        }
        return line+1;
    }


    // 这里传入的是getLine()后的地址
    // 得到最后的行int数值
    public static int getFinalLine(String address){
        int final_line = 0;
        String simple_address = getSimpleAddress(address);

        for(int i = 0; i < simple_address.length(); i++){
            if(i == 0) {
                if ((simple_address.charAt(i) == '+')) {
                    final_line = EDLineEditor.currentLine + (simple_address.charAt(i+1) - '0');
                    i += 2;
                } else if ((simple_address.charAt(i) == '-')) {
                    final_line = EDLineEditor.currentLine - (simple_address.charAt(i+1) - '0');
                    i += 2;
                } else {
                    final_line = 0;
                }
                if(i >= simple_address.length()){
                    break;
                }
            }
            //将 + 转为加法运算
            if(simple_address.charAt(i) == '+'){
                final_line = final_line + simple_address.charAt(i+1) - '0';
                i += 2;
                continue;
            }
            //将 - 转为加法运算
            else if(simple_address.charAt(i) == '-'){
                final_line = final_line - (simple_address.charAt(i+1) - '0');
                i += 2;
                continue;
            }
            else final_line += (int)(simple_address.charAt(i) - '0');
        }
        if(final_line < 0||final_line > EDLineEditor.cache_arr.size()) {EDLineEditor.error_exist = true;}
        if(EDLineEditor.error_exist) return -1;
        return final_line;
    }


    public static int getCurrentLine(){ return EDLineEditor.currentLine; }


    //将地址中的特殊符号 . ; $改为代表行的数值
    public static String getSimpleAddress(String address){
        String simple_address = "";
        address = getAddress(address);
        for(int i = 0; i < address.length(); i++){
            if(address.charAt(i) == '/' ){
                simple_address += getLine(address);
                i = i + getNextMatchLine(address)[1] - getNextMatchLine(address)[0];
                continue;
            }
            else if(address.charAt(i) == '?'){
                simple_address += getLine(address);
                i = i + getLastMatchLine(address)[1] - getLastMatchLine(address)[0];
                continue;
            }
            else if(address.charAt(i) == '$'){
                simple_address += Files.getInstance().fileRead().size();
            }
            else if(address.charAt(i) == '.'){
                simple_address += EDLineEditor.currentLine;
            }
            else if(address.charAt(i) == '\''){
               String mark = "" + address.charAt(i+1);
                int a = findMarkLine(EDLineEditor.cache_arr,"\'"+ mark);
                simple_address += a+1;
                i += 1;
            }
            else{
                simple_address += address.charAt(i);
            }
        }
        return simple_address;
    }

    //在w 和 W指令中得到后面的文件名
    public static String getFileName(String command){
        String fileName = "";
        int start = 0;
        for(int i = 0; i < command.length(); i ++){
            char singleCommand = command.charAt(i);

            if(singleCommand == '/') {
                for(int j = i+1; j < command.length(); j++){
                    if(command.charAt(j) == '/'){
                        i = j;
                        break;
                    }
                }
            }

            else if(singleCommand == '?'){
                for(int j = i+1; j < command.length(); j++){
                    if(command.charAt(j) == '?') {
                        i = j;
                        break;
                    }
                }
            }

            else if(singleCommand == 'w' || singleCommand == 'W'){
                start = i+2;
                int a = command.length() -1;
                if(start < command.length()) fileName = command.substring(start,command.length());
                else fileName = "";
            }
        }

        return fileName;
    }

    /**
     *  s 指令得到s的地址（防止s字符在/ / ？？里面出现造成干扰
     *  这个方法可以在第一个方法里面实现
     * @param commandstr
     * @return
     */

    public static int get_s_location(String commandstr){
        String address = "";
        for(int i = 0; i < commandstr.length(); i++){

            char singleCommand = commandstr.charAt(i);

            if(singleCommand == '/') {
                address+= '/';
                for(int j = i+1; j < commandstr.length(); j++){
                    address += commandstr.charAt(j);
                    if(commandstr.charAt(j) == '/') {
                        i = j;
                        break;
                    }
                }
            }

            else if(singleCommand == '?'){
                address += '?';
                for(int j = i+1; j < commandstr.length(); j++){
                    address += commandstr.charAt(j);
                    if(commandstr.charAt(j) == '?') {
                        i = j;
                        break;
                    }
                }
            }
            else if(singleCommand == '\''){
                address += "" + singleCommand + commandstr.charAt(i+1);
                i+=2;
            }

            else if((singleCommand>='A' && singleCommand<='Z'  ||  singleCommand>='a' && singleCommand<='z') || singleCommand == '='){
                if(i==0){
                    address = "" + EDLineEditor.currentLine;
                }
                else if(singleCommand == 's') return i;
            }
        }
        return 0;
    }


    //得到 m t 的目标地址
    public static int getAimLne(String command){
        int aimLine = 0;
        int location = 0;
        String aim_address = "";
        for(int i = 0; i < command.length(); i++){
            if(command.charAt(i) == 'm' || command.charAt(i) == 't'){
                if(i == command.length()-1) return EDLineEditor.currentLine;
                location = i;
                aim_address = command.substring(i+1);
                aimLine = getFinalLine(aim_address);
            }
        }

        if(EDLineEditor.error_exist) return -1;
        return aimLine;
    }

    // result initialize
    public static ArrayList<String> arr_initialize(ArrayList<String> arr){
        ArrayList<String> null_arr = new ArrayList();
        arr = null_arr;
        return arr;
    }


    // s指令中 得到替换的字符串
    // 传出str数组
    public static String[] getStr(String commandStr){

        String str = commandStr.substring(get_s_location(commandStr)+2,commandStr.length());

        String[] str_arr =  new String[3];

        String[] split_arr = str.split("/",-1);

        /*
        for(int i = 0; i < split_arr.length; i++){
            System.out.print(split_arr[i]);
        }
        */

        for(int i = 0; i < split_arr.length; i++){
            str_arr[i] = split_arr[i];
           // System.out.print(str_arr[i]);
        }
        return str_arr;
    }


/*
    public static String getSingleAddress(String address){
        String result = "";
        for(int i = 0; i < address.length(); i++){
            if(address.charAt(i) == '/'){
                i += getNextMatchLine(address)[1] - getNextMatchLine(address)[0];
                result += address.substring(getNextMatchLine(address)[0],getNextMatchLine(address)[1]);
            }
            else if(address.charAt(i) == '?'){
                i += getLastMatchLine(address)[1] - getLastMatchLine(address)[0];
            }

            else result += address.charAt(i);
        }
        return result;
    }
    */


    /**
     * 刚开始写代码时候得到两个/的位置值
     * 后来被用来获得两个// 和 ？？的距离差
     * 可修改
     * @param command
     * @return
     */
    public static int[] getNextMatchLine(String command){

        int first_diagnoal = 0;
        int second_diagnoal = 0;

        int[] diagnoal_arr = new int[2];

        for(int i = 0; i < command.length(); i++){
            if(command.charAt(i) == '/'){
                first_diagnoal = i;
                diagnoal_arr[0] = first_diagnoal;
                for(int j = i+1; j < command.length(); j++){
                    if(command.charAt(j) == '/'){
                        second_diagnoal = j;
                        diagnoal_arr[1] = second_diagnoal;
                        return diagnoal_arr;
                    }
                }
            }
        }
        return diagnoal_arr;
    }


    public static int[] getLastMatchLine(String command){

            int first_questionmark = 0;
            int second_questionmark = 0;

            int[] questionmark_arr = new int[2];

            for(int i = 0; i < command.length(); i++){
                if(command.charAt(i) == '?'){
                    first_questionmark = i;
                    questionmark_arr[0] = first_questionmark;
                    for(int j = i+1; j < command.length(); j++){
                        if(command.charAt(j) == '?'){
                            second_questionmark = j;
                            questionmark_arr[1] = second_questionmark;
                            return questionmark_arr;
                        }
                    }
                }
            }
            questionmark_arr[0] = first_questionmark;
            questionmark_arr[1] = second_questionmark;
            return questionmark_arr;
        }

     public static boolean symbol_one(String input){
        int question_count = 0;
        int diagonal_count = 0;
        for(int i = 0; i < input.length(); i++){
            if(input.charAt(i) == '?') question_count++;
            if(input.charAt(i) == '/') diagonal_count++;
        }
        if(question_count == 1 || diagonal_count == 1){
            return false;
        }
        return true;
     }

     // k指令中得到它所标记的行号
     public static int findMarkLine(ArrayList<String> arr, String mark){
        // mark 'k

        int line = 0;

        String mark_line = EDLineEditor.map.get(mark);
        for(int i = 0; i < arr.size(); i ++){
            if(arr.get(i).equals(mark_line)){
                return i + 1;
            }
        }
        return -1;
     }
}
