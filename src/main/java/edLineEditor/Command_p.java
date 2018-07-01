package edLineEditor;

import java.util.*;

public class Command_p extends Command implements CommandImp{
    @Override
    public void excute(String input_command) {

        original_file_array = EDLineEditor.cache_arr;

        String address = getAddress(input_command);


        if (common_is_exist(address)) {
            address_first_line = getRange(address)[0];
            address_second_line = getRange(address)[1];
        } else {
            address_first_line = getFinalLine(address);
            address_second_line = getFinalLine(address);
        }

        if (input_command.equals(";p")) {
            address_first_line = EDLineEditor.currentLine;
            address_second_line = original_file_array.size();
        }
        else if (input_command.equals(",p")) {
            address_first_line = 1;
            address_second_line = original_file_array.size();
        }
        else if (input_command.equals(".p")) {
            address_first_line = EDLineEditor.currentLine;
            address_second_line = EDLineEditor.currentLine;
        }

        //具体操作

        if (address_first_line < 0 || address_first_line > original_file_array.size()||address_first_line > address_second_line) {
            System.out.println("?");
        }
        else {
            for (int i = 1; i <= original_file_array.size(); i++) {
                if (address_first_line  <= i && i <= address_second_line ) {
                    System.out.print(original_file_array.get(i-1));
                }
            }
            EDLineEditor.currentLine = address_second_line;
        }
    }
    @Override
    public void undo() {

    }
}

