package edLineEditor;

import java.io.File;
import java.io.IOException;

public class Command_w extends Command implements CommandImp {
    @Override
    public void excute(String input_command) {

        /*
        保存指定行到指定文件，当前行不变。保存后不需要输出或打印任何内容。
        如果指定文件存在，则保存的内容覆盖文件内容。如果文件不存在，则创建文件并保存内容。
        如果参数file不指定，则使用默认文件名代替参数file。当没有默认文件名时，参数file必须指定，否则打印'?'
        提示。注意，w的file参数不会更改默认文件名。
         */

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

            if(input_command.charAt(0) == 'w'){
                address_first_line = 1;
                address_second_line = original_file_array.size();
                EDLineEditor.files_have_saved = true;
                EDLineEditor.error_exist = false;
            }

            if(EDLineEditor.error_exist){
                System.out.println("?");
            }

            else {
            if (input_command.equals("w")) {
                if(!EDLineEditor.default_fileName_exisit) System.out.println("?");
                else {
                    EDLineEditor.files_have_saved = true;
                    Files.getInstance().fileSave(original_file_array, EDLineEditor.fileName, false);
                }
            }
            else if(input_command.endsWith("w")){
                if(!EDLineEditor.default_fileName_exisit) System.out.println("?");
                else {
                    for (int i = 1; i <= original_file_array.size(); i++) {
                        if (address_first_line <= i && i <= address_second_line) {
                            EDLineEditor.result.add(original_file_array.get(i - 1));
                        }
                    }
                }
                Files.getInstance().fileSave(EDLineEditor.result, EDLineEditor.fileName, false);
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
                        Files.getInstance().fileSave(EDLineEditor.result, fileName, false);
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
                            Files.getInstance().fileSave(EDLineEditor.result, fileName, false);
                        }
                    }
                }
            }
        }
    }
        @Override
        public void undo () {

        }
}