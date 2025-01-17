package com.lcaohoanq.fucar.views.info;

import com.lcaohoanq.fucar.views.utils.AppAlert;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InfoView implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        AppAlert.IS_ABOUT_ME();
    }

}
