package MephiPackage.core;

import MephiPackage.builders.MissionBuilderImpl;
import MephiPackage.readers.IniTXTReader;
import MephiPackage.readers.Reader;

public class IniTXTReaderCreator extends ReaderCreator{

    @Override
    public Reader createReader() {
        return new IniTXTReader();
    }
}
