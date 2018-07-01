package edLineEditor;

public class    Command_k extends Command implements CommandImp {
    @Override
    public void excute(String input_command) {

        String mark = input_command.substring(input_command.length()-1,input_command.length());
        int line = getFinalLine(input_command)-1;

        original_file_array = EDLineEditor.cache_arr;
        EDLineEditor.result = arr_initialize(EDLineEditor.result);


        EDLineEditor.map.put('\'' + mark,original_file_array.get(line-1));

    }

    @Override
    public void undo() {

    }
}
