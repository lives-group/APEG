
package cryptix.jce.test;


import java.security.SecureRandom;


/*package*/ final class InsecureRandom extends SecureRandom {

    private final byte[] _stream;

    private int _streamOff;

    public InsecureRandom(byte[] stream) {
        _stream = stream;
        _streamOff = 0;
    }


    public void nextBytes(byte[] buf) {
        if(buf.length > (_stream.length - _streamOff))
            throw new Error("Out of fixed bytes!");

        System.arraycopy(_stream, _streamOff, buf, 0, buf.length);
        System.out.println("nextBytes yields: " + Util.toString(buf));
    }
}
