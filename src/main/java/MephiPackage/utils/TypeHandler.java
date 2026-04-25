package MephiPackage.utils;

import MephiPackage.utils.FileFormat;
import java.io.File;

public abstract class TypeHandler {

    protected TypeHandler next;

    public TypeHandler setNext(TypeHandler next) {
        this.next = next;
        return next;
    }

    public FileFormat handle(File file) {
        FileFormat result = check(file);  // ← теперь FileFormat
        if (result != null) {
            return result;
        }
        if (next != null) {
            return next.handle(file);
        }
        return null;
    }

    protected abstract FileFormat check(File file);
}