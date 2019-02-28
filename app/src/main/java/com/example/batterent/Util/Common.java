package com.example.batterent.Util;

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
}
