package edLineEditor;

import java.util.ArrayList;

public class Command_t extends Command implements CommandImp {
    @Override
    public void excute(String input_command) {

        EDLineEditor.last_cache_arr = EDLineEditor.cache_arr;
        original_file_array = EDLineEditor.cache_arr;
        EDLineEditor.result = arr_initialize(EDLineEditor.result);

            String transform_arr = "";

            if (common_is_exist(input_command)) {
                address_second_line = getRange(input_command)[1];
                address_first_line = getRange(input_command)[0];
            } else {
                if (input_command.charAt(0) == 't') {
                    address_first_line = EDLineEditor.currentLine;
                    address_second_line = EDLineEditor.currentLine;
                } else {
                    address_first_line = getFinalLine(input_command);
                    address_second_line = getFinalLine(input_command);
                }
            }


            int aimLine = getAimLne(input_command);

            EDLineEditor.currentLine = aimLine + 1;

            for (int i = 1; i <= original_file_array.size(); i++) {
                if (address_first_line <= i && i <= address_second_line) {
                    transform_arr += original_file_array.get(i - 1);
                }

            }

        /*
        for (int i = 0; i < arr.size(); i++){
            System.out.print(arr.get(i));
        }

        System.out.println("-----------");

*/

            for (int i = 1; i <= original_file_array.size() + address_second_line - address_first_line + 1; i++) {
                if (i < aimLine + 1) {
                    EDLineEditor.result.add(original_file_array.get(i - 1));
                } else if (i == aimLine + 1) {
                    EDLineEditor.result.add(transform_arr);
                    i = i + address_second_line - address_first_line;
                    EDLineEditor.currentLine = i;
                } else {
                    EDLineEditor.result.add(original_file_array.get(i - address_second_line + address_first_line - 2));
                }
            }
            //System.out.println(transform_arr);

            EDLineEditor.cache_arr = EDLineEditor.result;
            EDLineEditor.files_have_saved =false;
        }
    @Override
    public void undo() {

    }
}
