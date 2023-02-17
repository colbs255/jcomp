package org.colbs.jcomp;

import javax.tools.SimpleJavaFileObject;
import java.net.URI;
class StringSource extends SimpleJavaFileObject {
    private final String source;

    public StringSource(String name, String source) {
        super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension),
                Kind.SOURCE);
        this.source = source;
    }
    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return source;
    }
}
