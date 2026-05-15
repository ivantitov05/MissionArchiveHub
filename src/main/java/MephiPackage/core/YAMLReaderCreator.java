package MephiPackage.core;

import MephiPackage.builders.MissionBuilderImpl;
import MephiPackage.readers.Reader;
import MephiPackage.readers.YAMLReader;

public class YAMLReaderCreator extends ReaderCreator {

    @Override
    public Reader createReader() {
        return new YAMLReader();
    }
}
