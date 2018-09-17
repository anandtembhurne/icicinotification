/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icici.test;

/**
 *
 * @author uno
 */
public class MessageData extends Thread {

    private String data;

    public void data(String string) {
        this.data = string;
        run();
    }

    @Override
    public void run() {
        if (data != null) {

            data = data.replaceAll("'", "").trim();
            // System.out.println("user data="+data);
            String[] split = data.split(",");
            String gcmno = split[0];
            String appid = split[1].trim();
            String mobileno = split[3].trim();
//        long mobile = Long.parseLong(mobileno);

            String userid = split[4].trim();
            String message = split[5];
//        for (int i = 0; i < 10; i++) {
            System.out.println("message data");
            System.out.println("user id=" + userid + " gcm id=" + gcmno + " mobile=" + mobileno
                    + " message =" + message);

        }
    }

}
