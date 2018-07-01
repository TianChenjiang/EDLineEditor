package edLineEditor;

public class Command_z extends Command implements CommandImp {
    @Override
    public void excute(String input_command) {

        int n = 0;
        original_file_array = EDLineEditor.cache_arr;
        EDLineEditor.result = arr_initialize(EDLineEditor.result);

            String address = getAddress(input_command);
            int address_line2 = 0;

            address_line = getFinalLine(address);

            char singleCommand = input_command.charAt(input_command.length() - 1);


            if (input_command.charAt(0) == 'z') {
                address_line = EDLineEditor.currentLine + 1;
            }

            if ((singleCommand >= 'A' && singleCommand <= 'Z' || singleCommand >= 'a' && singleCommand <= 'z')) {
                address_line2 = original_file_array.size();
            } else {
                n = input_command.charAt(input_command.length() - 1) - '0';
                address_line2 = n + address_line;
            }


            if (address_line2 > original_file_array.size()) {
                EDLineEditor.currentLine = original_file_array.size();
            }

            if (address_line < 0) {
                System.out.println("?");
            } else {
                for (int i = 1; i <= original_file_array.size(); i++) {
                    if (address_line <= i && i <= address_line2) {
                        System.out.print(original_file_array.get(i - 1));
                    }
                }
            }

            EDLineEditor.currentLine = address_line2;
        }
    @Override
    public void undo() {

    }
}
