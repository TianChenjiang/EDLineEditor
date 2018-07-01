package edLineEditor;
import java.util.*;

public class Command_i extends Command implements CommandImp {
    @Override
    public void excute(String input_command) {

        EDLineEditor.last_cache_arr = EDLineEditor.cache_arr;
        original_file_array = EDLineEditor.cache_arr;
        EDLineEditor.result = arr_initialize(EDLineEditor.result);

        address_line = Command_a.getFinalLine(input_command);
        if (input_command.equals("i")) address_line = EDLineEditor.currentLine;


        // 具体操作
        if(address_line<0||(address_line>original_file_array.size())&&original_file_array.size()!=0){
            System.out.println("?");
        }
        else {

            input_array = EDLineEditor.change_array(Input.getInstance().inputMode());
            EDLineEditor.currentLine = address_line + input_array.size() - 1;
        if (original_file_array.size() == 0) {
            EDLineEditor.result.addAll(input_array);
            EDLineEditor.result.add("\n");
            EDLineEditor.cache_arr = EDLineEditor.result;

        } else {


            for (int i = 1; i <= original_file_array.size(); i++) {
                if (i < address_line) {
                    EDLineEditor.result.add(original_file_array.get(i - 1));
                }
                if (i == address_line) {
                    EDLineEditor.result.addAll(input_array);
                    EDLineEditor.result.add("\n");
                    EDLineEditor.result.add(original_file_array.get(i - 1));

                }
                if (i > address_line) {
                    EDLineEditor.result.add(original_file_array.get(i - 1));
                }
            }
            EDLineEditor.cache_arr = EDLineEditor.result;
            EDLineEditor.files_have_saved = false;
            //Files.getInstance().fileSave(result);
        }
    }
    }
    @Override
    public void undo() {

    }
}
