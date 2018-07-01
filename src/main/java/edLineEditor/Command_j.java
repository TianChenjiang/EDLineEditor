package edLineEditor;

public class Command_j extends Command_d implements CommandImp {
    @Override
    public void excute(String input_command) {

        EDLineEditor.last_cache_arr = EDLineEditor.cache_arr;
        original_file_array = EDLineEditor.cache_arr;
        EDLineEditor.result = arr_initialize(EDLineEditor.result);

            String merge_line_str = "";

            if (common_is_exist(input_command)) {
                address_second_line = getRange(input_command)[1];
                address_first_line = getRange(input_command)[0];
            } else {
                if (input_command.equals("j")) {
                    address_first_line = EDLineEditor.currentLine;
                    address_second_line = EDLineEditor.currentLine + 1;
                    if(address_second_line > original_file_array.size()) EDLineEditor.error_exist = true;
                } else {
                    address_first_line = getFinalLine(input_command);
                    address_second_line = getFinalLine(input_command);
                }
            }


            if (input_command.equals(",j")) {
                address_first_line = 1;
                address_second_line = original_file_array.size();
                EDLineEditor.error_exist = false;
            }

            if(address_first_line < 0 || address_second_line < 0 || address_first_line > address_second_line){
                EDLineEditor.error_exist = true;
            }

            if(EDLineEditor.error_exist){
                System.out.println("?");
            }else {
                for (int i = 1; i <= original_file_array.size(); i++) {
                    if (address_first_line <= i && i <= address_second_line) {
                        merge_line_str += original_file_array.get(i - 1);
                        merge_line_str = merge_line_str.replaceAll("\n", "");
                    }
                }


                // 2 - 3
                for (int i = 1; i <= original_file_array.size() - (address_second_line - address_first_line); i++) {
                    if(i < address_first_line){
                        EDLineEditor.result.add(original_file_array.get(i - 1));
                    }
                    else if (i == address_first_line) {
                        EDLineEditor.result.add(merge_line_str + "\n");
                        //EDLineEditor.result.add(original_file_array.get(i));
                        EDLineEditor.currentLine = i;
                    } else EDLineEditor.result.add(original_file_array.get(i - 1+(address_second_line - address_first_line)));
                }
                // System.out.print(merge_line_str);

                EDLineEditor.cache_arr = EDLineEditor.result;
                EDLineEditor.files_have_saved = false;
            }
    }
    @Override
    public void undo() {

    }
}
