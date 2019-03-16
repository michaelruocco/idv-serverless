package uk.co.mruoc.idv.jsonapi.verificationcontext;

import static org.apache.commons.lang3.StringUtils.deleteWhitespace;
import static uk.co.mruoc.file.ContentLoader.loadContentFromClasspath;

public class JsonLoader {

    public static String loadJson(final String path) {
        return deleteWhitespace(loadContentFromClasspath(path));
    }

}
