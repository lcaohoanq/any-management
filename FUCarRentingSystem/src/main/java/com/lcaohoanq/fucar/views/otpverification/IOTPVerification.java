package com.lcaohoanq.fucar.views.otpverification;

import java.io.IOException;

public interface IOTPVerification {

    void onOtpVerified();

    void onResendOtp() throws IOException, InterruptedException;

    void onBlockUser() throws IOException, InterruptedException;
}
