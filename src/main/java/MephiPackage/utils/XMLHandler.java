package MephiPackage.utils;

import java.io.File;
import java.io.IOException;

public class XMLHandler extends TypeHandler{
        @Override
        protected MephiPackage.utils.FileFormat check(File file) {
            try {
                new com.fasterxml.jackson.dataformat.xml.XmlMapper().readTree(file);
                return MephiPackage.utils.FileFormat.XML;
            } catch (IOException e) {
                return null;
            }
        }
}
