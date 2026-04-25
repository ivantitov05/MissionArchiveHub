package MephiPackage.core;

import MephiPackage.builders.MissionBuilderImpl;
import MephiPackage.readers.LegacyReaderAdapter;
import MephiPackage.readers.Reader;
import MephiPackage.readers.TXTReader;

public class TXTReaderCreator extends ReaderCreator{

    @Override
    public Reader createReader() {
        return new LegacyReaderAdapter(new TXTReader(), new MissionBuilderImpl());
    }
}
