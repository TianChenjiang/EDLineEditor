package edLineEditor;

import java.io.*;
import java.util.*;


public class EDLineEditor {

	/**
	 * 接收用户控制台的输入，解析命令，根据命令参数做出相应处理。
	 * 不需要任何提示输入，不要输出任何额外的内容。
	 * 输出换行时，使用System.out.println()。或者换行符使用System.getProperty("line.separator")。
	 *
	 * 待测方法为public static void main(String[] args)方法。args不传递参数，所有输入通过命令行进行。
	 * 方便手动运行。
	 *
	 * 说明：可以添加其他类和方法，但不要删除该文件，改动该方法名和参数，不要改动该文件包名和类名
	 */

	public static String fileName = "";
	public static String input = "";

    static boolean default_fileName_exisit = false;
    static String default_fileName = "";


    /**
     * cache_arr为缓存区 ArrayList
     * last_cache_arr为操作前 ArrayList
     * result 为每次操作后的ArrayList
     * temp 暂存ArrayList
     */
    public static ArrayList<String> cache_arr = new ArrayList<String>();
    public static ArrayList<String> last_cache_arr = new ArrayList<String>();
    public static ArrayList<String> result = new ArrayList<String>();
    public static ArrayList<String> temp = new ArrayList<String>();
    public static ArrayList<String> real_temp = new ArrayList<String>();

    public static Map<String, String> map = new HashMap<String, String>();

    /**
     * currentine 为当前行
     * s_location 为获得s字符在字符串的位置
     * error_exist为判断指令是否有错
     * files_saved为判断是否存储了文件
     */
    public static int currentLine = 1;
    public static int s_location = 0;
    public static int comma_location = 0;

    public static String s_input_command = "";
    public static boolean s_command_is_first = true;
    public static boolean error_exist = false;
    public static boolean files_have_saved = true;

    public static int count = 0;
    public static void main(String[] args) {

        Input.getInstance().content_input = new Scanner(System.in);

        while (true) {
            comma_location = 0;

            //System.out.println("Please input the command");

            error_exist = false;

            String command = Input.getInstance().content_input.nextLine();

            //输入 ed 读入文件 在缓存区操作
            if (command.contains("ed ")) {
                fileName = command.split(" ")[1];
                default_fileName_exisit = true;
                // 将文本读入缓存区
                Command.original_file_array = Files.getInstance().fileRead();

                temp = Command.original_file_array;
                result = Command.original_file_array;
                cache_arr = Command.original_file_array;
                currentLine = Command.original_file_array.size();
                continue;

            // 输入ed 在缓存区操作
            } else if (command.equals("ed")) {
                default_fileName_exisit = false;
                Command.arr_initialize(Command.original_file_array);
                Command.arr_initialize(cache_arr);
                Command.arr_initialize(result);

                //对初始变量进行初始化
                currentLine = 1;
                s_location = 0;
                comma_location = 0;

                 s_input_command = "";
                 s_command_is_first = true;
                 error_exist = false;
                 files_have_saved = true;
                continue;
            }

            // 进行退出操作
            if (command.equals("Q")) {
                break;
            }
            if (command.equals("q")) {
                if (!files_have_saved) {
                    System.out.println("?");
                    String input = Input.getInstance().content_input.nextLine();
                    if (input.equals("q")) {
                        break;
                    } else {
                        command = input;
                    }
                } else {
                    Input.getInstance().content_input.close();
                    break;
                }

            }
            if (command.equals("u")) {
                if(count == 0){
                    real_temp = last_cache_arr;
                    last_cache_arr = cache_arr;
                    cache_arr = real_temp;
                    count++;
                    continue;


                } else {
                    last_cache_arr = cache_arr;
                    cache_arr = temp;
                    count--;
                    continue;
                }

            }
            if(!Command.symbol_one(command)) {
                System.out.println("?");
                continue;
            }

            // 解析指令 新建不同的实例，执行excute方法
            String concreteCommand = Command.getConcreteCommand(command);

                switch (concreteCommand) {
                    case "z":
                        Command_z command_z = new Command_z();
                        command_z.excute(command);
                        continue;
                    case "c":
                        Command_c command_c = new Command_c();
                        command_c.excute(command);
                        continue;
                    case "a":
                        Command_a command_a = new Command_a();
                        command_a.excute(command);
                        continue;
                    case "i":
                        Command_i command_i = new Command_i();
                        command_i.excute(command);
                        continue;
                    case "d":
                        Command_d command_d = new Command_d();
                        command_d.excute(command);
                        continue;
                    case "p":
                        Command_p command_p = new Command_p();
                        command_p.excute(command);
                        continue;
                    case "t":
                        Command_t command_t = new Command_t();
                        command_t.excute(command);
                        continue;
                    case "w":
                        Command_w command_w = new Command_w();
                        command_w.excute(command);
                        continue;
                    case "W":
                        Command_w_upperCase command_w_upperCase = new Command_w_upperCase();
                        command_w_upperCase.excute(command);
                        continue;
                    case "s":
                        Command_s command_s = new Command_s();
                        s_command_is_first = !s_command_is_first;
                        command_s.excute(command);
                        continue;
                    case "m":
                        Command_m command_m = new Command_m();
                        command_m.excute(command);
                        continue;
                    case "j":
                        Command_j command_j = new Command_j();
                        command_j.excute(command);
                        continue;
                    case "k":
                        Command_k command_k = new Command_k();
                        command_k.excute(command);
                        continue;
                    case "f":
                        Command_f command_f = new Command_f();
                        command_f.excute(command);
                        continue;
                    case "=":
                        Command_print_line_num command_print_line_num = new Command_print_line_num();
                        command_print_line_num.excute(command);
                        continue;
                    default:
                        System.out.println("?");
                }
                error_exist = false;
            }

            Input.getInstance().content_input.close();
        }

	/*
	public static String inputMode(){
		String result = "";
        if (command_mode) {
            input = content_input.nextLine();
            result += input;
            return result;
        }
		while((input = content_input.nextLine()) != null) {
            result += input;
            if (input.equals(".")) {
                break;
            }
            if (input.contains("ed ")) {
                fileName = input.split(" ")[1];
                command_mode = true;
                break;
            } else {
                    File empty_file = new File("empty_file.txt");
                    command_mode = true;
                    break;
                }
            }
        return result;
        }
        */

	//将读入内容转为arraylist
    public static ArrayList<String> change_array(String str){
	    ArrayList<String> arr = new ArrayList<String>();
	    String[] input_split = str.split("\n");
	    for(int i = 0; i < input_split.length; i++){
	        if(i != input_split.length-1) {
                arr.add(input_split[i] + '\n');
            }
	            else{
	            arr.add(input_split[i]);
            }
        }
	    return arr;
    }

    /*
    public static ArrayList<ArrayList<String>> swap(ArrayList<ArrayList<String>> arr){
        ArrayList<String> temp_arr = new ArrayList<String>();
        temp_arr = arr.get(0);
        arr.set(0,arr.get(1));
        arr.set(1,temp_arr);
        return arr;
    }
    */
}
