package com.example.ccmyphone.OtherClasses;

public class ConverterClass {

    static double result = 0;

    public static double ConvertLength(String from, String to, double input) {

        if (from.equalsIgnoreCase("MM") && to.equalsIgnoreCase("MM")) {
            result = input;
        } else if (from.equalsIgnoreCase("MM") && to.equalsIgnoreCase("CM")) {
            result = input / 10;
        } else if (from.equalsIgnoreCase("MM") && to.equalsIgnoreCase("ME")) {
            result = input / 1000;
        } else if (from.equalsIgnoreCase("MM") && to.equalsIgnoreCase("KM")) {
            result = input / 1000000;
        } else if (from.equalsIgnoreCase("MM") && to.equalsIgnoreCase("MILE")) {
            result = input * 0.000000621371;
        } else if (from.equalsIgnoreCase("MM") && to.equalsIgnoreCase("FEET")) {
            result = input / 304.8;
        } else if (from.equalsIgnoreCase("MM") && to.equalsIgnoreCase("INCH")) {
            result = input * 0.0393701;
        } else if (from.equalsIgnoreCase("MM") && to.equalsIgnoreCase("YARD")) {
            result = input * 0.00109361;
        } else if (from.equalsIgnoreCase("CM") && to.equalsIgnoreCase("CM")) {
            result = input;
        } else if (from.equalsIgnoreCase("CM") && to.equalsIgnoreCase("MM")) {
            result = input * 10;
        } else if (from.equalsIgnoreCase("CM") && to.equalsIgnoreCase("ME")) {
            result = input / 100;
        } else if (from.equalsIgnoreCase("CM") && to.equalsIgnoreCase("KM")) {
            result = input / 100000;
        } else if (from.equalsIgnoreCase("CM") && to.equalsIgnoreCase("MILE")) {
            result = input / 0.00000621371;
        } else if (from.equalsIgnoreCase("CM") && to.equalsIgnoreCase("FEET")) {
            result = input / 30.48;
        } else if (from.equalsIgnoreCase("CM") && to.equalsIgnoreCase("INCH")) {
            result = input / 0.393701;
        } else if (from.equalsIgnoreCase("CM") && to.equalsIgnoreCase("YARD")) {
            result = input / 0.0109361;
        } else if (from.equalsIgnoreCase("ME") && to.equalsIgnoreCase("ME")) {
            result = input;
        } else if (from.equalsIgnoreCase("ME") && to.equalsIgnoreCase("MM")) {
            result = input * 1000;
        } else if (from.equalsIgnoreCase("ME") && to.equalsIgnoreCase("CM")) {
            result = input * 100;
        } else if (from.equalsIgnoreCase("ME") && to.equalsIgnoreCase("KM")) {
            result = input / 1000;
        } else if (from.equalsIgnoreCase("ME") && to.equalsIgnoreCase("MILE")) {
            result = input / 0.000621371;
        } else if (from.equalsIgnoreCase("ME") && to.equalsIgnoreCase("FEET")) {
            result = input / 0.3048;
        } else if (from.equalsIgnoreCase("ME") && to.equalsIgnoreCase("INCH")) {
            result = input * 39.3701;
        } else if (from.equalsIgnoreCase("ME") && to.equalsIgnoreCase("YARD")) {
            result = input * 1.09361;
        } else if (from.equalsIgnoreCase("KM") && to.equalsIgnoreCase("KM")) {
            result = input;
        } else if (from.equalsIgnoreCase("KM") && to.equalsIgnoreCase("MM")) {
            result = input * 1000000;
        } else if (from.equalsIgnoreCase("KM") && to.equalsIgnoreCase("CM")) {
            result = input * 100000;
        } else if (from.equalsIgnoreCase("KM") && to.equalsIgnoreCase("ME")) {
            result = input * 1000;
        } else if (from.equalsIgnoreCase("KM") && to.equalsIgnoreCase("MILE")) {
            result = input / 0.621371;
        } else if (from.equalsIgnoreCase("KM") && to.equalsIgnoreCase("FEET")) {
            result = input * 3280.84;
        } else if (from.equalsIgnoreCase("KM") && to.equalsIgnoreCase("INCH")) {
            result = input * 39370.1;
        } else if (from.equalsIgnoreCase("KM") && to.equalsIgnoreCase("YARD")) {
            result = input * 1093.61;
        } else if (from.equalsIgnoreCase("MILE") && to.equalsIgnoreCase("MILE")) {
            result = input;
        } else if (from.equalsIgnoreCase("MILE") && to.equalsIgnoreCase("MM")) {
            result = input * 0.000000621371;
        } else if (from.equalsIgnoreCase("MILE") && to.equalsIgnoreCase("CM")) {
            result = input * 0.00000621371;
        } else if (from.equalsIgnoreCase("MILE") && to.equalsIgnoreCase("ME")) {
            result = input * 0.000621371;
        } else if (from.equalsIgnoreCase("MILE") && to.equalsIgnoreCase("KM")) {
            result = input * 0.621371;
        } else if (from.equalsIgnoreCase("MILE") && to.equalsIgnoreCase("FEET")) {
            result = input * 5280;
        } else if (from.equalsIgnoreCase("MILE") && to.equalsIgnoreCase("INCH")) {
            result = input * 63360;
        } else if (from.equalsIgnoreCase("MILE") && to.equalsIgnoreCase("YARD")) {
            result = input * 1760;
        } else if (from.equalsIgnoreCase("FEET") && to.equalsIgnoreCase("FEET")) {
            result = input;
        } else if (from.equalsIgnoreCase("FEET") && to.equalsIgnoreCase("MM")) {
            result = input * 304.8;
        } else if (from.equalsIgnoreCase("FEET") && to.equalsIgnoreCase("CM")) {
            result = input * 30.48;
        } else if (from.equalsIgnoreCase("FEET") && to.equalsIgnoreCase("ME")) {
            result = input * 0.3048;
        } else if (from.equalsIgnoreCase("FEET") && to.equalsIgnoreCase("KM")) {
            result = input * 0.0003048;
        } else if (from.equalsIgnoreCase("FEET") && to.equalsIgnoreCase("MILE")) {
            result = input * 5280;
        } else if (from.equalsIgnoreCase("FEET") && to.equalsIgnoreCase("INCH")) {
            result = input / 0.0833333;
        } else if (from.equalsIgnoreCase("FEET") && to.equalsIgnoreCase("YARD")) {
            result = input / 0.333333;
        }else if (from.equalsIgnoreCase("YARD") && to.equalsIgnoreCase("YARD")) {
            result = input;
        } else if (from.equalsIgnoreCase("YARD") && to.equalsIgnoreCase("MM")) {
            result = input * 0.00109361;
        } else if (from.equalsIgnoreCase("YARD") && to.equalsIgnoreCase("CM")) {
            result = input * 0.0109361;
        } else if (from.equalsIgnoreCase("YARD") && to.equalsIgnoreCase("ME")) {
            result = input / 1.09361;
        } else if (from.equalsIgnoreCase("YARD") && to.equalsIgnoreCase("KM")) {
            result = input / 1093.61;
        } else if (from.equalsIgnoreCase("YARD") && to.equalsIgnoreCase("MILE")) {
            result = input / 1760;
        } else if (from.equalsIgnoreCase("YARD") && to.equalsIgnoreCase("INCH")) {
            result = input * 12;
        } else if (from.equalsIgnoreCase("YARD") && to.equalsIgnoreCase("FEET")) {
            result = input * 0.333333;
        }else if (from.equalsIgnoreCase("INCH") && to.equalsIgnoreCase("INCH")) {
            result = input;
        } else if (from.equalsIgnoreCase("INCH") && to.equalsIgnoreCase("MM")) {
            result = input * 0.0393701;
        } else if (from.equalsIgnoreCase("INCH") && to.equalsIgnoreCase("CM")) {
            result = input * 0.393701;
        } else if (from.equalsIgnoreCase("INCH") && to.equalsIgnoreCase("ME")) {
            result = input / 39.3701;
        } else if (from.equalsIgnoreCase("INCH") && to.equalsIgnoreCase("KM")) {
            result = input / 39370.1;
        } else if (from.equalsIgnoreCase("INCH") && to.equalsIgnoreCase("MILE")) {
            result = input / 63360;
        } else if (from.equalsIgnoreCase("INCH") && to.equalsIgnoreCase("YARD")) {
            result = input / 12;
        } else if (from.equalsIgnoreCase("INCH") && to.equalsIgnoreCase("FEET")) {
            result = input * 0.0833333;
        }
        return result;
    }


}
