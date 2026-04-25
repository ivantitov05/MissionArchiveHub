package MephiPackage.utils;

import MephiPackage.utils.TypeHandler;
import java.io.File;

public class TXTHandler extends TypeHandler {

    @Override
    protected MephiPackage.utils.FileFormat check(File file) {
        return MephiPackage.utils.FileFormat.TXT;
    }
}