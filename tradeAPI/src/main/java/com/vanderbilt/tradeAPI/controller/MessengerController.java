package com.vanderbilt.tradeAPI.controller;

import com.github.messenger4j.Messenger;
import com.github.messenger4j.exception.MessengerVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLOutput;

import static com.github.messenger4j.Messenger.*;

@RestController
@RequestMapping("/messenger")
public class MessengerController {

    private final Messenger messenger;

    @Autowired
    public MessengerController(final Messenger messenger){
        this.messenger = messenger;
    }

    @GetMapping
    public ResponseEntity<String> verifyWebhook(@RequestParam(MODE_REQUEST_PARAM_NAME) final String mode,
                                                @RequestParam(VERIFY_TOKEN_REQUEST_PARAM_NAME) final String verifyToken,
                                                @RequestParam(CHALLENGE_REQUEST_PARAM_NAME) final String challenge){
        System.out.println("= = = = = = = FACEBOOK STUFF  ==  = = ");
        System.out.println("RECEVED WEBHOOKS. MODE: " + mode + " verification tkn: " + verifyToken +
                " CHALLENGE: " + challenge + " \n ===============================");
        try{
            this.messenger.verifyWebhook(mode, verifyToken);
            System.out.println("MESSENGER IS CONFIGURED NICELY = = =  WOOOOW ");
            return ResponseEntity.ok(challenge);
        } catch (MessengerVerificationException e){
            System.out.println("EXCEPTION OCCURED ========= \n");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }
}
