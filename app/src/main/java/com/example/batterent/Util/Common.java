package com.example.batterent.Util;

import android.widget.Button;

import com.example.batterent.Model.Order;
import com.example.batterent.Model.User;

import java.util.ArrayList;
import java.util.List;

public class Common {

    public static List<String> registederPhone = new ArrayList<>();
    public static List<String> registederUsername = new ArrayList<>();
    public static int batteryId;
    public static User currentUser;
    public static String battery_model;
    public static String distributrPhone;
    public static final long API_CONNECTION_TIMEOUT = 1201;
    public static final long API_READ_TIMEOUT = 901;
    public static Order myOrder;
    public static Order myOrder2;
    public static final String BASE_URL = "https://unattested-stem.000webhostapp.com/";   // https://www your domain .com/
    public static final String SERVER_main_folder = "buuzuu/"; // use "foldername/"  -- if www.yourdomain.com/foldername/app
    public static String distributrEmail;
    public static String distributrName;
//  use ""             -- if www.yourdomain.com/app

    public static double  batteryVAl [] = new double[]
            {

                    11.9,
                    11.904,
                    11.908,
                    11.912,
                    11.916,
                    11.92,
                    11.924,
                    11.928,
                    11.932,
                    11.936,
                    11.94,
                    11.944,
                    11.948,
                    11.952,
                    11.956,
                    11.96,

                    11.964,
                    11.968,
                    11.972,
                    11.976,
                    11.98,
                    11.984,
                    11.988,
                    11.992,
                    11.996,
                    12,
                    12.008,
                    12.016,
                    12.024,
                    12.032,
                    12.04,
                    12.048,
                    12.056,
                    12.064,
                    12.072,
                    12.08,
                    12.088,
                    12.096,
                    12.104,
                    12.112,
                    12.12,
                    12.128,
                    12.136,
                    12.144,
                    12.152,
                    12.16,
                    12.168,
                    12.176,
                    12.184,
                    12.192,
                    12.2,
                    12.208,
                    12.216,
                    12.224,
                    12.232,
                    12.24,
                    12.248,
                    12.256,
                    12.264,
                    12.272,
                    12.28,
                    12.288,
                    12.296,
                    12.304,
                    12.312,
                    12.32,
                    12.328,
                    12.336,
                    12.344,
                    12.352,
                    12.36,
                    12.368,
                    12.376,
                    12.384,
                    12.392,
                    12.4,
                    12.412,
                    12.424,
                    12.436,
                    12.448,
                    12.46,
                    12.472,
                    12.484,
                    12.496,
                    12.508,
                    12.52,
                    12.532,
                    12.544,
                    12.556,
                    12.568,
                    12.58,
                    12.592,
                    12.604,
                    12.616,
                    12.628,
                    12.64,
                    12.652,
                    12.664,
                    12.676,
                    12.688,
                    12.7

            };


    public static Order recievedOrder;
    public static String balance = "0";
    public static int amountSOH ;
}
