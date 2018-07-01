package edLineEditor;

import java.util.*;

public class Command_a extends Command implements CommandImp{

    @Override
    public void excute(String input_command) {

        EDLineEditor.last_cache_arr = EDLineEditor.cache_arr;
        original_file_array = EDLineEditor.cache_arr;
        EDLineEditor.result = arr_initialize(EDLineEditor.result);

        //地址解析
        address_line = Command_a.getFinalLine(input_command);

        if (input_command.equals("a")) {
            if(original_file_array.size() == 0){
                address_line = 1;
                EDLineEditor.error_exist = false;
            }
            else address_line = EDLineEditor.currentLine;
        }
        if(address_line <= 0) EDLineEditor.error_exist = true;


        // 具体操作

        if(EDLineEditor.error_exist){
            System.out.println("?");
        }
        else {
        input_array = EDLineEditor.change_array(Input.getInstance().inputMode());
        EDLineEditor.currentLine = address_line + input_array.size() - 1;


        if (original_file_array.size() == 0) {
            EDLineEditor.result.addAll(input_array);
            //EDLineEditor.result.add("\n");
            EDLineEditor.currentLine = input_array.size();
            EDLineEditor.cache_arr = EDLineEditor.result;

        } else {

            for (int i = 1; i <= original_file_array.size(); i++) {
                if (i < address_line) {
                    EDLineEditor.result.add(original_file_array.get(i - 1));
                }
                if (i == address_line) {
                    EDLineEditor.result.add(original_file_array.get(i - 1));
                    EDLineEditor.result.addAll(input_array);
                    EDLineEditor.result.add("\n");
                }
                if (i > address_line) {
                    EDLineEditor.result.add(original_file_array.get(i - 1));
                }
                EDLineEditor.currentLine = address_line + input_array.size();
            }

            EDLineEditor.cache_arr = EDLineEditor.result;
            EDLineEditor.files_have_saved = false;
        }
    }
    }
    @Override
    public void undo() {
    }
}

