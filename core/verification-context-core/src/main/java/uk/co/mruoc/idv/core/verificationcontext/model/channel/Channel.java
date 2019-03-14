package uk.co.mruoc.idv.core.verificationcontext.model.channel;

public interface Channel {

    String getId();

    class Ids {

        public static final String AS3 = "AS3";
        public static final String RSA = "RSA";
        public static final String BBOS = "BBOS";

        private Ids() {
            // utility class
        }

    }

}
