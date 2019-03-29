package uk.co.mruoc.idv.json;

public interface JsonConverter {

    String toJson(final Object object);

    <T> T toObject(final String json, final Class<T> type);

    class JsonConversionException extends RuntimeException {

        public JsonConversionException(final Throwable cause) {
            super(cause);
        }

    }

}
