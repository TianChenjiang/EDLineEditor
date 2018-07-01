package edLineEditor;

// commandImp接口 有excute待实现方法 实现对每个命令的操作
public interface CommandImp {
    public void excute(String input_command);

    // 最初是为撤销操作设计的undo方法
    public void undo();
}
