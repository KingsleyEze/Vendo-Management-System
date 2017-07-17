package org.ng.undp.vdms.utils;

import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * Created by macbook on 5/1/17.
 */
public final  class ShortUUID {


    /**
     * Generate short UUID (13 characters)
     *
     * @return short UUID
     */
    public static String shortUUID() {
        UUID uuid = UUID.randomUUID();
        long l = ByteBuffer.wrap(uuid.toString().getBytes()).getLong();
        return Long.toString(l, Character.MAX_RADIX);
    }

}
