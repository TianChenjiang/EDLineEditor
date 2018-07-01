package edLineEditor;

import java.util.ArrayList;

public class Command_m extends Command implements CommandImp{
    @Override
    public void excute(String input_command) {

        EDLineEditor.last_cache_arr = EDLineEditor.cache_arr;
        original_file_array = EDLineEditor.cache_arr;
        EDLineEditor.result = arr_initialize(EDLineEditor.result);

            ArrayList<String> arr = new ArrayList<String>();

            String transform_arr = "";

            if (common_is_exist(input_command)) {
                address_second_line = getRange(input_command)[1];
                address_first_line = getRange(input_command)[0];
            } else {
                if (input_command.charAt(0) == 'm') {
                    address_first_line = EDLineEditor.currentLine;
                    address_second_line = EDLineEditor.currentLine;
                } else {
                    address_first_line = getFinalLine(input_command);
                    address_second_line = getFinalLine(input_command);
                }
            }

            //具体操作

            int aimLine = getAimLne(input_command);
            EDLineEditor.currentLine = aimLine;


            for (int i = 1; i <= original_file_array.size(); i++) {
                if (address_first_line <= i && i <= address_second_line) {
                    transform_arr += original_file_array.get(i - 1);
                } else arr.add(original_file_array.get(i - 1));
            }

        /*
        for (int i = 0; i < arr.size(); i++){
            System.out.print(arr.get(i));
        }

        System.out.println("-----------");

*/
        if(!common_is_exist(input_command)){
            for (int i = 1; i <= original_file_array.size(); i++) {
                if (i < aimLine) {
                    EDLineEditor.result.add(arr.get(i - 1));
                } else if (i == aimLine) {
                    EDLineEditor.result.add(transform_arr);
                } else {
                    EDLineEditor.result.add(arr.get(i - 1));
                }
            }
        }
        else {
            for (int i = 1; i <= original_file_array.size(); i++) {
                if (i < aimLine - address_second_line + address_first_line ) {
                    EDLineEditor.result.add(arr.get(i - 1));
                } else if (i == aimLine - address_second_line + address_first_line ) {
                    EDLineEditor.result.add(transform_arr);
                    i = i + address_second_line - address_first_line;
                    EDLineEditor.currentLine = i;
                } else {
                   //EDLineEditor.result.add(arr.get(i - address_second_line + address_first_line - 1 - 1));
                    EDLineEditor.result.add(arr.get(i - address_second_line + address_first_line-2));
                }
            }


        }
        EDLineEditor.cache_arr = EDLineEditor.result;
        EDLineEditor.files_have_saved = false;
    }
    @Override
    public void undo() {

    }
}
