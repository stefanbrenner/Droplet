/*******************************************************************************
 * Project: Droplet - Toolkit for Liquid Art Photographers
 * Copyright (C) 2012 Stefan Brenner
 *
 * This file is part of Droplet.
 *
 * Droplet is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Droplet is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Droplet. If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/

#include "logging.h"
#include "protocol.h"
#include "droplet.h"

#define BOUD_RATE         9600
#define MAX_INPUT_SIZE      50 // how many memory can we use one the different arduino devices?


// device mapping for crazyMachine
// char deviceMapping[DEVICE_NUMBERS] = { 3, 2, 11, A2, A1, A3, A4, A5, 13, 12 };

// /*
char deviceMapping[DEVICE_NUMBERS] = {  0,    1,   2,   3,   4,   5,   6,   7,   8,   9,
                                       10,   11,  12,  13,  14,  15,  16,  17,  18,  19,
                                       20,   21,  22,  23,  24,  25,  26,  27,  28,  29,
                                       30,   31,  32,  33,  34,  35,  36,  37,  38,  39,
                                       40,   41,  42,  43,  44,  45,  46,  47,  48,  49,
                                       // analog pins start at 50
                                       A0,   A1,  A2,  A3,  A4,  A5,  A6,  A7 };
                                       // higher analog pins on arduino mega
                                       // A8, A9, A10, A11, A12, A13, A14, A15 };
// */


/*
 * setup stuff befor we start our main loop
 */
void setup() {
  // start serial communication
  Serial.begin(BOUD_RATE);
 
  // setup pin modes
  for(int i = 0; i < DEVICE_NUMBERS; i++) {
    pinMode(deviceMapping[i], OUTPUT);
    // manually set pin to LOW otherwise it is 
    // by default set to HIGH on some boards (i.e. Uno)
    digitalWrite(deviceMapping[i], LOW);
  } 
  
  droplet.startButton = UNDEFINED_PIN;
  
}

int buttonState;             // the current reading from the input pin 
int lastButtonState = LOW;   // the previous reading from the input pin

// the following variables are long's because the time, measured in miliseconds,
// will quickly become a bigger number than can be stored in an int.
long lastDebounceTime = 0;  // the last time the output pin was toggled
long debounceDelay = 50;    // the debounce time; increase if the output flickers 
 
/*
 * entry point
 */
void loop() {
  
  if(droplet.startButton == UNDEFINED_PIN) {
    return;
  }
  
  // read the state of the switch into a local variable:
  int reading = digitalRead(droplet.startButton);

  // check to see if you just pressed the button 
  // (i.e. the input went from LOW to HIGH),  and you've waited 
  // long enough since the last press to ignore any noise:  

  // If the switch changed, due to noise or pressing:
  if (reading != lastButtonState) {
    // reset the debouncing timer
    lastDebounceTime = millis();
  } 
  
  if ((millis() - lastDebounceTime) > debounceDelay) {
    // whatever the reading is at, it's been there for longer
    // than the debounce delay, so take it as the actual current state:

    // if the button state has changed:
    if (reading != buttonState) {
      buttonState = reading;
      
      if(buttonState == HIGH) {
        logging(INFO, "Started execution due to start button event");
        executeActions();
      }
    }
  }

  // save the reading.  Next time through the loop,
  // it'll be the lastButtonState:
  lastButtonState = reading;
  
}



char input[MAX_INPUT_SIZE];
int count = 0;

void serialEvent() {
  while (Serial.available()) {
    
    // get the new byte
    char inChar = (char) Serial.read(); 
    
    //Serial.println("New char: " + String(inChar));
    
    // TODO brenner: consume input byte by byte
    
    if (inChar == '\n') {
      
      input[count] = '\0';
      
      logging(DEBUG, input);
      
      // TODO process byte by byte to prevent serial buffer overflow
      
      processCommand(input);
      
      count = 0;
      
      // TODO send received message next can come
      
    } else {
      
      // add it to the inputString:
      input[count] = inChar;
      count++;
      
    }
     
  }
}



