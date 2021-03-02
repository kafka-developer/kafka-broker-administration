//##
//Author: xargs-pratix:Prateek Shukla
//##

package com.xargspratix;

import java.io.IOException;
public class Run {
    public static void main (String[] args) throws IOException{
        if (args.length < 1) {
            throw new IllegalArgumentException("Must have either 'producer' or 'consumer' as argument");
        }
        switch (args[0]) {
            case "producer":
                Producer.main(args);
                break;
            case "consumer":
                Consumer.main(args);
                break;
            default:
                throw new IllegalArgumentException("No Clue what's going on " + args[0]);
        }
    }
}
