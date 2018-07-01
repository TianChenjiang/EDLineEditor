package edLineEditor;

public class Command_c extends Command implements CommandImp {
    @Override
    public void excute(String input_command) {

        EDLineEditor.last_cache_arr = EDLineEditor.cache_arr;
        original_file_array = EDLineEditor.cache_arr;
        EDLineEditor.result = arr_initialize(EDLineEditor.result);


        //地址解析
        if (common_is_exist(input_command)) {
            address_second_line = getRange(input_command)[1];
            address_first_line = getRange(input_command)[0];
        } else {
            if (input_command.charAt(0) == ';') {
                address_first_line = original_file_array.size();
                address_second_line = original_file_array.size();
            } else if(input_command.charAt(0) == ','){
                address_first_line = EDLineEditor.currentLine;
                address_second_line = original_file_array.size();
            }
            else if(input_command.equals("c")){
                address_first_line = EDLineEditor.currentLine;
                address_second_line = EDLineEditor.currentLine;
                if(original_file_array.size() == 0 ) EDLineEditor.error_exist = true;
            }
            else {
                address_first_line = getFinalLine(input_command);
                address_second_line = getFinalLine(input_command);
            }
        }

        // 具体操作
        if (EDLineEditor.error_exist||address_second_line > original_file_array.size()|| address_second_line < 0) {
            System.out.println("?");
        } else {

            if (input_command.equals(",c")) {
                address_first_line = 1;
                address_second_line = original_file_array.size();
            }

            input_array = EDLineEditor.change_array(Input.getInstance().inputMode());
            EDLineEditor.currentLine = address_line + input_array.size() - 1;

            if (input_array == null) {
                Command_d command_d = new Command_d();
                command_d.excute(input_command);
            } else {

                for (int i = 1; i <= original_file_array.size(); i++) {
                    if (i < address_first_line) {
                        EDLineEditor.result.add(original_file_array.get(i - 1));
                    }
                    if (address_first_line == i) {
                        EDLineEditor.result.addAll(input_array);
                        EDLineEditor.result.add("\n");
                        i += address_second_line - address_first_line;
                    }
                    if (i > address_second_line) {
                        EDLineEditor.result.add(original_file_array.get(i - 1));
                    }
                }
                // Files.getInstance().fileSave(result);
            }
            EDLineEditor.files_have_saved = false;
            EDLineEditor.cache_arr = EDLineEditor.result;
        }
    }
    @Override
    public void undo() {

    }
}
