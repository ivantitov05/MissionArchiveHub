package MephiPackage.core;

import MephiPackage.builders.MissionBuilderImpl;
import MephiPackage.readers.Reader;
import MephiPackage.readers.StreamReader;

public class StreamReaderCreator extends ReaderCreator{
    @Override
    public Reader createReader() {
        return new StreamReader(new MissionBuilderImpl());
    }
}
