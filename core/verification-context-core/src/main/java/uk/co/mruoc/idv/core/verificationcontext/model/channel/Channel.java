package uk.co.mruoc.idv.core.verificationcontext.model.channel;

public interface Channel {

    String getId();

    class Ids {

        public static final String AS3 = "AS3";
        public static final String RSA = "RSA";

        private Ids() {
            // utility class
        }

    }

}
