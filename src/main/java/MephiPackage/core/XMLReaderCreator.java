package MephiPackage.core;

import MephiPackage.builders.MissionBuilderImpl;
import MephiPackage.readers.LegacyReaderAdapter;
import MephiPackage.readers.Reader;
import MephiPackage.readers.XMLReader;

public class XMLReaderCreator extends ReaderCreator{

    @Override
    public Reader createReader() {
        return new LegacyReaderAdapter(new XMLReader(), new MissionBuilderImpl());
    }
}
