/**
 * DLog.java
 *
 * Copyright (c) 2015, John Pushnik, All rights reserved.
 */
package com.armadillo.common;

import android.util.Log;

/**
 * @author john.pushnik
 * @since Jul 5, 2012
 *
 */
public class DLog {
	public static void d() {
		String msg =  formatMsg("");
		if (Constants.SHOW_DEBUG) {
			Log.d(getClassName(), msg);
		}
	}
	public static void d(String tag, String msg) {
            msg =  formatMsg(msg);
            if (Constants.SHOW_DEBUG) {
                Log.d(tag, msg);
            }
	}
	public static void d(String tag, String msg, Throwable tr) {
            msg = formatMsg(msg);
            if (Constants.SHOW_DEBUG) {
                Log.d(tag, msg, tr);
            }
	}

	public static void i() {
		String msg =  formatMsg("");
		if (Constants.SHOW_INFO) {
			Log.d(getClassName(), msg);
		}
	}
	public static void i(String tag, String msg) {
            msg = formatMsg(msg);
            if (Constants.SHOW_INFO) {
                Log.i(tag, msg);
            }
	}
	public static void i(String tag, String msg, Throwable tr) {
            msg = formatMsg(msg);
            if (Constants.SHOW_INFO) {
                Log.i(tag, msg, tr);
            }
	}

	public static void e() {
		String msg =  formatMsg("");
		if (Constants.SHOW_ERROR) {
			Log.d(getClassName(), msg);
		}
	}
	public static void e(String tag, String msg) {
            msg = formatMsg(msg);
            if (Constants.SHOW_ERROR) {
                Log.e(tag, msg);
            }
	}
	public static void e(String tag, String msg, Throwable tr) {
            msg = formatMsg(msg);
            if (Constants.SHOW_ERROR) {
                Log.e(tag, msg, tr);
            }
	}

    public static void v() {
        String msg =  formatMsg("");
        if (Constants.SHOW_VERBOSE) {
            Log.d(getClassName(), msg);
        }
    }
    public static void v(String tag, String msg) {
        msg = formatMsg(msg);
        if (Constants.SHOW_VERBOSE) {
            Log.v(tag, msg);
        }
    }
	public static void v(String tag, String msg, Throwable tr) {
            msg = formatMsg(msg);
            if (Constants.SHOW_VERBOSE) {
                Log.v(tag, msg, tr);
            }
	}

	public static void w() {
		String msg =  formatMsg("");
		if (Constants.SHOW_WARN) {
			Log.d(getClassName(), msg);
		}
	}
	public static void w(String tag, String msg) {
            msg = formatMsg(msg);
            if (Constants.SHOW_WARN) {
                Log.w(tag, msg);
            }
	}
	public static void w(String tag, String msg, Throwable tr) {
            msg = formatMsg(msg);
            if (Constants.SHOW_WARN) {
                Log.w(tag, msg, tr);
            }
	}

	public static void wtf() {
		String msg =  formatMsg("");
		if (Constants.SHOW_WARN) {
			Log.d(getClassName(), msg);
		}
	}
	public static void wtf(String tag, String msg) {
            msg = formatMsg(msg);
            if (Constants.SHOW_WARN) {
                Log.wtf(tag, msg);
            }
	}
	public static void wtf(String tag, String msg, Throwable tr) {
            msg = formatMsg(msg);
            if (Constants.SHOW_WARN) {
                Log.wtf(tag, msg, tr);
            }
	}

	public static String getClassName() {
        String fullClassName = Thread.currentThread().getStackTrace()[4].getClassName();
        return fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
	}

    public static String formatMsg(String message)
    {
        String methodName = Thread.currentThread().getStackTrace()[4].getMethodName();
        int lineNumber = Thread.currentThread().getStackTrace()[4].getLineNumber();


        return methodName + "():" + lineNumber + " " + message;
    }
}
