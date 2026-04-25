package MephiPackage.core;

import MephiPackage.builders.MissionBuilderImpl;
import MephiPackage.readers.JSONReader;
import MephiPackage.readers.LegacyReaderAdapter;
import MephiPackage.readers.Reader;

public class JSONReaderCreator extends ReaderCreator{

    @Override
    public Reader createReader() {
        return new LegacyReaderAdapter(new JSONReader(), new MissionBuilderImpl());
    }
}
