package converters;

import models.old.Plist;

public interface Converter<T> {
    T convert(Plist plist);
    String getPath();
    String getInitialFolder();
    String getDestinationFolder();
}