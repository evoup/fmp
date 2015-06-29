package com.madhouse.fmp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class ATest {
	
	
	public static void main(String[] args) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.CHINA);
		System.out.println(format.format(new Date(1440320854162L)));
	}



}
