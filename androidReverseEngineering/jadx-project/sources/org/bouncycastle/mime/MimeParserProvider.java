package org.bouncycastle.mime;

import java.io.InputStream;

/* loaded from: classes.dex */
public interface MimeParserProvider {
    MimeParser createParser(InputStream inputStream);

    MimeParser createParser(Headers headers, InputStream inputStream);
}
