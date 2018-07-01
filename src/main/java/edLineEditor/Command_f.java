package edLineEditor;

import java.io.File;

public class Command_f extends Command implements CommandImp {
    @Override
    public void excute(String input_command) {

        original_file_array = EDLineEditor.cache_arr;
        EDLineEditor.result = arr_initialize(EDLineEditor.result);

            if (input_command.equals("f")) {
                if (EDLineEditor.default_fileName_exisit) {
                    System.out.println(EDLineEditor.fileName);
                } else {
                    System.out.println("?");
                }
            }

            // 设置默认文件名
            else {
                if(input_command.equals("$f")) System.out.println("?");
                else {
                String default_file_name = input_command.substring(2, input_command.length());
                if (!EDLineEditor.default_fileName_exisit) {
                    EDLineEditor.default_fileName = default_file_name;
                } else {
                   // Files.getInstance().renameFile(EDLineEditor.fileName, default_file_name);
                    EDLineEditor.fileName = default_file_name;
                }
                }
            }
        }
    @Override
    public void undo(){
    }
}
