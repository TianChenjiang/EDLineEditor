package edLineEditor;

import java.io.File;
import java.io.IOException;

public class Command_w_upperCase extends Command implements CommandImp {
    @Override
    public void excute(String input_command) {
        original_file_array = EDLineEditor.cache_arr;
        EDLineEditor.result = arr_initialize(EDLineEditor.result);


        String fileName = getFileName(input_command);

        if (common_is_exist(input_command)) {
            address_first_line = getRange(input_command)[0];
            address_second_line = getRange(input_command)[1];
        } else {
            address_first_line = getFinalLine(input_command);
            address_second_line = getFinalLine(input_command);
        }

        if(input_command.charAt(0) == 'W'){
            address_first_line = 1;
            address_second_line = original_file_array.size();
            EDLineEditor.files_have_saved = true;
            EDLineEditor.error_exist = false;
        }

        if(EDLineEditor.error_exist){
            System.out.println("?");
        }

        else {
            if (input_command.equals("W")) {
                EDLineEditor.files_have_saved = true;
                Files.getInstance().fileSave(original_file_array, EDLineEditor.fileName, true);  // true为在文本末尾追加
            } else if(input_command.substring(input_command.length()-1).equals("W")){
                for (int i = 1; i <= original_file_array.size(); i++) {
                    if (address_first_line <= i && i <= address_second_line) {
                        EDLineEditor.result.add(original_file_array.get(i - 1));
                    }
                }
                Files.getInstance().fileSave(EDLineEditor.result, EDLineEditor.fileName, true);
            }
            else {


                /*
                if (EDLineEditor.default_fileName_exisit) {
                    for (int i = 1; i <= original_file_array.size(); i++) {
                        if (address_first_line <= i && i <= address_second_line) {
                            EDLineEditor.result.add(original_file_array.get(i - 1));
                        }
                        Files.getInstance().fileSave(EDLineEditor.cache_arr, EDLineEditor.fileName, false);
                    }

                }
                */
                //else
                {
                    File file = new File(fileName);
                    if (file.exists()) {
                        for (int i = 1; i <= original_file_array.size(); i++) {
                            if (address_first_line <= i && i <= address_second_line) {
                                EDLineEditor.result.add(original_file_array.get(i - 1));
                            }
                        }
                        Files.getInstance().fileSave(EDLineEditor.result, fileName, true);
                    } else {
                        try {
                            file.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        for (int i = 1; i <= original_file_array.size(); i++) {
                            if (address_first_line <= i && i <= address_second_line) {
                                EDLineEditor.result.add(original_file_array.get(i - 1));
                            }
                            Files.getInstance().fileSave(EDLineEditor.result, fileName, true);
                        }
                    }
                }
            }
        }
    }
    @Override
    public void undo() {

    }
}
