package edLineEditor;

import java.util.*;
public class Command_d extends Command implements CommandImp{


    @Override
    public void excute(String input_command) {

        EDLineEditor.last_cache_arr = EDLineEditor.cache_arr;
        original_file_array = EDLineEditor.cache_arr;
        EDLineEditor.result = arr_initialize(EDLineEditor.result);

        String address = getAddress(input_command);


        //地址获得

        if (common_is_exist(address)) {
            address_first_line = getRange(address)[0];
            address_second_line = getRange(address)[1];
        } else {
            if (input_command.equals(";d")) {
                address_first_line = original_file_array.size();
                address_second_line = original_file_array.size();
            } else if (input_command.equals("d")) {
                if (original_file_array.size() == 0) EDLineEditor.error_exist = true;
                address_first_line = EDLineEditor.currentLine;
                address_second_line = EDLineEditor.currentLine;
            } else {
                address_first_line = getFinalLine(address);
                address_second_line = getFinalLine(address);
            }
        }

        // 删除操作

        if (address_second_line > original_file_array.size()) {
            EDLineEditor.currentLine = address_first_line - 1;
            //被删除文本的上一行
        } else {
            EDLineEditor.currentLine = address_second_line + 1;
        }

        if(address_second_line > original_file_array.size()||address_second_line<0||address_first_line>address_second_line) EDLineEditor.error_exist =true;
        if (EDLineEditor.error_exist) {
            System.out.println("?");
        } else {


            for (int i = 1; i <= original_file_array.size(); i++) {
                if (address_first_line <= i && i <= address_second_line) {
                    continue;
                } else EDLineEditor.result.add(original_file_array.get(i - 1));
            }

            EDLineEditor.cache_arr = EDLineEditor.result;
            EDLineEditor.files_have_saved = false;
            //  Files.getInstance().fileSave(result);
        }
    }
    @Override
    public void undo() {

    }
}


