package edLineEditor;

public class Command_s extends Command implements CommandImp{
    @Override
    public void excute(String input_command) {
        /*
        (.,.)s[/str1/str2/count]
	    (.,.)s[/str1/str2/g]
	    (.,.)s
       有符合要在指定行用str2替换str1。当最后为count（正整数）时，对指定的每一行，替换第count个匹配的str1（若该行没有，则不替换），
       若不指定，即(.,.)s/str1/str2/，默认每一行替换第一个。
	   当最后为g时，替换所有。当指定行没求的或没有第count个时，输出'?'。(.,.)s重复上一次替换命令，当前行被设为最后一个被改变的行。
         */

        EDLineEditor.last_cache_arr = EDLineEditor.cache_arr;
        original_file_array = EDLineEditor.cache_arr;
        EDLineEditor.result = arr_initialize(EDLineEditor.result);

        if(EDLineEditor.s_input_command != "" && input_command.charAt(input_command.length()-1) == 's'){
            int s_location = get_s_location(input_command);
            input_command = input_command + EDLineEditor.s_input_command.substring(get_s_location(EDLineEditor.s_input_command)+1);
        }

            if (common_is_exist(input_command)) {
                address_first_line = getRange(input_command)[0];
                address_second_line = getRange(input_command)[1];
            } else {
                if ((input_command.length()>1 &&input_command.substring(0,1).equals("s"))||input_command.equals("s")) {
                    address_first_line = EDLineEditor.currentLine;
                    address_second_line = EDLineEditor.currentLine;
                } else {
                    address_first_line = getFinalLine(input_command);
                    address_second_line = getFinalLine(input_command);
                }

            }

            if(input_command.endsWith("s") && EDLineEditor.s_input_command.equals("")){
               EDLineEditor.error_exist = true;
            }

            if (input_command.length() > 1 && input_command.substring(0, 2).equals(",s")) {
                address_first_line = 1;
                address_second_line = original_file_array.size();
                EDLineEditor.error_exist = false;
            }
            //System.out.print(input_command.substring(0,1));



        // 具体操作
        if (EDLineEditor.error_exist || address_second_line < address_first_line || address_second_line > original_file_array.size()) {
            System.out.println("?");
        } else {

            if (input_command.equals("s")) {
                address_first_line = 0;
                address_second_line = 0;
                excute(EDLineEditor.s_input_command);
            }
            else {

                String[] str_arr = getStr(input_command);

                String str1 = str_arr[0];
                String str2 = str_arr[1];
                String str3 = str_arr[2];

                EDLineEditor.currentLine = address_second_line;
                int a  = 0;
                if (str3.equals("")){

                    //只替换第一个
                    for (int i = 1; i <= original_file_array.size(); i++) {
                        if (address_first_line <= i && i <= address_second_line) {
                            if (original_file_array.get(i - 1).contains(str1)) {
                                original_file_array.set(i - 1, original_file_array.get(i - 1).replaceFirst(str1, str2));
                                EDLineEditor.currentLine = i;
                                a++;
                            }
                            if(a==0){
                                System.out.println("?");
                            }
                        }
                        // System.out.print(original_file_array.get(i-1));
                        EDLineEditor.result.add((original_file_array.get(i - 1)));
                    }
                }


                /**
                 *
                 *  (.,.)s[/str1/str2/count]
                 *  (.,.)s[/str1/str2/g]
                 *  这两种情况
                 */

                else if (str3.equals("g")) {
                    for (int i = 1; i <= original_file_array.size(); i++) {
                        if (address_first_line <= i && i <= address_second_line) {
                            if (original_file_array.get(i - 1).contains(str1)) {
                                EDLineEditor.currentLine = i;
                                original_file_array.set(i - 1, original_file_array.get(i - 1).replaceAll(str1, str2));
                                EDLineEditor.currentLine = i;
                            }
                        }
                        EDLineEditor.result.add((original_file_array.get(i - 1)));
                    }

                } else {
                    boolean have_matched = false;
                    if(EDLineEditor.error_exist) System.out.println("?");
                    else{
                    int real_count = 0;
                    if (Character.isDigit(str3.charAt(0))) {
                        int count = Integer.parseInt(str3);
                        for (int i = 1; i <= original_file_array.size(); i++) {
                            if (address_first_line <= i && i <= address_second_line) {
                                int strIndex = original_file_array.get(i - 1).indexOf(str1);
                                if (strIndex == -1) {
                                    real_count = 0;
                                    System.out.println("?");
                                    have_matched = true;
                                    break;
                                } else {
                                    while (strIndex != -1 && real_count <= count) {
                                        //从之前找的位置的后面接着找
                                        if (real_count == count - 1) {
                                            String str_1 = original_file_array.get(i - 1).substring(0, strIndex);
                                            String str_2 = original_file_array.get(i - 1).substring(strIndex).replaceFirst(str1, str2);
                                            String relace_str = str_1 + str_2;
                                            original_file_array.set(i - 1, relace_str);
                                            EDLineEditor.currentLine = i;
                                            // System.out.print(original_file_array.get(i-1));
                                            have_matched = true;
                                            real_count++;
                                            break;
                                        }
                                        // 最后找到匹配是 real_count  = count
                                        strIndex = original_file_array.get(i - 1).indexOf(str1, strIndex + 1);
                                        real_count++;
                                    }


                                }
                                real_count = 0;
                            }
                            EDLineEditor.result.add((original_file_array.get(i - 1)));
                        }
                        //如果没有找到匹配 则输出？
                        if(!have_matched) {

                            System.out.println("?");
                        }
                    }

                }
            }

            EDLineEditor.cache_arr = EDLineEditor.result;


        EDLineEditor.s_input_command = input_command;
        EDLineEditor.s_location = 0;
    }EDLineEditor.files_have_saved = false;
    }
    }
    @Override
    public void undo() {

    }

}
