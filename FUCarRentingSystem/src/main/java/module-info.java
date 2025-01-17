module com.lcaohoanq.fucar {
    requires javafx.controls;
    requires java.naming;  // Java Naming and Directory Interface (JNDI) API. (if hibernate say
    // error about javax reference)
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires javafx.graphics;
    requires java.sql;
    requires mysql.connector.j;
    requires java.desktop;
    requires io.github.cdimascio.dotenv.java;
    requires static lombok;
    requires org.slf4j;
    requires com.fasterxml.jackson.databind;
    requires java.net.http;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires jakarta.persistence;
    requires thymeleaf;
    requires javax.mail.api;
    requires swingx;

    opens com.lcaohoanq.fucar to javafx.fxml;
    exports com.lcaohoanq.fucar;

    opens com.lcaohoanq.fucar.models to org.hibernate.orm.core;
    exports  com.lcaohoanq.fucar.models;
    opens com.lcaohoanq.fucar.services to org.hibernate.orm.core;
    exports com.lcaohoanq.fucar.services;

    opens com.lcaohoanq.fucar.views to javafx.graphics;
    exports com.lcaohoanq.fucar.views;

    opens com.lcaohoanq.fucar.layouts to javafx.fxml;
    exports com.lcaohoanq.fucar.layouts;

    opens com.lcaohoanq.fucar.dtos to com.fasterxml.jackson.databind;
    exports com.lcaohoanq.fucar.dtos;

    opens com.lcaohoanq.fucar.repositories to org.hibernate.orm.core;
    exports com.lcaohoanq.fucar.repositories;

    opens com.lcaohoanq.fucar.daos to org.hibernate.orm.core;
    exports com.lcaohoanq.fucar.daos;

    opens com.lcaohoanq.fucar.enums to org.hibernate.orm.core;
    exports com.lcaohoanq.fucar.enums;
    opens com.lcaohoanq.fucar.utils to org.hibernate.orm.core;
    exports com.lcaohoanq.fucar.utils;


    opens com.lcaohoanq.fucar.views.base to javafx.fxml;
    exports com.lcaohoanq.fucar.controllers;
    opens com.lcaohoanq.fucar.controllers to javafx.fxml;
    exports com.lcaohoanq.fucar.views.utils;
    opens com.lcaohoanq.fucar.views.utils to javafx.fxml;
    opens com.lcaohoanq.fucar.views.game.board to javafx.fxml;
    exports com.lcaohoanq.fucar.views.changepassword;
    opens com.lcaohoanq.fucar.views.changepassword to javafx.fxml;
    exports com.lcaohoanq.fucar.views.menu;
    opens com.lcaohoanq.fucar.views.menu to javafx.fxml;
    exports com.lcaohoanq.fucar.views.info;
    opens com.lcaohoanq.fucar.views.info to javafx.fxml;
    exports com.lcaohoanq.fucar.views.otpverification;
    opens com.lcaohoanq.fucar.views.otpverification to javafx.fxml;
    opens com.lcaohoanq.fucar.views.score to javafx.fxml;
    exports com.lcaohoanq.fucar.views.score;
//    opens com.lcaohoanq.fucar to org.testfx;
}