package edLineEditor;

import java.util.ArrayList;

public class Command_print_line_num extends Command implements CommandImp {
    @Override
    public void excute(String input_command) {

            String address = getSimpleAddress(input_command);

            int line = getFinalLine(address);

            System.out.println(line);

        }
    @Override
    public void undo() {

    }

}
